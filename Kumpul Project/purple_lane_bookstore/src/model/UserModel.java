package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UserModel extends Model{
	private int userId = 0, roleId = 0;
	private String username = "", password = "";

	public UserModel() {
		tableName = "users";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	
	public void createAccount() {
		String query = String.format("INSERT INTO %s VALUES(null, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, roleId);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Model> getAllUser() {
		Vector<Model> tempUserList = new Vector<>();
		
		String query = String.format("SELECT * FROM %s", tableName);
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				int userId = rs.getInt("userId");
				int roleId = rs.getInt("roleId");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				UserModel tempUser = new UserModel();
				tempUser.setUserId(userId);
				tempUser.setRoleId(roleId);
				tempUser.setUsername(username);
				tempUser.setPassword(password);
				
				tempUserList.add(tempUser);
			}
			return tempUserList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UserModel getOneUser(String username) {
		String query = String.format("SELECT * FROM %s WHERE username='%s'", tableName, username);
		ResultSet rs = con.executeQuery(query);
		
		UserModel userModel = new UserModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("userId");
				int roleId = rs.getInt("roleId");
				String name = rs.getString("username");
				String password = rs.getString("password");
				
				userModel.setUserId(id);
				userModel.setRoleId(roleId);
				userModel.setUsername(name);
				userModel.setPassword(password);
				return userModel;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public UserModel getOneUser(String username, String password) {
		String query = String.format("SELECT * FROM %s WHERE username='%s' AND password='%s'", tableName, username, password);
		ResultSet rs = con.executeQuery(query);
		
		UserModel userModel = new UserModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("userId");
				int roleId = rs.getInt("roleId");
				String name = rs.getString("username");
				String tempPassword = rs.getString("password");
				
				userModel.setUserId(id);
				userModel.setRoleId(roleId);
				userModel.setUsername(name);
				userModel.setPassword(tempPassword);
				return userModel;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
