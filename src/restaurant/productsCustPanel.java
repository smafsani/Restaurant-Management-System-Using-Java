package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

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

public class productsCustPanel {

	private JFrame frame;
	private int w = 690;
	private int h = 550;
	private JPanel panel, pan;
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
					productsCustPanel window = new productsCustPanel();
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
	public productsCustPanel() {
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
		lblNewLabel.setBackground(new Color(64, 64, 202));
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
				if(searchtextField.getText().equals("Enter Product Name  ")) {
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
				if(!searchtextField.getText().equals("") && !searchtextField.getText().equals("Enter Product Name  ")) {
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
		searchBtn.setBackground(new Color(64, 64, 202));
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
		allBtn.setBackground(new Color(64, 64, 202));
		allBtn.setForeground(Color.WHITE);
		allBtn.setFocusPainted(false);
		allBtn.setHorizontalAlignment(SwingConstants.CENTER);
		allBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		allBtn.setBorder(new MatteBorder(1,1,1,1, new Color(102, 51, 255)));
		searchField.add(allBtn);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 81, 680, 380);
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
		table.getTableHeader().setBackground(new Color(64, 64, 202));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setFont(new Font("SansSerif", Font.PLAIN, 12));
		table.setOpaque(false);
		model.addColumn("Name");
		model.addColumn("Category");
		model.addColumn("Selling Price");
		model.addColumn("Available");
		scrollPane.setViewportView(table);
		setTable();
				
		
	}
	private void setPlaceholder() {
		searchtextField.setText("Enter Product Name  ");
		searchtextField.setForeground(Color.GRAY);
	}

	private void setTable() {
		try {
			pst = con.prepareStatement("Select * FROM products");
			rs = pst.executeQuery();
			flag = false;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(7)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}
	private void setTable(String str) {
		try {
			pst = con.prepareStatement("Select * FROM products WHERE product_name=?");
			pst.setString(1, str);
			rs = pst.executeQuery();
			flag = true;
			while(rs.next())
			{
				model.addRow(new Object[] {rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(7)});
			}
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);}
	}

	public void clearPanel() {
		panel.remove(pan);
		panel.revalidate();
		panel.repaint();
	}
}
