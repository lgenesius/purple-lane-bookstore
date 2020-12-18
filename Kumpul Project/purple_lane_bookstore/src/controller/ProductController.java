package controller;

import java.util.Vector;

import model.Model;
import model.ProductModel;
import view.AddToCartMenuView;
import view.ManageProductMenuView;

public class ProductController extends Controller{
	private ProductModel product;
	private static ProductController controller;

	private ProductController() {
		product = new ProductModel();
	}
	
	public void viewManageProductMenu() {
		new ManageProductMenuView().display();
	}
	
	public void viewAddToCartMenu() {
		new AddToCartMenuView().display();
	}
	
	public static synchronized ProductController getInstance() {
		return controller = (controller == null) ? new ProductController() : controller;
	}

	public Vector<Model> getAllProduct() {
		return product.getAllProduct();
	}
	
//	public Vector<Model> searchProduct(String search) {
//		return product.getSearchData(search);
//	}
	
	public ProductModel getOneProduct(int productId) {
		return product.getOneProduct(productId);
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public boolean createProduct(String productId, String productName, String productAuthor, String productPrice, String productStock) {
		if(createProductVal(productId, productName, productAuthor, productPrice, productStock)) {
			int tempProdPrice = Integer.parseInt(productPrice);
			int tempProdStock = Integer.parseInt(productStock);
			insert(productName, productAuthor, tempProdPrice, tempProdStock);
			message = "Success Insert";
			return true;
		}
		
		return false;
	}
	
	public boolean updateProduct(String productId, String productName, String productAuthor, String productPrice, String productStock) {
		if(updateProductVal(productId, productName, productAuthor, productPrice, productStock)) {
			int tempProdId = Integer.parseInt(productId);
			int tempProdPrice = Integer.parseInt(productPrice);
			int tempProdStock = Integer.parseInt(productStock);
			update(tempProdId, productName, productAuthor, tempProdPrice, tempProdStock);
			message = "Success Update";
			return true;
		}

		return false;
	}
	
	public boolean deleteProduct(String productId) {
		if(deleteProductVal(productId)) {
			int tempProdId = Integer.parseInt(productId);
			delete(tempProdId);
			message = "Success Delete";
			return true;
		}
		
		return false;
	}
	
	public ProductModel searchProduct(int productId) {
		return getOneProduct(productId);
	}
	
	private boolean deleteProductVal(String productId) {
		if(productId.equals("")) {
			message = "Cannot process! Product ID cannot empty!";
			return false;
		}
		return true;
	}
	
	private boolean updateProductVal(String productId, String productName, String productAuthor, String productPrice, String productStock) {
		if(productId.equals("")) {
			message = "Cannot process! Product ID cannot empty!";
			return false;
		}
		if(!checkIsEmptyVal(productName, productAuthor, productPrice, productStock)) {
			return false;
		}
		if(!checkZeroAndNumVal(productPrice, productStock)) {
			return false;
		}
		
		return true;
	}
	
	private boolean createProductVal(String productId, String productName, String productAuthor, String productPrice, String productStock) {
		if(!productId.equals("")) {
			message = "Cannot process! Product ID must be empty!";
			return false;
		}
		if(!checkIsEmptyVal(productName, productAuthor, productPrice, productStock)) {
			return false;
		}
		if(!checkZeroAndNumVal(productPrice, productStock)) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkIsEmptyVal(String productName, String productAuthor, String productPrice, String productStock) {
		if(productName.equals("")) {
			message = "Cannot process! Product Name cannot empty!";
			return false;
		}
		if(productAuthor.equals("")) {
			message = "Cannot process! Product Author cannot empty!";
			return false;
		}
		if(productPrice.equals("")) {
			message = "Cannot process! Product Price cannot empty!";
			return false;
		}
		if(productStock.equals("")) {
			message = "Cannot process! Product Stock cannot empty!";
			return false;
		}
			
		return true;
	}
	
	private boolean checkZeroAndNumVal(String productPrice, String productStock) {
		int tempPrice;
		try {
			tempPrice = Integer.parseInt(productPrice);
		} catch (NumberFormatException e) {
			message = "Cannot process! Product Price must be numeric!";
			return false;
		}
		if(tempPrice <= 0) {
			message = "Cannot process! Product Price must be more than zero!";
			return false;
		}
		int tempStock;
		try {
			tempStock = Integer.parseInt(productStock);
		} catch (NumberFormatException e) {
			message = "Cannot process! Product Stock must be numeric!";
			return false;
		}
		if(tempStock <= 0) {
			message = "Cannot process! Product Stock must be more than zero!";
			return false;
		}
		
		return true;
	}
	
	public boolean searchValidation(String search) {
		if(search.equals("")) {
			message = "Cannot process! Search cannot empty!";
			return false;
		}
		try {
			int searchInt = Integer.parseInt(search);
		} catch (NumberFormatException e) {
			message = "No Result";
			return false;
		}
		
		return true;
	}

	private void insert(String productName, String productAuthor, int productPrice, int productStock) {
		ProductModel tempProduct = new ProductModel();
		tempProduct.setProductName(productName);
		tempProduct.setProductAuthor(productAuthor);
		tempProduct.setProductPrice(productPrice);
		tempProduct.setProductStock(productStock);
		tempProduct.createProduct();
	}
	
	private void update(int productId, String productName, String productAuthor, int productPrice, int productStock) {
		ProductModel tempProduct = new ProductModel();
		tempProduct.setProductId(productId);
		tempProduct.setProductName(productName);
		tempProduct.setProductAuthor(productAuthor);
		tempProduct.setProductPrice(productPrice);
		tempProduct.setProductStock(productStock);
		tempProduct.updateProduct();
	}
	
	private void delete(int productId) {
		ProductModel tempProduct = new ProductModel();
		tempProduct.setProductId(productId);
		tempProduct.deleteProduct();
	}
	
	public void reduceStock(int productId, int productQuantity) {
		ProductModel productModel = new ProductModel();
		int currStock = getOneProduct(productId).getProductStock() - productQuantity;
		productModel.setProductId(productId);
		productModel.setProductStock(currStock);
		productModel.updateStock();
	}
}
