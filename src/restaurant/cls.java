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
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTextArea;

public class cls {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel, pan, panel2;
	private button btnOrder, btnDelete;
	private JTextArea errorTextArea;
	private JPanel searchField;
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private JTextField searchtextField;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private boolean flag = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cls window = new cls();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public cls() {
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
		
		JLabel lblNewLabel = new JLabel("Orders");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(102, 102, 204));
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
				if(searchtextField.getText().equals("Enter Order ID  ")) {
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
				if(!searchtextField.getText().equals("") && !searchtextField.getText().equals("Enter Order ID  ")) {
					model.setRowCount(0);
					setTable(searchtextField.getText());
					if(model.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "No product found of Product-ID "+searchtextField.getText());
					}
				}
			}
		});
		searchBtn.setBounds(225, 6, 75, 23);
		searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchBtn.setBackground(new Color(102, 102, 153));
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
		allBtn.setBackground(new Color(102, 102, 153));
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
					false, false, false
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
		model.addColumn("OrderID");
		model.addColumn("UserID");
		model.addColumn("Name");
		scrollPane.setViewportView(table);
		setTable();
		
		btnOrder = new button("Order",new Color(135, 135, 215),new Color(74, 74, 123));
		btnOrder.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnOrder.hover(new Color(48, 48, 81), new Color(48, 48, 81));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnOrder.hover(new Color(135, 135, 215),new Color(74, 74, 123));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorTextArea.setVisible(false);
				if(table.getSelectedRow() != -1) {
					panel.setVisible(false);
					TableModel model2 = table.getModel();
					int id = Integer.parseInt(model2.getValueAt(table.getSelectedRow(), 0).toString());
					panel2 = new Order(id).returnPanel();
					panel2.setLocation(0, 0);
					frame.getContentPane().add(panel2);
					//panel2.add(btnBack);
				}
			}
		});
		btnOrder.setForeground(Color.WHITE);
		btnOrder.setHorizontalAlignment(SwingConstants.CENTER);
		btnOrder.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnOrder.setBounds(500, 425, 80, 25);
		panel.add(btnOrder);
		
		btnDelete = new button("Delete",new Color(135, 135, 215),new Color(74, 74, 123));
		btnDelete.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDelete.hover(new Color(48, 48, 81), new Color(48, 48, 81));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDelete.hover(new Color(135, 135, 215),new Color(74, 74, 123));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorTextArea.setVisible(false);
				if(table.getSelectedRow() != -1)
					removeProduct(table.getSelectedRow());
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
		errorTextArea.setBounds(185, 453, 330, 45);
		panel.add(errorTextArea);
		
	}
	private void setPlaceholder() {
		searchtextField.setText("Enter Order ID  ");
		searchtextField.setForeground(Color.GRAY);
	}
	
	private void removeProduct(int rowIndex) {
		try {
			TableModel model2 = table.getModel();
			int id = Integer.parseInt(model2.getValueAt(rowIndex, 0).toString());
			pst = con.prepareStatement("DELETE FROM orders WHERE order_id = ?");
			pst.setInt(1, id);
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Order", "Delete", JOptionPane.YES_NO_OPTION);
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
			pst = con.prepareStatement("Select * FROM orders");
			rs = pst.executeQuery();
			flag = false;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private void setTable(String str) {
		int x=0;
		boolean chkd = true;
		try {
			x = Integer.parseInt(str);
			chkd = true;
		}catch(Exception e) {errorTextArea.setText("Enter Valid Order_ID"); errorTextArea.setVisible(true);}
		if(chkd) {
			try {
				pst = con.prepareStatement("Select * FROM orders WHERE order_id=?");
				pst.setInt(1, x);
				
				
				rs = pst.executeQuery();
				flag = true;
				while(rs.next())
				{
					model.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3)});
				}
			} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);}
		}
	}

	public void clearPanel() {
		panel.remove(pan);
		panel.revalidate();
		panel.repaint();
	}
}
class Order{
	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel;
	PreparedStatement pst = null;
	Connection con = null;
	ResultSet rs = null;
	private int orderid = -1;
	private JTextField useridField, orderidField, nameField, mobileField;
	private JTextArea itemArea, addressArea;
	private JTextField ordertimeField;
	
	
	public Order(int oid) {
		orderid = oid;
		con = dbconnection.connectDB();
		initialize();
	}
	public JPanel returnPanel() {
		return panel;
	}
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 589);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Order");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 0, 102));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		JLabel lblNewLab_1 = new JLabel("Order ID:");
		lblNewLab_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_1.setForeground(Color.BLACK);
		lblNewLab_1.setBounds(60, 70, 115, 25);
		panel.add(lblNewLab_1);
		
		orderidField = new JTextField();
		orderidField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		orderidField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		orderidField.setBounds(185, 68, 258, 30);
		panel.add(orderidField);
		orderidField.setColumns(10);
		
		JLabel lblNewLab_2 = new JLabel("User ID:");
		lblNewLab_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_2.setForeground(Color.BLACK);
		lblNewLab_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_2.setBounds(60, 115, 115, 25);
		panel.add(lblNewLab_2);
		
		useridField = new JTextField();
		useridField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		useridField.setBounds(185, 113, 258, 30);
		useridField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(useridField);
		useridField.setColumns(10);
		
		JLabel lblNewLab_3 = new JLabel("Name:");
		lblNewLab_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_3.setForeground(Color.BLACK);
		lblNewLab_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_3.setBounds(60, 160, 115, 25);
		panel.add(lblNewLab_3);
		
		JLabel lblNewLab_4 = new JLabel("Mobile:");
		lblNewLab_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_4.setForeground(Color.BLACK);
		lblNewLab_4.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_4.setBounds(44, 205, 131, 25);
		panel.add(lblNewLab_4);
		
		mobileField = new JTextField();
		mobileField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		mobileField.setBounds(185, 203, 258, 30);
		mobileField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(mobileField);
		mobileField.setColumns(10);
		
		JLabel lblNewLab_5 = new JLabel("Address:");
		lblNewLab_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_5.setForeground(Color.BLACK);
		lblNewLab_5.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_5.setBounds(60, 250, 115, 25);
		panel.add(lblNewLab_5);
		
		addressArea = new JTextArea();
		addressArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
		addressArea.setColumns(10);
		addressArea.setBounds(185, 248, 258, 45);
		addressArea.setLineWrap(true);
		addressArea.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(addressArea);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nameField.setColumns(10);
		nameField.setBounds(185, 158, 258, 30);
		nameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(nameField);
		
		JLabel lblNewLab_6 = new JLabel("Items:");
		lblNewLab_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_6.setForeground(Color.BLACK);
		lblNewLab_6.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_6.setBounds(60, 310, 115, 25);
		panel.add(lblNewLab_6);
		
		itemArea = new JTextArea();
		itemArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
		itemArea.setColumns(10);
		itemArea.setBounds(185, 308, 258, 45);
		itemArea.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(itemArea);
		
		JLabel lblNewLab_7 = new JLabel("Order Time:");
		lblNewLab_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLab_7.setForeground(Color.BLACK);
		lblNewLab_7.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLab_7.setBounds(60, 370, 115, 25);
		panel.add(lblNewLab_7);
		
		ordertimeField = new JTextField();
		ordertimeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		ordertimeField.setColumns(10);
		ordertimeField.setBounds(185, 368, 258, 30);
		ordertimeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(ordertimeField);
		
		
		fillFields();
	
	}
	
	private void fillFields() {
		if(orderid != -1) {
			try {
				pst = con.prepareStatement("SELECT * FROM orders WHERE order_id=?");
				pst.setInt(1, orderid);
				rs = pst.executeQuery();
				if(rs.next()) {
					orderidField.setText(Integer.toString(orderid));
					useridField.setText(Integer.toString(rs.getInt(2)));
					nameField.setText(rs.getString(3));
					mobileField.setText(rs.getString(4));
					addressArea.setText(rs.getString(5));
					itemArea.setText(rs.getString(6));
					ordertimeField.setText(rs.getString(7));
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
}
