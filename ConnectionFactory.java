import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/** @author Vishwas*/

public class ConnectionFactory {
	public static Connection getMYSQLConnection()  {

		Connection connection = null;

		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ultimate_rest", "root","root");
			if (connection != null) {
				System.out.println("Database Connected!");
			} else {
				System.out.println("Failed to make connection!");
			}
			return connection;

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		} 
/**@author Vishwas */
		

	}
}
