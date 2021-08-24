package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Cursor;

public class setSalariesPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	private JTextField gmField, amField, chefField, achefField, kmField;
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private JTextField sgField, dgField, dwField, serverField;
	private String gmS, amS, cS, acS, kmS, sS, dwS, dgS, sgS;
	private int gmi, ami, ci, aci, kmi, si, dwi, dgi, sgi;
	private String globPass, globType;
	private JPanel checkPanel;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private JLabel gmLabel, amLabel, chefLabell, achefLabel, kmLabel;
	private button btnReset, btnUpdate;
	private JLabel serverLabell, dwLabell, dgLabell, sgLabell;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setSalariesPanel window = new setSalariesPanel("a", "a");
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
	public setSalariesPanel(String s, String t) {
		globPass = s;
		globType = t;
		gmS = amS = cS = acS = kmS = sS = dwS = dgS = sgS = null;
		gmi = ami = ci = aci = kmi = si = dwi = dgi = sgi = -1;
		initialize();
		set(false);
		con = dbconnection.connectDB();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JPanel returnPanel() {
		return panel;
	}
	public void resetFields()
	{
		amField.setText(amS+"  Tk");
		chefField.setText(cS+"  Tk");
		achefField.setText(acS+"  Tk");
		kmField.setText(kmS+"  Tk");
		serverField.setText(sS+"  Tk");
		dwField.setText(dwS+"  Tk");
		dgField.setText(dgS+"  Tk");
		sgField.setText(sgS+"  Tk");
		gmField.setText(gmS+"  Tk");
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
		
		checkPanel = new JPanel();
		checkPanel.setBackground(Color.WHITE);
		checkPanel.setBounds(0, 0, w, h);
		checkPanel.setVisible(true);
		checkPanel.setLayout(null);
		panel.add(checkPanel);
		
		JLabel lb = new JLabel("Enter your password");
		lb.setBounds(245, 210, 200, 30);
		lb.setFont(new Font("Serif", Font.BOLD, 18));
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		checkPanel.add(lb);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBounds(245, 240, 200, 30);
		passwordField.setEchoChar('\u25cf');
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		checkPanel.add(passwordField);
		
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
		show_hide_pass.setForeground(Color.BLACK);
		show_hide_pass.setOpaque(false);
		show_hide_pass.setBounds(245, 270, 110, 18);
		checkPanel.add(show_hide_pass);
		
		JTextArea ta = new JTextArea();
		ta.setBounds(200, 315, 290, 40);
		ta.setBackground(Color.RED);
		ta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		ta.setForeground(Color.WHITE);
		
		ta.setVisible(false);
		checkPanel.add(ta);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setBounds(375, 288, 70, 25);
		btnCheck.setBackground(Color.WHITE);
		btnCheck.setForeground(Color.BLACK);
		btnCheck.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.setVisible(false);
				String s = String.valueOf(passwordField.getPassword());
				System.out.println(globPass+"  "+s);
				if(s.equals(globPass) && !s.equals("")) {
					System.out.println("OK");
					checkPanel.setVisible(false);
					set(true);
					fillFields();
				}
				else
				{
					ta.setVisible(true);
					ta.setText(" Wrong Password. Try Again.");
				}
			}
		});
		checkPanel.add(btnCheck);
		
		
		lblNewLabel = new JLabel("Set Salaries");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(102, 0, 51));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		gmLabel = new JLabel("General Manager:");
		gmLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		gmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gmLabel.setForeground(Color.BLACK);
		gmLabel.setBounds(60, 80, 126, 25);
		panel.add(gmLabel);
		
		gmField = new JTextField();
		gmField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gmField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		gmField.setBounds(195, 80, 258, 25);
		if(!globType.equals("Owner")) {
			gmField.setEnabled(false);
			gmField.setDisabledTextColor(Color.BLACK);}
		panel.add(gmField);
		gmField.setColumns(10);
		
		amLabel = new JLabel("Assistant Manager:");
		amLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amLabel.setForeground(Color.BLACK);
		amLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		amLabel.setBounds(52, 120, 134, 25);
		panel.add(amLabel);
		
		amField = new JTextField();
		amField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		amField.setBounds(195, 120, 258, 25);
		amField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(amField);
		amField.setColumns(10);
		
		chefLabell = new JLabel("Chef:");
		chefLabell.setHorizontalAlignment(SwingConstants.RIGHT);
		chefLabell.setForeground(Color.BLACK);
		chefLabell.setFont(new Font("SansSerif", Font.BOLD, 14));
		chefLabell.setBounds(60, 160, 126, 25);
		panel.add(chefLabell);
		
		achefLabel = new JLabel("Assistant Chef:");
		achefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		achefLabel.setForeground(Color.BLACK);
		achefLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		achefLabel.setBounds(60, 200, 126, 25);
		panel.add(achefLabel);
		
		chefField = new JTextField();
		chefField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		chefField.setColumns(10);
		chefField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		chefField.setBounds(195, 160, 258, 25);
		panel.add(chefField);
		
		achefField = new JTextField();
		achefField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		achefField.setColumns(10);
		achefField.setBounds(195, 200, 258, 24);
		achefField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.add(achefField);
		
		kmLabel = new JLabel("Kitchen Manager:");
		kmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		kmLabel.setForeground(Color.BLACK);
		kmLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		kmLabel.setBounds(60, 240, 126, 25);
		panel.add(kmLabel);
		
		kmField = new JTextField();
		kmField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		kmField.setColumns(10);
		kmField.setBounds(195, 240, 258, 25);
		kmField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.add(kmField);
		
		btnReset = new button("Reset",new Color(202, 0, 101), new Color(91, 0, 45));
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnReset.hover(new Color(80, 0, 40),new Color(80, 0, 40));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnReset.hover(new Color(202, 0, 101), new Color(91, 0, 45));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				resetFields();
			}
		});
		btnReset.setForeground(Color.WHITE);
		btnReset.setHorizontalAlignment(SwingConstants.CENTER);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReset.setBounds(195, 440, 82, 30);
		panel.add(btnReset);
		
		btnUpdate = new button("Update",new Color(202, 0, 101), new Color(91, 0, 45));
		btnUpdate.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnUpdate.hover(new Color(80, 0, 40),new Color(80, 0, 40));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUpdate.hover(new Color(202, 0, 101), new Color(91, 0, 45));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!gmS.equals(gmField.getText())) {
					gmS = gmField.getText(); updateValue(gmi, gmS);}
				if(!amS.equals(amField.getText())) {
					amS = amField.getText(); updateValue(ami, amS);}
				if(!cS.equals(chefField.getText())) {
					cS = chefField.getText(); updateValue(ci, cS);}
				if(!acS.equals(achefField.getText())) {
					acS = achefField.getText(); updateValue(aci, acS);}
				if(!kmS.equals(kmField.getText())) {
					kmS = kmField.getText(); updateValue(kmi, kmS);}
				if(!sS.equals(serverField.getText())) {
					sS = serverField.getText(); updateValue(si, sS);}
				if(!dwS.equals(dwField.getText())) {
					dwS = dwField.getText(); updateValue(dwi, dwS);}
				if(!dgS.equals(dgField.getText())) {
					dgS = dgField.getText(); updateValue(dgi, dgS);}
				if(!sgS.equals(sgField.getText())) {
					sgS = sgField.getText(); updateValue(sgi, sgS);}
				
				resetFields();
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnUpdate.setBounds(371, 440, 82, 30);
		panel.add(btnUpdate);
		
		serverLabell = new JLabel("Server:");
		serverLabell.setHorizontalAlignment(SwingConstants.RIGHT);
		serverLabell.setForeground(Color.BLACK);
		serverLabell.setFont(new Font("SansSerif", Font.BOLD, 14));
		serverLabell.setBounds(60, 280, 126, 25);
		panel.add(serverLabell);
		
		dwLabell = new JLabel("Dish Washer:");
		dwLabell.setHorizontalAlignment(SwingConstants.RIGHT);
		dwLabell.setForeground(Color.BLACK);
		dwLabell.setFont(new Font("SansSerif", Font.BOLD, 14));
		dwLabell.setBounds(60, 320, 126, 25);
		panel.add(dwLabell);
		
		dgLabell = new JLabel("Delivery Guy:");
		dgLabell.setHorizontalAlignment(SwingConstants.RIGHT);
		dgLabell.setForeground(Color.BLACK);
		dgLabell.setFont(new Font("SansSerif", Font.BOLD, 14));
		dgLabell.setBounds(60, 360, 126, 25);
		panel.add(dgLabell);
		
		sgLabell = new JLabel("Security Guard:");
		sgLabell.setHorizontalAlignment(SwingConstants.RIGHT);
		sgLabell.setForeground(Color.BLACK);
		sgLabell.setFont(new Font("SansSerif", Font.BOLD, 14));
		sgLabell.setBounds(60, 400, 126, 25);
		panel.add(sgLabell);
		
		sgField = new JTextField();
		sgField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		sgField.setColumns(10);
		sgField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		sgField.setBounds(195, 400, 258, 25);
		panel.add(sgField);
		
		dgField = new JTextField();
		dgField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		dgField.setColumns(10);
		dgField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dgField.setBounds(195, 360, 258, 25);
		panel.add(dgField);
		
		dwField = new JTextField();
		dwField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		dwField.setColumns(10);
		dwField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dwField.setBounds(195, 320, 258, 25);
		panel.add(dwField);
		
		serverField = new JTextField();
		serverField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		serverField.setColumns(10);
		serverField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		serverField.setBounds(195, 280, 258, 25);
		panel.add(serverField);		
	}
	private void updateValue(int i, String s) {
		double salry = Double.parseDouble(s);
		try {
			String query = "UPDATE salaries SET salary=? WHERE ID=?";
			pst = con.prepareStatement(query);
			pst.setDouble(1, salry);
			pst.setInt(2, i);
			pst.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}
	
	private void fillFields() {
		try {
			pst = con.prepareStatement("SELECT * FROM salaries");
			rs = pst.executeQuery();
			
			while(rs.next()) {
				String s = rs.getString("post");
				if(s.equals("General Manager"))
					{gmi=rs.getInt("ID"); gmS=Double.toString(rs.getDouble("salary")); gmField.setText(gmS+"  Tk");}
				else if(s.equals("Assistant Manager"))
					{ami=rs.getInt("ID"); amS=Double.toString(rs.getDouble("salary")); amField.setText(amS+"  Tk");}
				else if(s.equals("Chef"))
					{ci=rs.getInt("ID"); cS=Double.toString(rs.getDouble("salary")); chefField.setText(cS+"  Tk");}
				else if(s.equals("Assistant Chef"))
					{aci=rs.getInt("ID"); acS=Double.toString(rs.getDouble("salary")); achefField.setText(acS+"  Tk");}
				else if(s.equals("Kitchen Manager"))
					{kmi=rs.getInt("ID"); kmS=Double.toString(rs.getDouble("salary")); kmField.setText(kmS+"  Tk");}
				else if(s.equals("Server"))
					{si=rs.getInt("ID"); sS=Double.toString(rs.getDouble("salary")); serverField.setText(sS+"  Tk");}
				else if(s.equals("Dish Washer"))
					{dwi=rs.getInt("ID"); dwS=Double.toString(rs.getDouble("salary")); dwField.setText(dwS+"  Tk");}
				else if(s.equals("Delivery Guy"))
					{dgi=rs.getInt("ID"); dgS=Double.toString(rs.getDouble("salary")); dgField.setText(dgS+"  Tk");}
				else if(s.equals("Security Guard"))
					{sgi=rs.getInt("ID"); sgS=Double.toString(rs.getDouble("salary")); sgField.setText(sgS+"  Tk");}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void set(boolean bool) {
		amField.setVisible(bool);
		amLabel.setVisible(bool);
		chefField.setVisible(bool);
		chefLabell.setVisible(bool);
		achefField.setVisible(bool);
		achefLabel.setVisible(bool);
		kmField.setVisible(bool);
		kmLabel.setVisible(bool);
		serverField.setVisible(bool);
		serverLabell.setVisible(bool);
		dwField.setVisible(bool);
		dwLabell.setVisible(bool);
		dgField.setVisible(bool);
		dgLabell.setVisible(bool);
		sgField.setVisible(bool);
		sgLabell.setVisible(bool);
		gmField.setVisible(bool);
		gmLabel.setVisible(bool);
		
		lblNewLabel.setVisible(bool);
		btnReset.setVisible(bool);
		btnUpdate.setVisible(bool);		
	}
}
