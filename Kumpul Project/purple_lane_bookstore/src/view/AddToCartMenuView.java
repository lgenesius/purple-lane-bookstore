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

import controller.CartController;
import controller.ProductController;
import controller.UserController;
import model.Model;
import model.ProductModel;

public class AddToCartMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	private JLabel prodIdLbl, prodQuantityLbl, informationLbl;
	private JTextField prodIdField, prodQuantityField, searchField;
	private JButton addToCartBtn, resetBtn, backBtn, searchBtn, viewCartBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, buttonPanel, backPanel, titlePanel, informationPanel, searchPanel;
	
	public AddToCartMenuView() {
		super();
		width = 600;
		height = 400;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Book List");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		prodIdLbl = new JLabel("Product ID");
		prodQuantityLbl = new JLabel("Product Quantity");
		informationLbl = new JLabel("Click Reset button to view all product");
		informationLbl.setForeground(Color.BLUE);
		informationLbl.setVisible(false);
		
		prodIdField = new JTextField();
		prodQuantityField = new JTextField();
		searchField = new JTextField();
		searchField.setForeground(Color.GRAY);
		searchField.setText("Search product..");
		
		viewCartBtn = new JButton("View Cart");
		addToCartBtn = new JButton("Add to Cart");
		resetBtn = new JButton("Reset");
		backBtn = new JButton("Back");
		searchBtn = new JButton("Search");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2,1,0,30));
		bottomPanel = new JPanel(new GridLayout(0,1,0,5));
		
		searchPanel = new JPanel(new GridLayout(1,2));
		informationPanel = new JPanel();
		titlePanel = new JPanel();
		backPanel = new JPanel();
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
		backPanel.add(backBtn);
		searchPanel.add(searchField);
		searchPanel.add(searchBtn);
		topPanel.add(searchPanel, BorderLayout.EAST);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		centerPanel.add(scrollPane);
		dataPanel.add(prodIdLbl);
		dataPanel.add(prodIdField);
		dataPanel.add(prodQuantityLbl);
		dataPanel.add(prodQuantityField);
		centerPanel.add(dataPanel);
		
		buttonPanel.add(addToCartBtn);
		buttonPanel.add(viewCartBtn);
		buttonPanel.add(resetBtn);
		informationPanel.add(informationLbl);
		bottomPanel.add(informationPanel);
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
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				UserController.getInstance().viewCustomerMenu();				
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
		
		addToCartBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CartController.getInstance().addToCart(prodIdField.getText(), prodQuantityField.getText())) {
					resetAll();
				}
				
				JOptionPane.showMessageDialog(addToCartBtn, CartController.getInstance().getMessage());
				System.out.println(CartController.getInstance().getMessage());
			}
		});
		
		viewCartBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CartController.getInstance().viewManageCartMenu();
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
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
	}
	
	public void getSearchedProduct(int productId) {
		Vector<Object> tProducts = new Vector<>();
		
		ProductModel tempResult = ProductController.getInstance().searchProduct(productId);
		if(tempResult == null || tempResult.getProductStock() == 0) {
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
	
	public void fetchTableData() {
		Vector<Object> tProducts = new Vector<>();
		
		for (Model model : ProductController.getInstance().getAllProduct()) {
			Vector<String> tempStringList = new Vector<>();
			ProductModel tempProduct = (ProductModel) model;
			if(tempProduct.getProductStock() == 0) continue;
			
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
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(240);
		table.getColumnModel().getColumn(2).setPreferredWidth(240);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
	
	public void resetAll() {
		prodIdField.setText("");
		prodQuantityField.setText("");
		fetchTableData();
		informationLbl.setVisible(false);
		searchField.setForeground(Color.GRAY);
		searchField.setText("Search product..");
	}

}
