package restaurant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import afsani.gradiant.label.button;
import afsani.gradiant.panels.mainboard.MainBoardBG;
import javax.swing.JLabel;

public class CustomerPanel extends JFrame{
	
	public CustomerPanel() {
		setLayout(null);
		setSize(910,650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initialize();
	}
	
	private void initialize() {
		MainBoardBG panel = new MainBoardBG();
		panel.setSize(910, 650);
		panel.setVisible(true);
		panel.setLayout(null);
		add(panel);
		
		
		button bton = new button("Log out", new Color(31, 111, 212), new Color(129, 161, 202));
		bton.setHorizontalAlignment(SwingConstants.CENTER);
		bton.setForeground(Color.WHITE);
		bton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bton.setBounds(815, 580, 75, 27);
		bton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				Main man = new Main();
			}
			
			public void mousePressed(MouseEvent e) {
			bton.hover(new Color(31, 96, 178), new Color(108, 134, 168));}
			
			public void mouseReleased(MouseEvent e) {
				bton.hover(new Color(31, 111, 212), new Color(129, 161, 202));}
		});
		panel.add(bton);
	}
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CustomerPanel().setVisible(true);
				}catch (Exception e) {
				e.printStackTrace();}
				
			}
		});
	}
}
