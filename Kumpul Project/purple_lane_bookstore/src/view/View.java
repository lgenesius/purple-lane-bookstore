package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class View extends JFrame{
	protected int width;
	protected int height;
	protected JLabel titleForm;

	public View() {
		initComponents();
		addComponents();
		initListeners();
	}
	
	public void display() {
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Purple Lane Bookstore");
		
		setVisible(true);
	}

	public abstract void initComponents();
	public abstract void addComponents();
	public abstract void initListeners();
}
