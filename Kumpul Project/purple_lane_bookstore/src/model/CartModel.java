package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CartModel extends Model{
	private int userId = 0, productId = 0, quantity = 0;
	
	public CartModel() {
		tableName = "carts";
	}
	
	public CartModel(int userId, int productId, int quantity) {
		tableName = "carts";
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void insert() {
		String query = String.format("INSERT INTO %s VALUES(?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		String query = String.format("UPDATE %s SET productQuantity=? WHERE userId=? AND productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, quantity);
			ps.setInt(2, userId);
			ps.setInt(3, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete() {
		String query = String.format("DELETE FROM %s WHERE userId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<CartModel> getAllCart(int userId) {
		Vector<CartModel> tempCartList = new Vector<>();
		
		String query = String.format("SELECT * FROM %s WHERE userId=%d", tableName, userId);
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				int tempUserId = rs.getInt("userId");
				int tempProductId = rs.getInt("productId");
				int quantity = rs.getInt("productQuantity");
				
				CartModel tempCart = new CartModel();
				tempCart.setUserId(tempUserId);
				tempCart.setProductId(tempProductId);
				tempCart.setQuantity(quantity);
				
				tempCartList.add(tempCart);
			}
			return tempCartList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CartModel getOneCart(int userId, int productId) {
		String query = String.format("SELECT * FROM %s WHERE userId=%d AND productId=%d", tableName, userId, productId);
		ResultSet rs = con.executeQuery(query);
		
		CartModel cartModel = new CartModel();
		
		try {
			while(rs.next()) {
				int tempUserId = rs.getInt("userId");
				int tempProductId = rs.getInt("productId");
				int quantity = rs.getInt("productQuantity");
				
				cartModel.setUserId(tempUserId);
				cartModel.setProductId(tempProductId);
				cartModel.setQuantity(quantity);
				return cartModel;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

