package restaurant;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class dbconnection {
	
	public static Connection connectDB()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "1076");
			return con;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}

}
