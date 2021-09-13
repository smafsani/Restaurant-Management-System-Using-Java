package restaurant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;
import afsani.gradiant.panels.mainboard.MainBoardBG;
import javax.swing.JLabel;

public class AdminPanel extends JFrame{

	public int x = 150, y = 40;
	public button foodOrder, addProduct, addUser, addEmployee, products, usersLabel, ordersBtn, profileBtn;
	public Color col1 = new Color(0, 127, 224);
	public Color col2 = new Color(0, 70, 123);
	public JPanel containerPanel;
	private JLabel lblNewLabel;
	private String globalUsername, globalPassword, globalType;
	public AdminPanel(String s1, String s2, String s3) {
		globalUsername = s1;
		globalPassword = s2;
		globalType = s3;
		getContentPane().setLayout(null);
		setSize(910,650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initialize();
	}
	
	
	private void initialize() {
		MainBoardBG panel = new MainBoardBG();
		panel.setSize(910, 650);
		panel.setVisible(true);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		
		button bton = new button("Log out", new Color(0, 130, 220), new Color(0, 43, 90));
		bton.setBorder(new MatteBorder(1,1,1,1, Color.red));
		bton.setHorizontalAlignment(SwingConstants.CENTER);
		bton.setForeground(Color.WHITE);
		bton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bton.setBounds(815, 580, 75, 27);
		bton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new Main().frame.setVisible(true);
			}
			
			public void mousePressed(MouseEvent e) {
				bton.hover(new Color(0, 40, 88), new Color(0, 40, 88));}
			
			public void mouseReleased(MouseEvent e) {
				bton.hover(new Color(0, 130, 220), new Color(0, 43, 90));}
		});
		panel.add(bton);
		
		MainBoardBG barPanel = new MainBoardBG();
		barPanel.setBounds(5, 55, 158, 520);
		barPanel.setColors(new Color(0, 143, 255), new Color(0, 65, 115));
		barPanel.setBorder(new MatteBorder(2, 2, 2, 2, new Color(255, 255, 255)));
		panel.add(barPanel);
		barPanel.setLayout(null);
		
		foodOrder = new button("Food Order", col1, col2);
		foodOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				foodOrder.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				foodOrder.setSize(x+10, y);
				setButtons("fo");
				
				JPanel pan = new OrderFood(globalType).returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
			}
			
		});
		foodOrder.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		foodOrder.setForeground(Color.WHITE);
		foodOrder.setFont(new Font("SansSerif", Font.BOLD, 16));
		foodOrder.setHorizontalAlignment(SwingConstants.CENTER);
		foodOrder.setBounds(4, 15, 150, 40);
		foodOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		barPanel.add(foodOrder);
		
		addProduct = new button("Add Product", col1, col2);
		addProduct.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		addProduct.setHorizontalAlignment(SwingConstants.CENTER);
		addProduct.setForeground(Color.WHITE);
		addProduct.setFont(new Font("SansSerif", Font.BOLD, 16));
		addProduct.setBounds(4, 80, 150, 40);
		addProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addProduct.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				addProduct.setSize(x+10, y);
				setButtons("ap");
				JPanel pan = new addProducPanel().returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(addProduct);
		
		addUser = new button("Add User", col1, col2);
		addUser.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		addUser.setHorizontalAlignment(SwingConstants.CENTER);
		addUser.setForeground(Color.WHITE);
		addUser.setFont(new Font("SansSerif", Font.BOLD, 16));
		addUser.setBounds(4, 145, 150, 40);
		addUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addUser.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				addUser.setSize(x+10, y);
				setButtons("au");
				
				JPanel pan = new addUserPanel().returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
