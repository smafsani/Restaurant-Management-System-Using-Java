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

public class CustomerPanel extends JFrame{

	public int x = 150, y = 40;
	public button foodOrder, addProduct, addUser, addEmployee, products, usersLabel, ordersBtn, salaries;
	public Color col1 = new Color(0, 127, 224);
	public Color col2 = new Color(0, 70, 123);
	public JPanel containerPanel;
	private JLabel lblNewLabel;
	private String globalUsername, globalPassword;
	private button profile;
	public CustomerPanel(String s1, String s2) {
		globalUsername = s1;
		globalPassword = s2;
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
				new Main().setVisible(true);
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
				
				clearContainerPanel();
			}
			
		});
		foodOrder.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		foodOrder.setForeground(Color.WHITE);
		foodOrder.setFont(new Font("SansSerif", Font.BOLD, 16));
		foodOrder.setHorizontalAlignment(SwingConstants.CENTER);
		foodOrder.setBounds(4, 15, 150, 40);
		foodOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		barPanel.add(foodOrder);
		
		
		products = new button("Products", col1, col2);
		products.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		products.setHorizontalAlignment(SwingConstants.CENTER);
		products.setForeground(Color.WHITE);
		products.setFont(new Font("SansSerif", Font.BOLD, 16));
		products.setBounds(4, 80, 150, 40);
		products.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		products.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				products.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				products.setSize(x+10, y);
				setButtons("pr");
				
				JPanel pan = new productsCustPanel().returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
				
			}
			
		});
		barPanel.add(products);
		
		profile = new button("Profile", col1, col2);
		profile.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		profile.setHorizontalAlignment(SwingConstants.CENTER);
		profile.setForeground(Color.WHITE);
		profile.setFont(new Font("SansSerif", Font.BOLD, 16));
		profile.setBounds(4, 145, 150, 40);
		profile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				profile.hover(new Color(1, 57, 99), new Color(1, 57, 99));
				profile.setSize(x+10, y);
				setButtons("pro");
				
				JPanel pan = new ProfilePanel(globalUsername, globalPassword).returnPanel();
				pan.setBounds(10, 10, 690, 500);
				clearContainerPanel();
				containerPanel.add(pan);
			}
			
		});
		barPanel.add(profile);

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
		if(!s.equals("pr")) {
			products.setSize(x, y);
			products.hover(col1, col2);
		}
		if(!s.equals("pro")) {
			profile.setSize(x, y);
			profile.hover(col1, col2);
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
					new CustomerPanel("", "").setVisible(true);
				}catch (Exception e) {
				e.printStackTrace();}
				
			}
		});
	}
}
