package restaurant;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private int quan=0;
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
	private JTextField txtD;
	private JScrollPane scrollPane_1;
	private JCheckBox checkBoxTable;
	private JCheckBox checkBoxHome;
	private String globalType = "";
	private JTextArea receiptArea;
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
		lblNewLabel.setBackground(new Color(100, 100, 100));
		lblNewLabel.setBounds(1, 1, 688, 46);
		panel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 50, 331, 259);
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
		table.getTableHeader().setBackground(new Color(117, 0, 117));
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
		table.getColumnModel().getColumn(2).setMinWidth(55);
		table.getColumnModel().getColumn(2).setMaxWidth(55);
		table.getColumnModel().getColumn(2).setWidth(55);
		table.getColumnModel().getColumn(3).setMinWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setWidth(50);
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
		
		btnAdd = new button("Add",new Color(255, 0, 255),new Color(127, 0, 127));
		btnAdd.start = Color.LIGHT_GRAY;
		btnAdd.end = Color.GRAY;
		btnAdd.setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnAdd.hover(new Color(75, 0, 75), new Color(75, 0, 75));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnAdd.hover(new Color(255, 0, 255),new Color(127, 0, 127));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
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
		
		txtD = new JTextField(Integer.toString(quan));
		txtD.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		txtD.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtD.setHorizontalAlignment(SwingConstants.CENTER);
		txtD.setBounds(84, 311, 61, 25);
		panel.add(txtD);
		txtD.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(358, 49, 296, 325);
		panel.add(scrollPane_1);
		
		receiptArea = new JTextArea();
		scrollPane_1.setViewportView(receiptArea);
		receiptArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		receiptArea.setText("a\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na");
		
		checkBoxTable = new JCheckBox("On Table");
		checkBoxTable.setBackground(new Color(250, 250, 250));
		if(globalType.contains("Admin"))
			checkBoxTable.setVisible(true);
		else
			checkBoxTable.setVisible(false);
		checkBoxTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxTable.isSelected())
					checkBoxHome.setSelected(false);
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
				if(checkBoxHome.isSelected())
					checkBoxTable.setSelected(false);
			}
		});
		checkBoxHome.setBounds(5, 343, 112, 20);
		panel.add(checkBoxHome);
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
}
