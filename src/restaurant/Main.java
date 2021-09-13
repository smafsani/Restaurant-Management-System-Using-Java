package restaurant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import afsani.gradiant.panels.login.LoginBackground;
import afsani.gradiant.label.button;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Main{
	public JTextField usernameField;
	public JPasswordField passwordField;
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	public JPanel pan1, mainPanel;
	public JLabel usernameErrorlab;
	public JLabel passwordErrorlab;
	public JFrame frame;
	protected LoginBackground panel;
	private button btn2;
	public Main()
	{
		con = dbconnection.connectDB();
		init();
	}
	public void init()
	{
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setSize(810, 650);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new LoginBackground();
		mainPanel.setSize(810, 650);
		mainPanel.setVisible(true);
		mainPanel.setLayout(null);
		frame.getContentPane().add(mainPanel);
		
		panel = new LoginBackground();
		panel.setSize(810, 650);
		panel.setVisible(true);
		panel.setLayout(null);
		mainPanel.add(panel);
		
		JLabel username = new JLabel("Username: ");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Tahoma", Font.BOLD, 13));
		username.setForeground(Color.WHITE);
		username.setBounds(199, 203, 99, 22);
		panel.add(username);
		
		usernameField = new JTextField();
		usernameField.setBounds(324, 201, 228, 27);
		usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(usernameField);
		usernameField.setForeground(Color.BLACK);
		usernameField.setColumns(10);
		
		JLabel password = new JLabel("Password: ");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setForeground(Color.WHITE);
		password.setFont(new Font("Tahoma", Font.BOLD, 13));
		password.setBounds(199, 273, 99, 22);
		panel.add(password);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(324, 271, 228, 27);
		passwordField.setEchoChar('\u25cf');
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(passwordField);
		
		// LOGIN BUTTON FUNCTIONS
		
		btn2 = new button("Login", Color.BLUE, new Color(0, 142, 173));
		btn2.setHorizontalAlignment(SwingConstants.CENTER);
		btn2.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		btn2.setForeground(Color.WHITE);
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.setBounds(485, 330, 65, 27);
		btn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn2.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
			{btn2.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));}
			
			public void mouseExited(MouseEvent e)
			{btn2.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));}
			
			public void mousePressed(MouseEvent e) {
			btn2.hover(new Color(0, 0, 190), new Color(0, 110, 135));}
			
			public void mouseReleased(MouseEvent e) {
			btn2.hover(Color.BLUE, new Color(0, 142, 173));}
			
			public void mouseClicked(MouseEvent e) {
				String pass = String.valueOf(passwordField.getPassword()); 
				String u = usernameField.getText().toLowerCase();
				if(u.equals("")){usernameErrorlab.setVisible(true);}
				else {usernameErrorlab.setVisible(false);}
				
				if(pass.equals("")) {passwordErrorlab.setVisible(true);}
				else {passwordErrorlab.setVisible(false);}
				
				if(!u.equals(usernameField.getText()))
					JOptionPane.showMessageDialog(null, "All characters of username should be in lower case.");
				
				else if(!u.equals("") && !pass.equals(""))
				{
					try {
						pst = con.prepareStatement("select * from users where username=? and password=?");
						pst.setString(1, u);
						pst.setString(2, pass);
						rs = pst.executeQuery();
						
						if(rs.next()){
							if(rs.getString("password").equals(pass)) {
								JOptionPane.showMessageDialog(null, "Login Successful.");
								if(rs.getString("type").equals("Admin")) {
									AdminPanel adm = new AdminPanel(u, pass, rs.getString("type"));
									adm.setVisible(true);
								}
								else if(rs.getString("type").equals("User")) {
									CustomerPanel cust = new CustomerPanel(u, pass, rs.getString("type"));
									cust.setVisible(true);
								}
								else if(rs.getString("type").equals("AdminOwner")) {
									AdminPanel adm = new AdminPanel(u, pass, rs.getString("type"));
									adm.setVisible(true);
								}
								frame.setVisible(false);		
							}
							
							else
								JOptionPane.showMessageDialog(null, "Invalid Username, Password", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid Username, Password", "Error", JOptionPane.ERROR_MESSAGE);
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,e1);
					}
				}
			}
		});
		panel.add(btn2);
		
		
		
		JCheckBox show_hide_pass = new JCheckBox("Show Password");
		show_hide_pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(show_hide_pass.isSelected()) {
				passwordField.setEchoChar((char)0);}
				else {
				passwordField.setEchoChar('\u25cf');}
			}
		});
		show_hide_pass.setFont(new Font("Tahoma", Font.PLAIN, 11));
		show_hide_pass.setForeground(Color.WHITE);
		show_hide_pass.setOpaque(false);
		show_hide_pass.setBounds(324, 300, 110, 18);
		panel.add(show_hide_pass);
		
		usernameErrorlab = new JLabel("Username is required!");
		usernameErrorlab.setVisible(false);
		usernameErrorlab.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameErrorlab.setForeground(Color.WHITE);
		usernameErrorlab.setBackground(Color.RED);
		usernameErrorlab.setOpaque(true);
		usernameErrorlab.setBounds(324, 228, 228, 18);
		panel.add(usernameErrorlab);
		
		passwordErrorlab = new JLabel("Password is required!");
		passwordErrorlab.setVisible(false);
		passwordErrorlab.setOpaque(true);
		passwordErrorlab.setForeground(Color.WHITE);
		passwordErrorlab.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordErrorlab.setBackground(Color.RED);
		passwordErrorlab.setBounds(324, 298, 228, 18);
		panel.add(passwordErrorlab);
		
		JLabel backToLogin = new JLabel("Login");
		backToLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainPanel.remove(pan1);
				passwordField.setText("");
				usernameField.setText("");
				usernameErrorlab.setVisible(false);
				passwordErrorlab.setVisible(false);
				panel.setVisible(true);
			}
			public void mouseEntered(MouseEvent e) {
				backToLogin.setSize(40, 20);
			}
			public void mouseExited(MouseEvent e) {
				backToLogin.setSize(40, 18);
			}
		});
		backToLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backToLogin.setForeground(Color.WHITE);
		backToLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
		backToLogin.setBounds(650, 80, 40, 18);
		
		JLabel lblNewLabel = new JLabel("Not have any user account?");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(324, 375, 158, 14);
		panel.add(lblNewLabel);
		
		JLabel registerLabel = new JLabel("Register");
		registerLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.setVisible(false);
				pan1 = new register().returnPanel();
				mainPanel.add(pan1);
				pan1.add(backToLogin);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		registerLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GREEN));
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerLabel.setForeground(Color.GREEN);
		registerLabel.setBounds(485, 375, 48, 14);
		panel.add(registerLabel);
		
		JLabel lblNewLabel_2 = new JLabel("LOGIN");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 28));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(0, 36, 793, 52);
		panel.add(lblNewLabel_2);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
