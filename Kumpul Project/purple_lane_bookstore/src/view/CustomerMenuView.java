package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CartController;
import controller.ProductController;
import controller.TransactionController;
import controller.UserController;

public class CustomerMenuView extends View{
	private JButton transHistoryBtn, bookListBtn, logoutBtn, viewCartBtn;
	private JPanel topPanel, centerPanel, transHistoryPanel, bookListPanel, logoutPanel, titlePanel, viewCartPanel;
	
	public CustomerMenuView() {
		super();
		width = 400;
		height = 270;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Welcome to Purple Lane Bookstore, " + UserController.getInstance().getModel().getUsername() +"!");
		
		logoutBtn = new JButton("Logout");
		bookListBtn = new JButton("Book List");
		viewCartBtn = new JButton("View Cart");
		transHistoryBtn = new JButton("Transaction History");
		
		titlePanel = new JPanel();
		logoutPanel = new JPanel();
		transHistoryPanel = new JPanel();
		bookListPanel = new JPanel();
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1,0,10));
		viewCartPanel = new JPanel();
		
	}

	@Override
	public void addComponents() {
		logoutPanel.add(logoutBtn);
		titlePanel.add(titleForm);
		topPanel.add(logoutPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		transHistoryPanel.add(transHistoryBtn);
		bookListPanel.add(bookListBtn);
		viewCartPanel.add(viewCartBtn);
		centerPanel.add(bookListPanel);
		centerPanel.add(viewCartPanel);
		centerPanel.add(transHistoryPanel);
		centerPanel.add(new JPanel());
		
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
		
		bookListBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProductController.getInstance().viewAddToCartMenu();
			}
		});
		
		viewCartBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CartController.getInstance().viewManageCartMenu();
			}
		});
		
		transHistoryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				TransactionController.getInstance().viewTransactionHistoryMenu();
			}
		});
	}

}
