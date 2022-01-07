package model;

import java.sql.Timestamp;


public class UserInfoDto {

	private String userId;         //ユーザーID
	private String userName;       //ユーザー名
	private String passWord;       //ユーザーパスワード
	private Timestamp time ;       //更新時刻

	public UserInfoDto() {}

	public UserInfoDto(UserInfoDto dto) {
		this.userId   = dto.getUserId();
		this.userName = dto.getUserName();
		this.passWord = dto.getPassWord();
		this.time     = dto.getTime();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
				"userId:%s、userName:%s、passWord:%s、time:%4$tF %4$tT",
				this.userId, this.userName, this.passWord, this.time);

		return dtoInfo;
	}
}
