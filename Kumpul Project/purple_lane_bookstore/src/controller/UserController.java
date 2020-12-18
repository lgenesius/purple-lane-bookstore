package controller;

import java.util.Vector;

import model.Model;
import model.RoleModel;
import model.UserModel;
import view.ManageProductMenuView;
import view.CustomerMenuView;
import view.CustomerRegistrationView;
import view.LoginView;
import view.ManagerMenuView;
import view.ManagePromoMenuView;
import view.ManageStaffMenuView;


public class UserController extends Controller{
	private UserModel user;
	private static UserController controller;

	private UserController() {
		user = new UserModel();
	}
	
	public static synchronized UserController getInstance() {
		return controller = (controller == null) ? new UserController() : controller;
	}
	
	public void viewStartMenu() {
		new LoginView().display();
	}
	
	public void viewRegistrationMenu() {
		new CustomerRegistrationView().display();
	}
	
	public void viewManagerMenu() {
		new ManagerMenuView().display();
	}
	
	public void viewManageStaffMenu() {
		new ManageStaffMenuView().display();
	}
	
	public void viewCustomerMenu() {
		new CustomerMenuView().display();
	}

	public UserModel getModel() {
		return user;
	}
	
	public boolean createCustomerAccount(String username, String password, String confPassword) {
		if(checkRegistrationVal(username, password, confPassword)) {
			UserModel tempUser = new UserModel();
			RoleModel tempRole = processRole("Customer");
			
			tempUser.setRoleId(tempRole.getRoleId());
			tempUser.setUsername(username);
			tempUser.setPassword(password);
			tempUser.createAccount();
			message = "Success registered";
			return true;
		} else {
			return false;
		}
	}
	
	public boolean createStaffAccount(String username, String role, String password) {
		if(checkNamePass(username, password)) {
			if(role.equals("Admin")) createAdminAccount(username, password);
			else createPromotionTeamAccount(username, password);
			
			return true;
		}
		
		return false;
	}
	
	private void createAdminAccount(String username, String password) {
		UserModel tempUser = new UserModel();
		RoleModel tempRole = processRole("Admin");
		
		tempUser.setRoleId(tempRole.getRoleId());
		tempUser.setUsername(username);
		tempUser.setPassword(password);
		tempUser.createAccount();
		message = "Success registered";
	}
	
	private void createPromotionTeamAccount(String username, String password) {
		UserModel tempUser = new UserModel();
		RoleModel tempRole = processRole("Promotion Team");
		
		tempUser.setRoleId(tempRole.getRoleId());
		tempUser.setUsername(username);
		tempUser.setPassword(password);
		tempUser.createAccount();
		message = "Success registered";
	}
	
	private boolean checkRegistrationVal(String username, String password, String confPassword) {
		if(!checkNamePass(username, password)) {
			return false;
		}
		if(confPassword.equals("")) {
			message = "Registration Failed! Confirm Password cannot empty";
			return false;
		}
		if(!password.equals(confPassword)) {
			message = "Registration Failed! Password not match with Confirm Password";
			return false;
		}
		if(getOneUser(username) != null) {
			message = "Registration Failed! This username is taken by another user";
			return false;
		}
		return true;
	}
	
	private boolean checkNamePass(String username, String password) {
		if(username.equals("")) {
			message = "Failed! Username cannot empty";
			return false;
		}
		if(password.equals("")) {
			message = "Failed! Password cannot empty";
			return false;
		}
		
		return true;
	}
	
	public boolean login(String username, String password) {
		if(checkNamePass(username, password)) {
			UserModel tempUser = getOneUser(username, password);
			if(tempUser != null) {
				user.setUserId(tempUser.getUserId());
				user.setRoleId(tempUser.getRoleId());
				user.setUsername(tempUser.getUsername());
				user.setPassword(tempUser.getPassword());
				if(user.getRoleId() == processRole("Customer").getRoleId()) CartController.getInstance().initCartList(user.getRoleId());

				message = "Success login";
			} else {
				message = "Login Failed! Wrong username or password";
				return false;
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public void logout() {
		user.setUserId(0);
		user.setRoleId(0);
		user.setUsername("");
		user.setPassword("");
		message = "Logout...";
	}
	
	public Vector<Model> getAllStaff() {
		Vector<Model> staffList = new Vector<>();
		Vector<Model> tempStaffList = user.getAllUser();
		for (Model model : tempStaffList) {
			UserModel tempUser = (UserModel) model;
			if(tempUser.getRoleId() == processRole("Admin").getRoleId() || tempUser.getRoleId() == processRole("Promotion Team").getRoleId()) {
				staffList.add(tempUser);
			}
		}
		
		return staffList;
	}
	
	public RoleModel processRole(String roleName) {
		return RoleController.getInstance().getOneRole(roleName);
	}
	
	public RoleModel processRole(int roleId) {
		return RoleController.getInstance().getOneRole(roleId);
	}
	
	public UserModel getOneUser(String username) {
		return user.getOneUser(username);
	}
	
	private UserModel getOneUser(String username, String password) {
		return user.getOneUser(username, password);
	}

	@Override
	public String getMessage() {
		return message;
	}
}
