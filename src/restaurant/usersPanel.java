package restaurant;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTextArea;

public class usersPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel, pan, checkPanel;
	private button btnUpdate, btnDelete;
	private JTextArea errorTextArea;
	private JPanel searchField;
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private JTextField searchtextField;
	private JTable table;
	private String globalun, globalpass;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private boolean flag = false;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usersPanel window = new usersPanel("a", "a");
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
	public usersPanel(String s1, String s2) {
		globalun = s1;
		globalpass = s2;
		con = dbconnection.connectDB();
		initialize();
		set(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JPanel returnPanel() {
		return panel;
	}
	private void set(boolean bool) {
		searchField.setVisible(bool);
		btnUpdate.setVisible(bool);
		btnDelete.setVisible(bool);
		scrollPane.setVisible(bool);
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
				System.out.println(globalpass+"  "+s);
				if(s.equals(globalpass) && !s.equals("")) {
					System.out.println("OK");
					checkPanel.setVisible(false);
					set(true);
				}
				else
				{
					ta.setVisible(true);
					ta.setText(" Wrong Password. Try Again.");
				}
			}
		});
		checkPanel.add(btnCheck);
		
		JLabel lblNewLabel = new JLabel("Users");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(185, 0, 185));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		searchField = new JPanel();
		searchField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		searchField.setBackground(new Color(255, 255, 255));
		searchField.setBounds(0, 46, 690, 35);
		panel.add(searchField);
		searchField.setLayout(null);
		
		searchtextField = new JTextField();
		searchtextField.setBounds(5, 5, 217, 25);
		searchtextField.setForeground(Color.BLACK);
		if(!searchtextField.isFocusOwner() && searchtextField.getText().equals("")){
			setPlaceholder();
		}
		searchtextField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if(searchtextField.getText().equals("Enter User ID/Username  ")) {
					searchtextField.setText("");
					searchtextField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(searchtextField.getText().equals("")) {
					setPlaceholder();
				}
			}
		});
		searchField.add(searchtextField);
		searchtextField.setColumns(10);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!searchtextField.getText().equals("") && !searchtextField.getText().equals("Enter User ID/Username  ")) {
					model.setRowCount(0);
					setTable(searchtextField.getText());
					if(model.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "No user found of ID/Name "+searchtextField.getText());
					}
				}
			}
		});
		searchBtn.setBounds(225, 6, 75, 23);
		searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchBtn.setBackground(new Color(117, 0, 117));
		searchBtn.setForeground(Color.WHITE);
		searchBtn.setFocusPainted(false);
		searchBtn.setHorizontalAlignment(SwingConstants.CENTER);
		searchBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		searchBtn.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		searchField.add(searchBtn);
		
		JButton allBtn = new JButton("Search All");
		allBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flag) {
					model.setRowCount(0);
					setTable();
					setPlaceholder();
				}
			}
		});
		allBtn.setBounds(610, 6, 75, 23);
		allBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		allBtn.setBackground(new Color(117, 0, 117));
		allBtn.setForeground(Color.WHITE);
		allBtn.setFocusPainted(false);
		allBtn.setHorizontalAlignment(SwingConstants.CENTER);
		allBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		allBtn.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		searchField.add(allBtn);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 81, 680, 341);
		scrollPane.getViewport().setBackground(Color.WHITE);
		panel.add(scrollPane);
		
		
		model = new DefaultTableModel() {
			boolean[] columnEditable = new boolean[] {
					false, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int col) {
				return columnEditable[col];
			}
		};
		
		table = new JTable(model);
		table.setRowHeight(25);
		table.getTableHeader().setBackground(Color.BLACK);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setFont(new Font("SansSerif", Font.PLAIN, 12));
		table.setOpaque(false);
		model.addColumn("User ID");
		model.addColumn("Username");
		model.addColumn("Full Name");
		model.addColumn("Password");
		model.addColumn("Mobile");
		model.addColumn("Type");
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
		setTable();
		
		btnUpdate = new button("Update",new Color(255, 0, 255),new Color(127, 0, 127));
		btnUpdate.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnUpdate.hover(new Color(75, 0, 75), new Color(75, 0, 75));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUpdate.hover(new Color(255, 0, 255),new Color(127, 0, 127));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorTextArea.setVisible(false);
				if(table.getSelectedRow() != -1)
					updateUser(table.getSelectedRow());
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnUpdate.setBounds(500, 425, 80, 25);
		panel.add(btnUpdate);
		
		btnDelete = new button("Delete",new Color(255, 0, 255),new Color(127, 0, 127));
		btnDelete.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDelete.hover(new Color(75, 0, 75), new Color(75, 0, 75));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDelete.hover(new Color(255, 0, 255),new Color(127, 0, 127));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorTextArea.setVisible(false);
				if(table.getSelectedRow() != -1)
					removeUser(table.getSelectedRow());
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
		btnDelete.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnDelete.setBounds(400, 425, 80, 25);
		panel.add(btnDelete);
		
		errorTextArea = new JTextArea();
		errorTextArea.setVisible(false);
		errorTextArea.setForeground(Color.WHITE);
		errorTextArea.setBackground(Color.RED);
		errorTextArea.setBounds(135, 453, 430, 45);
		panel.add(errorTextArea);
		
	}
	private void setPlaceholder() {
		searchtextField.setText("Enter User ID/Username  ");
		searchtextField.setForeground(Color.GRAY);
	}
	
	private void removeUser(int rowIndex) {
		try {
			TableModel model2 = table.getModel();
			int id = Integer.parseInt(model2.getValueAt(rowIndex, 0).toString());
			pst = con.prepareStatement("DELETE FROM users WHERE user_id = ?");
			pst.setInt(1, id);
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete this user", "Delete", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION)
			{
				pst.executeUpdate();
				model.setRowCount(0);
				setTable();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void setTable() {
		try {
			pst = con.prepareStatement("Select * FROM users");
			rs = pst.executeQuery();
			flag = false;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
						, rs.getString(6)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private void setTable(String str) {
		int x=0, flg=0;
		for(char c: str.toCharArray()) {
			if(!Character.isDigit(c)) {
				flg = 1;
				break;
			}
		}
		if(flg==0) x = Integer.parseInt(str);
		try {
			if(flg == 0) {
				pst = con.prepareStatement("Select * FROM users WHERE user_id=?");
				pst.setInt(1, x);
			}
			else {
				pst = con.prepareStatement("Select * FROM users WHERE username=?");
				pst.setString(1, str);
			}
			rs = pst.executeQuery();
			flag = true;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
						, rs.getString(6)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private boolean checkValidOrNot(String s,String str) {
		if(str.equals("type") && (!s.equals("Admin") || !s.equals("User")))
		{
			errorTextArea.setText("Invalid "+str+" input. Please check and try again.");
			errorTextArea.setVisible(true);
		}
		else if(s.equals("")) {
			errorTextArea.setText("Invalid "+str+" input. Please check and try again.");
			errorTextArea.setVisible(true);
		}
		else {
			return true;
		}
		return false;
	}
	
	
	private void updateUser(int rowIndex) {
		TableModel model2 = table.getModel();
		String id = model2.getValueAt(rowIndex, 0).toString();
		String uname = model2.getValueAt(rowIndex, 1).toString();
		String fname = model2.getValueAt(rowIndex, 2).toString();
		String pass = model2.getValueAt(rowIndex, 3).toString();
		String mobile = model2.getValueAt(rowIndex, 4).toString();
		String type = model2.getValueAt(rowIndex, 5).toString();
		boolean bool = true;
		bool = checkValidOrNot(uname, "username");
		if(bool)bool = checkValidOrNot(fname, "full name");
		if(bool)bool = checkValidOrNot(pass, "password");
		if(bool)bool = checkValidOrNot(mobile, "mobile");
		if(bool) bool = checkValidOrNot(type, "type");
		if(bool) {
			try {
				pst = con.prepareStatement("UPDATE users SET username = ?, fullname = ?, password = ?,"
						+ " mobile = ?, type = ? WHERE user_id = ?;");
				pst.setString(1, uname);
				pst.setString(2, fname);
				pst.setString(3, pass);
				pst.setString(4, mobile);
				pst.setString(5, type);
				pst.setInt(6, Integer.parseInt(id));
				pst.executeUpdate();
				
				model.setRowCount(0);
				setTable();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public void clearPanel() {
		panel.remove(pan);
		panel.revalidate();
		panel.repaint();
	}
}