class register{
	private JTextField usernameField;
	private JPasswordField passwordField, passwordField2;
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JFrame frame;
	private LoginBackground panel;
	private JTextField fullnameField,emailField,mobileField;
	private JCheckBox show_hide_pass, show_hide_pass2;
	private button btn1;
	private button btn;
	public register()
	{
		con = dbconnection.connectDB();
		init();
		setBorders(null);
	}
	public JPanel returnPanel() {
		return panel;
	}
	public void init()
	{
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setSize(810, 650);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new LoginBackground();
		panel.setSize(810, 650);
		panel.setVisible(true);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("REGISTER");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 28));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(0, 36, 793, 52);
		panel.add(lblNewLabel_2);
		
		JLabel username = new JLabel("Username: ");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Tahoma", Font.BOLD, 13));
		username.setForeground(Color.WHITE);
		username.setBounds(199, 120, 99, 22);
		panel.add(username);
		
		usernameField = new JTextField();
		usernameField.setBounds(324, 118, 228, 27);
		usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(usernameField);
		usernameField.setForeground(Color.BLACK);
		usernameField.setColumns(10);
		
		JLabel fullname = new JLabel("Full Name: ");
		fullname.setHorizontalAlignment(SwingConstants.CENTER);
		fullname.setFont(new Font("Tahoma", Font.BOLD, 13));
		fullname.setForeground(Color.WHITE);
		fullname.setBounds(199, 170, 99, 22);
		panel.add(fullname);
		
		fullnameField = new JTextField();
		fullnameField.setBounds(324, 168, 228, 27);
		fullnameField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(fullnameField);
		fullnameField.setForeground(Color.BLACK);
		fullnameField.setColumns(10);
		
		JLabel email = new JLabel("Email: ");
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.setFont(new Font("Tahoma", Font.BOLD, 13));
		email.setForeground(Color.WHITE);
		email.setBounds(199, 220, 99, 22);
		panel.add(email);
		
		emailField = new JTextField();
		emailField.setBounds(324, 218, 228, 27);
		emailField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(emailField);
		emailField.setForeground(Color.BLACK);
		emailField.setColumns(10);
		
		JLabel mobile = new JLabel("Mobile: ");
		mobile.setHorizontalAlignment(SwingConstants.CENTER);
		mobile.setFont(new Font("Tahoma", Font.BOLD, 13));
		mobile.setForeground(Color.WHITE);
		mobile.setBounds(199, 270, 99, 22);
		panel.add(mobile);
		
		mobileField = new JTextField();
		mobileField.setBounds(324, 268, 228, 27);
		mobileField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(mobileField);
		mobileField.setForeground(Color.BLACK);
		mobileField.setColumns(10);
		
		JLabel password = new JLabel("Password: ");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setForeground(Color.WHITE);
		password.setFont(new Font("Tahoma", Font.BOLD, 13));
		password.setBounds(199, 320, 99, 22);
		panel.add(password);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(324, 318, 228, 27);
		passwordField.setEchoChar('\u25cf');
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(passwordField);
		
		JLabel confpassword = new JLabel("Confirm Password: ");
		confpassword.setHorizontalAlignment(SwingConstants.CENTER);
		confpassword.setForeground(Color.WHITE);
		confpassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		confpassword.setBounds(169, 370, 129, 22);
		panel.add(confpassword);
		
		passwordField2 = new JPasswordField();
		passwordField2.setForeground(Color.BLACK);
		passwordField2.setBounds(324, 368, 228, 27);
		passwordField2.setEchoChar('\u25cf');
		passwordField2.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(passwordField2);

		show_hide_pass = new JCheckBox("Show Password");
		show_hide_pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(show_hide_pass.isSelected()) {
				passwordField.setEchoChar((char)0);}
				else {
				passwordField.setEchoChar('\u25cf');}
			}
		});
		show_hide_pass.setFont(new Font("Tahoma", Font.PLAIN, 11));
		show_hide_pass.setForeground(Color.WHITE);
		show_hide_pass.setOpaque(false);
		show_hide_pass.setBounds(324, 345, 110, 18);
		panel.add(show_hide_pass);
		
		show_hide_pass2 = new JCheckBox("Show Password");
		show_hide_pass2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(show_hide_pass2.isSelected()) {
				passwordField2.setEchoChar((char)0);}
				else {
				passwordField2.setEchoChar('\u25cf');}
			}
		});
		show_hide_pass2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		show_hide_pass2.setForeground(Color.WHITE);
		show_hide_pass2.setOpaque(false);
		show_hide_pass2.setBounds(324, 395, 110, 18);
		panel.add(show_hide_pass2);
		
		JTextArea errorArea = new JTextArea();
		errorArea.setBackground(Color.RED);
		errorArea.setForeground(Color.WHITE);
		errorArea.setBounds(225, 465, 400, 50);
		errorArea.setLineWrap(true);
		errorArea.setBorder(new MatteBorder(5,5,5,5,Color.RED));
		errorArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
		errorArea.setVisible(false);
		panel.add(errorArea);
		
		btn1 = new button("Register", Color.BLUE, new Color(0, 142, 173));
		btn1.setHorizontalAlignment(SwingConstants.CENTER);
		btn1.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		btn1.setForeground(Color.WHITE);
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.setBounds(465, 430, 85, 27);
		btn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				errorArea.setVisible(false);
				setBorders(null);
				boolean bool = checkErrors(errorArea);
				if(bool == true) {
					try {
						pst = con.prepareStatement("INSERT INTO users (username, fullname, password, mobile, email)"
								+ "VALUES(?,?,?,?,?)");
						pst.setString(1, usernameField.getText());
						pst.setString(2, fullnameField.getText());
						pst.setString(3, String.valueOf(passwordField.getPassword()));
						pst.setString(4, mobileField.getText());
						pst.setString(5, emailField.getText());
						pst.executeUpdate();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
					resetFields(errorArea);
					JOptionPane.showMessageDialog(null, "Successfully registered!\nBack to login page to login", "Successful", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			public void mouseEntered(MouseEvent e)
			{btn1.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));}
			
			public void mouseExited(MouseEvent e)
			{btn1.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));}
			
			public void mousePressed(MouseEvent e) {
			btn1.hover(new Color(0, 0, 190), new Color(0, 110, 135));}
			
			public void mouseReleased(MouseEvent e) {
				btn1.hover(Color.BLUE, new Color(0, 142, 173));}
		});
		panel.add(btn1);
		
		btn = new button("Reset", Color.BLUE, new Color(0, 142, 173));
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		btn.setForeground(Color.WHITE);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBounds(324, 430, 65, 27);
		btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				resetFields(errorArea);
			}
			
			public void mouseEntered(MouseEvent e)
			{btn.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));}
			
			public void mouseExited(MouseEvent e)
			{btn.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));}
			
			public void mousePressed(MouseEvent e) {
			btn.hover(new Color(0, 0, 190), new Color(0, 110, 135));}
			
			public void mouseReleased(MouseEvent e) {
				btn.hover(Color.BLUE, new Color(0, 142, 173));}
		});
		panel.add(btn);
		
	}
	public boolean checkErrors(JTextArea t) {
		String un = usernameField.getText();
		String fn = fullnameField.getText();
		String em = emailField.getText();
		String mo = mobileField.getText();
		String ps = String.valueOf(passwordField.getPassword());
		String ps2 = String.valueOf(passwordField2.getPassword());
		setBorders(new MatteBorder(1,1,1,1,Color.RED));
		if(un.equals("") || fn.equals("") || ps.equals("") || em.equals("") || mo.equals("") || ps2.equals("")) {
			t.setText("Every field must be filled. Please fill the fields and try again.");
			t.setVisible(true);
			return false;
		}
		try {
			pst = con.prepareStatement("SELECT username, email FROM users");
			rs = pst.executeQuery();
			while(rs.next()) {
				if(un.equals(rs.getString(1))) {
					t.setText("This username already taken. Try again.");
					t.setVisible(true);
					usernameField.setBorder(new MatteBorder(1,1,1,1, Color.RED));
					return false;
				}
				if(em.equals(rs.getString(2))) {
					t.setText("This email already taken. Try again.");
					t.setVisible(true);
					emailField.setBorder(new MatteBorder(1,1,1,1, Color.RED));
					return false;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(char c: un.toCharArray()) {
			if(!Character.isLowerCase(c) && !Character.isDigit(c)) {
				t.setText("Username can contain lowercase alphabet and 0-9 only.\nTry again.");
				t.setVisible(true);
				usernameField.setBorder(new MatteBorder(1,1,1,1, Color.RED));
				return false;
			}
		}
		
		if((!em.contains(".com") && !em.contains("@")) || !Character.isAlphabetic(em.charAt(0))) {
			t.setText("Email must contain \".com\", \"@\" and start with alphabet.\nTry Again.");
			emailField.setBorder(new MatteBorder(1,1,1,1,Color.RED));
			t.setVisible(true);
			return false;
		}
		
		if(ps.length()<6) {
			t.setText("Password length must be 6 or more. Try Again.");
			t.setVisible(true);
			passwordField.setBorder(new MatteBorder(1,1,1,1, Color.RED));
			return false;
		}
		if(!ps.equals(ps2)) {
			t.setText("Password does not match. Try Again.");
			t.setVisible(true);
			return false;
		}
		
		for(char c: mo.toCharArray()) {
			if(!Character.isDigit(c)) {
				t.setText("Invalid mobile number. Try Again.");
				t.setVisible(true);
				mobileField.setBorder(new MatteBorder(1,1,1,1,Color.RED));
				return false;
			}
		}
		return true;
	}
	public void setBorders(Border b) {
		if(usernameField.getText().equals(""))
			usernameField.setBorder(b);
		else
			usernameField.setBorder(null);
		if(fullnameField.getText().equals(""))
			fullnameField.setBorder(b);
		else
			fullnameField.setBorder(null);
		if(emailField.getText().equals(""))	
			emailField.setBorder(b);
		else
			emailField.setBorder(null);
		if(mobileField.getText().equals(""))
			mobileField.setBorder(b);
		else
			mobileField.setBorder(null);
		if(String.valueOf(passwordField.getPassword()).equals(""))
			passwordField.setBorder(b);
		else
			passwordField.setBorder(null);
		if(String.valueOf(passwordField2.getPassword()).equals(""))
			passwordField2.setBorder(b);
		else
			passwordField2.setBorder(null);
	}
	
	public void resetFields(JTextArea errorArea) {
		usernameField.setText("");
		passwordField.setText("");
		passwordField2.setText("");
		fullnameField.setText("");
		emailField.setText("");
		mobileField.setText("");
		show_hide_pass.setSelected(false);
		show_hide_pass2.setSelected(false);
		errorArea.setVisible(false);
	}
}
