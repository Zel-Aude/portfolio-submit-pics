package model;


public class AdminInfoBusinessLogic {

	public AdminInfoBusinessLogic() {}

	//管理者データをDBから取得するようDAOに依頼するメソッド
	public AdminInfoDto executeSelectAdminInfo(String inputAdminId, String inputPassWord) {

		//DAOクラスをインスタンス化＆「admin_info」テーブルから管理者データを抽出するよう依頼
		AdminInfoDao dao = new AdminInfoDao(TestDBConnection.generateConnection());
		AdminInfoDto dto = dao.selectAdmin(inputAdminId, inputPassWord);

		return dto;
	}
}
