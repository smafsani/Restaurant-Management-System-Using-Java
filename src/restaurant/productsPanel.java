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

public class productsPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel, pan;
	private button btnUpdate, btnDelete;
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
					productsPanel window = new productsPanel();
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
	public productsPanel() {
		con = dbconnection.connectDB();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JLabel lblNewLabel = new JLabel("Products");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 23));
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
				if(searchtextField.getText().equals("Enter Product ID/Name  ")) {
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
				if(!searchtextField.getText().equals("") && !searchtextField.getText().equals("Enter Product ID/Name  ")) {
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
					false, true, true, true, true, true, true, true
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
		model.addColumn("Product ID");
		model.addColumn("Name");
		model.addColumn("Category");
		model.addColumn("Buying Price");
		model.addColumn("Selling Price");
		model.addColumn("Profit");
		model.addColumn("Available");
		model.addColumn("Receiving Date");
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(7).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		setTable();
		
		btnUpdate = new button("Update",new Color(135, 135, 215),new Color(74, 74, 123));
		btnUpdate.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnUpdate.hover(new Color(48, 48, 81), new Color(48, 48, 81));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUpdate.hover(new Color(135, 135, 215),new Color(74, 74, 123));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				errorTextArea.setVisible(false);
				if(table.getSelectedRow() != -1)
					updateProduct(table.getSelectedRow());
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnUpdate.setBounds(500, 425, 80, 25);
		panel.add(btnUpdate);
		
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
		errorTextArea.setBounds(135, 453, 430, 45);
		panel.add(errorTextArea);
		
	}
	private void setPlaceholder() {
		searchtextField.setText("Enter Product ID/Name  ");
		searchtextField.setForeground(Color.GRAY);
	}
	private void updateProduct(int rowIndex) {
		TableModel model2 = table.getModel();
		String id = model2.getValueAt(rowIndex, 0).toString();
		String name = model2.getValueAt(rowIndex, 1).toString();
		String cat = model2.getValueAt(rowIndex, 2).toString();
		String bp = model2.getValueAt(rowIndex, 3).toString();
		String sp = model2.getValueAt(rowIndex, 4).toString();
		String prft = model2.getValueAt(rowIndex, 5).toString();
		String avil = model2.getValueAt(rowIndex, 6).toString();
		String rd = model2.getValueAt(rowIndex, 7).toString();
		boolean bool = true;
		bool = checkValidOrNot2(name, "name");
		if(bool)bool = checkValidOrNot2(cat, "category");
		if(bool)bool = checkValidOrNot3(bp, "buying price");
		if(bool)bool = checkValidOrNot3(sp, "selling price");
		if(bool)bool = checkValidOrNot3(prft, "profit");
		if(bool)bool = checkValidOrNot3(avil, "quantity");
		if(bool)bool = checkValidOrNot2(rd, "receiving date");
		
		if(bool) {
			try {
				pst = con.prepareStatement("UPDATE products SET product_name = ?, category = ?, buying_price = ?,"
						+ " selling_price = ?, profit = ?, quantity = ?, receiving_date = ?"
						+ "WHERE product_id = ?;");
				pst.setString(1, name);
				pst.setString(2, cat);
				pst.setInt(3, Integer.parseInt(bp));
				pst.setInt(4, Integer.parseInt(sp));
				pst.setInt(5, Integer.parseInt(prft));
				pst.setInt(6, Integer.parseInt(avil));
				pst.setString(7, rd);
				pst.setInt(8, Integer.parseInt(id));
				pst.executeUpdate();
				
				model.setRowCount(0);
				setTable();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	private void removeProduct(int rowIndex) {
		try {
			TableModel model2 = table.getModel();
			int id = Integer.parseInt(model2.getValueAt(rowIndex, 0).toString());
			pst = con.prepareStatement("DELETE FROM products WHERE product_id = ?");
			pst.setInt(1, id);
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete this product", "Delete", JOptionPane.YES_NO_OPTION);
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
			pst = con.prepareStatement("Select * FROM products");
			rs = pst.executeQuery();
			flag = false;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)
						, rs.getInt(6), rs.getInt(7), rs.getString(8)});
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
				pst = con.prepareStatement("Select * FROM products WHERE product_id=?");
				pst.setInt(1, x);
			}
			else {
				pst = con.prepareStatement("Select * FROM products WHERE product_name=?");
				pst.setString(1, str);
			}
			rs = pst.executeQuery();
			flag = true;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)
						, rs.getInt(6), rs.getInt(7), rs.getString(8)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private boolean checkValidOrNot2(String s,String str) {
		if(s.equals("")) {
			errorTextArea.setText("Invalid "+str+" input. Please check and try again.");
			errorTextArea.setVisible(true);
		}
		else {
			return true;
		}
		return false;
	}

	private boolean checkValidOrNot3(String s,String str) {
		try {
			int x = Integer.parseInt(s);
			System.out.println(x);
			return true;
		} catch (Exception e) {
			errorTextArea.setText("Invalid "+str+" input. Please check and try again.");
			errorTextArea.setVisible(true);
		}
		return false;
	}
	public void clearPanel() {
		panel.remove(pan);
		panel.revalidate();
		panel.repaint();
	}
}
