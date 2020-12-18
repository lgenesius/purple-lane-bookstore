package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TransactionDetailModel extends Model{
	private int transactionId = 0, productId = 0, productQuantity = 0;
	
	public TransactionDetailModel() {
		tableName = "transaction_details";
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Vector<Model> getAllTransactionDetail(int transactionId) {
		Vector<Model> tempTransDetailList = new Vector<>();
		
		String query = String.format("SELECT * FROM %s WHERE transactionId=%d", tableName, transactionId);
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				int transId = rs.getInt("transactionId");
				int prodId = rs.getInt("productId");
				int quantity = rs.getInt("productQuantity");
				
				TransactionDetailModel tempTransDetail = new TransactionDetailModel();
				tempTransDetail.setTransactionId(transId);
				tempTransDetail.setProductId(prodId);
				tempTransDetail.setProductQuantity(quantity);
				
				tempTransDetailList.add(tempTransDetail);
			}
			return tempTransDetailList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void createTransactionDetail() {
		String query = String.format("INSERT INTO %s VALUES(?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, transactionId);
			ps.setInt(2, productId);
			ps.setInt(3, productQuantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
