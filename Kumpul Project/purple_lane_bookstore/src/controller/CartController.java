package controller;

import java.util.Vector;

import model.CartModel;
import model.Model;
import model.ProductModel;
import view.CheckoutMenuView;
import view.ManageCartMenuView;
import view.ManagerMenuView;
import view.PaymentMenuView;

public class CartController extends Controller{
	private Vector<CartModel> cartList;
	private Vector<CartModel> selectedCart;
	private int totalSelectedCart = 0;
	
	private static CartController controller;
	
	private CartController() {
		cartList = new Vector<>();
		selectedCart = new Vector<>();
	}
	
	public static synchronized CartController getInstance() {
		return controller = (controller == null) ? new CartController() : controller;
	}
	
	public void setTotalSelectedToZero() {
		totalSelectedCart = 0;
	}
	
	public int getTotalSelectedCart() {
		return totalSelectedCart;
	}

	public Vector<CartModel> getCartList() {
		return cartList;
	}

	public void setCartList(Vector<CartModel> cartList) {
		this.cartList = cartList;
	}

	public Vector<CartModel> getSelectedCart() {
		return selectedCart;
	}

	public void setSelectedCart(Vector<CartModel> selectedCart) {
		this.selectedCart = selectedCart;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void viewManageCartMenu() {
		new ManageCartMenuView().display();
	}
	
	public void viewCheckOutMenu() {
		new CheckoutMenuView().display();
	}
	
	public void viewPaymentMenu() {
		new PaymentMenuView().display();
	}
	
	public boolean addToCart(String productId, String quantity) {
		if(CartValidation(productId, quantity)) {
			int tempUserId = UserController.getInstance().getModel().getUserId();
			int tempProductId = Integer.parseInt(productId);
			int tempQuantity = Integer.parseInt(quantity);
			CartModel cart = getOneCart(tempUserId, tempProductId);
			if(cart == null) {
				//insert
				insert(tempUserId, tempProductId, tempQuantity);
				cartList.add(new CartModel(tempUserId, tempProductId, tempQuantity));
			} else {
				ProductModel product = ProductController.getInstance().getOneProduct(tempProductId);
				if((tempQuantity + cart.getQuantity()) > product.getProductStock()) {
					message = "The inputed quantity must be below product stock!";
					return false;
				}
				//update
				tempQuantity = tempQuantity + cart.getQuantity();
				update(tempUserId, tempProductId, tempQuantity);
				setCartListQuantity(tempUserId, tempProductId, tempQuantity);
			}
			
			message = "Add to cart success!";
			return true;
		}
		
		return false;
	}
	
	public boolean updateCart(String productId, String quantity) {
		if(CartValidation(productId, quantity)) {
			int tempUserId = UserController.getInstance().getModel().getUserId();
			int tempProductId = Integer.parseInt(productId);
			int tempQuantity = Integer.parseInt(quantity);
			
			CartModel cart = getOneCart(tempUserId, tempProductId);
			if(cart == null) {
				message = "There is no this Product ID in this cart";
				return false;
			}
			//update
			update(tempUserId, tempProductId, tempQuantity);
			setCartListQuantity(tempUserId, tempProductId, tempQuantity);
			
			message = "Update cart success!";
			return true;
		}
		
		return false;
	}
	
	public void deleteCart(int userId) {
		delete(userId);
	}
	
	public boolean CartValidation(String productId, String quantity) {
		if(!checkEmptyVal(productId, quantity)) {
			return false;
		}
		if(!checkNumVal(productId, quantity)) {
			return false;
		}
		
		return true;
	}
	
	public void processSelectedCart(String productId, String quantity, boolean isSelected) {
		int tempProductId = Integer.parseInt(productId);
		int tempQuantity = Integer.parseInt(quantity);
		if(isSelected) {
			selectedCart.add(new CartModel(UserController.getInstance().getModel().getUserId(), tempProductId, tempQuantity));
			totalSelectedCart++;
		}
	}
	
	public void removeSelectedCart() {
		selectedCart.clear();
		totalSelectedCart = 0;
	}
	
	public boolean checkEmptyVal(String productId, String quantity) {
		if(productId.equals("")) {
			message = "Cannot process! Product ID cannot empty!";
			return false;
		}
		if(quantity.equals("")) {
			message = "Cannot process! Product Quantity cannot empty!";
			return false;
		}
		
		return true;
	}
	
	public boolean checkNumVal(String productId, String quantity) {
		int tempProductId =0, tempProductQuant = 0;
		try {
			tempProductId = Integer.parseInt(productId);
		} catch (NumberFormatException e) {
			message = "Cannot process! Product Id must be numeric!";
			return false;
		}
		try {
			tempProductQuant = Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			message = "Cannot process! Product Quantity must be numeric!";
			return false;
		}
		
		if(tempProductQuant <= 0) {
			message = "Cannot process! Product Quantity must be more than zero!";
			return false;
		}
		
		if(!checkIdAndQuantity(tempProductId, tempProductQuant)) {
			return false;
		}
		
		return true;
	}
	
	public boolean checkIdAndQuantity(int productId, int quantity) {
		ProductModel product = ProductController.getInstance().getOneProduct(productId);
		if(product == null) {
			message = "There is no product with this ID";
			return false;
		}
		if(quantity > product.getProductStock()) {
			message = "Product quantity must not more than the product stock!";
			return false;
		}
		return true;
	}
	
	private void setCartListQuantity(int userId, int productId, int quantity) {
		for (CartModel cartModel : cartList) {
			if(cartModel.getUserId() == userId && cartModel.getProductId() == productId) {
				cartModel.setQuantity(quantity);
				break;
			}	
		}
	}
	
	public void initCartList(int userId) {
		cartList = getAllCart(userId);
	}
	
	private void insert(int userId, int productId, int quantity) {
		CartModel tempCart = new CartModel();
		tempCart.setUserId(userId);
		tempCart.setProductId(productId);
		tempCart.setQuantity(quantity);
		tempCart.insert();
	}
	
	private void update(int userId, int productId, int quantity) {
		CartModel tempCart = new CartModel();
		tempCart.setUserId(userId);
		tempCart.setProductId(productId);
		tempCart.setQuantity(quantity);
		tempCart.update();
	}
	
	private void delete(int userId) {
		CartModel tempCart = new CartModel();
		tempCart.setUserId(userId);
		tempCart.delete();
	}
	
	public Vector<CartModel> getAllCart(int userId) {
		CartModel tempCart = new CartModel();
		return tempCart.getAllCart(userId);
	}
	
	public CartModel getOneCart(int userId, int productId) {
		CartModel tempCart = new CartModel();
		return tempCart.getOneCart(userId, productId);
	}
}
