package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public class TransactionModel extends Model{
	private int transactionId = 0, userId = 0;
	private String paymentType = "", promoCode = "", cardNumber = "";
	private Timestamp transactionDate;
	
	public TransactionModel() {
		tableName = "transactions";
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void createTransaction() {
		
		String query = String.format("INSERT INTO %s VALUES(null, ?, ?, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setTimestamp(1, transactionDate);
			ps.setString(2, paymentType);
			ps.setString(3, cardNumber);
			ps.setString(4, promoCode);
			ps.setInt(5, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public TransactionModel getOneTransaction() {
		TransactionModel transModel = new TransactionModel();
		ResultSet rs = null;
		String query = String.format("SELECT * FROM %s WHERE transactionDate = ?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setTimestamp(1, transactionDate);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(rs.next()) {
				transModel.setTransactionId(rs.getInt("transactionId"));
				transModel.setTransactionDate(rs.getTimestamp("transactionDate"));
				transModel.setPaymentType(rs.getString("paymentType"));
				transModel.setCardNumber(rs.getString("cardNumber"));
				transModel.setPromoCode(rs.getString("promoCode"));
				transModel.setUserId(rs.getInt("userId"));
				return transModel;
			}
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Model> getTransactionHistory() {
		Vector<Model> tempTransHistoryList = new Vector<>();
		
		ResultSet rs = null;
		
		String query = String.format("SELECT * FROM %s WHERE userId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, userId);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(rs.next()) {
				int transId = rs.getInt("transactionId");
				Timestamp ts = rs.getTimestamp("transactionDate");
				String payType = rs.getString("paymentType");
				String cardNumber = rs.getString("cardNumber");
				String promoCode = rs.getString("promoCode");
				int userId = rs.getInt("userId");
				
				TransactionModel transModel = new TransactionModel();
				transModel.setTransactionId(transId);
				transModel.setTransactionDate(ts);
				transModel.setPaymentType(payType);
				transModel.setCardNumber(cardNumber);
				transModel.setPromoCode(promoCode);
				transModel.setUserId(userId);
				
				tempTransHistoryList.add(transModel);
			}
			return tempTransHistoryList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tempTransHistoryList;
	}
	
	public Vector<Model> getTransactionReport(int month, int year) {
		Vector<Model> tempTransList = new Vector<>();
		
		ResultSet rs = null;
		String query = String.format("SELECT * FROM %s WHERE MONTH(transactionDate)=? AND YEAR(transactionDate)=?", tableName);
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, month);
			ps.setInt(2, year);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(rs.next()) {
				int transId = rs.getInt("transactionId");
				Timestamp ts = rs.getTimestamp("transactionDate");
				String payType = rs.getString("paymentType");
				String cardNumber = rs.getString("cardNumber");
				String promoCode = rs.getString("promoCode");
				int userId = rs.getInt("userId");
				
				TransactionModel transModel = new TransactionModel();
				transModel.setTransactionId(transId);
				transModel.setTransactionDate(ts);
				transModel.setPaymentType(payType);
				transModel.setCardNumber(cardNumber);
				transModel.setPromoCode(promoCode);
				transModel.setUserId(userId);
				
				tempTransList.add(transModel);
			}
			return tempTransList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tempTransList;
	}
}
