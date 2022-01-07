package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserPictureDao {

	private Connection con;

	public UserPictureDao() {}

	public UserPictureDao(Connection c) {
		this.con = c;
	}

	//ユーザーが登録している画像データのパスをDBから取得するメソッド
	public List<String> selectUserPicture(String inputUserId) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		//抽出データ（List型）格納用変数
		List<String> pictureList = new ArrayList<String>();

		try {
			//発行するSQL文の生成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT          ");
			sb.append("   picture1,     ");
			sb.append("   picture2,     ");
			sb.append("   picture3      ");
			sb.append(" FROM            ");
			sb.append("   user_pictures ");
			sb.append(" WHERE           ");
			sb.append("   user_id = ?   ");

			ps = con.prepareStatement(sb.toString());

			//パラメータをセット
			ps.setString( 1, inputUserId );

			rs = ps.executeQuery();

			//ResultSetオブジェクトからユーザーデータを抽出
			if(rs.next()) {
				pictureList.add( rs.getString("picture1") );
				pictureList.add( rs.getString("picture2") );
				pictureList.add( rs.getString("picture3") );
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

		return pictureList;
	}

	//ユーザーが選択した画像データのパスをDBで更新するメソッド
	public boolean updateUserPicture(String userId, List<String> upPictureList) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;

		//実行結果（真：成功、偽：例外発生）格納用変数
		boolean isSuccess = true;

		try {
			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

		    List<String> pictureNumList = new ArrayList<String>();
		    String       pictureColumn  = "picture%d = ?";

		    for(int i = 0; i < upPictureList.size(); i++) {
		    	pictureNumList.add(String.format(pictureColumn, i + 1));
		    }

		    //発行するSQL文の生成
		    StringBuilder sb = new StringBuilder();
		    sb.append(" UPDATE user_pictures SET          ");
		    sb.append(  String.join(" , ", pictureNumList) );
		    sb.append(" WHERE user_id = ?                 ");

			ps = con.prepareStatement(sb.toString());

			//パラメータをセット
			int placeholderNum = 1;
		    for(int i = 0; i < upPictureList.size(); i++) {
		    	ps.setString(i + 1, upPictureList.get(i));
		    	placeholderNum++;
		    }

		    ps.setString(placeholderNum, userId);

			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false;

		} finally {

			//トランザクションの終了
			if(isSuccess){
				//明示的にコミットを実施
				try {
					con.commit();
				} catch(SQLException e) {
					e.printStackTrace();
				}

			} else {
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			//接続の解除
			try {
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

		return isSuccess ;
	}
}
