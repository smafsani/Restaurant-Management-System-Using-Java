package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class OrderFoodCustomer {

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
	private String globalName = "", globalPass="";
	private JTextArea receiptArea,addrName, addrMobile, addressArea;
	private button resetBtn;
	private JLabel lblNewLabel_2,lblNewLabel_3,lblNewLabel_4;
	private button deliverBtn;
	private JLabel lblNewLabel_5;
	private String items="";
	private Map<String, product> map = new HashMap<String, product>();
	private button totalBtn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderFoodCustomer window = new OrderFoodCustomer("user1", "user1pass");
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
	public OrderFoodCustomer(String n, String p) {
		globalName = n;
		globalPass = p;
		totalPrice = 0;
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
						addIntoItem(table.getSelectedRow());
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
		panel.add(receiptAreaScroll);
		
		receiptArea = new JTextArea();
		receiptAreaScroll.setViewportView(receiptArea);
		receiptArea.setEditable(false);
		receiptArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		receiptArea.setFont(new Font("SensSerif", Font.PLAIN, 12));
		
		resetBtn = new button("Reset", new Color(0, 133, 0),new Color(0, 51, 51));
		resetBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setHorizontalAlignment(SwingConstants.CENTER);
		resetBtn.setBounds(5, 348, 70, 25);
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
				receiptArea.setText("");
				model.setRowCount(0);
				setTable();
				map.clear();
			}
		});
		panel.add(resetBtn);
		
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
		addrName.setColumns(10);
		
		addrMobile = new JTextArea();
		addrMobile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		addrMobile.setColumns(10);
		addrMobile.setBounds(449, 394, 231, 20);
		addrMobile.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		panel.add(addrMobile);
		
		addressArea = new JTextArea();
		addressArea.setBounds(449, 422, 231, 60);
		addressArea.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		panel.add(addressArea);
		
		deliverBtn = new button("Order", new Color(0, 133, 0),new Color(0, 51, 51));
		deliverBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deliverBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		deliverBtn.setForeground(Color.WHITE);
		deliverBtn.setHorizontalAlignment(SwingConstants.CENTER);
		deliverBtn.setBounds(84, 348, 70, 25);
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
				if(!map.isEmpty()) {
					if(addressArea.getText().equals("") || addrMobile.getText().equals("") || addrName.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Give Your Information");
					}
					else {
						String s = "";
						for(Entry<String, product> entry: map.entrySet()) {
							s += Integer.toString(entry.getValue().orderedQuantity)+"-"+entry.getValue().Name+",";
						}
						String s2 = "\nName: "+addrName.getText()+"\n";
						s2 += "Mobile: "+addrMobile.getText()+"\n";
						s2 += "Address: "+addressArea.getText()+"\n";
						receiptArea.setText(receiptArea.getText()+s2);
						makeOrder(s);
					}
				}
			}
		});
		panel.add(deliverBtn);
		
		totalBtn = new button("Total", new Color(0, 133, 0),new Color(0, 51, 51));
		totalBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		totalBtn.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
		totalBtn.setForeground(Color.WHITE);
		totalBtn.setHorizontalAlignment(SwingConstants.CENTER);
		totalBtn.setBounds(165, 348, 70, 25);
		totalBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				totalBtn.hover(new Color(0, 51, 51),new Color(0, 51, 51));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				totalBtn.hover(new Color(0, 133, 0),new Color(0, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				receiptArea.setText(receiptArea.getText()+"\nTotal: "+totalPrice+" Tk");
			}
		});
		panel.add(totalBtn);
		
		lblNewLabel_5 = new JLabel("Your Info For Home Delivery:");
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
	public void addIntoItem(int rowIndex) {
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
			String str = Integer.toString(Quantity)+" "+Name+"(s) ";
			str+=Integer.toString(Price*Quantity)+" Tk\n";
			receiptArea.setText(receiptArea.getText()+str);
			items = Integer.toString(Quantity)+" "+Name+", ";
			md.setValueAt(qnt-Quantity, rowIndex, 3);
			product pr = new product(Name, md.getValueAt(rowIndex, 1).toString(), qnt-Quantity, Quantity);
			map.put(Name, pr);
		}
	}
	public void makeOrder(String s) {
		String name = addrName.getText();
		String mobile = addrMobile.getText();
		String address = addressArea.getText();
		Date obj = new Date();
		String date = obj.toString();
		date = date.substring(0, date.length()-9);
		addrName.setText("");
		addrMobile.setText("");
		addressArea.setText("");
		int id = 0;
		try {
			pst = con.prepareStatement("SELECT user_id FROM users WHERE username=? and password=?");
			pst.setString(1, globalName);
			pst.setString(2, globalPass);
			rs = pst.executeQuery();
			if(rs.next()) {
				id = rs.getInt("user_id");
			}
		}
		catch(Exception e) {}

		try {
			pst = con.prepareStatement("INSERT INTO orders (user_id, name, mobile, address, items, order_time, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.setString(3, mobile);
			pst.setString(4, address);
			pst.setString(5, s);
			pst.setString(6, date);
			pst.setString(7, "Not Delivered");
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Your order is sent");
		} catch (Exception e) {
			// TODO: handle exception
		}
		resetQuantity();
	}
	public void resetQuantity() {
		for(Entry<String, product>entry: map.entrySet()) {
			try {
				pst = con.prepareStatement("UPDATE products SET quantity=? WHERE product_name=? and category=?");
				pst.setInt(1, entry.getValue().Quantity);
				pst.setString(2, entry.getValue().Name);
				pst.setString(3, entry.getValue().Category);
				pst.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
class product{
	String Name;
	String Category;
	int Quantity;
	int orderedQuantity;
	public product(String n, String c, int q, int oq) {
		Name = n;
		Category = c;
		Quantity = q;
		orderedQuantity = oq;
	}
}