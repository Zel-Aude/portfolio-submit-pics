package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RegistrationUserBusinessLogic {

	public RegistrationUserBusinessLogic() {}

	//新規登録したユーザーを、DAOに登録依頼するメソッド
	public boolean executeRegistrationUser(UserInfoDto dto) {

		boolean succesInsert = false;

		//DAOクラスをインスタンス化＆対象のユーザーデータを、
		//「user_member」と「user_pictures」の各テーブルに登録するよう依頼
		RegistrationAndDeletionDao dao = new RegistrationAndDeletionDao(
				TestDBConnection.generateConnection());

		succesInsert = dao.insertUser(dto);

		return succesInsert;
	}

	//新規登録したユーザーのディレクトリを作成するメソッド
	public void createUserDirectory(String userId) {

	    try {
	        Path userDir = PathConfig.PICTURES_DIR_PATH.getPath().resolve(userId);

	        Files.createDirectory(userDir);

	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	}
}