package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RoleModel extends Model{
	private int roleId;
	private String roleName;
	
	public RoleModel() {
		tableName = "roles";
	}
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public RoleModel getOneRole(int roleId) {
		String query = String.format("SELECT * FROM %s WHERE roleId=%d", tableName, roleId);
		ResultSet rs = con.executeQuery(query);
		
		RoleModel roleModel = new RoleModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("roleId");
				String name = rs.getString("roleName");
				
				roleModel.setRoleId(id);
				roleModel.setRoleName(name);
			}
			return roleModel;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public RoleModel getOneRole(String roleName) {
		String query = String.format("SELECT * FROM %s WHERE roleName='%s'", tableName, roleName);
		ResultSet rs = con.executeQuery(query);
		
		RoleModel roleModel = new RoleModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("roleId");
				String name = rs.getString("roleName");
				
				roleModel.setRoleId(id);
				roleModel.setRoleName(name);
			}
			return roleModel;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
