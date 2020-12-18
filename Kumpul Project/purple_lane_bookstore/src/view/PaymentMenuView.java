package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.CartController;
import controller.PromoController;
import controller.TransactionController;
import controller.UserController;

public class PaymentMenuView extends View{
	private JLabel paymentTypeLbl, cardNumberLbl, promoCodeLbl;
	private JComboBox<String> paymentTypeCBox, promoCodeCBox;
	private JTextField cardNumberField;
	private JButton payBtn, backBtn;
	private JPanel topPanel, centerPanel, bottomPanel, buttonPanel, backPanel, titlePanel;
	
	public PaymentMenuView() {
		super();
		width = 400;
		height = 320;
	}

	@Override
	public void initComponents() {
		titleForm = new JLabel("Payment Menu");
		
		paymentTypeLbl = new JLabel("Payment Type");
		cardNumberLbl = new JLabel("Card Number");
		promoCodeLbl = new JLabel("Promo Code");
		
		cardNumberField = new JTextField();
		
		String []paymentType = {
			"Debit",
			"Card Credit"
		};
		
		paymentTypeCBox = new JComboBox<>(paymentType);
		promoCodeCBox = new JComboBox<>(PromoController.getInstance().initPromoCode());
		
		payBtn = new JButton("Pay");
		backBtn = new JButton("Back");
		
		topPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(0,2,0,30));
		bottomPanel = new JPanel();
		buttonPanel = new JPanel();
		backPanel = new JPanel();
		titlePanel = new JPanel();
	}

	@Override
	public void addComponents() {
		backPanel.add(backBtn);
		titlePanel.add(titleForm);
		topPanel.add(backPanel, BorderLayout.WEST);
		topPanel.add(titlePanel, BorderLayout.SOUTH);
		
		centerPanel.add(paymentTypeLbl);
		centerPanel.add(paymentTypeCBox);
		centerPanel.add(cardNumberLbl);
		centerPanel.add(cardNumberField);
		centerPanel.add(promoCodeLbl);
		centerPanel.add(promoCodeCBox);
		
		buttonPanel.add(payBtn);
		bottomPanel.add(new JPanel());
		bottomPanel.add(buttonPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void initListeners() {
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CartController.getInstance().removeSelectedCart();
				dispose();
				CartController.getInstance().viewCheckOutMenu();
			}
		});
		
		payBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(TransactionController.getInstance().processTransaction(paymentTypeCBox.getSelectedItem().toString(), promoCodeCBox.getSelectedItem().toString(), cardNumberField.getText())) {
					dispose();
					UserController.getInstance().viewCustomerMenu();
				}
				
				JOptionPane.showMessageDialog(payBtn, TransactionController.getInstance().getMessage());
				System.out.println(TransactionController.getInstance().getMessage());
			}
		});
	}

	
}
