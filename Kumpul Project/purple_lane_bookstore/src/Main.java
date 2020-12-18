import controller.UserController;

public class Main {

	public Main() {
		UserController.getInstance().viewStartMenu();
	}

	public static void main(String[] args) {
		new Main();

	}

}
