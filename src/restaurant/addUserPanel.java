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
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class addUserPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	private JTextField userNameField;
	private JTextField fullNameField;
	private JTextField mobileNoField;
	private JCheckBox adminCheckBox, userCheckBox;
	private Connection con = null;
	public PreparedStatement pst = null;
	private ResultSet rs = null;
	private JPasswordField passwordField;
	private JPasswordField confPassField;
	private JTextArea ErrorMessageArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addUserPanel window = new addUserPanel();
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
	public addUserPanel() {
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
		fullNameField.setText("");
		passwordField.setText("");
		confPassField.setText("");
		mobileNoField.setText("");
		userNameField.setText("");
		userCheckBox.setSelected(false);
		adminCheckBox.setSelected(false);
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
		
		JLabel lblNewLabel = new JLabel("Add New User");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(153, 50, 204));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		JLabel lblNewLab_1 = new JLabel("Username:");
		lblNewLab_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_1.setForeground(Color.BLACK);
		lblNewLab_1.setBounds(60, 80, 115, 25);
		panel.add(lblNewLab_1);
		
		userNameField = new JTextField();
		userNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		userNameField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		userNameField.setBounds(185, 78, 258, 30);
		panel.add(userNameField);
		userNameField.setColumns(10);
		
		JLabel lblNewLab_2 = new JLabel("Full Name:");
		lblNewLab_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_2.setForeground(Color.BLACK);
		lblNewLab_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_2.setBounds(60, 130, 115, 25);
		panel.add(lblNewLab_2);
		
		fullNameField = new JTextField();
		fullNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		fullNameField.setBounds(185, 128, 258, 30);
		fullNameField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(fullNameField);
		fullNameField.setColumns(10);
		
		JLabel lblNewLab_3 = new JLabel("Password:");
		lblNewLab_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_3.setForeground(Color.BLACK);
		lblNewLab_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_3.setBounds(60, 180, 115, 25);
		panel.add(lblNewLab_3);
		
		JLabel lblNewLab_4 = new JLabel("Confirm Password:");
		lblNewLab_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_4.setForeground(Color.BLACK);
		lblNewLab_4.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_4.setBounds(44, 230, 131, 25);
		panel.add(lblNewLab_4);
		
		JLabel lblNewLab_5 = new JLabel("Mobile Number:");
		lblNewLab_5.setToolTipText("Must be unique");
		lblNewLab_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5.setForeground(Color.BLACK);
		lblNewLab_5.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5.setBounds(60, 280, 115, 25);
		panel.add(lblNewLab_5);
		
		mobileNoField = new JTextField();
		mobileNoField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mobileNoField.setColumns(10);
		mobileNoField.setBounds(185, 278, 258, 30);
		mobileNoField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(mobileNoField);
		
		button btnReset = new button("Reset",new Color(130, 74, 177), new Color(60, 0, 90));
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 174, 255)));
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnReset.hover(new Color(187, 105, 255),new Color(187, 105, 255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnReset.hover(new Color(130, 74, 177), new Color(60, 0, 90));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		btnReset.setForeground(Color.WHITE);
		btnReset.setHorizontalAlignment(SwingConstants.CENTER);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReset.setBounds(185, 395, 82, 30);
		panel.add(btnReset);
		
		button btnDone = new button("Done",new Color(130, 74, 177), new Color(60, 0, 90));
		btnDone.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 174, 255)));
		btnDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDone.hover(new Color(187, 105, 255),new Color(187, 105, 255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDone.hover(new Color(130, 74, 177), new Color(60, 0, 90));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ErrorMessageArea.setVisible(false);
		
				String un, fn, pass, confpass, mno, type;
				un = fn = pass = confpass = mno = type = "";
				try {
					un = userNameField.getText();
					fn = fullNameField.getText();
					pass = String.valueOf(passwordField.getPassword());
					confpass = String.valueOf(confPassField.getPassword());
					mno = mobileNoField.getText();
					if(adminCheckBox.isSelected())
						type = "Admin";
					else if(userCheckBox.isSelected())
						type = "User";
				}
				catch(Exception e4) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
				boolean bool2 = false;
				bool2 = checkErrors(un, fn, pass, confpass, mno, type);
				boolean bool = false;
				if(bool2) bool = isUniqueUsernameMobile(un, mno);
				if(bool && bool2) {
					try {
						pst = con.prepareStatement("INSERT INTO users (username, fullname, password, mobile, type) VALUES (?, ?, ?, ?, ?)");
						pst.setString(1, un);
						pst.setString(2, fn);
						pst.setString(3, pass);
						pst.setString(4, mno);
						pst.setString(5, type);
						pst.executeUpdate();
						clearFields();
						
						pst = con.prepareStatement("SELECT user_id FROM users where username=? and password=?");
						pst.setString(1, un);
						pst.setString(2, pass);
						rs = pst.executeQuery();
						rs.next();
						String str = "<html><p style=\"font-size: 12px; font-family: sans-serif;\">User is successfully added.<br>The user ID of the user is: "
								+ "<span style=\"text-decoration:underline;font-weight:bold\">"
								+ rs.getString("user_id") +"</span></p></html>";
						showOptionPane(str);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);}
				}
			}
		});
		btnDone.setForeground(Color.WHITE);
		btnDone.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDone.setHorizontalAlignment(SwingConstants.CENTER);
		btnDone.setBounds(361, 395, 82, 30);
		panel.add(btnDone);
		
		JLabel lblNewLab_5_1 = new JLabel("Account Type:");
		lblNewLab_5_1.setToolTipText("");
		lblNewLab_5_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5_1.setForeground(Color.BLACK);
		lblNewLab_5_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5_1.setBounds(60, 335, 115, 25);
		panel.add(lblNewLab_5_1);
		
		adminCheckBox = new JCheckBox("Admin");
		adminCheckBox.setForeground(new Color(255, 255, 255));
		adminCheckBox.setBackground(new Color(51, 102, 255));
		adminCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(adminCheckBox.isSelected())
					userCheckBox.setSelected(false);
			}
		});
		adminCheckBox.setFont(new Font("SansSerif", Font.BOLD, 14));
		adminCheckBox.setBounds(185, 333, 70, 30);
		panel.add(adminCheckBox);
		
		userCheckBox = new JCheckBox("User");
		userCheckBox.setForeground(new Color(255, 255, 255));
		userCheckBox.setBackground(new Color(51, 102, 255));
		userCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userCheckBox.isSelected())
					adminCheckBox.setSelected(false);
			}
		});
		userCheckBox.setFont(new Font("SansSerif", Font.BOLD, 14));
		userCheckBox.setBounds(265, 333, 70, 30);
		panel.add(userCheckBox);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('\u25cf');
		passwordField.setFont(new Font("SansSerif", Font.PLAIN, 9));
		passwordField.setBounds(185, 178, 258, 30);
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(passwordField);
		
		confPassField = new JPasswordField();
		confPassField.setEchoChar('\u25cf');
		confPassField.setFont(new Font("SansSerif", Font.PLAIN, 9));
		confPassField.setBounds(185, 228, 258, 30);
		confPassField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(confPassField);
		
		ErrorMessageArea = new JTextArea();
		ErrorMessageArea.setVisible(false);
		ErrorMessageArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
		ErrorMessageArea.setBorder(null);
		ErrorMessageArea.setBackground(Color.RED);
		ErrorMessageArea.setForeground(Color.WHITE);
		ErrorMessageArea.setBounds(105, 438, 466, 55);
		panel.add(ErrorMessageArea);
		ErrorMessageArea.setColumns(10);
		ErrorMessageArea.setEditable(false);
		
		JCheckBox showpassBox = new JCheckBox("show password");
		showpassBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showpassBox.isSelected()) {
					passwordField.setEchoChar((char)0);
					passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));}
				else {
					passwordField.setEchoChar('\u25cf');
					passwordField.setFont(new Font("SansSerif", Font.PLAIN, 9));}
			}
		});
		showpassBox.setFont(new Font("Serif", Font.PLAIN, 13));
		showpassBox.setBorder(null);
		showpassBox.setBackground(Color.WHITE);
		showpassBox.setBounds(185, 208, 101, 15);
		panel.add(showpassBox);
		
		JCheckBox showconfpassBox = new JCheckBox("show password");
		showconfpassBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showconfpassBox.isSelected()) {
					confPassField.setEchoChar((char)0);
					confPassField.setFont(new Font("SansSerif", Font.PLAIN, 16));}
				else {
					confPassField.setEchoChar('\u25cf');
					confPassField.setFont(new Font("SansSerif", Font.PLAIN, 9));}
			}
		});
		showconfpassBox.setFont(new Font("Serif", Font.PLAIN, 13));
		showconfpassBox.setBorder(null);
		showconfpassBox.setBackground(Color.WHITE);
		showconfpassBox.setBounds(185, 258, 101, 15);
		panel.add(showconfpassBox);
	}
	private boolean isUniqueUsernameMobile(String username, String mobileNo) {
		boolean userN = false;
		boolean mobileN = false;
		try {
			pst = con.prepareStatement("select * from users where username=?");
			pst.setString(1, username);
			rs = pst.executeQuery();
			if(rs.next() == false) userN = true;
			
			pst = con.prepareStatement("select * from users where mobile=?");
			pst.setString(1, mobileNo);
			rs = pst.executeQuery();
			if(rs.next() == false) mobileN = true;
			
			if(userN == false && mobileN == true) { ErrorMessageArea.setText(" This username is already taken.");
			ErrorMessageArea.setVisible(true);}
			else if(userN == true && mobileN == false) { ErrorMessageArea.setText(" Someone is already registered by this number. Please try another number.");
			ErrorMessageArea.setVisible(true);}
			else if(userN == false && mobileN == false){ ErrorMessageArea.setText(" This username is already taken and the number is already registered.\n"
					+ " Please use unique username and mobile number.");
			ErrorMessageArea.setVisible(true);}
			else
				return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	public boolean checkErrors(String un, String fn, String pass, String cpass, String mobile, String type) {
		if(!un.equals(un.toLowerCase()))
		{
			ErrorMessageArea.setText(" Username must have all lowercase characters. Try again.");
			ErrorMessageArea.setVisible(true);
			return false;
		}	
		if(pass.equals("") || cpass.equals("") || mobile.equals("") || un.equals("") || fn.equals(""))
		{
			ErrorMessageArea.setText(" Missing information. Please fill up every fields and try again.");
			ErrorMessageArea.setVisible(true);
			return false;
		}
		if(pass.length()<4) {
			ErrorMessageArea.setText(" Password length must be greater than 3. Try again.");
			ErrorMessageArea.setVisible(true);
			return false;
		}
		if(!pass.equals(cpass)) {
			ErrorMessageArea.setText(" Passwords are not matched. Please confirm password and try again.");
			ErrorMessageArea.setVisible(true);
			return false;
		}
		if(type.equals("")) {
			ErrorMessageArea.setText("Passwords select account type and try again.");
			ErrorMessageArea.setVisible(true);
			return false;
		}
		return true;
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
