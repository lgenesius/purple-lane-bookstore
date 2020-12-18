package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CartController;
import controller.ProductController;
import controller.TransactionController;
import controller.UserController;
import model.Model;
import model.ProductModel;
import model.TransactionModel;

public class TransactionHistoryMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	
	private JLabel informationLbl;
	private JButton backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, informationPanel, backPanel, titlePanel;
	
	public TransactionHistoryMenuView() {
		super();
		width = 600;
		height = 400;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel(UserController.getInstance().getModel().getUsername() + "'s Transaction History");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		informationLbl = new JLabel("Select specific row to see the transaction detail");
		informationLbl.setForeground(Color.BLUE);
		
		backBtn = new JButton("Back");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1));
		bottomPanel = new JPanel();
		
		backPanel = new JPanel();
		titlePanel = new JPanel();
		informationPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("Trans ID");
		tHeader.add("Date");
		tHeader.add("Payment Type");
		tHeader.add("Card Number");
		tHeader.add("Promo Code");
		
		backPanel.add(backBtn);
		titlePanel.add(titleForm);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		
		centerPanel.add(scrollPane);
		
		informationPanel.add(informationLbl);
		bottomPanel.add(informationPanel);
		
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
				UserController.getInstance().viewCustomerMenu();
			}
		});
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				dispose();
				TransactionController.getInstance().setTransDetailInfo(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()), table.getValueAt(selectedRow, 1).toString(), table.getValueAt(selectedRow, 4).toString());
				TransactionController.getInstance().viewTransactionDetailMenu();
			}
		});
	}
	
	public void fetchTableData() {
		Vector<Object> tTransactions = new Vector<>();
		
		for (Model model : TransactionController.getInstance().getTransactionHistory()) {
			Vector<String> tempStringList = new Vector<>();
			TransactionModel tempTrans = (TransactionModel) model;
			tempStringList.add(String.valueOf(tempTrans.getTransactionId()));
			tempStringList.add(tempTrans.getTransactionDate().toString());
			tempStringList.add(tempTrans.getPaymentType());
			tempStringList.add(tempTrans.getCardNumber());
			tempStringList.add(tempTrans.getPromoCode());
			
			tTransactions.add(tempStringList);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tTransactions, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.setModel(dtm);
		setColumnWidth();
	}
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

}
