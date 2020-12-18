package controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Vector;

import model.CartModel;
import model.Model;
import model.PromoModel;
import model.TransactionDetailModel;
import model.TransactionModel;
import view.PaymentMenuView;
import view.TransactionDetailMenuView;
import view.TransactionHistoryMenuView;
import view.TransactionReportMenuView;

public class TransactionController extends Controller{
	private TransactionModel activeTransaction;
	private static TransactionController controller;
	
	private int transactionId;
	private String transactionDate;
	private String promoCode;
	
	public void setTransDetailInfo(int transactionId, String transactionDate, String promoCode) {
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.promoCode = promoCode;
	}
	
	public int getTransId() {
		return transactionId;
	}
	
	public String getTransDate() {
		return transactionDate;
	}
	
	public String getPromoCode() {
		return promoCode;
	}
	
	private TransactionController() {
		activeTransaction = new TransactionModel();
	}
	
	public static synchronized TransactionController getInstance() {
		return controller = (controller == null) ? new TransactionController() : controller;
	}
	
	public void viewTransactionHistoryMenu() {
		new TransactionHistoryMenuView().display();
	}
	
	public void viewTransactionDetailMenu() {
		new TransactionDetailMenuView().display();
	}
	
	public void viewTransactionReportMenu() {
		new TransactionReportMenuView().display();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public boolean processTransaction(String paymentType, String promoCode, String cardNumber) {
		if(isCardNumberRight(cardNumber)) {
			TransactionModel transModel = processPayment(paymentType, cardNumber, promoCode);
			processTransactionDetail(transModel.getTransactionId());
			message = "Transaction Success! Your cart is now empty";
			return true;
		}
		
		return false;
	}
	
	private void processTransactionDetail(int transId) {
		TransactionDetailModel tdm = new TransactionDetailModel();
		tdm.setTransactionId(transId);
		for (CartModel cart : CartController.getInstance().getSelectedCart()) {
			tdm.setProductId(cart.getProductId());
			tdm.setProductQuantity(cart.getQuantity());
			tdm.createTransactionDetail();
			
			ProductController.getInstance().reduceStock(cart.getProductId(), cart.getQuantity());
		}
		
		CartController.getInstance().getSelectedCart().clear();
		CartController.getInstance().setTotalSelectedToZero();
		CartController.getInstance().getCartList().clear();
		CartController.getInstance().deleteCart(UserController.getInstance().getModel().getUserId());
	}
	
	public boolean isCardNumberRight(String cardNumber) {
		if(cardNumber.equals("")) {
			message = "Cannot process! Card Number cannot empty!";
			return false;
		}
		
		try {
			long numericCard = Long.parseLong(cardNumber);
		} catch (NumberFormatException e) {
			message = "Cannot process! Card Number must be numeric!";
			return false;
		}
		
		if(cardNumber.length() < 16) {
			message = "Cannot process! Card Number must be at least 16 digits";
			return false;
		}
		
		return true;
	}
	
	private TransactionModel processPayment(String paymentType, String cardNumber, String promoCode) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return insert(paymentType, cardNumber, promoCode, timestamp);
	}
	
	private TransactionModel insert(String paymentType, String cardNumber, String promoCode, Timestamp ts) {
		TransactionModel transModel = new TransactionModel();
		transModel.setPaymentType(paymentType);
		transModel.setCardNumber(cardNumber);
		transModel.setPromoCode(promoCode);
		transModel.setTransactionDate(ts);
		transModel.setUserId(UserController.getInstance().getModel().getUserId());
		transModel.createTransaction();
		return transModel.getOneTransaction();
	}
	
	public long usePromoCode(String promoCode, long totalPrice) {
		PromoModel promo = PromoController.getInstance().getOnePromo(promoCode);
		
		if(totalPrice > (long) promo.getPromoDiscount()) {
			return totalPrice - (long) promo.getPromoDiscount();
		} else {
			return 0;
		}
	}
	
	public Vector<Model> getTransactionHistory() {
		TransactionModel transModel = new TransactionModel();
		transModel.setUserId(UserController.getInstance().getModel().getUserId());
		return transModel.getTransactionHistory();
	}
	
	public Vector<Model> getTransactionDetail(int transactionId) {
		TransactionDetailModel transDetModel = new TransactionDetailModel();
		return transDetModel.getAllTransactionDetail(transactionId);
	}
	
	public Vector<Model> getTransactionReport(String month, String year) {
		int yearNumber = Integer.parseInt(year);
		int monthNumber = getMonthNumeric(month);
		if(monthNumber != 0) {
			TransactionModel transModel = new TransactionModel();
			return transModel.getTransactionReport(monthNumber, yearNumber);
		}
		
		return new Vector<>();
	}
	
	private int getMonthNumeric(String month) {
		if(month.equals("January")) return 1;
		if(month.equals("February")) return 2;
		if(month.equals("March")) return 3;
		if(month.equals("April")) return 4;
		if(month.equals("May")) return 5;
		if(month.equals("June")) return 6;
		if(month.equals("July")) return 7;
		if(month.equals("August")) return 8;
		if(month.equals("September")) return 9;
		if(month.equals("October")) return 10;
		if(month.equals("November")) return 11;
		if(month.equals("Desember")) return 12;
		
		return 0;
	}
	
	private String getMonthName(int month) {
		if(month == 1) return "January";
		if(month == 2) return "February";
		if(month == 3) return "March";
		if(month == 4) return "April";
		if(month == 5) return "May";
		if(month == 6) return "June";
		if(month == 7) return "July";
		if(month == 8) return "August";
		if(month == 9) return "September";
		if(month == 10) return "October";
		if(month == 11) return "November";
		if(month == 12) return "Desember";
		
		return "January";
	}
	
	public String getCurrentMonth() {
		LocalDate currentdate = LocalDate.now();
		int currentMonth = currentdate.getMonthValue();
		return getMonthName(currentMonth);
	}
	
	public String getCurrentYear() {
		LocalDate currentdate = LocalDate.now();
		int currentYear = currentdate.getYear();
		return String.valueOf(currentYear);
	}
}
