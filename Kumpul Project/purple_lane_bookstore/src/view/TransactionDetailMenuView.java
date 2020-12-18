package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ProductController;
import controller.PromoController;
import controller.RoleController;
import controller.TransactionController;
import controller.UserController;
import model.Model;
import model.ProductModel;
import model.TransactionDetailModel;
import model.TransactionModel;

public class TransactionDetailMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	
	private JLabel totalPriceLbl;
	private JButton backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, totalPricePanel, backPanel, titlePanel;
	
	public TransactionDetailMenuView() {
		super();
		width = 600;
		height = 400;
	}

	@Override
	public void initComponents() {
		
		titleForm = new JLabel("Transaction Date : "+ TransactionController.getInstance().getTransDate());
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		totalPriceLbl = new JLabel("Total Price :");
		totalPriceLbl.setForeground(Color.BLUE);
		
		backBtn = new JButton("Back");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1));
		bottomPanel = new JPanel();
		
		backPanel = new JPanel();
		titlePanel = new JPanel();
		totalPricePanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("Product ID");
		tHeader.add("Product Name");
		tHeader.add("Quantity");
		tHeader.add("Price");

		backPanel.add(backBtn);
		titlePanel.add(titleForm);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		
		centerPanel.add(scrollPane);
		
		totalPricePanel.add(totalPriceLbl);
		bottomPanel.add(totalPricePanel);
		
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
				if(RoleController.getInstance().getOneRole(UserController.getInstance().getModel().getRoleId()).getRoleName().equals("Customer")) {
					dispose();
					TransactionController.getInstance().viewTransactionHistoryMenu();
				} else if(RoleController.getInstance().getOneRole(UserController.getInstance().getModel().getRoleId()).getRoleName().equals("Manager")){
					dispose();
					TransactionController.getInstance().viewTransactionReportMenu();
				}
			}
		});
	}

	public void fetchTableData() {
		long totalPrice = 0;
		Vector<Object> tTransDetails = new Vector<>();
		
		for (Model model : TransactionController.getInstance().getTransactionDetail(TransactionController.getInstance().getTransId())) {
			Vector<String> tempStringList = new Vector<>();
			TransactionDetailModel tempTransDetail = (TransactionDetailModel) model;
			ProductModel product = ProductController.getInstance().getOneProduct(tempTransDetail.getProductId());
			
			tempStringList.add(String.valueOf(tempTransDetail.getProductId()));
			tempStringList.add(product.getProductName());
			tempStringList.add(String.valueOf(tempTransDetail.getProductQuantity()));
			
			int price = tempTransDetail.getProductQuantity() * product.getProductPrice();
			tempStringList.add(String.valueOf(price));
			
			totalPrice = totalPrice + (long) price;
			
			tTransDetails.add(tempStringList);
		}
		
		if(!TransactionController.getInstance().getPromoCode().equals("")) {
			totalPrice = TransactionController.getInstance().usePromoCode(TransactionController.getInstance().getPromoCode(), totalPrice);
			totalPriceLbl.setText("With Promo Code : "+TransactionController.getInstance().getPromoCode()+", Total Price : "+ totalPrice);
		} else {
			totalPriceLbl.setText("Total Price : "+ totalPrice);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tTransDetails, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.setModel(dtm);
		setColumnWidth();
	}
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
}
