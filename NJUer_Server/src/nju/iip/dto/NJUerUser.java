package nju.iip.dto;

/**
 * 用户类
 * @author wangqiang
 *
 */
public class NJUerUser {
	
	private String username;//帐号
	private String password;//密码
	private String email;//邮箱
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