//				System.out.println(globalUsername);
//				System.out.println(globalPassword);
			}
			
		});
		barPanel.add(addUser);
		
		addEmployee = new button("Add Employee", col1, col2);
		addEmployee.setBorder(new MatteBorder(2, 2, 2, 2,Color.WHITE));
		addEmployee.setHorizontalAlignment(SwingConstants.CENTER);
		addEmployee.setForeground(Color.WHITE);
		addEmployee.setFont(new Font("SansSerif", Font.BOLD, 16));
		addEmployee.setBounds(4, 210, 150, 40);
		addEmployee.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addEmployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addEmployee.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				addEmployee.setSize(x+10, y);
				setButtons("ae");
				
				JPanel pan = new addEmployeePanel(globalPassword, globalType).returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(addEmployee);
		
		products = new button("Products", col1, col2);
		products.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		products.setHorizontalAlignment(SwingConstants.CENTER);
		products.setForeground(Color.WHITE);
		products.setFont(new Font("SansSerif", Font.BOLD, 16));
		products.setBounds(4, 275, 150, 40);
		products.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		products.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				products.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				products.setSize(x+10, y);
				setButtons("pr");
				
				JPanel pan = new productsPanel().returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(products);
		
		usersLabel = new button("Users", col1, col2);
		usersLabel.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		usersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersLabel.setForeground(Color.WHITE);
		usersLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		usersLabel.setBounds(4, 340, 150, 40);
		usersLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		usersLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				usersLabel.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				usersLabel.setSize(x+10, y);
				setButtons("ul");
				
				JPanel pan = new usersPanel(globalUsername, globalPassword).returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
			}
			
		});
		barPanel.add(usersLabel);
		
		ordersBtn = new button("Orders", col1, col2);
		ordersBtn.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		ordersBtn.setHorizontalAlignment(SwingConstants.CENTER);
		ordersBtn.setForeground(Color.WHITE);
		ordersBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		ordersBtn.setBounds(4, 405, 150, 40);
		ordersBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ordersBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ordersBtn.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				ordersBtn.setSize(x+10, y);
				setButtons("ord");
				
				JPanel pan = new ordersPanel().returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(ordersBtn);
		
		profileBtn = new button("Profile", col1, col2);
		profileBtn.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		profileBtn.setHorizontalAlignment(SwingConstants.CENTER);
		profileBtn.setForeground(Color.WHITE);
		profileBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		profileBtn.setBounds(4, 470, 150, 40);
		profileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profileBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				profileBtn.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				profileBtn.setSize(x+10, y);
				setButtons("pro");
				
				JPanel pan = new ProfilePanel(globalUsername, globalPassword).returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(profileBtn);
		
		containerPanel = new JPanel();
		containerPanel.setBackground(new Color(1, 57, 99));
		containerPanel.setBounds(163, 55, 710, 520);
		containerPanel.setVisible(true);
		containerPanel.setLayout(null);
		panel.add(containerPanel);
		
		lblNewLabel = new JLabel("Food Area");
		lblNewLabel.setBorder(null);
		lblNewLabel.setForeground(new Color(25, 25, 112));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 897, 53);
		panel.add(lblNewLabel);
	}
	public void setButtons(String s) {
		if(!s.equals("fo")) {
			foodOrder.setSize(x, y);
			foodOrder.hover(col1, col2);
		}
		if(!s.equals("ap")) {
			addProduct.setSize(x, y);
			addProduct.hover(col1, col2);
		}
		if(!s.equals("au")) {
			addUser.setSize(x, y);
			addUser.hover(col1, col2);
		}
		if(!s.equals("ae")) {
			addEmployee.setSize(x, y);
			addEmployee.hover(col1, col2);
		}
		if(!s.equals("pr")) {
			products.setSize(x, y);
			products.hover(col1, col2);
		}
		if(!s.equals("ul")) {
			usersLabel.setSize(x, y);
			usersLabel.hover(col1, col2);
		}
		if(!s.equals("ord")) {
			ordersBtn.setSize(x, y);
			ordersBtn.hover(col1, col2);
		}
		if(!s.equals("pro")) {
			profileBtn.setSize(x, y);
			profileBtn.hover(col1, col2);
		}
		
	}
	
	public void clearContainerPanel()
	{
		containerPanel.removeAll();
		containerPanel.revalidate();
		containerPanel.repaint();
	}
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdminPanel("admin", "password", "Admin").setVisible(true);
				}catch (Exception e) {
				e.printStackTrace();}
				
			}
		});
	}
}
