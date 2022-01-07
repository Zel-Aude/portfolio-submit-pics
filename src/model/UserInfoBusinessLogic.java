package model;


public class UserInfoBusinessLogic {

	public UserInfoBusinessLogic() {}

	//ユーザーデータをDBから取得するようDAOに依頼するメソッド
	public UserInfoDto executeSelectUserInfo(String inputUserId, String inputPassWord) {

		//DAOクラスをインスタンス化＆「user_member」テーブルからユーザーデータを抽出するよう依頼
		UserInfoDao dao = new UserInfoDao(TestDBConnection.generateConnection());
		UserInfoDto dto = dao.selectUser(inputUserId, inputPassWord);

		return dto;
	}
}
