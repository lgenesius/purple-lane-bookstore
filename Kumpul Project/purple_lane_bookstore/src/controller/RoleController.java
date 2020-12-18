package controller;

import java.util.Vector;

import model.Model;
import model.RoleModel;

public class RoleController extends Controller{
	private RoleModel role;
	private static RoleController controller;

	private RoleController() {
		role = new RoleModel();
	}
	
	public static synchronized RoleController getInstance() {
		return controller = (controller == null) ? new RoleController() : controller;
	}
	
	public RoleModel getOneRole(int roleId) {
		return role.getOneRole(roleId);
	}
	
	public RoleModel getOneRole(String roleName) {
		return role.getOneRole(roleName);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
