package controller;

import java.util.Vector;

import model.Model;
import model.PromoModel;
import view.ManagePromoMenuView;

public class PromoController extends Controller{
	private PromoModel promo;
	private static PromoController controller;
	
	private PromoController() {
		promo = new PromoModel();
	}
	
	public static synchronized PromoController getInstance() {
		return controller = (controller == null) ? new PromoController() : controller;
	}
	
	public void viewManagePromoMenu() {
		new ManagePromoMenuView().display();
	}

	public Vector<Model> getAllPromo() {
		return promo.getAllPromo();
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public boolean createPromo(String promoId, String promoCode, String promoDiscount, String promoNote) {
		if(createPromoVal(promoId, promoCode, promoDiscount, promoNote)) {
			int tempPromoDisc = Integer.parseInt(promoDiscount);
			insert(promoCode, tempPromoDisc, promoNote);
			message = "Success insert";
			return true;
		}
		
		return false;
	}
	
	public boolean updatePromo(String promoId, String promoCode, String promoDiscount, String promoNote) {
		if(updatePromoVal(promoId, promoCode, promoDiscount, promoNote)) {
			int tempPromoId = Integer.parseInt(promoId);
			int tempPromoDisc = Integer.parseInt(promoDiscount);
			update(tempPromoId, promoCode, tempPromoDisc, promoNote);
			message = "Success update";
			return true;
		}
		
		return false;
	}
	
	public boolean deletePromo(String promoId) {
		if(!promoId.equals("")) {
			int tempPromoId = Integer.parseInt(promoId);
			delete(tempPromoId);
			message = "Success delete";
			return true;
		}
		
		message = "Cannot process! Promo ID cannot empty!";
		return false;
	}
	
	private boolean updatePromoVal(String promoId, String promoCode, String promoDiscount, String promoNote) {
		if(promoId.equals("")) {
			message = "Cannot process! Promo ID cannot empty!";
			return false;
		}
		if(!checkIsEmptyVal(promoCode, promoDiscount, promoNote)) {
			return false;
		}
		if(!checkLessAndNumVal(promoDiscount)) {
			return false;
		}
		
		return true;
	}
	
	private boolean createPromoVal(String promoId, String promoCode, String promoDiscount, String promoNote) {
		if(!promoId.equals("")) {
			message = "Cannot process! Promo ID must be empty!";
			return false;
		}
		if(!checkIsEmptyVal(promoCode, promoDiscount, promoNote)) {
			return false;
		}
		if(!checkLessAndNumVal(promoDiscount)) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkLessAndNumVal(String promoDiscount) {
		int tempPromoDisc;
		try {
			tempPromoDisc = Integer.parseInt(promoDiscount);
		} catch (NumberFormatException e) {
			message = "Cannot process! Promo Discount must be numeric!";
			return false;
		}
		if(tempPromoDisc < 15000) {
			message = "Cannot process! Promo Discount must be more than 15000";
			return false;
		}
		
		return true;
	}
	
	private boolean checkIsEmptyVal(String promoCode, String promoDiscount, String promoNote) {
		if(promoCode.equals("")) {
			message = "Cannot process! Promo Code cannot empty!";
			return false;
		}
		if(promoDiscount.equals("")) {
			message = "Cannot process! Promo Discount cannot empty!";
			return false;
		}
		if(promoNote.equals("")) {
			message = "Cannot process! Promo Note cannot empty!";
			return false;
		}
		
		return true;
	}
	
	public PromoModel getOnePromo(String promoCode) {
		PromoModel promo = new PromoModel();
		return promo.getOnePromo(promoCode);
	}
	
	private void insert(String promoCode, int promoDiscount, String promoNote) {
		PromoModel tempPromo = new PromoModel();
		tempPromo.setPromoCode(promoCode);
		tempPromo.setPromoDiscount(promoDiscount);
		tempPromo.setPromoNote(promoNote);
		tempPromo.createPromo();
	}
	
	private void update(int promoId, String promoCode, int promoDiscount, String promoNote) {
		PromoModel tempPromo = new PromoModel();
		tempPromo.setPromoId(promoId);
		tempPromo.setPromoCode(promoCode);
		tempPromo.setPromoDiscount(promoDiscount);
		tempPromo.setPromoNote(promoNote);
		tempPromo.updatePromo();
	}
	
	private void delete(int promoId) {
		PromoModel tempPromo = new PromoModel();
		tempPromo.setPromoId(promoId);
		tempPromo.deletePromo();
	}

	public Vector<String> initPromoCode() {
		Vector<String> promoCode = new Vector<>();
		promoCode.add("");
		
		for (Model model : getAllPromo()) {
			PromoModel promo = (PromoModel) model;
			
			promoCode.add(promo.getPromoCode());
		}
		
		return promoCode;
		
	}
}
