package org.streamer.studentinfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.streamer.studentinfo.model.User;

public class UserDao {

	public User login(Connection con, User user) throws Exception {
		User resultUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}

	public boolean register(Connection con, User user) throws Exception {
		
		//检测重复
		String sql_name = "select * from t_user where userName=?";
		PreparedStatement pstmt_name = con.prepareStatement(sql_name);
		pstmt_name.setString(1, user.getUserName());
		ResultSet rs = pstmt_name.executeQuery();
		if (rs.next()) {
			return false;
		}
		//插入
		String sql = "insert into t_user(userName,password) values(?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		int mun = pstmt.executeUpdate();
		if (mun>-1)
			return true;
		return false;
	}
	
	public int modifyPassword(Connection con, User user) throws Exception {
		String sql = "update t_user set password=? where userId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setInt(2, user.getUserId());
		return pstmt.executeUpdate();
	}
}
