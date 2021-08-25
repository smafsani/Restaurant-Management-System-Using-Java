package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class addEmployeePanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel, panel2, mainPanel;
	private JTextField enField, enidField, mnField, adrsField, esField;
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private double countSalary = 0.0;
	private double gmd, amd, cd, acd, kmd, sd, dwd, dgd, sgd;
	private String types="";
	private JCheckBox gmCheckBox, amCheckBox, chefCheckBox, achefCheckBox, kmCheckBox;
	private JCheckBox serverCheckBox, dwCheckBox, dgCheckBox, sgCheckBox;
	private JTextArea errorMessageArea;
	private String globalPassword="", globalType="";
	private JLabel labelBack;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addEmployeePanel window = new addEmployeePanel("a", "a");
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
	public addEmployeePanel(String s1, String s2) {
		globalPassword = s1;
		globalType = s2;
		countSalary = 0.0;
		gmd = amd = cd = acd = kmd = sd = dwd = dgd = sgd = -1.0;
		initialize();
		con = dbconnection.connectDB();
		getSalaries();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JPanel returnPanel() {
		return mainPanel;
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 589);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, w, h);
		mainPanel.setLayout(null);
		frame.getContentPane().add(mainPanel);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		mainPanel.add(panel);
		
		JLabel lblNewLabel = new JLabel("Add New Employee");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 0, 102));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		JLabel editBtn = new JLabel("Edit Employees");
		editBtn.setBounds(580, 47, 100, 25);
		editBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		editBtn.setHorizontalAlignment(SwingConstants.CENTER);
		editBtn.setForeground(Color.BLACK);
		editBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.setVisible(false);
				panel2 = new editEmployees().returnPanel();
				panel2.setLocation(0, 0);
				mainPanel.add(panel2);
				labelBack.setLocation(5, 425);
				panel2.add(labelBack);
			}
		});
		panel.add(editBtn);
		
		JLabel salaryBtn = new JLabel("Set Salaries");
		salaryBtn.setBounds(490, 47, 90, 25);
		salaryBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		salaryBtn.setHorizontalAlignment(SwingConstants.CENTER);
		salaryBtn.setForeground(Color.BLACK);
		salaryBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salaryBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.setVisible(false);
				panel2 = new setSalariesPanel(globalPassword, globalType).returnPanel();
				panel2.setLocation(0, 0);
				mainPanel.add(panel2);
				labelBack.setLocation(5, 46);
				panel2.add(labelBack);
			}
		});
		panel.add(salaryBtn);
		
		
		JLabel lblNewLab_1 = new JLabel("Employee Name:");
		lblNewLab_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_1.setForeground(Color.BLACK);
		lblNewLab_1.setBounds(60, 75, 115, 25);
		panel.add(lblNewLab_1);
		
		enField = new JTextField();
		enField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		enField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		enField.setBounds(185, 75, 258, 25);
		panel.add(enField);
		enField.setColumns(10);
		
		JLabel lblNewLab_2 = new JLabel("Employee NID:");
		lblNewLab_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_2.setForeground(Color.BLACK);
		lblNewLab_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_2.setBounds(60, 120, 115, 25);
		panel.add(lblNewLab_2);
		
		enidField = new JTextField();
		enidField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		enidField.setBounds(185, 120, 258, 25);
		enidField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(enidField);
		enidField.setColumns(10);
		
		JLabel lblNewLab_3 = new JLabel("Mobile Number:");
		lblNewLab_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_3.setForeground(Color.BLACK);
		lblNewLab_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_3.setBounds(60, 165, 115, 25);
		panel.add(lblNewLab_3);
		
		JLabel lblNewLab_4 = new JLabel("Address:");
		lblNewLab_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_4.setForeground(Color.BLACK);
		lblNewLab_4.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_4.setBounds(60, 210, 115, 25);
		panel.add(lblNewLab_4);
		
		mnField = new JTextField();
		mnField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mnField.setColumns(10);
		mnField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		mnField.setBounds(185, 165, 258, 25);
		panel.add(mnField);
		
		adrsField = new JTextField();
		adrsField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		adrsField.setColumns(10);
		adrsField.setBounds(185, 210, 258, 30);
		adrsField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(adrsField);
		
		JLabel lblNewLab_5 = new JLabel("Salary:");
		lblNewLab_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5.setForeground(Color.BLACK);
		lblNewLab_5.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5.setBounds(60, 350, 115, 25);
		panel.add(lblNewLab_5);
		
		esField = new JTextField("0.0");
		esField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		esField.setColumns(10);
		esField.setBounds(185, 350, 258, 25);
		esField.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(esField);
		
		button btnReset = new button("Reset",new Color(0, 0, 255), new Color(0, 0, 115));
		btnReset.setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnReset.hover(new Color(0, 0, 102),new Color(0, 0, 102));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnReset.hover(new Color(0, 0, 255), new Color(0, 0, 115));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setForeground(Color.WHITE);
		btnReset.setHorizontalAlignment(SwingConstants.CENTER);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReset.setBounds(185, 400, 82, 30);
		panel.add(btnReset);
		
		button btnDone = new button("Done",new Color(0, 0, 255), new Color(0, 0, 115));
		btnDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDone.setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		btnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDone.hover(new Color(0, 0, 102),new Color(0, 0, 102));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDone.hover(new Color(0, 0, 255), new Color(0, 0, 115));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorMessageArea.setVisible(false);
				String name="", addr="", mno="", nid="", sal="";
				double y;
				getTypes();
				name = enField.getText();
				nid = enidField.getText();
				addr = adrsField.getText();
				mno = mnField.getText();
				sal = esField.getText();
				boolean bool = checkThese(name, nid, addr, mno, sal);
				if(bool) {
					y = Double.parseDouble(sal);
					try {
						pst = con.prepareStatement("INSERT INTO employees (Name, NID, Address, Mobile, Salary, Type)"
								+ "VALUES (?, ?, ?, ?, ?, ?)");
						pst.setString(1, name);
						pst.setString(2, nid);
						pst.setString(3, addr);
						pst.setString(4, mno);
						pst.setDouble(5, y);
						pst.setString(6, types);
						pst.executeUpdate();
						clearFields();	
						
						pst = con.prepareStatement("SELECT ID FROM employees where Name=? and NID=?");
						pst.setString(1, name);
						pst.setString(2, nid);
						rs = pst.executeQuery();
						rs.next();
						String str = "<html><p style=\"font-size: 12px; font-family: sans-serif;\">Employee Is Successfully Added.<br>The Employee-ID of the Employee is: "
							+ "<span style=\"text-decoration:underline;font-weight:bold\">"
							+ rs.getString("ID") +"</span></p></html>";
						showOptionPane(str);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		btnDone.setForeground(Color.WHITE);
		btnDone.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDone.setHorizontalAlignment(SwingConstants.CENTER);
		btnDone.setBounds(361, 400, 82, 30);
		panel.add(btnDone);
		
		JLabel lblNewLab_5_1 = new JLabel("Type:");
		lblNewLab_5_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5_1.setForeground(Color.BLACK);
		lblNewLab_5_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5_1.setBounds(60, 255, 115, 25);
		panel.add(lblNewLab_5_1);
		
		gmCheckBox = new JCheckBox("General Manager");
		gmCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gmCheckBox.isSelected())countSalary += gmd;
				else countSalary -= gmd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		gmCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		gmCheckBox.setBackground(Color.WHITE);
		gmCheckBox.setBounds(185, 255, 125, 23);
		panel.add(gmCheckBox);
		
		amCheckBox = new JCheckBox("Assistent Manager");
		amCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(amCheckBox.isSelected())countSalary += amd;
				else countSalary -= amd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		amCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		amCheckBox.setBackground(Color.WHITE);
		amCheckBox.setBounds(315, 255, 130, 23);
		panel.add(amCheckBox);
		
		chefCheckBox = new JCheckBox("Chef");
		chefCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chefCheckBox.isSelected())countSalary += cd;
				else countSalary -= cd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		chefCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		chefCheckBox.setBackground(Color.WHITE);
		chefCheckBox.setBounds(452, 255, 65, 23);
		panel.add(chefCheckBox);
		
		achefCheckBox = new JCheckBox("Assistant Chef");
		achefCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(achefCheckBox.isSelected())countSalary += acd;
				else countSalary -= acd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		achefCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		achefCheckBox.setBackground(Color.WHITE);
		achefCheckBox.setBounds(185, 280, 125, 23);
		panel.add(achefCheckBox);
		
		kmCheckBox = new JCheckBox("Kitchen Manager");
		kmCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(kmCheckBox.isSelected())countSalary += kmd;
				else countSalary -= kmd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		kmCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		kmCheckBox.setBackground(Color.WHITE);
		kmCheckBox.setBounds(315, 280, 125, 23);
		panel.add(kmCheckBox);
		
		serverCheckBox = new JCheckBox("Server");
		serverCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serverCheckBox.isSelected())countSalary += sd;
				else countSalary -= sd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		serverCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		serverCheckBox.setBackground(Color.WHITE);
		serverCheckBox.setBounds(452, 280, 125, 23);
		panel.add(serverCheckBox);
		
		dwCheckBox = new JCheckBox("Dish Washer");
		dwCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dwCheckBox.isSelected())countSalary += dwd;
				else countSalary -= dwd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		dwCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		dwCheckBox.setBackground(Color.WHITE);
		dwCheckBox.setBounds(185, 305, 125, 23);
		panel.add(dwCheckBox);
		
		dgCheckBox = new JCheckBox("Delivery Guy");
		dgCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dgCheckBox.isSelected())countSalary += dgd;
				else countSalary -= dgd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");				
			}
		});
		dgCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		dgCheckBox.setBackground(Color.WHITE);
		dgCheckBox.setBounds(315, 305, 125, 23);
		panel.add(dgCheckBox);
		
		sgCheckBox = new JCheckBox("Security Guard");
		sgCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sgCheckBox.isSelected())countSalary += sgd;
				else countSalary -= sgd;
				if(countSalary>=0.0) esField.setText(Double.toString(countSalary));
				else esField.setText("0.0");
			}
		});
		sgCheckBox.setFont(new Font("Serif", Font.PLAIN, 14));
		sgCheckBox.setBackground(Color.WHITE);
		sgCheckBox.setBounds(452, 305, 125, 23);
		panel.add(sgCheckBox);
		
		errorMessageArea = new JTextArea();
		errorMessageArea.setVisible(false);
		errorMessageArea.setEditable(false);
		errorMessageArea.setForeground(Color.WHITE);
		errorMessageArea.setBackground(Color.RED);
		errorMessageArea.setBounds(140, 440, 408, 46);
		panel.add(errorMessageArea);
		
		
		labelBack = new JLabel("Back");
		labelBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel2.setVisible(false);
				panel.setVisible(true);
				clearFields();
			}
		});
		labelBack.setBounds(5, 425, 75, 23);
		labelBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelBack.setBackground(new Color(30, 74, 123));
		labelBack.setForeground(Color.BLACK);
		labelBack.setHorizontalAlignment(SwingConstants.CENTER);
		labelBack.setFont(new Font("SansSerif", Font.BOLD, 14));
		
	}
	
	private void getTypes() {
		if(gmCheckBox.isSelected())
			types += "General Manager/";
		if(amCheckBox.isSelected())
			types += "Assitant Manager/";
		if(chefCheckBox.isSelected())
			types += "Chef/";
		if(achefCheckBox.isSelected())
			types += "Assistant Chef/";
		if(kmCheckBox.isSelected())
			types += "Kitchen Manager/";
		if(serverCheckBox.isSelected())
			types += "Server/";
		if(dwCheckBox.isSelected())
			types += "Dish Washer/";
		if(dgCheckBox.isSelected())
			types += "Delivery Guy/";
		if(sgCheckBox.isSelected())
			types += "Security Guard/";
		if(!types.equals(""))
			types = types.substring(0, types.length()-1);
		
	}
	private void getSalaries() {
		try {
			pst = con.prepareStatement("SELECT * FROM salaries");
			rs = pst.executeQuery();
			
			while(rs.next()) {
				String s = rs.getString("post");
				if(s.equals("General Manager"))
					gmd = rs.getDouble("salary");  
				else if(s.equals("Assistant Manager"))
					amd = rs.getDouble("salary");
				else if(s.equals("Chef"))
					cd = rs.getDouble("salary");
				else if(s.equals("Assistant Chef"))
					acd = rs.getDouble("salary");
				else if(s.equals("Kitchen Manager"))
					kmd = rs.getDouble("salary");
				else if(s.equals("Server"))
					sd = rs.getDouble("salary");
				else if(s.equals("Dish Washer"))
					dwd = rs.getDouble("salary");
				else if(s.equals("Delivery Guy"))
					dgd = rs.getDouble("salary");
				else if(s.equals("Security Guard"))
					sgd = rs.getDouble("salary");
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private boolean checkThese(String name, String nid, String addr, String mno, String sal) {
		if(name.equals("") || nid.equals("") || addr.equals("") || mno.equals("") || sal.equals("0.0")){
			errorMessageArea.setText("Missing information. Please fill up all fields and try again.");
			errorMessageArea.setVisible(true);
			return false;}
		try {
			Double.parseDouble(sal);
		}catch(Exception e) {
			errorMessageArea.setText("Invalid salary input. Please check and try again");
			errorMessageArea.setVisible(true);
			return false;}
		for(char c: nid.toCharArray()) {
			if(!Character.isDigit(c)){
				errorMessageArea.setText("Invalid NID input. Please check and try again");
				errorMessageArea.setVisible(true);
				return false;
			}
		}
		
		return true;
	}
	private void clearFields()
	{
		countSalary = 0.0;
		types = "";
		enidField.setText("");
		mnField.setText("");
		adrsField.setText("");
		esField.setText("0.0");
		enField.setText("");
		gmCheckBox.setSelected(false);
		amCheckBox.setSelected(false);
		chefCheckBox.setSelected(false);
		achefCheckBox.setSelected(false);
		kmCheckBox.setSelected(false);
		serverCheckBox.setSelected(false);
		dwCheckBox.setSelected(false);
		dgCheckBox.setSelected(false);
		sgCheckBox.setSelected(false);
		
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

class editEmployees{
	
	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	private JPanel searchField;
	private JTextField searchtextField;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JTable table;
	private button btnUpdate,btnDelete;
	PreparedStatement pst = null;
	Connection con = null;
	ResultSet rs = null;
	private JTextArea errorTextArea;
	private boolean flag = false;
	public editEmployees() {
		con = dbconnection.connectDB();
		initialize();
	}
	public JPanel returnPanel() {
		return panel;
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
		
		JLabel lblNewLabel = new JLabel("Employees");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 0, 102));
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
				if(searchtextField.getText().equals("Enter Employee ID/Username  ")) {
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
				if(!searchtextField.getText().equals("") && !searchtextField.getText().equals("Enter Employee ID/Username  ")) {
					model.setRowCount(0);
					setTable(searchtextField.getText());
					if(model.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "No Employee found of ID/Name "+searchtextField.getText());
					}
				}
			}
		});
		searchBtn.setBounds(225, 6, 75, 23);
		searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchBtn.setBackground(new Color(50, 74, 123));
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
		allBtn.setBackground(new Color(50, 74, 123));
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
					false, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int col) {
				return columnEditable[col];
			}
		};
		
		table = new JTable(model);
		table.setRowHeight(25);
		table.getTableHeader().setBackground(new Color(0, 0, 102));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setFont(new Font("SansSerif", Font.PLAIN, 12));
		table.setOpaque(false);
		model.addColumn("EmployeeID");
		model.addColumn("Name");
		model.addColumn("NID");
		model.addColumn("Address");
		model.addColumn("Mobile");
		model.addColumn("Salary");
		model.addColumn("Type");
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.setRowHeight(table.getRowHeight()+5);
		for(int j=0;j<=6;j++) {
		}
		scrollPane.setViewportView(table);
		
		setTable();
		
		btnUpdate = new button("Update",new Color(100, 135, 215),new Color(30, 74, 123));
		btnUpdate.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnUpdate.hover(new Color(48, 48, 81), new Color(48, 48, 81));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUpdate.hover(new Color(100, 135, 215),new Color(30, 74, 123));
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
		
		btnDelete = new button("Delete",new Color(100, 135, 215),new Color(30, 74, 123));
		btnDelete.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDelete.hover(new Color(48, 48, 81), new Color(48, 48, 81));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDelete.hover(new Color(100, 135, 215),new Color(30, 74, 123));
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
	static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
	    WordWrapCellRenderer() {
	        setLineWrap(true);
	        setWrapStyleWord(true);
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        setText(value.toString());
	        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
	        if (table.getRowHeight(row) != getPreferredSize().height) {
	            table.setRowHeight(row, getPreferredSize().height);
	        }
	        return this;
	    }
	}
	private void setPlaceholder() {
		searchtextField.setText("Enter Employee ID/Username  ");
		searchtextField.setForeground(Color.GRAY);
	}
	private void removeUser(int rowIndex) {
		try {
			TableModel model2 = table.getModel();
			int id = Integer.parseInt(model2.getValueAt(rowIndex, 0).toString());
			pst = con.prepareStatement("DELETE FROM employees WHERE ID = ?");
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
			pst = con.prepareStatement("Select * FROM employees");
			rs = pst.executeQuery();
			flag = false;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
						, rs.getDouble(6), rs.getString(7)});
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
				pst = con.prepareStatement("Select * FROM employees WHERE ID=?");
				pst.setInt(1, x);
			}
			else {
				pst = con.prepareStatement("Select * FROM employees WHERE Name=?");
				pst.setString(1, str);
			}
			rs = pst.executeQuery();
			flag = true;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
						,rs.getDouble(6), rs.getString(7)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private void printError(String str) {
		
		errorTextArea.setText("Invalid "+str+" input. Please check and try again.");
		errorTextArea.setVisible(true);
		
	}
	private boolean checkThese(String name, String nid, String addr, String mno, String sal) {
		if(name.equals("") || nid.equals("") || addr.equals("") || mno.equals("") || sal.equals("0.0")){
			errorTextArea.setText("Missing information. Please fill up all fields and try again.");
			errorTextArea.setVisible(true);
			return false;}
		double d;
		try {
			d = Double.parseDouble(sal);
		}catch(Exception e) {
			printError("salary");
			return false;}
		for(char c: nid.toCharArray()) {
			if(!Character.isDigit(c)){
				printError("NID");
				return false;
			}
		}
		
		return true;
	}
	private void updateUser(int rowIndex) {
		TableModel model2 = table.getModel();
		String id = model2.getValueAt(rowIndex, 0).toString();
		String name = model2.getValueAt(rowIndex, 1).toString();
		String nid = model2.getValueAt(rowIndex, 2).toString();
		String addr = model2.getValueAt(rowIndex, 3).toString();
		String mobile = model2.getValueAt(rowIndex, 4).toString();
		String sal = model2.getValueAt(rowIndex, 5).toString();
		String type = model2.getValueAt(rowIndex, 6).toString();
		boolean bool = true;
		bool = checkThese(name, nid, addr, mobile, sal);
		if(bool) {
			try {
				pst = con.prepareStatement("UPDATE employees SET Name = ?, NID = ?, Address = ?,"
						+ " Mobile = ?, Salary=?, Type = ? WHERE ID = ?;");
				pst.setString(1, name);
				pst.setString(2, nid);
				pst.setString(3, addr);
				pst.setString(4, mobile);
				pst.setDouble(5, Double.parseDouble(sal));
				pst.setString(6, type);
				pst.setInt(7, Integer.parseInt(id));
				pst.executeUpdate();
				
				model.setRowCount(0);
				setTable();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}