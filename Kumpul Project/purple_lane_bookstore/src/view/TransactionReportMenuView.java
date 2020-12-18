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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.TransactionController;
import controller.UserController;
import model.Model;
import model.TransactionModel;

public class TransactionReportMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	
	private JLabel informationLbl, monthLbl, yearLbl;
	private JButton backBtn, searchBtn;
	private JComboBox<String> monthCBox, yearCBox;
	private JPanel topPanel, centerPanel, bottomPanel, informationPanel, backPanel, titlePanel, dataPanel, searchPanel;
	
	public TransactionReportMenuView() {
		super();
		width = 600;
		height = 500;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Purple Lane Bookstore Transaction Report");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		monthLbl = new JLabel("Select Month");
		yearLbl = new JLabel("Select Year");
		informationLbl = new JLabel("Select specific row to see the transaction detail");
		informationLbl.setForeground(Color.BLUE);
		informationLbl.setVisible(false);
		
		backBtn = new JButton("Back");
		searchBtn = new JButton("Search");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1));
		bottomPanel = new JPanel(new GridLayout(0,1));
		
		backPanel = new JPanel();
		searchPanel = new JPanel();
		titlePanel = new JPanel();
		informationPanel = new JPanel();
		dataPanel = new JPanel(new GridLayout(0,2,0,10));
		
		String []months = {
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"Desember"
		};
		
		monthCBox = new JComboBox<>(months);
		monthCBox.setSelectedItem(TransactionController.getInstance().getCurrentMonth());
		
		String []years = {
			"2005",	
			"2006",
			"2007",
			"2008",
			"2009",
			"2010",
			"2011",
			"2012",
			"2013",
			"2014",
			"2015",
			"2016",
			"2017",
			"2018",
			"2019",
			"2020"
		};
		
		yearCBox = new JComboBox<>(years);
		yearCBox.setSelectedItem(TransactionController.getInstance().getCurrentYear());
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
		dataPanel.add(monthLbl);
		dataPanel.add(monthCBox);
		dataPanel.add(yearLbl);
		dataPanel.add(yearCBox);
		bottomPanel.add(dataPanel);
		searchPanel.add(searchBtn);
		bottomPanel.add(new JPanel());
		bottomPanel.add(searchPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void initListeners() {
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fetchTableData();
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
		
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserController.getInstance().viewManagerMenu();
			}
		});
	}

	public void fetchTableData() {
		Vector<Object> tTransactions = new Vector<>();
		
		for (Model model : TransactionController.getInstance().getTransactionReport(monthCBox.getSelectedItem().toString(), yearCBox.getSelectedItem().toString())) {
			Vector<String> tempStringList = new Vector<>();
			TransactionModel tempTrans = (TransactionModel) model;
			tempStringList.add(String.valueOf(tempTrans.getTransactionId()));
			tempStringList.add(tempTrans.getTransactionDate().toString());
			tempStringList.add(tempTrans.getPaymentType());
			tempStringList.add(tempTrans.getCardNumber());
			tempStringList.add(tempTrans.getPromoCode());
			
			tTransactions.add(tempStringList);
		}
		
		if(!tTransactions.isEmpty()) {
			informationLbl.setVisible(true);
		} else {
			informationLbl.setVisible(false);
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
