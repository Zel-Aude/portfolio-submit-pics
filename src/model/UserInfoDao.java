package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class UserInfoDao {

	private Connection con;

	public UserInfoDao() {}

	public UserInfoDao(Connection c) {
		this.con = c;
	}

	//登録されている最後のユーザーIDを取得するメソッド
	public Optional<String> selectLatestId() {

		//抽出データ（最新のID）格納用変数
		String latestId = null;

		//発行するSQL文の生成
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT             ");
		sb.append("   user_id          ");
		sb.append(" FROM               ");
		sb.append("   user_member      ");
		sb.append(" ORDER BY           ");
		sb.append("   user_id          ");
		sb.append(" DESC LIMIT 1       ");

		try(Connection connect = con;
				PreparedStatement ps = connect.prepareStatement(sb.toString());
				ResultSet         rs = ps.executeQuery()) {

			//ResultSetオブジェクトから1レコード分のデータを変数に格納
			if(rs.next()) {
				latestId = rs.getString("user_id");
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(latestId);
	}

	//任意のユーザーデータを1件取得するメソッド
	public UserInfoDto selectUser(String inputUserId, String inputPassWord) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		//抽出データ（UserInfoDto型）格納用変数
		UserInfoDto dto = new UserInfoDto();

		try {
			//発行するSQL文の生成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT             ");
			sb.append("   user_id  ,       ");
			sb.append("   user_name,       ");
			sb.append("   password         ");
			sb.append(" FROM               ");
			sb.append("   user_member      ");
			sb.append(" WHERE              ");
			sb.append("   user_id  = ? AND ");
			sb.append("   password = ?     ");

			ps = con.prepareStatement(sb.toString());

			//パラメータをセット
			ps.setString( 1, inputUserId   );
			ps.setString( 2, inputPassWord );

			rs = ps.executeQuery();

			//ResultSetオブジェクトからユーザーデータを抽出
			if(rs.next()) {
				dto.setUserId(   rs.getString("user_id")   );
				dto.setUserName( rs.getString("user_name") );
				dto.setPassWord( rs.getString("password")  );
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

	//全てのユーザーデータを取得するメソッド、使用しない
	public Optional<List<UserInfoDto>> selectAllUser() {

		//抽出データ（List型）格納用変数
		List<UserInfoDto> dtoList = new ArrayList<UserInfoDto>();

		//発行するSQL文の生成
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT *      ");
		sb.append(" FROM          ");
		sb.append("   user_member ");

		try(Connection connect = con;
				PreparedStatement ps = connect.prepareStatement(sb.toString());
				ResultSet         rs = ps.executeQuery()) {

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()) {
				UserInfoDto dto = new UserInfoDto();
				dto.setUserId(   rs.getString("user_id")   );
				dto.setUserName( rs.getString("user_name") );
				dto.setPassWord( rs.getString("password")  );
				dto.setTime(     rs.getTimestamp("time")   );
				dtoList.add(dto);
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(dtoList);
	}

	//任意のユーザーデータ範囲を取得するメソッド
	public List<UserInfoDto> selectUserAnyRange(int limit, int offset) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		//抽出データ（List型）格納用変数
		List<UserInfoDto> dtoList = new ArrayList<UserInfoDto>(limit);

		try {
			//発行するSQL文の生成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT *      ");
			sb.append(" FROM          ");
			sb.append("   user_member ");
			sb.append(" LIMIT  ?      ");
			sb.append(" OFFSET ?      ");

			ps = con.prepareStatement(sb.toString());

			//パラメータをセット
			ps.setInt( 1, limit  );
			ps.setInt( 2, offset );

			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()) {
				UserInfoDto dto = new UserInfoDto();
				dto.setUserId(   rs.getString("user_id")   );
				dto.setUserName( rs.getString("user_name") );
				dto.setPassWord( rs.getString("password")  );
				dto.setTime(     rs.getTimestamp("time")   );
				dtoList.add(dto);
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

		return dtoList;
	}

	//最後のユーザーデータ範囲を取得するメソッド
	public Map<Integer, List<UserInfoDto>> selectUserLastRange(int maxPageRecords) {

		//JDBCの接続に使用するオブジェクトを宣言
		PreparedStatement ps = null;
		ResultSet         rs = null;

		Integer                         idCount       = 0;
		List<UserInfoDto>               dtoList       = new ArrayList<UserInfoDto>();
		Map<Integer, List<UserInfoDto>> lastRangeMap = new HashMap<Integer, List<UserInfoDto>>();

		try {
			//発行するSQL文の生成
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT           ");
			sb.append("   COUNT(user_id) ");
			sb.append(" FROM             ");
			sb.append("   user_member    ");

			String countSql = sb.toString();

			sb.delete(0, sb.length());

			sb.append(" SELECT *      ");
			sb.append(" FROM          ");
			sb.append("   user_member ");
			sb.append(" ORDER BY      ");
			sb.append("   user_id     ");
			sb.append(" DESC LIMIT ?  ");

			String selectSql = sb.toString();

			List<String> sqlList = Arrays.asList(countSql, selectSql);

			for(String sql : sqlList) {

				ps = con.prepareStatement(sql);

				if(sql.equals(selectSql)) {
					//パラメータをセット
					ps.setInt( 1, maxPageRecords );
				}

				rs = ps.executeQuery();

				if(sql.equals(countSql)) {

					if(rs.next()) {
						idCount = rs.getInt("COUNT(user_id)");
					}
				}

				if(sql.equals(selectSql)) {
					//ResultSetオブジェクトから1レコード分のデータをDTOに格納
					while(rs.next()) {
						UserInfoDto dto = new UserInfoDto();
						dto.setUserId(   rs.getString("user_id")   );
						dto.setUserName( rs.getString("user_name") );
						dto.setPassWord( rs.getString("password")  );
						dto.setTime(     rs.getTimestamp("time")   );
						dtoList.add(dto);
					}
				}
			}

			lastRangeMap.put(idCount, dtoList);

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

		return lastRangeMap;
	}
}
