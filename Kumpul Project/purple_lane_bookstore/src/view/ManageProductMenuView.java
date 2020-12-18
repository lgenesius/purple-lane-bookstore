package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ProductController;
import controller.UserController;
import model.Model;
import model.ProductModel;

public class ManageProductMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	private JLabel prodIdLbl, prodNameLbl, prodAuthorLbl, prodPriceLbl, prodStockLbl, informationLbl;
	private JTextField prodIdField, prodNameField, prodAuthorField, prodPriceField, prodStockField, searchField;
	private JButton insertBtn, updateBtn, deleteBtn, resetBtn, logoutBtn, searchBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, buttonPanel, logoutPanel, titlePanel, informationPanel, searchPanel;
	
	public ManageProductMenuView() {
		super();
		width = 600;
		height = 750;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Admin Product View");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		prodIdLbl = new JLabel("Product ID");
		prodNameLbl = new JLabel("Product Name");
		prodAuthorLbl = new JLabel("Product Author");
		prodPriceLbl = new JLabel("Product Price");
		prodStockLbl = new JLabel("Product Stock");
		informationLbl = new JLabel("Click Reset button to view all product");
		informationLbl.setForeground(Color.BLUE);
		informationLbl.setVisible(false);
		
		prodIdField = new JTextField();
		prodIdField.setEditable(false);
		prodNameField = new JTextField();
		prodAuthorField = new JTextField();
		prodPriceField = new JTextField();
		prodStockField = new JTextField();
		searchField = new JTextField();
		searchField.setForeground(Color.GRAY);
		searchField.setText("Search product..");
		
		logoutBtn = new JButton("Logout");
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		resetBtn = new JButton("Reset");
		searchBtn = new JButton("Search");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2,1,0,30));
		bottomPanel = new JPanel(new GridLayout(0,1,0,5));
		
		searchPanel = new JPanel(new GridLayout(1,2));
		informationPanel = new JPanel();
		titlePanel = new JPanel();
		logoutPanel = new JPanel();
		dataPanel = new JPanel(new GridLayout(0,2,0,20));
		buttonPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("ID");
		tHeader.add("Name");
		tHeader.add("Author");
		tHeader.add("Price");
		tHeader.add("Stock");
		
		titlePanel.add(titleForm);
		logoutPanel.add(logoutBtn);
		searchPanel.add(searchField);
		searchPanel.add(searchBtn);
		topPanel.add(searchPanel, BorderLayout.EAST);
		topPanel.add(logoutPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		centerPanel.add(scrollPane);
		dataPanel.add(prodIdLbl);
		dataPanel.add(prodIdField);
		dataPanel.add(prodNameLbl);
		dataPanel.add(prodNameField);
		dataPanel.add(prodAuthorLbl);
		dataPanel.add(prodAuthorField);
		dataPanel.add(prodPriceLbl);
		dataPanel.add(prodPriceField);
		dataPanel.add(prodStockLbl);
		dataPanel.add(prodStockField);
		centerPanel.add(dataPanel);
		
		buttonPanel.add(insertBtn);
		buttonPanel.add(updateBtn);
		buttonPanel.add(deleteBtn);
		buttonPanel.add(resetBtn);
		informationPanel.add(informationLbl);
		bottomPanel.add(new JPanel());
		bottomPanel.add(informationPanel);
		bottomPanel.add(buttonPanel);
		
		fetchTableData();
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
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
		
		insertBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ProductController.getInstance().createProduct(prodIdField.getText(), prodNameField.getText(), prodAuthorField.getText(), prodPriceField.getText(), prodStockField.getText())) {
					resetAll();
				}
				JOptionPane.showMessageDialog(insertBtn, ProductController.getInstance().getMessage());
				System.out.println(ProductController.getInstance().getMessage());
			}
		});
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedRow = table.getSelectedRow();
				prodIdField.setText(table.getValueAt(selectedRow, 0).toString());
				prodNameField.setText(table.getValueAt(selectedRow, 1).toString());
				prodAuthorField.setText(table.getValueAt(selectedRow, 2).toString());
				prodPriceField.setText(table.getValueAt(selectedRow, 3).toString());
				prodStockField.setText(table.getValueAt(selectedRow, 4).toString());
			}
		});
		
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ProductController.getInstance().updateProduct(prodIdField.getText(), prodNameField.getText(), prodAuthorField.getText(), prodPriceField.getText(), prodStockField.getText())) {
					resetAll();
				}
	
				JOptionPane.showMessageDialog(updateBtn, ProductController.getInstance().getMessage());
				System.out.println(ProductController.getInstance().getMessage());
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(deleteBtn, "Do you want to delete ?", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
				if(input == 0) {
					if(ProductController.getInstance().deleteProduct(prodIdField.getText())) {
						resetAll();
					}
					
					JOptionPane.showMessageDialog(updateBtn, ProductController.getInstance().getMessage());
					System.out.println(ProductController.getInstance().getMessage());
				}
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ProductController.getInstance().searchValidation(searchField.getText())) {
					int productId = Integer.parseInt(searchField.getText());
					getSearchedProduct(productId);
				} else {
					JOptionPane.showMessageDialog(searchBtn, ProductController.getInstance().getMessage());
					System.out.println(ProductController.getInstance().getMessage());
				}
			}
		});
		
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(searchField.getText().equals("")) {
					searchField.setForeground(Color.GRAY);
					searchField.setText("Search product..");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if(searchField.getText().equals("Search product..")) {
					searchField.setText("");
					searchField.setForeground(Color.BLACK);
				}
			}
		});
	}
	
	public void resetAll() {
		prodIdField.setText("");
		prodNameField.setText("");
		prodAuthorField.setText("");
		prodPriceField.setText("");
		prodStockField.setText("");
		fetchTableData();
		informationLbl.setVisible(false);
		searchField.setForeground(Color.GRAY);
		searchField.setText("Search product..");
	}

	public void fetchTableData() {
		Vector<Object> tProducts = new Vector<>();
		
		for (Model model : ProductController.getInstance().getAllProduct()) {
			Vector<String> tempStringList = new Vector<>();
			ProductModel tempProduct = (ProductModel) model;
			tempStringList.add(String.valueOf(tempProduct.getProductId()));
			tempStringList.add(tempProduct.getProductName());
			tempStringList.add(tempProduct.getProductAuthor());
			tempStringList.add(String.valueOf(tempProduct.getProductPrice()));
			tempStringList.add(String.valueOf(tempProduct.getProductStock()));
			
			tProducts.add(tempStringList);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tProducts, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.setModel(dtm);
		setColumnWidth();
	}
	
//	public void fetchSearchData(String search) {
//		Vector<Object> tProducts = new Vector<>();
//		
//		Vector<Model> tempResult = ProductController.getInstance().searchProduct(search);
//		if(tempResult.isEmpty()) {
//			JOptionPane.showMessageDialog(searchBtn, "No result");
//			return;
//		}
//		
//		for (Model model : tempResult) {
//			Vector<String> tempStringList = new Vector<>();
//			ProductModel tempProduct = (ProductModel) model;
//			
//			tempStringList.add(String.valueOf(tempProduct.getProductId()));
//			tempStringList.add(tempProduct.getProductName());
//			tempStringList.add(tempProduct.getProductAuthor());
//			tempStringList.add(String.valueOf(tempProduct.getProductPrice()));
//			tempStringList.add(String.valueOf(tempProduct.getProductStock()));
//			
//			tProducts.add(tempStringList);
//			
//			DefaultTableModel dtm = new DefaultTableModel(tProducts, tHeader) {
//				public boolean isCellEditable(int row, int column) {
//					return false;
//				};
//			};
//			
//			informationLbl.setVisible(true);
//			
//			table.setModel(dtm);
//			setColumnWidth();
//		}
//	}
	
	public void getSearchedProduct(int productId) {
		Vector<Object> tProducts = new Vector<>();
		
		ProductModel tempResult = ProductController.getInstance().searchProduct(productId);
		if(tempResult == null) {
			JOptionPane.showMessageDialog(searchBtn, "No result");
			return;
		}
		
		Vector<String> tempStringList = new Vector<>();
		tempStringList.add(String.valueOf(tempResult.getProductId()));
		tempStringList.add(tempResult.getProductName());
		tempStringList.add(tempResult.getProductAuthor());
		tempStringList.add(String.valueOf(tempResult.getProductPrice()));
		tempStringList.add(String.valueOf(tempResult.getProductStock()));
		
		tProducts.add(tempStringList);
		
		DefaultTableModel dtm = new DefaultTableModel(tProducts, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		informationLbl.setVisible(true);
		table.setModel(dtm);
		setColumnWidth();
	}
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(240);
		table.getColumnModel().getColumn(2).setPreferredWidth(240);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
}
