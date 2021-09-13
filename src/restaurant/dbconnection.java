package restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

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
	
	public static void sendEmail(String toEmail) {
		String fromEmail = "syedafsani2019@gmail.com";
		String host = "localhost";
		
		Properties properties = System.getProperties();
		
	}

}
