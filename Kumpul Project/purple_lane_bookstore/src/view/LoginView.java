package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.CartController;
import controller.ProductController;
import controller.PromoController;
import controller.RoleController;
import controller.UserController;
import model.CartModel;
import model.UserModel;

public class LoginView extends View{
	private JLabel usernameLbl, passwordLbl, informationLbl;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginBtn, registerBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, informationPanel;

	public LoginView() {
		super();
		width = 400;
		height = 230;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Login");
		
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		informationLbl = new JLabel("Doesn't have an account yet ? Click Register button");
		informationLbl.setForeground(Color.BLUE);
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		loginBtn = new JButton("Login");
		registerBtn = new JButton("Register");
		
		topPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(0,1,0,10));
		dataPanel = new JPanel(new GridLayout(2,2,0,5));
		informationPanel = new JPanel();
		bottomPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		topPanel.add(titleForm);
		
		dataPanel.add(usernameLbl);
		dataPanel.add(usernameField);
		dataPanel.add(passwordLbl);
		dataPanel.add(passwordField);
		centerPanel.add(dataPanel);
		informationPanel.add(informationLbl);
		centerPanel.add(informationPanel);
		
		bottomPanel.add(loginBtn);
		bottomPanel.add(registerBtn);
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void initListeners() {
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(UserController.getInstance().login(usernameField.getText(), passwordField.getText())) {
					if(UserController.getInstance().getModel().getRoleId() == UserController.getInstance().processRole("Customer").getRoleId()) {
						dispose();
						CartController.getInstance().initCartList(UserController.getInstance().getModel().getUserId());
						UserController.getInstance().viewCustomerMenu();
					} else if(UserController.getInstance().getModel().getRoleId() == UserController.getInstance().processRole("Promotion Team").getRoleId()) {
						dispose();
						PromoController.getInstance().viewManagePromoMenu();
					} else if(UserController.getInstance().getModel().getRoleId() == UserController.getInstance().processRole("Manager").getRoleId()) {
						dispose();
						UserController.getInstance().viewManagerMenu();
					} else if(UserController.getInstance().getModel().getRoleId() == UserController.getInstance().processRole("Admin").getRoleId()) {
						dispose();
						ProductController.getInstance().viewManageProductMenu();
					}
					System.out.println(UserController.getInstance().getMessage());
				} else {
					JOptionPane.showMessageDialog(loginBtn, UserController.getInstance().getMessage());
					System.out.println(UserController.getInstance().getMessage());
				}
			}
		});
		
		registerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				UserController.getInstance().viewRegistrationMenu();
			}
		});
	}

}
