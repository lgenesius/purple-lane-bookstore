package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProductModel extends Model{
	private int productId = 0, productPrice = 0, productStock = 0;
	private String productName = "", productAuthor = "";
	
	public ProductModel() {
		tableName = "products";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductAuthor() {
		return productAuthor;
	}

	public void setProductAuthor(String productAuthor) {
		this.productAuthor = productAuthor;
	}

	public Vector<Model> getAllProduct() {
		Vector<Model> tempProductList = new Vector<>();
		
		String query = String.format("SELECT * FROM %s", tableName);
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("productId");
				String name = rs.getString("productName");
				String author = rs.getString("productAuthor");
				int price = rs.getInt("productPrice");
				int stock = rs.getInt("productStock");
				
				ProductModel tempProduct = new ProductModel();
				tempProduct.setProductId(id);
				tempProduct.setProductName(name);
				tempProduct.setProductAuthor(author);
				tempProduct.setProductPrice(price);
				tempProduct.setProductStock(stock);
				
				tempProductList.add(tempProduct);
			}
			return tempProductList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public Vector<Model> getSearchData(String search) {
//		// you can search product name or product author
//		Vector<Model> tempProductList = new Vector<>();
//		ResultSet rs = null;
//		String query = String.format("SELECT * FROM %s WHERE productName LIKE ? OR productAuthor LIKE ?", tableName);
//		PreparedStatement ps = con.prepareStatement(query);
//		try {
//			ps.setString(1, "%" + search + "%");
//			ps.setString(2, "%" + search + "%");
//			rs = ps.executeQuery();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		
//		
//		try {
//			while(rs.next()) {
//				int id = rs.getInt("productId");
//				String name = rs.getString("productName");
//				String author = rs.getString("productAuthor");
//				int price = rs.getInt("productPrice");
//				int stock = rs.getInt("productStock");
//				
//				ProductModel tempProduct = new ProductModel();
//				tempProduct.setProductId(id);
//				tempProduct.setProductName(name);
//				tempProduct.setProductAuthor(author);
//				tempProduct.setProductPrice(price);
//				tempProduct.setProductStock(stock);
//				
//				tempProductList.add(tempProduct);
//			}
//			return tempProductList;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public ProductModel getOneProduct(int productId) {
		String query = String.format("SELECT * FROM %s WHERE productId=%d", tableName, productId);
		ResultSet rs = con.executeQuery(query);
		
		ProductModel productModel = new ProductModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("productId");
				String name = rs.getString("productName");
				String author = rs.getString("productAuthor");
				int price = rs.getInt("productPrice");
				int stock = rs.getInt("productStock");
				
				productModel.setProductId(id);
				productModel.setProductName(name);
				productModel.setProductAuthor(author);
				productModel.setProductPrice(price);
				productModel.setProductStock(stock);
				return productModel;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createProduct() {
		String query = String.format("INSERT INTO %s VALUES(null, ?, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, productName);
			ps.setString(2, productAuthor);
			ps.setInt(3, productPrice);
			ps.setInt(4, productStock);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateProduct() {
		String query = String.format("UPDATE %s SET productName=?, productAuthor=?, productPrice=?, productStock=? WHERE productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, productName);
			ps.setString(2, productAuthor);
			ps.setInt(3, productPrice);
			ps.setInt(4, productStock);
			ps.setInt(5, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateStock() {
		String query = String.format("UPDATE %s SET productStock=? WHERE productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, productStock);
			ps.setInt(2, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProduct() {
		String query = String.format("DELETE FROM %s WHERE productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
