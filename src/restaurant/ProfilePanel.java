package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import afsani.gradiant.panels.info.InfoBackground;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProfilePanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	private JTextArea errorTextArea;
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private String uname, upass;
	private JTextField userNameField, TypeField, mobileNField, fullNameField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfilePanel window = new ProfilePanel("admin", "password");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfilePanel(String u, String p) {
		uname = u;
		upass = p;
		con = dbconnection.connectDB();
		initialize();
	}

	public JPanel returnPanel() {
		return panel;
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new InfoBackground();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Profile");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		errorTextArea = new JTextArea();
		errorTextArea.setVisible(false);
		errorTextArea.setForeground(Color.WHITE);
		errorTextArea.setBackground(Color.RED);
		errorTextArea.setBounds(185, 453, 330, 45);
		panel.add(errorTextArea);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(69, 80, 126, 25);
		panel.add(lblNewLabel_1);
		
		JLabel full = new JLabel("Full Name:");
		full.setHorizontalAlignment(SwingConstants.RIGHT);
		full.setForeground(Color.WHITE);
		full.setFont(new Font("SansSerif", Font.BOLD, 14));
		full.setBounds(69, 130, 126, 25);
		panel.add(full);
		
		JLabel lblNewLabel_1_2 = new JLabel("Mobile:");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(69, 180, 126, 25);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Type:");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(69, 230, 126, 25);
		panel.add(lblNewLabel_1_3);
		
		userNameField = new JTextField();
		userNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userNameField.setEnabled(false);
		userNameField.setDisabledTextColor(Color.black);
		userNameField.setBounds(205, 80, 275, 25);
		panel.add(userNameField);
		userNameField.setColumns(10);
		
		TypeField = new JTextField();
		TypeField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TypeField.setEnabled(false);
		TypeField.setDisabledTextColor(Color.black);
		TypeField.setColumns(10);
		TypeField.setBounds(205, 230, 275, 25);
		panel.add(TypeField);
		
		mobileNField = new JTextField();
		mobileNField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mobileNField.setEnabled(false);
		mobileNField.setDisabledTextColor(Color.black);
		mobileNField.setColumns(10);
		mobileNField.setBounds(205, 180, 275, 25);
		panel.add(mobileNField);
		
		fullNameField = new JTextField();
		fullNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fullNameField.setEnabled(false);
		fullNameField.setDisabledTextColor(Color.black);
		fullNameField.setColumns(10);
		fullNameField.setBounds(205, 130, 275, 25);
		panel.add(fullNameField);
		
		fillFields();
	
	}
	private void fillFields() {
		try {
			pst = con.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
			pst.setString(1, uname);
			pst.setString(2, upass);
			rs= pst.executeQuery();
			
			if(rs.next()) {
				userNameField.setText(rs.getString(2));
				fullNameField.setText(rs.getString(3));
				mobileNField.setText(rs.getString(5));
				TypeField.setText(rs.getString(6));
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}

