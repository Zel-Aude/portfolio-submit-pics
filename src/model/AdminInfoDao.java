package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminInfoDao {

	private Connection con;

	public AdminInfoDao() {}

	public AdminInfoDao(Connection c) {
		this.con = c;
	}

	//管理者データをDBから取得するメソッド
	public AdminInfoDto selectAdmin(String inputAdminId, String inputPassWord) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		//抽出データ（AdminInfoDto型）格納用変数
		AdminInfoDto dto = new AdminInfoDto();

		try {
			//発行するSQL文の生成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT             ");
			sb.append("   admin_id  ,      ");
			sb.append("   admin_name,      ");
			sb.append("   password         ");
			sb.append(" FROM               ");
			sb.append("   admin_info       ");
			sb.append(" WHERE              ");
			sb.append("   admin_id = ? AND ");
			sb.append("   password = ?     ");

			ps = con.prepareStatement(sb.toString());

			//パラメータをセット
			ps.setString( 1, inputAdminId  );
			ps.setString( 2, inputPassWord );

			rs = ps.executeQuery();

			//ResultSetオブジェクトからユーザーデータを抽出
			if(rs.next()) {
				dto.setAdminId(   rs.getString("admin_id")   );
				dto.setAdminName( rs.getString("admin_name") );
				dto.setPassWord(  rs.getString("password")   );
			}

		} catch(SQLException e) {
			e.printStackTrace();

		} finally {
			//接続の解除
			try {
				if(!rs.isClosed()) {
					rs.close();
				}
				if(!ps.isClosed()) {
					ps.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}

			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
