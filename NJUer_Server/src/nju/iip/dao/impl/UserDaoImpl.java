package nju.iip.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import nju.iip.dto.NJUerUser;
import nju.iip.utils.DBConnection;

/**
 * 用户信息有关的数据库操作
 * 
 * @author wangqiang
 * 
 */
public class UserDaoImpl {

	private Connection conn = null;
	private Statement sm = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	/**
	 * 判断用户名是否存在
	 * @param openId
	 * @return
	 */
	public boolean checkUserName(String username) {
		boolean flag = false;
		String sql = "select * from userinfo where username='" + username
				+ "'";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			flag = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return flag;
	}
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(NJUerUser user) {
		conn =DBConnection.getConn();
    	ps = null;
    	String sql = "insert into userinfo(username,password,email) values(?,?,?)";
    	try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			return ps.executeUpdate() == 1 ? true : false;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}
	
	/**
	 * 判断用户名密码是否正确
	 * @param openId
	 * @return
	 */
	public boolean checkPassword(String username,String password) {
		boolean flag = false;
		String sql = "select * from userinfo where username='" + username+ "' and password='"+password+"'";;
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			flag = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return flag;
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (sm != null) {
			try {
				sm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
