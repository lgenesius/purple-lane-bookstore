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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CartController;
import controller.ProductController;
import controller.UserController;
import model.CartModel;
import model.Model;
import model.ProductModel;

public class ManageCartMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	private JLabel prodIdLbl, prodQuantityLbl, informationLbl;
	private JTextField prodIdField, prodQuantityField;
	private JButton checkOutBtn, addMoreBtn, updateBtn, mainMenuBtn;
	private JPanel topPanel, centerPanel, bottomPanel, buttonPanel, dataPanel, addMorePanel, titlePanel, informationPanel, mainMenuPanel;
	
	public ManageCartMenuView() {
		super();
		width = 600;
		height = 400;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel(UserController.getInstance().getModel().getUsername()+"'s Cart Menu");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		prodIdLbl = new JLabel("Product ID");
		prodQuantityLbl = new JLabel("Product Quantity");
		informationLbl = new JLabel("Click Checkout button if you ready to checkout");
		informationLbl.setForeground(Color.BLUE);
		
		prodIdField = new JTextField();
		prodQuantityField = new JTextField();
		
		checkOutBtn = new JButton("Checkout");
		addMoreBtn = new JButton("Add More");
		updateBtn = new JButton("Update");
		mainMenuBtn = new JButton("Main Menu");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2,1,0,30));
		bottomPanel = new JPanel(new GridLayout(0,1,0,2));
		
		informationPanel = new JPanel();
		titlePanel = new JPanel();
		addMorePanel = new JPanel();
		dataPanel = new JPanel(new GridLayout(0,2,0,20));
		buttonPanel = new JPanel();
		mainMenuPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("ID");
		tHeader.add("Name");
		tHeader.add("Quantity");
		tHeader.add("Total Price");
		
		titlePanel.add(titleForm);
		addMorePanel.add(addMoreBtn);
		mainMenuPanel.add(mainMenuBtn);
		topPanel.add(addMorePanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		topPanel.add(mainMenuPanel, BorderLayout.EAST);
		centerPanel.add(scrollPane);
		dataPanel.add(prodIdLbl);
		dataPanel.add(prodIdField);
		dataPanel.add(prodQuantityLbl);
		dataPanel.add(prodQuantityField);
		centerPanel.add(dataPanel);
		
		buttonPanel.add(updateBtn);
		buttonPanel.add(checkOutBtn);
		informationPanel.add(informationLbl);
		bottomPanel.add(informationPanel);
		bottomPanel.add(buttonPanel);
		
		fetchTableData();
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void fetchTableData() {
		Vector<Object> tCarts = new Vector<>();
		
		for (CartModel model : CartController.getInstance().getCartList()) {
			Vector<String> tempStringList = new Vector<>();
			CartModel tempCart =  model;
			tempStringList.add(String.valueOf(tempCart.getProductId()));
			
			ProductModel tempProduct = ProductController.getInstance().getOneProduct(tempCart.getProductId());
			tempStringList.add(tempProduct.getProductName());
			
			tempStringList.add(String.valueOf(tempCart.getQuantity()));
			
			int totalPrice = tempProduct.getProductPrice() * tempCart.getQuantity();
			tempStringList.add(String.valueOf(totalPrice));
			
			tCarts.add(tempStringList);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tCarts, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.setModel(dtm);
		setColumnWidth();
	}
	
	public void resetAll() {
		prodIdField.setText("");
		prodQuantityField.setText("");
		fetchTableData();
	}
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(240);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	@Override
	public void initListeners() {
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
				prodQuantityField.setText(table.getValueAt(selectedRow, 2).toString());
			}
		});
		
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(CartController.getInstance().updateCart(prodIdField.getText(), prodQuantityField.getText())) {
					resetAll();
				}
				
				JOptionPane.showMessageDialog(updateBtn, CartController.getInstance().getMessage());
				System.out.println(CartController.getInstance().getMessage());
			}
		});
		
		addMoreBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProductController.getInstance().viewAddToCartMenu();
			}
		});
		
		checkOutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!CartController.getInstance().getCartList().isEmpty()) {
					dispose();
					CartController.getInstance().viewCheckOutMenu();
				} else {
					String message = "Cannot checkout, your cart is empty!";
					JOptionPane.showMessageDialog(checkOutBtn, message);
				}
			}
		});
		
		mainMenuBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserController.getInstance().viewCustomerMenu();
			}
		});
	}

}
