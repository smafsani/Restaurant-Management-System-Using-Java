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
import javax.swing.border.MatteBorder;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
public class Main extends JFrame{
	public JLabel label;
	public JTextField usernameField;
	public JPasswordField passwordField;
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	public JPanel pan;
	public JLabel usernameErrorlab;
	public JLabel passwordErrorlab;
	private JComboBox comboBox;
	
	public Main()
	{
		setLayout(null);
		setSize(810, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		con = dbconnection.connectDB();
	}
	public void init()
	{
		LoginBackground panel = new LoginBackground();
		panel.setSize(810, 650);
		panel.setVisible(true);
		panel.setLayout(null);
		add(panel);
		
		label = new JLabel();
		label.setBounds(0, 90, 810, 50);
		label.setText("Login");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 35));
		label.setForeground(Color.WHITE);
		panel.add(label);
		
		JLabel username = new JLabel("Username: ");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Tahoma", Font.BOLD, 13));
		username.setForeground(Color.WHITE);
		username.setBounds(199, 253, 99, 22);
		panel.add(username);
		
		usernameField = new JTextField();
		usernameField.setBounds(324, 253, 228, 27);
		usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(usernameField);
		usernameField.setForeground(Color.BLACK);
		usernameField.setColumns(10);
		
		JLabel password = new JLabel("Password: ");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setForeground(Color.WHITE);
		password.setFont(new Font("Tahoma", Font.BOLD, 13));
		password.setBounds(199, 323, 99, 22);
		panel.add(password);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(324, 323, 228, 27);
		passwordField.setEchoChar('\u25cf');
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(passwordField);
		
		button btn = new button("Reset", Color.BLUE, new Color(0, 142, 173));
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		btn.setForeground(Color.WHITE);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBounds(324, 410, 65, 27);
		btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btn);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			usernameField.setText(""); passwordField.setText("");}
			
			public void mouseEntered(MouseEvent e)
			{btn.setBorder(new MatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));}
			
			public void mouseExited(MouseEvent e)
			{btn.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));}
			
			public void mousePressed(MouseEvent e) {
			btn.hover(new Color(0, 0, 190), new Color(0, 110, 135));}
			
			public void mouseReleased(MouseEvent e) {
				btn.hover(Color.BLUE, new Color(0, 142, 173));}
		});
		
		// LOGIN BUTTON FUNCTIONS
		
		button btn2 = new button("Login", Color.BLUE, new Color(0, 142, 173));
		btn2.setHorizontalAlignment(SwingConstants.CENTER);
		btn2.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		btn2.setForeground(Color.WHITE);
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.setBounds(485, 410, 65, 27);
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
				String ty = comboBox.getSelectedItem().toString();
				if(u.equals("")){usernameErrorlab.setVisible(true);}
				else {usernameErrorlab.setVisible(false);}
				
				if(pass.equals("")) {passwordErrorlab.setVisible(true);}
				else {passwordErrorlab.setVisible(false);}
				
				if(ty.equals("Select Type")){JOptionPane.showMessageDialog(null, "Please Select The Type");}
				
				else if(!u.equals(usernameField.getText()))
					JOptionPane.showMessageDialog(null, "All characters of username should be in lower case.");
				
				else if(!u.equals("") && !pass.equals(""))
				{
					try {
						pst = con.prepareStatement("select * from users where username=? and password=?");
						pst.setString(1, u);
						pst.setString(2, pass);
						rs = pst.executeQuery();
						
						if(rs.next()){
							if(rs.getString("password").equals(pass) && comboBox.getSelectedItem().equals(rs.getString("type"))) {
								JOptionPane.showMessageDialog(null, "Login Successful.");
								AdminPanel adm = new AdminPanel(u, pass);
								adm.setVisible(true);
								setVisible(false);
								
							}
							
							else
								JOptionPane.showMessageDialog(null, "Invalid Username, Password Or Selected Type.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid Username, Password Or Selected Type.", "Error", JOptionPane.ERROR_MESSAGE);
						
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
		show_hide_pass.setBounds(324, 370, 110, 18);
		panel.add(show_hide_pass);
		
		usernameErrorlab = new JLabel("Username is required!");
		usernameErrorlab.setVisible(false);
		usernameErrorlab.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameErrorlab.setForeground(Color.WHITE);
		usernameErrorlab.setBackground(Color.RED);
		usernameErrorlab.setOpaque(true);
		usernameErrorlab.setBounds(324, 280, 228, 18);
		panel.add(usernameErrorlab);
		
		passwordErrorlab = new JLabel("Password is required!");
		passwordErrorlab.setVisible(false);
		passwordErrorlab.setOpaque(true);
		passwordErrorlab.setForeground(Color.WHITE);
		passwordErrorlab.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordErrorlab.setBackground(Color.RED);
		passwordErrorlab.setBounds(324, 350, 228, 18);
		panel.add(passwordErrorlab);
		
		String str[] = new String[3];
		str[0] = "Select Type";
		str[1] = "Admin";
		str[2] = "User";
		comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(393, 178, 99, 20);
		comboBox.setModel(new DefaultComboBoxModel(str));
		panel.add(comboBox);
		
		
	}
	
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
