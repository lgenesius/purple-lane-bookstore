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

import controller.UserController;

public class CustomerRegistrationView extends View{
	private JLabel usernameLbl, passwordLbl, confirmPasswordLbl, informationLbl;
	private JTextField usernameField;
	private JPasswordField passwordField, confirmPasswordField;
	private JButton registerBtn, backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, informationPanel;

	public CustomerRegistrationView() {
		super();
		width = 450;
		height = 300;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Register");
		
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		confirmPasswordLbl = new JLabel("Confirm Password");
		informationLbl = new JLabel("Registered ? Click Back button to go to the Login Form");
		informationLbl.setForeground(Color.BLUE);
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		confirmPasswordField = new JPasswordField();
		
		registerBtn = new JButton("Register");
		backBtn = new JButton("Back");
		
		topPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(0,1,0,30));
		dataPanel = new JPanel(new GridLayout(3,3,0,5));
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
		dataPanel.add(confirmPasswordLbl);
		dataPanel.add(confirmPasswordField);
		centerPanel.add(dataPanel);
		informationPanel.add(informationLbl);
		centerPanel.add(informationPanel);
		
		bottomPanel.add(registerBtn);
		bottomPanel.add(backBtn);
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void initListeners() {
		registerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(UserController.getInstance().createCustomerAccount(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText())) {
					JOptionPane.showMessageDialog(registerBtn, UserController.getInstance().getMessage());
					System.out.println(UserController.getInstance().getMessage());
					dispose();
					UserController.getInstance().viewStartMenu();
				} else {
					JOptionPane.showMessageDialog(registerBtn, UserController.getInstance().getMessage());
					System.out.println(UserController.getInstance().getMessage());
				}
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserController.getInstance().viewStartMenu();
			}
		});
	}

}
