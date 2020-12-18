package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.UserController;
import model.Model;
import model.UserModel;

public class ManageStaffMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	
	private JLabel usernameLbl, passwordLbl, roleLbl;
	private JTextField usernameField;
	private JComboBox<String> roleCBox;
	
	private JPasswordField passwordField;
	private JButton insertBtn, backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, buttonPanel, backPanel, titlePanel;
	
	public ManageStaffMenuView() {
		super();
		width = 550;
		height = 500;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Staff List View");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		roleLbl = new JLabel("Role");
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		String []roleStaffList = {
				"Admin",
				"Promotion Team"
		};
		roleCBox = new JComboBox<>(roleStaffList);
		
		insertBtn = new JButton("Hire");
		backBtn = new JButton("Back");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2,1,0,60));
		bottomPanel = new JPanel(new GridLayout(0,1,0,5));
		
		titlePanel = new JPanel();
		backPanel = new JPanel();
		dataPanel = new JPanel(new GridLayout(0,2,0,20));
		buttonPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("ID");
		tHeader.add("Username");
		tHeader.add("Role");

		titlePanel.add(titleForm);
		backPanel.add(backBtn);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		centerPanel.add(scrollPane);
		dataPanel.add(usernameLbl);
		dataPanel.add(usernameField);
		dataPanel.add(roleLbl);
		dataPanel.add(roleCBox);
		dataPanel.add(passwordLbl);
		dataPanel.add(passwordField);
		centerPanel.add(dataPanel);
		
		buttonPanel.add(insertBtn);
		bottomPanel.add(new JPanel());
		bottomPanel.add(buttonPanel);
		
		fetchTableData();
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void initListeners() {
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserController.getInstance().viewManagerMenu();
			}
		});
		
		insertBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(UserController.getInstance().createStaffAccount(usernameField.getText(), roleCBox.getSelectedItem().toString(), passwordField.getText()) ) {
					resetAll();
					fetchTableData();
				}
				
				System.out.println(UserController.getInstance().getMessage());
				JOptionPane.showMessageDialog(insertBtn, UserController.getInstance().getMessage());
			}
		});
	}
	
	public void resetAll() {
		usernameField.setText("");
		passwordField.setText("");
	}
	
	public void fetchTableData() {
		Vector<Object> tUsers = new Vector<>();
		
		for (Model model : UserController.getInstance().getAllStaff()) {
			Vector<String> tempStringList = new Vector<>();
			UserModel tempUser = (UserModel) model;
			tempStringList.add(String.valueOf(tempUser.getUserId()));
			tempStringList.add(tempUser.getUsername());
			tempStringList.add(UserController.getInstance().processRole(tempUser.getRoleId()).getRoleName());
			
			tUsers.add(tempStringList);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tUsers, tHeader) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.setModel(dtm);
	}

}
