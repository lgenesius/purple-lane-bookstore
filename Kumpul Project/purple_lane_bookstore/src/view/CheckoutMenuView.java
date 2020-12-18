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
import javax.swing.table.DefaultTableModel;

import controller.CartController;
import controller.ProductController;
import controller.TransactionController;
import controller.UserController;
import model.CartModel;
import model.ProductModel;

public class CheckoutMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private Vector<String> tHeader;
	
	private JLabel informationLbl;
	private JButton paymentBtn, backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, buttonPanel, informationPanel, backPanel, titlePanel;
	
	public CheckoutMenuView() {
		super();
		width = 600;
		height = 400;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel(UserController.getInstance().getModel().getUsername()+"'s Checkout Menu");
		
		initColReturnType();
		
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		informationLbl = new JLabel("Select which product(s) you want to pay, if you ready click Payment button");
		informationLbl.setForeground(Color.BLUE);
		
		paymentBtn = new JButton("Payment");
		backBtn = new JButton("Back");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,1));
		bottomPanel = new JPanel(new GridLayout(0,1,0,2));
		buttonPanel = new JPanel();
		backPanel = new JPanel();
		titlePanel = new JPanel();
		informationPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("ID");
		tHeader.add("Name");
		tHeader.add("Quantity");
		tHeader.add("Total Price");
		tHeader.add("Select");
		
		titlePanel.add(titleForm);
		backPanel.add(backBtn);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		
		centerPanel.add(scrollPane);
		
		buttonPanel.add(paymentBtn);
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
			Vector<Object> tempObjectList = new Vector<>();
			CartModel tempCart =  model;
			tempObjectList.add(String.valueOf(tempCart.getProductId()));
			
			ProductModel tempProduct = ProductController.getInstance().getOneProduct(tempCart.getProductId());
			tempObjectList.add(tempProduct.getProductName());
			
			tempObjectList.add(String.valueOf(tempCart.getQuantity()));
			
			int totalPrice = tempProduct.getProductPrice() * tempCart.getQuantity();
			tempObjectList.add(String.valueOf(totalPrice));
			
			tempObjectList.add(false);
			
			tCarts.add(tempObjectList);
		}
		
		dtm = new DefaultTableModel(tCarts, tHeader) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.setModel(dtm);
		setColumnWidth();
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
				if(table.getValueAt(selectedRow, 4).equals(false)) {
					table.setValueAt(true, selectedRow, 4);
				} else {
					table.setValueAt(false, selectedRow, 4);
				}
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CartController.getInstance().viewManageCartMenu();
			}
		});
		
		paymentBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean select = true;
				for(int i = 0; i < CartController.getInstance().getCartList().size(); i++) {
					if(table.getValueAt(i, 4).equals(false)) select = false;
					else select = true;
					
					CartController.getInstance().processSelectedCart(table.getValueAt(i, 0).toString(), table.getValueAt(i, 2).toString(), select);
				}
				
				if(CartController.getInstance().getTotalSelectedCart() != 0) {
					dispose();
					CartController.getInstance().viewPaymentMenu();
				} else {
					String message = "Cannot pay! There is no selected cart!";
					JOptionPane.showMessageDialog(paymentBtn, message);
				}
			}
		});
	}
	
	private void setColumnWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(240);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
	
	public void initColReturnType() {
		table = new JTable(dtm) {
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return String.class;
					default:
						return Boolean.class;
				}
			}
		};
	}

}
