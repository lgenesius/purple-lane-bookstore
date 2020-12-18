package view;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.PromoController;
import controller.UserController;
import model.Model;
import model.PromoModel;

public class ManagePromoMenuView extends View{
	private JTable table;
	private JScrollPane scrollPane;
	private Vector<String> tHeader;
	private JLabel promIdLbl, promCodeLbl, promDiscLbl, promNoteLbl;
	private JTextField promIdField, promCodeField, promDiscField;
	private JTextArea promNoteArea;
	private JButton insertBtn, updateBtn, deleteBtn, resetBtn, logoutBtn;
	private JPanel topPanel, centerPanel, bottomPanel, dataPanel, buttonPanel, logoutPanel, titlePanel;
	
	public ManagePromoMenuView() {
		super();
		width = 550;
		height = 650;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Promotion Team View");
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		tHeader = new Vector<>();
		
		promIdLbl = new JLabel("Promo ID");
		promCodeLbl = new JLabel("Promo Code");
		promDiscLbl = new JLabel("Product Discount");
		promNoteLbl = new JLabel("Product Note");
		
		promIdField = new JTextField();
		promIdField.setEditable(false);
		promCodeField = new JTextField();
		promDiscField = new JTextField();
		promNoteArea = new JTextArea();
		
		logoutBtn = new JButton("Logout");
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		resetBtn = new JButton("Reset");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2,1,0,60));
		bottomPanel = new JPanel(new GridLayout(0,1,0,5));
		
		titlePanel = new JPanel();
		logoutPanel = new JPanel();
		dataPanel = new JPanel(new GridLayout(0,2,0,20));
		buttonPanel = new JPanel();
	}

	@Override
	public void addComponents() {
		tHeader.add("ID");
		tHeader.add("Code");
		tHeader.add("Discount");
		tHeader.add("Note");
		
		titlePanel.add(titleForm);
		logoutPanel.add(logoutBtn);
		topPanel.add(logoutPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		centerPanel.add(scrollPane);
		dataPanel.add(promIdLbl);
		dataPanel.add(promIdField);
		dataPanel.add(promCodeLbl);
		dataPanel.add(promCodeField);
		dataPanel.add(promDiscLbl);
		dataPanel.add(promDiscField);
		dataPanel.add(promNoteLbl);
		dataPanel.add(promNoteArea);
		centerPanel.add(dataPanel);
		
		buttonPanel.add(insertBtn);
		buttonPanel.add(updateBtn);
		buttonPanel.add(deleteBtn);
		buttonPanel.add(resetBtn);
		bottomPanel.add(new JPanel());
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
				promIdField.setText(table.getValueAt(selectedRow, 0).toString());
				promCodeField.setText(table.getValueAt(selectedRow, 1).toString());
				promDiscField.setText(table.getValueAt(selectedRow, 2).toString());
				promNoteArea.setText(table.getValueAt(selectedRow, 3).toString());
			}
			
		});
		
		insertBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(PromoController.getInstance().createPromo(promIdField.getText(), promCodeField.getText(), promDiscField.getText(), promNoteArea.getText())) {
					resetAll();
					fetchTableData();
				}
				
				System.out.println(PromoController.getInstance().getMessage());
				JOptionPane.showMessageDialog(insertBtn, PromoController.getInstance().getMessage());
			}
		});
		
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(PromoController.getInstance().updatePromo(promIdField.getText(), promCodeField.getText(), promDiscField.getText(), promNoteArea.getText())) {
					resetAll();
					fetchTableData();
				}
				
				System.out.println(PromoController.getInstance().getMessage());
				JOptionPane.showMessageDialog(insertBtn, PromoController.getInstance().getMessage());
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(deleteBtn, "Do you want to delete ?", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
				if(input == 0) {
					if(PromoController.getInstance().deletePromo(promIdField.getText())) {
						resetAll();
						fetchTableData();
					}
					
					System.out.println(PromoController.getInstance().getMessage());
					JOptionPane.showMessageDialog(insertBtn, PromoController.getInstance().getMessage());
				}
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});

	}
	
	public void resetAll() {
		promIdField.setText("");
		promCodeField.setText("");
		promDiscField.setText("");
		promNoteArea.setText("");
	}
	
	public void fetchTableData() {
		Vector<Object> tPromos = new Vector<>();
		
		for (Model model : PromoController.getInstance().getAllPromo()) {
			Vector<String> tempStringList = new Vector<>();
			PromoModel tempPromo = (PromoModel) model;
			tempStringList.add(String.valueOf(tempPromo.getPromoId()));
			tempStringList.add(tempPromo.getPromoCode());
			tempStringList.add(String.valueOf(tempPromo.getPromoDiscount()));
			tempStringList.add(tempPromo.getPromoNote());
			
			tPromos.add(tempStringList);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(tPromos, tHeader) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.setModel(dtm);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

}
