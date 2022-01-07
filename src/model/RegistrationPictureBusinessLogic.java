package model;

import java.util.List;

public class RegistrationPictureBusinessLogic {

	public RegistrationPictureBusinessLogic() {}

	//ユーザーが選択した画像データの登録をDAOに依頼するメソッド
	public boolean executeRegistrationPicture(String userId, List<String> upPictureList) {

		boolean succesUpload = false;

		//DAOクラスをインスタンス化＆対象の写真データを登録するよう依頼
		UserPictureDao dao = new UserPictureDao(TestDBConnection.generateConnection());
		succesUpload = dao.updateUserPicture(userId, upPictureList);

		return succesUpload;
	}
}