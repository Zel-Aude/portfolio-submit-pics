package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ShowPictureBusinessLogic {

	private enum DefaultPictures {

		PICTURE_1("pictures/フロスト.jpg"),
		PICTURE_2("pictures/空 02.jpg"),
		PICTURE_3("pictures/木星.jpg");

		private final String pictureName;

		private DefaultPictures(String pictureName) {
			this.pictureName = pictureName;
		}

		@Override
		public String toString() {
			return this.pictureName;
		}
	}

	public ShowPictureBusinessLogic() {}

	//ユーザーが登録している画像データの取得をDAOに依頼するメソッド
	public List<String> executeShowPicture(String userId) {

		UserPictureDao dao = new UserPictureDao(TestDBConnection.generateConnection());

		List<String> extractPictureList = dao.selectUserPicture(userId);
		extractPictureList = preparePicture(extractPictureList, userId);

		return extractPictureList;
	}

	//登録画像データが無いもしくは3枚未満のときに、代替画像を準備するメソッド
	private List<String> preparePicture(List<String> extractPictureList, String userId) {

		final String EMP = TestDBConnection.getEmp();

		long emptyCount = extractPictureList.stream()
											  .filter(ep -> ep.equals(EMP))
											  .count();

		DefaultPictures[] defaultPictureArray = DefaultPictures.values();

		if(emptyCount == defaultPictureArray.length) {
			//登録画像データが無かった場合
			extractPictureList.clear();

			for(DefaultPictures dp : defaultPictureArray) {
				extractPictureList.add(dp.toString());
			}

		} else {
			//登録画像データがある場合
			deleteUnregisteredPicture(extractPictureList, userId);

			Path   picturesDirPath               = PathConfig.PICTURES_DIR_PATH.getPath();
			String extractPicturesDirPartString = picturesDirPath.getFileName().toString();

			for(int i = 0 ; i < extractPictureList.size() ; i++) {
				String picturePathString = extractPictureList.get(i);

				if(picturePathString.equals(EMP)) {
					extractPictureList.set(i, defaultPictureArray[i].toString());

				} else {
					int extractIndex =
							picturePathString.indexOf(extractPicturesDirPartString);

					String registeredPicture = picturePathString.substring(extractIndex);
					extractPictureList.set(i, registeredPicture);
				}
			}
		}

		return extractPictureList;
	}

	//登録されていない画像データを削除するメソッド
	private void deleteUnregisteredPicture(List<String> extractPictureList, String userId) {

		Path userDir = PathConfig.PICTURES_DIR_PATH.getPath().resolve(userId);

		if(Files.exists(userDir)) {

		    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(userDir)) {

		    	for(Path file : dirStream) {

		    		String   extractFileName  = file.toRealPath().toString();
		    		boolean deletingDecision = true;

		    		for(String ep : extractPictureList) {

		    			if(ep.equals(extractFileName)) {
		    				deletingDecision = false;
		    				break;
		    			}
		    		}

		    		if(deletingDecision) {
		    			Files.deleteIfExists(file);
		    		}
		    	}

		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
	}
}