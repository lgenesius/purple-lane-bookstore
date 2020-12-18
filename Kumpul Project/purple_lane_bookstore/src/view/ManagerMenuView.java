package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.TransactionController;
import controller.UserController;

public class ManagerMenuView extends View{
	private JButton transReportBtn, staffListBtn, logoutBtn;
	private JPanel topPanel, centerPanel, transReportPanel, staffListPanel, logoutPanel, titlePanel;
	
	public ManagerMenuView() {
		super();
		width = 400;
		height = 200;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Manager Menu View");
		
		logoutBtn = new JButton("Logout");
		transReportBtn = new JButton("Transaction Report");
		staffListBtn = new JButton("Staff List");
		
		titlePanel = new JPanel();
		logoutPanel = new JPanel();
		transReportPanel = new JPanel();
		staffListPanel = new JPanel();
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1));
	}

	@Override
	public void addComponents() {
		logoutPanel.add(logoutBtn);
		titlePanel.add(titleForm);
		topPanel.add(logoutPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		transReportPanel.add(transReportBtn);
		staffListPanel.add(staffListBtn);
		centerPanel.add(staffListPanel);
		centerPanel.add(transReportPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	@Override
	public void initListeners() {
		logoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserController.getInstance().logout();
				System.out.println(UserController.getInstance().getMessage());
				dispose();
				UserController.getInstance().viewStartMenu();
			}
		});
		
		transReportBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				TransactionController.getInstance().viewTransactionReportMenu();
			}
		});
		
		staffListBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserController.getInstance().viewManageStaffMenu();;
			}
		});
	}

}
