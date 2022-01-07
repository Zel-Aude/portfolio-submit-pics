package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * test_dbへのconnectionを生成するclass
 * conの接続解除は利用先classで必ず行うこと
 */
public final class TestDBConnection {

	private enum DriverAndDBConfig {
		//JDBCドライバの相対パス（MySQL5系の場合は「com.mysql.jdbc.Driver」）
		DRIVER_NAME("com.mysql.cj.jdbc.Driver"),
		//接続先のデータベース（「test_db」でない場合は該当の箇所を変更）
		JDBC_URL(
				"jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST"
				+ "&useSSL=false"),
		//接続するユーザー名（「test_user」でない場合は該当の箇所を変更）
		USER_ID("test_user"),
		//接続するユーザーのパスワード（「test_pass」でない場合は該当の箇所を変更）
		USER_PASS("test_pass");

		private final String configValue;

		private DriverAndDBConfig(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public String toString() {
			return this.configValue;
		}
	}

	//DAOで使用する定数
	private static final String EMP = "empty";

	private TestDBConnection() {}

	public static Connection generateConnection() {

		Connection con = null;

		try {
			//JDBCドライバをロード＆接続先として指定
			Class.forName(DriverAndDBConfig.DRIVER_NAME.toString());

			//接続の確立（Connectionオブジェクトの取得）
			con = DriverManager.getConnection(
					DriverAndDBConfig.JDBC_URL.toString(),
					DriverAndDBConfig.USER_ID.toString(),
					DriverAndDBConfig.USER_PASS.toString());

		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return con;
	}


	public static String getEmp() {
		return EMP;
	}
}
