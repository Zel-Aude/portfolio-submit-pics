package model;

import java.sql.Timestamp;


public class AdminInfoDto {

	private String adminId;        //管理者ID
	private String adminName;      //管理者名
	private String passWord;       //管理者パスワード
	private Timestamp time ;       //更新時刻

	public AdminInfoDto() {}

	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		String dtoInfo = String.format(
				"adminId:%s、adminName:%s、passWord:%s、time:%4$tF %4$tT",
				this.adminId, this.adminName, this.passWord, this.time);

		return dtoInfo;
	}
}
