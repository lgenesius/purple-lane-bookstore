package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PromoModel extends Model{
	private int promoId = 0, promoDiscount = 0;
	private String promoCode = "", promoNote = "";
	
	public int getPromoId() {
		return promoId;
	}

	public void setPromoId(int promoId) {
		this.promoId = promoId;
	}

	public int getPromoDiscount() {
		return promoDiscount;
	}

	public void setPromoDiscount(int promoDiscount) {
		this.promoDiscount = promoDiscount;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPromoNote() {
		return promoNote;
	}

	public void setPromoNote(String promoNote) {
		this.promoNote = promoNote;
	}

	public PromoModel() {
		tableName = "promos";
	}

	public Vector<Model> getAllPromo() {
		Vector<Model> tempPromoList = new Vector<>();
		
		String query = String.format("SELECT * FROM %s", tableName);
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("promoId");
				String code = rs.getString("promoCode");
				int discount = rs.getInt("promoDiscount");
				String note = rs.getString("promoNote");
				
				PromoModel tempPromo = new PromoModel();
				tempPromo.setPromoId(id);
				tempPromo.setPromoCode(code);
				tempPromo.setPromoDiscount(discount);
				tempPromo.setPromoNote(note);
				
				tempPromoList.add(tempPromo);
			}
			return tempPromoList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PromoModel getOnePromo(String promoCode) {
		String query = String.format("SELECT * FROM %s WHERE promoCode='%s'", tableName, promoCode);
		ResultSet rs = con.executeQuery(query);
		
		PromoModel promoModel = new PromoModel();
		
		try {
			while(rs.next()) {
				int id = rs.getInt("promoId");
				String code = rs.getString("promoCode");
				int discount = rs.getInt("promoDiscount");
				String note = rs.getString("promoNote");
				
				promoModel.setPromoId(id);
				promoModel.setPromoCode(code);
				promoModel.setPromoDiscount(discount);
				promoModel.setPromoNote(note);
				return promoModel;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void createPromo() {
		String query = String.format("INSERT INTO %s VALUES(null, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, promoCode);
			ps.setInt(2, promoDiscount);
			ps.setString(3, promoNote);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updatePromo() {
		String query = String.format("UPDATE %s SET promoCode=?, promoDiscount=?, promoNote=? WHERE promoId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, promoCode);
			ps.setInt(2, promoDiscount);
			ps.setString(3, promoNote);
			ps.setInt(4, promoId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePromo() {
		String query = String.format("DELETE FROM %s WHERE promoId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, promoId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
