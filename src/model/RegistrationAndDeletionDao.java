package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/*
 * user_memberテーブルとuser_picturesテーブルへの登録と削除を一括で行うDAO
 */
public class RegistrationAndDeletionDao {

	private static final String MEMBER_TABLE_NAME   = " user_member ";
	private static final String PICTURES_TABLE_NAME = " user_pictures ";

	private static final String MEMBER_COLUMN_SPECIFICATION =
			MEMBER_TABLE_NAME + "( user_id, user_name, password, time ";

	private static final String PICTURES_COLUMN_SPECIFICATION =
			PICTURES_TABLE_NAME + "( user_id, picture1, picture2, picture3 ";

	private Connection con;

	public RegistrationAndDeletionDao() {}

	public RegistrationAndDeletionDao(Connection c) {
		this.con = c;
	}

	//ユーザーと登録画像パスの初期値をDBに登録するメソッド
	public boolean insertUser(UserInfoDto dto){

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;

		//実行結果（真：成功、偽：例外発生）格納用変数
		boolean isSuccess = true;

		try {
			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			List<String> insertTableList = Arrays.asList(
					MEMBER_COLUMN_SPECIFICATION, PICTURES_COLUMN_SPECIFICATION);

			StringBuilder sb = new StringBuilder();

			final String EMP = TestDBConnection.getEmp();

			for(String table : insertTableList) {
				//発行するSQL文の生成
				sb.append(" INSERT INTO ");
				sb.append(     table     );
				sb.append(" ) VALUES (  ");
				sb.append("   ?,        ");
				sb.append("   ?,        ");
				sb.append("   ?,        ");
				sb.append("   ?         ");
				sb.append(" )           ");

				ps = con.prepareStatement(sb.toString());

				if(table.contains(MEMBER_TABLE_NAME)) {
					//パラメータをセット
					ps.setString(    1, dto.getUserId()   );
					ps.setString(    2, dto.getUserName() );
					ps.setString(    3, dto.getPassWord() );
					ps.setTimestamp( 4, dto.getTime()     );
				}

				if(table.contains(PICTURES_TABLE_NAME)) {
					//パラメータをセット
					ps.setString( 1, dto.getUserId() );
					ps.setString( 2, EMP             );
					ps.setString( 3, EMP             );
					ps.setString( 4, EMP             );
				}

				ps.executeUpdate();

				sb.delete(0, sb.length());
			}

		} catch(SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false;

		} finally {
			//トランザクションの終了
			if(isSuccess) {
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

		return isSuccess;
	}

	//選択したユーザーと登録画像をDBから削除するメソッド
	public boolean deleteUser(List<String> targetList) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps  = null;

		//実行結果（真：成功、偽：例外発生）格納用変数
		boolean isSuccess = true;

		try {
			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			List<String> deleteTableList = Arrays.asList(
					MEMBER_TABLE_NAME, PICTURES_TABLE_NAME);

			StringBuilder sb = new StringBuilder();

			for(String table : deleteTableList) {
				//発行するSQL文の生成
				sb.append(" DELETE FROM       ");
				sb.append(       table         );
				sb.append(" WHERE user_id = ? ");

				ps = con.prepareStatement(sb.toString());

				int count = 0;

				for(String tgt : targetList) {
					ps.setString( 1, tgt );
					ps.addBatch();
					count++;

					if(count % 100 == 0 || count == targetList.size()) {
						ps.executeBatch();
					}
				}

				sb.delete(0, sb.length());
			}

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

		return isSuccess;
	}

	//ユーザーと登録画像のDBデータを全て削除するメソッド
	public boolean truncateUser() {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps  = null;

		//実行結果（真：成功、偽：例外発生）格納用変数
		boolean isSuccess = true;

		try {
			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			List<String> truncateTableList = Arrays.asList(
					MEMBER_TABLE_NAME, PICTURES_TABLE_NAME);

			StringBuilder sb = new StringBuilder();

			for(String table : truncateTableList) {
				//発行するSQL文の生成
				sb.append(" TRUNCATE TABLE ");
				sb.append(       table      );

				ps = con.prepareStatement(sb.toString());

				ps.execute();

				sb.delete(0, sb.length());
			}

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

		return isSuccess;
	}

	//ダミーユーザー作成時にIDのレコードをカウントするためのテスト用メソッド
	public int[] selectCountId() {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		List<String> idCountTableList = Arrays.asList(
				MEMBER_TABLE_NAME, PICTURES_TABLE_NAME);

		int[] idCountArray = new int[idCountTableList.size()];

		try {
			StringBuilder sb = new StringBuilder();

			for(int i = 0 ; i < idCountTableList.size() ; i++) {
				//発行するSQL文の生成
				sb.append(" SELECT                ");
				sb.append("   COUNT(user_id)      ");
				sb.append(" FROM                  ");
				sb.append( idCountTableList.get(i) );

				ps = con.prepareStatement(sb.toString());

				rs = ps.executeQuery();

				if(rs.next()) {
					idCountArray[i] = rs.getInt("COUNT(user_id)");
				}

				sb.delete(0, sb.length());
			}

		} catch(SQLException e) {
			e.printStackTrace();

		} finally {
			//接続解除
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

		return idCountArray;
	}

	//ダミーユーザーの登録をまとめて行うためのテスト用メソッド
	public boolean insertInBulkUser(List<UserInfoDto> insertList){

		PreparedStatement ps = null;

		//実行結果（真：成功、偽：例外発生）格納用変数
		boolean isSuccess = true;

		try {
			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			List<String> insertTableList = Arrays.asList(
					MEMBER_COLUMN_SPECIFICATION, PICTURES_COLUMN_SPECIFICATION);

			StringBuilder sb = new StringBuilder();

			final String EMP = TestDBConnection.getEmp();

			for(String table : insertTableList) {
				//発行するSQL文の生成
				sb.append(" INSERT INTO ");
				sb.append(     table     );
				sb.append(" ) VALUES (  ");
				sb.append("   ?,        ");
				sb.append("   ?,        ");
				sb.append("   ?,        ");
				sb.append("   ?         ");
				sb.append(" )           ");

				ps = con.prepareStatement(sb.toString());

				int count             = 0;
				int batchCountAllSum = 0;

				for(UserInfoDto dto : insertList) {

					if(table.contains(MEMBER_TABLE_NAME)) {
						//パラメータをセット
						ps.setString(    1, dto.getUserId()   );
						ps.setString(    2, dto.getUserName() );
						ps.setString(    3, dto.getPassWord() );
						ps.setTimestamp( 4, dto.getTime()     );
					}

					if(table.contains(PICTURES_TABLE_NAME)) {
						//パラメータをセット
						ps.setString( 1, dto.getUserId() );
						ps.setString( 2, EMP             );
						ps.setString( 3, EMP             );
						ps.setString( 4, EMP             );
					}

					ps.addBatch();
					count++;

					if(count % 100 == 0 || count == insertList.size()) {

						int[] batchCountArray = ps.executeBatch();
						int   batchCountSum   = Arrays.stream(batchCountArray).sum();
						batchCountAllSum += batchCountSum;
					}
				}

				System.out.println(String.format("追加件数：%d", batchCountAllSum));

				sb.delete(0, sb.length());
			}

		} catch(SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false;

		} finally {
			//トランザクションの終了
			if(isSuccess) {
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
			if(ps != null) {
				try {
					ps.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return isSuccess;
	}
}
