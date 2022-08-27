package test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.RegistrationAndDeletionDao;
import model.TestDBConnection;
import model.UserInfoDto;
import model.UserPictureDao;

public class DummyUserCreatingWithDeletion {

	private static final String NEW_LINE = System.getProperty("line.separator");

	private static final int    CANCEL_NUM    = 100;
	private static final int    ROOP_NUM_MAX  = 61;
	//登録するデータの一括設定用定数
	private static final int    ROOP_NUM       = 61;         // 設定から-1した値がID総数
	private static final String ID_HEAD        = "at%03d";   // ID文字数、数値は必ずID桁数に合わせる
	private static final String TEST_NAME      = "unknown"; // 登録名
	private static final String TEST_PASS_WORD = "b7r8492"; // 登録パスワード

	private static final String PICTURES_DIR_PATH_STRING =
			"C:\\pleiades\\2022-06\\workspace\\portfolio-submit-pics\\WebContent\\pictures";

	private static final String PICTURE_FOR_COPY = "photo0000-6691.jpg";

	public static void main(String[] args) {

		int testNum = Integer.parseInt(args[0]); // 0か1の番号で実行処理を決定

		List<UserInfoDto> testInsertList = new ArrayList<UserInfoDto>(ROOP_NUM - 1);

		if(testNum == 1) {
			testInsertList = createInsertList(testInsertList);

			if(!executableInsert()) {
				testNum = CANCEL_NUM;
			}
		}

		RegistrationAndDeletionDao dao = new RegistrationAndDeletionDao(
				TestDBConnection.generateConnection());

		boolean isSuccess = false;
		String   message   = "";

		long start = System.currentTimeMillis();

		switch(testNum) {

			case 0: //テーブルレコード一括削除及び全ユーザーディレクトリの削除
				isSuccess = dao.truncateUser();

				if(isSuccess) {

					Path clearDir = Paths.get(PICTURES_DIR_PATH_STRING);
					clearUserDirectory(clearDir);
				}

				message = String.format("一括削除結果：%b", isSuccess);
				break;

			case 1: //レコード追加及びユーザーディレクトリの作成、テスト画像の登録
				isSuccess = dao.insertInBulkUser(testInsertList);

				if(isSuccess) {

					for(UserInfoDto dto : testInsertList) {

						createUserDirectory(dto.getUserId());
						pictureFileCopy(dto.getUserId());
					}
				}

				message = String.format("追加結果：%b", isSuccess);
				break;

			case 100: //レコード追加の中止
				message = "レコード追加をキャンセルしました" + NEW_LINE
						+ "ROOP_NUMの設定が大きすぎる、もしくはID数に不整合がある可能性があります";
				break;

			default:
				message = "不正な値です";
				break;
		}

		long end = System.currentTimeMillis();

		long elapsedTime = end - start;

		System.out.println(message);
		System.out.println(String.format("%dミリ秒", elapsedTime));
	}

	//ダミーユーザーの登録内容を作成するメソッド
	private static List<UserInfoDto> createInsertList(List<UserInfoDto> insertList) {

		Timestamp testTime = new Timestamp(System.currentTimeMillis());

		for(int i = 1 ; i < ROOP_NUM ; i++ ) {

			UserInfoDto dto = new UserInfoDto();
			dto.setUserId(   String.format(ID_HEAD, i) );
			dto.setUserName( TEST_NAME                 );
			dto.setPassWord( TEST_PASS_WORD            );
			dto.setTime(     testTime                   );
			insertList.add(dto);
		}

		return insertList;
	}

	//レコード追加をしてよいかの判定をするメソッド
	private static boolean executableInsert() {

		RegistrationAndDeletionDao dao = new RegistrationAndDeletionDao(
				TestDBConnection.generateConnection());

		int[] idCountArray          = dao.selectCountId();
		int   memberTableIdCount   = idCountArray[0];
		int   picturesTableIdCount = idCountArray[1];

		boolean isExecutable = true;

		if(ROOP_NUM > ROOP_NUM_MAX
				|| memberTableIdCount > 0
				|| memberTableIdCount != picturesTableIdCount) {

			isExecutable = false;
		}

		return isExecutable;
	}

	//全てのユーザーディレクトリを削除するメソッド
	private static void clearUserDirectory(Path dirPath) {

	    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirPath)) {

	    	Path protectDir            = Paths.get(PICTURES_DIR_PATH_STRING);
	    	int  protectFileHierarchy = protectDir.getNameCount() + 1;

	    	for(Path file : dirStream) {

	    		if(Files.isDirectory(file)) {
	    			clearUserDirectory(file);

	    		} else {

	    			int clearFileHierarchy = file.getNameCount();

	    			if(clearFileHierarchy != protectFileHierarchy) {
	    				Files.deleteIfExists(file);
	    			}
	    		}
	    	}

	    	if(!dirPath.equals(protectDir)) {
	    		Files.deleteIfExists(dirPath);
	    	}

	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	}

	//ダミーユーザーのディレクトリを作成するメソッド
	private static void createUserDirectory(String userId) {

	    try {
	        Path dir = Paths.get(PICTURES_DIR_PATH_STRING, userId);
	        Files.createDirectory(dir);

	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	}

	//テスト用画像をダミーユーザーのディレクトリにコピーするメソッド
	private static void pictureFileCopy(String userId) {

		try {
			Path srcFile  = Paths.get(PICTURES_DIR_PATH_STRING, PICTURE_FOR_COPY);
			Path copyFile = Paths.get(PICTURES_DIR_PATH_STRING, userId, PICTURE_FOR_COPY);

			if(Files.exists(srcFile)) {

				Files.copy(srcFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
				registrationPicuture(userId, copyFile);
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	//テスト用画像をDBに登録するメソッド
	private static void registrationPicuture(String userId, Path file) {

		List<String> upPictureList = List.of(file.toString());

		UserPictureDao dao = new UserPictureDao(TestDBConnection.generateConnection());
		dao.updateUserPicture(userId, upPictureList);
	}
}
