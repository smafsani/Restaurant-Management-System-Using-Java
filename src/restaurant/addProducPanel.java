package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Cursor;

public class addProducPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	private JTextField product_cat_field;
	private JTextField pr_name_field;
	private JTextField buy_price_field;
	private JTextField sell_price_field;
	private JTextField quantityField;
	private Connection con = null;
	public PreparedStatement pst = null;
	private JTextField rcvDateField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addProducPanel window = new addProducPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public addProducPanel() {
		initialize();
		con = dbconnection.connectDB();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JPanel returnPanel() {
		return panel;
	}
	public void clearFields()
	{
		pr_name_field.setText("");
		buy_price_field.setText("");
		sell_price_field.setText("");
		quantityField.setText("");
		product_cat_field.setText("");
		rcvDateField.setText("");
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 589);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Add New Product");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 102, 0));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		JLabel lblNewLab_1 = new JLabel("Product Category:");
		lblNewLab_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_1.setForeground(Color.BLACK);
		lblNewLab_1.setBounds(23, 155, 132, 25);
		panel.add(lblNewLab_1);
		
		product_cat_field = new JTextField();
		product_cat_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		product_cat_field.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		product_cat_field.setBounds(165, 153, 258, 30);
		panel.add(product_cat_field);
		product_cat_field.setColumns(10);
		
		JLabel lblNewLab_2 = new JLabel("Product Name:");
		lblNewLab_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_2.setForeground(Color.BLACK);
		lblNewLab_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_2.setBounds(40, 100, 115, 25);
		panel.add(lblNewLab_2);
		
		pr_name_field = new JTextField();
		pr_name_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		pr_name_field.setBounds(165, 98, 258, 30);
		pr_name_field.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(pr_name_field);
		pr_name_field.setColumns(10);
		
		JLabel lblNewLab_3 = new JLabel("Buying price:");
		lblNewLab_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_3.setForeground(Color.BLACK);
		lblNewLab_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_3.setBounds(40, 210, 115, 25);
		panel.add(lblNewLab_3);
		
		JLabel lblNewLab_4 = new JLabel("Selling price:");
		lblNewLab_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_4.setForeground(Color.BLACK);
		lblNewLab_4.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_4.setBounds(40, 265, 115, 25);
		panel.add(lblNewLab_4);
		
		buy_price_field = new JTextField();
		buy_price_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		buy_price_field.setColumns(10);
		buy_price_field.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		buy_price_field.setBounds(165, 208, 258, 30);
		panel.add(buy_price_field);
		
		sell_price_field = new JTextField();
		sell_price_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		sell_price_field.setColumns(10);
		sell_price_field.setBounds(165, 263, 258, 30);
		sell_price_field.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(sell_price_field);
		
		JLabel lblNewLab_5 = new JLabel("Quantity:");
		lblNewLab_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5.setForeground(Color.BLACK);
		lblNewLab_5.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5.setBounds(40, 320, 115, 25);
		panel.add(lblNewLab_5);
		
		quantityField = new JTextField();
		quantityField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		quantityField.setColumns(10);
		quantityField.setBounds(165, 318, 258, 30);
		quantityField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(quantityField);
		
		button btnReset = new button("Reset",new Color(0, 235, 0), new Color(0, 120, 0));
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnReset.hover(new Color(0, 102, 0),new Color(0, 102, 0));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnReset.hover(new Color(0, 235, 0), new Color(0, 120, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		btnReset.setForeground(Color.WHITE);
		btnReset.setHorizontalAlignment(SwingConstants.CENTER);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReset.setBounds(165, 430, 82, 30);
		panel.add(btnReset);
		
		button btnDone = new button("Done",new Color(0, 235, 0), new Color(0, 120, 0));
		btnDone.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		btnDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDone.hover(new Color(0, 102, 0),new Color(0, 102, 0));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDone.hover(new Color(0, 235, 0), new Color(0, 120, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int flag = 0;
				int q = -1;
				int buyPrice = -1, sellPrice = -1, prft = -1;
				String name = "", date="", cat="";
				try {
					name = pr_name_field.getText();
					cat = product_cat_field.getText();
					buyPrice = Integer.parseInt(buy_price_field.getText());
					sellPrice = Integer.parseInt(sell_price_field.getText());
					prft = sellPrice - buyPrice;
					q = Integer.parseInt(quantityField.getText());
					date = rcvDateField.getText();
				}
				catch(Exception e4) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
					flag = 1;
				}
				if(!cat.equals("") && q>-1 && buyPrice>-1 && sellPrice>-1 && prft>-1 &&
						!date.equals("") && !name.equals("")) {
					try {
						pst = con.prepareStatement("INSERT INTO products (product_name, category, buying_price, selling_price, profit, quantity, receiving_date)"
								+ "VALUES(?, ?, ?, ?, ?, ?, ?);");
						pst.setString(1, name);
						pst.setString(2, cat);
						pst.setDouble(3, buyPrice);
						pst.setDouble(4, sellPrice);
						pst.setDouble(5, prft);
						pst.setInt(6, q);
						pst.setString(7, date);
						pst.executeUpdate();
						clearFields();
						System.out.println(cat+"  "+name+"  "+buyPrice+"  "+sellPrice+"  "+prft+"  "+q);
						
						pst = con.prepareStatement("SELECT product_id FROM products where product_name=? and category=?");
						pst.setString(1, name);
						pst.setString(2, cat);
						ResultSet rs = pst.executeQuery();
						rs.next();
						String str = "<html><p style=\"font-size: 12px; font-family: sans-serif;\">Product Is Successfully Added.<br>The Product-ID of the product is: "
							+ "<span style=\"text-decoration:underline;font-weight:bold\">"
							+ rs.getInt("product_id") +"</span></p></html>";
						showOptionPane(str);
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
				else if(flag == 0)
					JOptionPane.showMessageDialog(null, "Invalid Input");
			}
		});
		btnDone.setForeground(Color.WHITE);
		btnDone.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDone.setHorizontalAlignment(SwingConstants.CENTER);
		btnDone.setBounds(341, 430, 82, 30);
		panel.add(btnDone);
		
		JLabel lblNewLabel_1 = new JLabel(" /= Tk");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel_1.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		lblNewLabel_1.setBounds(425, 210, 46, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel(" /= Tk");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel_1_1.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		lblNewLabel_1_1.setBounds(425, 265, 46, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLab_5_1 = new JLabel("Receiving Date:");
		lblNewLab_5_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5_1.setForeground(Color.BLACK);
		lblNewLab_5_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5_1.setBounds(40, 375, 115, 25);
		panel.add(lblNewLab_5_1);
		
		rcvDateField = new JTextField();
		rcvDateField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		rcvDateField.setColumns(10);
		rcvDateField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		rcvDateField.setBounds(165, 373, 258, 30);
		panel.add(rcvDateField);
	}
	private void showOptionPane(String s) {
		JTextPane lab = new JTextPane();
		lab.setContentType("text/html");
		lab.setSize(300, 100);
		lab.setText(s);
		lab.setOpaque(true);
		lab.setForeground(Color.BLACK);
		lab.setFont(new Font("Serif", Font.PLAIN, 18));
		UIManager.put("Button.background", new Color(0, 153, 255));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		JOptionPane.showMessageDialog(null, lab, "Successfull", JOptionPane.INFORMATION_MESSAGE);
	}
}
