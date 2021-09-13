package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import afsani.gradiant.label.button;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class OrderFood {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private int quan=0, totalPrice=0;
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
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private button btnAdd;
	private JTextField quantityField;
	private JScrollPane receiptAreaScroll;
	private JCheckBox checkBoxTable;
	private JCheckBox checkBoxHome;
	private String globalType = "";
	private JTextArea receiptArea,addrName, addrMobile, addressArea;
	private button generateReceipt,resetBtn,printBtn;
	private JLabel lblNewLabel_2,lblNewLabel_3,lblNewLabel_4;
	private String dateGlob="";
	private button deliverBtn;
	private JLabel lblNewLabel_5;
	private String items="";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderFood window = new OrderFood("Admin");
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
	public OrderFood(String s) {
		totalPrice = 0;
		globalType = s;
		con = dbconnection.connectDB();
		initialize();
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
		frame.setBounds(100, 100, 706, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(250, 250, 250));
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Order Food");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 51, 51));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 50, 331, 259);
		scrollPane.getViewport().setBackground(Color.WHITE);
		panel.add(scrollPane);
		
		model = new DefaultTableModel() {
			boolean[] columnEditable = new boolean[] {
					false, false, false, false
			};
			public boolean isCellEditable(int row, int col) {
				return columnEditable[col];
			}
		};
		
		table = new JTable(model);
		table.setRowHeight(25);
		table.getTableHeader().setBackground(new Color(0, 51, 51));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setFont(new Font("SansSerif", Font.PLAIN, 12));
		table.setOpaque(false);
		model.addColumn("Name");
		model.addColumn("Category");
		model.addColumn("Price");
		model.addColumn("Quantity");
		table.getColumnModel().getColumn(1).setMinWidth(75);
		table.getColumnModel().getColumn(1).setMaxWidth(75);
		table.getColumnModel().getColumn(1).setWidth(75);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(2).setWidth(50);
		table.getColumnModel().getColumn(3).setMinWidth(55);
		table.getColumnModel().getColumn(3).setMaxWidth(55);
		table.getColumnModel().getColumn(3).setWidth(55);
		table.setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setRowHeight(table.getRowHeight()-5);
		scrollPane.setViewportView(table);
		setTable();
		
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
		TableModel m = table.getModel();
		int len = m.getColumnCount();
		for(int j=0; j<len; j++) {
			table.getColumnModel().getColumn(j).setCellRenderer(render);
		}
		
		btnAdd = new button("Add",new Color(0, 133, 0),new Color(0, 51, 51));
		btnAdd.setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnAdd.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnAdd.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(quantityField.getText().equals("") || quantityField.getText().equals("0")) {
					JOptionPane.showMessageDialog(null, "Please Enter Quantity");
				}
				else
				{
					if(table.getSelectedRow() != -1) {
						addIntoRecepit(table.getSelectedRow());
					}
				}
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		btnAdd.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnAdd.setBounds(268, 312, 68, 25);
		panel.add(btnAdd);
		
		JLabel lblNewLabel_1 = new JLabel("Quantity:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1.setBounds(5, 311, 81, 25);
		panel.add(lblNewLabel_1);
		
		quantityField = new JTextField(Integer.toString(quan));
		quantityField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		quantityField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		quantityField.setHorizontalAlignment(SwingConstants.CENTER);
		quantityField.setBounds(84, 311, 61, 25);
		panel.add(quantityField);
		quantityField.setColumns(10);
		
		receiptAreaScroll = new JScrollPane();
		receiptAreaScroll.setBounds(358, 49, 322, 299);
		receiptAreaScroll.setVisible(false);
		panel.add(receiptAreaScroll);
		
		receiptArea = new JTextArea();
		receiptAreaScroll.setViewportView(receiptArea);
		receiptArea.setEditable(false);
		receiptArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		receiptArea.setFont(new Font("SensSerif", Font.PLAIN, 12));
		
		checkBoxTable = new JCheckBox("On Table");
		checkBoxTable.setBackground(new Color(250, 250, 250));
		if(globalType.contains("Admin"))
			checkBoxTable.setVisible(true);
		else
			checkBoxTable.setVisible(false);
		checkBoxTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxTable.isSelected()) {
					printBtn.setEnabled(true);
					checkBoxHome.setSelected(false);
					deliverBtn.setEnabled(false);
					addressArea.setEnabled(false);
					addrMobile.setEnabled(false);
					addrName.setEnabled(false);
				}
				else if(!checkBoxHome.isSelected()) {
					printBtn.setEnabled(false);
					deliverBtn.setEnabled(false);
					addressArea.setEnabled(false);
					addrMobile.setEnabled(false);
					addrName.setEnabled(false);
				}
			}
		});
		checkBoxTable.setBounds(120, 343, 81, 20);
		panel.add(checkBoxTable);
		
		checkBoxHome = new JCheckBox("Home Delivery");
		checkBoxHome.setBackground(new Color(250, 250, 250));
		if(globalType.contains("Admin"))
			checkBoxHome.setVisible(true);
		else
			checkBoxHome.setVisible(false);
		checkBoxHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxHome.isSelected()) {
					checkBoxTable.setSelected(false);
					printBtn.setEnabled(true);
					deliverBtn.setEnabled(true);
					addressArea.setEnabled(true);
					addrMobile.setEnabled(true);
					addrName.setEnabled(true);
				}
				else if(!checkBoxTable.isSelected()) {
					printBtn.setEnabled(false);
					deliverBtn.setEnabled(false);
					addressArea.setEnabled(false);
					addrMobile.setEnabled(false);
					addrName.setEnabled(false);
				}
			}
		});
		checkBoxHome.setBounds(5, 343, 112, 20);
		panel.add(checkBoxHome);
		
		generateReceipt = new button("Generate Receipt", new Color(0, 133, 0),new Color(0, 51, 51));
		generateReceipt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		generateReceipt.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		generateReceipt.setForeground(Color.WHITE);
		generateReceipt.setHorizontalAlignment(SwingConstants.CENTER);
		generateReceipt.setBounds(10, 380, 107, 25);
		generateReceipt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				generateReceipt.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				generateReceipt.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!receiptArea.getText().contains("BILL RECEIPT"))
				{
					receiptAreaScroll.setVisible(true);
					String str="";
					str="***************************************************************\n";
					str+="*                                     BILL RECEIPT                                      *\n";
					str+="***************************************************************\n";
					Date obj = new Date();
					String date = obj.toString();
					str+=date+"\nName(quantity)                                                               Price\n\n";
					receiptArea.setText(str+receiptArea.getText());
					dateGlob = date.substring(0, date.length()-9);
				}
			}
		});
		panel.add(generateReceipt);
		
		resetBtn = new button("Reset", new Color(0, 133, 0),new Color(0, 51, 51));
		resetBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setHorizontalAlignment(SwingConstants.CENTER);
		resetBtn.setBounds(130, 380, 70, 25);
		resetBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				resetBtn.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				resetBtn.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				receiptArea.setText("");
				quan = 0;
				totalPrice=0;
				quantityField.setText(Integer.toString(quan));
				checkBoxHome.setSelected(false);
				checkBoxTable.setSelected(false);
				receiptAreaScroll.setVisible(false);
			}
		});
		panel.add(resetBtn);
		
		printBtn = new button("Print Receipt", new Color(0, 133, 0),new Color(0, 51, 51));
		printBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		printBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		printBtn.setForeground(Color.WHITE);
		if(!checkBoxHome.isSelected() && !checkBoxTable.isSelected())
			printBtn.setEnabled(false);
		printBtn.setHorizontalAlignment(SwingConstants.CENTER);
		printBtn.setBounds(215, 380, 110, 25);
		printBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				printBtn.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				printBtn.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!receiptArea.getText().equals("") && receiptAreaScroll.isVisible()) {
					if(checkBoxHome.isSelected()) {
						String n = addrName.getText();
						String m = addrMobile.getText();
						String adr = addressArea.getText();
						if(n.equals("") || m.equals("") || adr.equals("")) 
							JOptionPane.showMessageDialog(null, "Please Fill All The Customer Info Fields.");
						else {
							String s = "\nTotal:\t\t\t"+Integer.toString(totalPrice)+"\n";
							totalPrice = 0;
							s += "\nCustomer Info:\n----------------------\n";
							s += "Name: "+n+"\n"+"Mobile: "+m+"\n"+"Address: "+adr+"\n\n\n";
							s += "                                                                              Signature";
							receiptArea.setText(receiptArea.getText()+s);
							try {
								receiptArea.print();
							} catch (PrinterException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					else if(checkBoxTable.isSelected()) {
						String s = "\nTotal:\t\t\t"+Integer.toString(totalPrice)+"\n";
						totalPrice = 0;
						s += "\n\n                                                                              Signature";
						receiptArea.setText(receiptArea.getText()+s);
						try {
							receiptArea.print();
						} catch (PrinterException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Generate Receipt First");
				}
			}
		});
		panel.add(printBtn);
		
		lblNewLabel_2 = new JLabel("NAME:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(358, 370, 81, 20);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("MOBILE:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(358, 394, 81, 20);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("ADDRESS:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(371, 422, 68, 20);
		panel.add(lblNewLabel_4);
		
		addrName = new JTextArea();
		addrName.setFont(new Font("SansSerif", Font.PLAIN, 12));
		addrName.setBounds(449, 370, 231, 20);
		addrName.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		panel.add(addrName);
		addrName.setEnabled(false);
		addrName.setColumns(10);
		
		addrMobile = new JTextArea();
		addrMobile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		addrMobile.setColumns(10);
		addrMobile.setBounds(449, 394, 231, 20);
		addrMobile.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		addrMobile.setEnabled(false);
		panel.add(addrMobile);
		
		addressArea = new JTextArea();
		addressArea.setBounds(449, 422, 231, 60);
		addressArea.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		addressArea.setEnabled(false);
		panel.add(addressArea);
		
		deliverBtn = new button("Deliver", new Color(0, 133, 0),new Color(0, 51, 51));
		deliverBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deliverBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		deliverBtn.setForeground(Color.WHITE);
		deliverBtn.setEnabled(false);
		deliverBtn.setHorizontalAlignment(SwingConstants.CENTER);
		deliverBtn.setBounds(10, 415, 70, 25);
		deliverBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deliverBtn.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				deliverBtn.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				deliverOrder();
			}
		});
		panel.add(deliverBtn);
		
		lblNewLabel_5 = new JLabel("Customer Info For Home Delivery:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(395, 349, 285, 20);
		panel.add(lblNewLabel_5);
	}
	
	public void setTable() {
		try {
			pst = con.prepareStatement("SELECT product_name, category, selling_price, quantity FROM products");
			rs = pst.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)});
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void addIntoRecepit(int rowIndex) {
		TableModel md = table.getModel();
		String Name = md.getValueAt(rowIndex, 0).toString();
		int Price = Integer.parseInt(md.getValueAt(rowIndex, 2).toString());
		int qnt = Integer.parseInt(md.getValueAt(rowIndex, 3).toString());
		int Quantity = Integer.parseInt(quantityField.getText());
		
		if(Quantity > qnt)
			JOptionPane.showMessageDialog(null, "We don't have this item enough in stock");
		else {
			quan += Quantity;
			totalPrice += Price*Quantity;
			String str = Name+"("+Integer.toString(Quantity)+")";
			int len = Name.length()/12;
			len = 3-len;
			for(int j=0;j<len;j++) 
				str+="\t";
			str+=Integer.toString(Price*Quantity)+"\n";
			receiptArea.setText(receiptArea.getText()+str);
			items = Integer.toString(Quantity)+" "+Name+", ";
			md.setValueAt(qnt-Quantity, rowIndex, 3);
			resetQuantity(Name,md.getValueAt(rowIndex, 1).toString(), qnt-Quantity);
		}
	}
	public void deliverOrder() {
		String name = addrName.getText();
		String mobile = addrMobile.getText();
		String address = addressArea.getText();
		String time = dateGlob;
		String item = items;
		addrName.setText("");
		addrMobile.setText("");
		addressArea.setText("");
		try {
			pst = con.prepareStatement("INSERT INTO orders (name, mobile, address, items, order_time, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?)");
			pst.setString(1, name);
			pst.setString(2, mobile);
			pst.setString(3, address);
			pst.setString(4, items);
			pst.setString(5, time);
			pst.setString(6, "Delivered");
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void resetQuantity(String Name, String Category, int quantity) {
		try {
			pst = con.prepareStatement("UPDATE products SET quantity=? WHERE product_name=? and category=?");
			pst.setInt(1, quantity);
			pst.setString(2, Name);
			pst.setString(3, Category);
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}