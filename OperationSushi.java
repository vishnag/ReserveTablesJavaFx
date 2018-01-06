import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import Model.Availability;
import Model.Customer;
import Model.Employee;
import Model.Item;
import Model.Person;
import Model.Rating;
import Model.ReservationInfo;

public class OperationSushi {
		private Connection connection;
		public OperationSushi(Connection c) {
			this.connection = c;
		}


		/**
		 * retrieve food items from database
		 * @Vishwas type beverage or food , or null for combination
		 * @return menu - list of items
		 */
		public ArrayList<Item> getMenu(String type) {
			String sql ="SELECT itemName, price, description FROM MenuSushi";
			if ( type != null) {
				sql += " WHERE type=?";
			}
			try {
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				if ( type != null) {
					statement.setString(1, type);
				}
				ResultSet rs = statement.executeQuery();
				ArrayList<Item> menu = new ArrayList<Item>();// maybe food menu if there is beverage menu
				while (rs.next()) {
					String itemName = rs.getString("itemName");
					double price = rs.getDouble("price");
					String desc = rs.getString("description");
					Item i = new Item(itemName, price, desc);
					menu.add(i);
				}
				rs.close();
				statement.close();
				return menu;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * Add a customer
		 * @Vihswas ln last name
		 * @Vishwas fn fist name
		 * @Vishwas email email
	     * @return true if succeed, false otherwise
	     */
		public boolean addCustomer(String ln, String fn, String email) {
			String sql_addcustomer = "INSERT INTO customerSushi (email,lastName,firstName,updatedAt) VALUES (?,?,?,CURRENT_TIMESTAMP)";
			
			try {
				PreparedStatement addCustomer = (PreparedStatement) connection.prepareStatement(sql_addcustomer);
				addCustomer.setString(1, email);
				addCustomer.setString(2, ln);
				addCustomer.setString(3, fn);
				addCustomer.execute();
				addCustomer.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
		}

		/**
		 * Get a customer by id
		 * @Vishwas id customer id
		 * @return a customer
	     */
		public Customer getACustomer(int id) {
			String sql = "Select * From customerSushi where cid = ?";

			try {

				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				statement.setInt(1, id);
				ResultSet rs = statement.executeQuery();

				if (rs.next()) {
					Customer customer = new Customer();
					customer.setEmail(rs.getString("email"));
					customer.setFirstName(rs.getString("firstName"));
					customer.setLastName(rs.getString("lastName"));
					customer.setId(rs.getInt("cid"));

					if (statement != null) statement.close();

					return customer;
				} else {
					return null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error at get a customer");
				return null;
			}
		}

	    /**
		 * Get a customer id using email
		 * @Vishwas email customer email
		 * @return a customer id
	     */
		public int getCID ( String email) {
			String sql = "Select cid FROM customer WHERE email=?";
			try {

				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				statement.setString(1, email);
				ResultSet rs = statement.executeQuery();
				if ( rs.next()) {
					return rs.getInt("cid"); //cid
				} else {
					return -1; // not exist
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed: "+e.getMessage());
				return -1;
			}
		}
		// / need to check if customer in the database already, if not , add to database ( another method)
		// need another method to get id of available table with given number of seats


		/**
		 * Reserve a table
		 * @Vishwas partySize number of people in a party
		 * @Vishwas d reservation date
		 * @Vishwas tID table id
		 * @Vishwas c a customer
	     * @Vishwas true if succeed, false otherwise
	     */
		public boolean reserveTable(int partySize,Date d,int tID , Customer c) {
			PreparedStatement statement = null;
			int customerid = getCID(c.getEmail());
			String sql_reserve ="INSERT INTO ReservationSushi (reservationDate,partySize,cID,tID) values(?, ?, ?,?)";

			try {
				connection.setAutoCommit(false);
				statement = (PreparedStatement) connection.prepareStatement(sql_reserve);
				statement.setDate(1, d);
				statement.setInt(2, partySize);
				statement.setInt(3, customerid);
				statement.setInt(4, tID);
				System.out.println("The query is" + statement.getPreparedSql());
				statement.executeUpdate();
				/*String sql = "INSERT INTO reservation(reservationDate,partySize,cID,tID) values('"+d+"','"+partySize+"','"+customerid+"','"+tID+"')";
				java.sql.Statement stmt = connection.createStatement();
				System.out.println("The query" + sql);
				int rs = stmt.executeUpdate(sql);*/
				connection.commit();
				if (statement != null) {
					statement.close();
				}

				connection.setAutoCommit(true);

				return true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Failed: "+e.getMessage());
				return false;
			}
		}


		/**
		 * Cancel a reservation
		 * @Vishwas email
		 * @Vishwas d
	     * @return
	     */
		public boolean cancelReservation(String email, Date d) {
			boolean success = false;

			int cid = getCID(email);

			if (cid != -1) {
				String queryCancelReservation ="DELETE FROM ReservationSushi WHERE cID=? AND reservationDate=?";
				try {
					PreparedStatement statement = (PreparedStatement) connection.prepareStatement(queryCancelReservation);
					statement.setInt(1, cid);
					statement.setDate(2, d);
					statement.execute();
					statement.close();
					success = true;

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					success = false;
					System.out.println("Failed: "+e.getMessage());
				} finally {

					return success;
				}
			} else {
				return false;
			}

		}

		/**
		 * Change a reservation
		 * @Vishwas email current user's email
		 * @Vishwas date date to change
		 * @Vishwas partySize number of guests to change
		 * @return true if success, false otherwise
		 * @throws SQLException
	     */
		public boolean changeReservation(String email , Date date, int partySize) throws SQLException {
			boolean success = false;
			PreparedStatement updateReservation = null;
			int customerID = getCID(email);
			String query = "update reservationSushi set reservationDate = ?, partysize = ? where cid = ?";

			try {
				connection.setAutoCommit(false);
				updateReservation = (PreparedStatement) connection.prepareStatement(query);
				updateReservation.setDate(1, date);
				updateReservation.setInt(2, partySize);
				updateReservation.setInt(3, customerID);
				updateReservation.execute();

				connection.commit();
				success = true;

			} catch (SQLException e) {
				e.printStackTrace();
				success = false;
			} finally {
				if (updateReservation != null) {
					updateReservation.close();
				}

				connection.setAutoCommit(true);
				return success;
			}
		}

		/**
		 * Rate a restaurant and give a feedback
		 * Rate uses SQL INSERT INTO
		 * @Vishwas stars number of stars
		 * @Vishwas feedback user's feedback
		 * @Vishwas customer a customer
		 * @return true if rate works fine, false otherwise
		 * @throws SQLException
	     */
		public boolean rate(int stars, String feedback, Customer customer) throws SQLException {
			boolean success = false;
			PreparedStatement insertStatement = null;
			int customerID = getCID(customer.getEmail());
			String query = "INSERT INTO RatingSushi (cid, stars, feedback) VALUES (?,?,?) " +
					"ON DUPLICATE KEY UPDATE stars=?, feedback=?";

			try {
				connection.setAutoCommit(false);
				insertStatement = (PreparedStatement) connection.prepareStatement(query);
				insertStatement.setInt(1, customerID);
				insertStatement.setInt(2, stars);
				insertStatement.setString(3, feedback);
				insertStatement.setInt(4, stars);
				insertStatement.setString(5, feedback);
				insertStatement.execute();

				connection.commit();
				success = true;

			} catch (SQLException e) {
				e.printStackTrace();
				success = false;
			} finally {
				if (insertStatement != null) {
					insertStatement.close();
				}

				connection.setAutoCommit(true);
				return success;
			}
		}

		/**
		 * Get ratings and feedbacks
		 * @return list of ratings and feedbacks in Rating objects
		 * @throws SQLException
	     */
		public ArrayList<Rating> getRatingsAndFeedbacks() throws SQLException {
			String sql ="SELECT * FROM RatingSushi";

			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Rating> list = new ArrayList<>();

				while (rs.next()) {
					Rating rating = new Rating();
					rating.setCID(rs.getInt("cID"));
					rating.setFeedback(rs.getString("feedback"));
					rating.setStars(rs.getInt("stars"));

					list.add(rating);

				}

				if (statement != null) {
					statement.close();
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error! Cannot get ratings and feedbacks");
				return null;
			}
		}

		/**
		 * Get average rating
		 * @return average rating in double or -1 if fails
		 * @throws SQLException
	     */
		public double getAverageRating () throws SQLException {
			String sql = "SELECT avg(stars)" +
					"FROM RatingSushi";
			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				double avgRating = 0;

				if (rs.next()) {
					avgRating = rs.getDouble(1);
				}

				if (statement != null) statement.close();
				return avgRating;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error! Cannot get average rating ");
				return -1;
			}

		}

		/**
		 * Get the number of employees by position
		 * @Vishwas position position in string
		 * @return the number of employees by position; 0 if position is not specified, -1 if there are errors
	     */
		public int numOfEmployeesByType(String position) {
			String sql = "SELECT count(*)\n" +
					"FROM EmployeeSushi\n" +
					"WHERE position=?";
			if (position == null) {
				return 0;
			}

			try {
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				statement.setString(1, position);
				ResultSet rs = statement.executeQuery();
				return rs.getInt(0);


			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error! Cannot get numeber of employees by position");
				return -1;
			}
		}

		/**
		 * Get all reservations
		 * @return a list of reservations
	     */
		public ArrayList<ReservationInfo> getAllReservations() {
			String sql = "SELECT firstName, lastName, tID, partysize, seats, reservationDate\n" +
					"FROM ReservationSushi NATURAL JOIN Customer NATURAL JOIN aTableSushi";

			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<ReservationInfo> list = new ArrayList<>();

				while (rs.next()) {
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					int tid = rs.getInt("tID");
					int partySize = rs.getInt("partySize");
					int seats = rs.getInt("seats");
					String date = rs.getDate("reservationDate").toString();

					ReservationInfo reservation = new ReservationInfo(tid, firstName, lastName, partySize, seats, date);
					list.add(reservation);
				}

				if (statement != null) statement.close();

				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}
		}

		/**
		 * Get all customers and all employees
		 * @return a list of all people
	     */
		public ArrayList<Person> getAllCustomersAndEmployees() {
			String sql = "SELECT firstname, lastname, email, updatedAt, discount, lastWorked\n" +
					"FROM customerSushi LEFT JOIN employeeSushi using(firstname, lastname, email)\n" +
					"UNION\n" +
					"SELECT firstname, lastname, email, updatedAt, discount, lastWorked\n" +
					"FROM customerSushi RIGHT JOIN employeeSushi using(firstname, lastname, email)";
			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Person> list = new ArrayList<>();

				while (rs.next()) {
					String fn = rs.getString("firstName");
					String ln = rs.getString("lastName");
					String email = rs.getString("email");

					Person person = new Person(fn, ln, email);
					list.add(person);
				}

				if (statement != null) statement.close();
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * Get All Employees
		 * @return a list of all employees
	     */
		public ArrayList<Employee> getAllEmployees() {
			String sql = "SELECT * FROM EmployeeSushi";

			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Employee> list = new ArrayList<>();

				while (rs.next()) {
					int eid = rs.getInt("eid");
					String fn = rs.getString("firstName");
					String ln = rs.getString("lastName");
					String position = rs.getString("position");
					String email = rs.getString("email");
					String lastworked = rs.getDate("lastworked").toString();

					Employee em = new Employee(fn, ln, email, position, lastworked);
//					em.setId(eid);

					list.add(em);
				}

				if (statement != null) statement.close();

				return  list;


			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}
		}

		/**
		 * Get a list of employees who are also customers
		 * @return a list of employees
	     */
		public ArrayList<Employee> getEmployeesWhoAreCustomers() {
			String sql = "select * from employeeSushi " +
					"where exists(" +
					"select * from customerSushi " +
					"where EmployeeSushi.firstName = customerSushi.firstname " +
					"AND employeeSushi.lastname = customerSushi.lastname)";

			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Employee> list = new ArrayList<>();

				while (rs.next()) {
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					int id = rs.getInt("eID");
					String position = rs.getString("position");
					String email = rs.getString("email");
					Date lastWorked = rs.getDate("lastWorked");

					Employee em = new Employee(firstName, lastName, email, position, lastWorked.toString());
//					em.setId(id);
					list.add(em);
				}

				if (statement != null) {
					statement.close();
				}

				return list;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error at get a list of employees who are also customers");
				return null;
			}
		}

		/**
		 * Get all customers
		 * @return a list of all customers
	     */
		public ArrayList<Customer> getAllCustomers() {
			String sql = "SELECT * FROM CustomerSushi";

			try {
				Statement statement = (Statement) connection.createStatement();
				ArrayList<Customer> list = new ArrayList<>();
				ResultSet rs = statement.executeQuery(sql);

				while (rs.next()) {
					int cid = rs.getInt("cid");
					String fn = rs.getString("firstName");
					String ln = rs.getString("lastName");
					String email = rs.getString("email");
					Timestamp time = rs.getTimestamp("updatedAt");
					String updatedAt = "null";
					if (time != null)
						updatedAt = time.toString();
					int discount = rs.getInt("discount");

					Customer customer = new Customer(fn, ln, email, updatedAt, discount);
					list.add(customer);
				}

				if (statement != null) {
					statement.close();
				}

				return list;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * Archive customers with a given date
		 * @Vishwas date
		 * @return true if succeed, false otherwise
	     */
		public boolean archive(String date) {
			String sql = "call archiveCustomers(?)";


			try {
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				statement.setString(1, date);
				statement.execute();

				if (statement != null) {
					statement.close();

				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
		}

		/**
		 * Get all archived Customers
		 * @return a list of all archived customers
	     */
		public ArrayList<Customer> getArchivedCustomers() {
			String sql = "SELECT * From arc_customerSushi";
			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Customer> list = new ArrayList<>();
				while (rs.next()) {
					int id = rs.getInt("cid");
					String firstname = rs.getString("firstName");
					String lastname = rs.getString("lastName");
					String email = rs.getString("email");
					Timestamp time = rs.getTimestamp("updatedAt");
					String updatedAt = "null";
					if (time != null)
						updatedAt = time.toString();
					int discount = rs.getInt("discount");

					Customer customer = new Customer(firstname, lastname, email, updatedAt, discount);
//					customer.setId(id);
					list.add(customer);
				}

				if (statement != null) statement.close();

				return list;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}
		}

		/**
		 * Create temporary table for weekly dates
		 * @return true if succeed, false otherwise
		 */
		public boolean callDates() {
			CallableStatement cstmt = null;
			try {
				String sql= "{call weekDates()}";
				cstmt= connection.prepareCall(sql);
				return cstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
		}

		/**
		 * Get the number of tables available for the week
		 * @return ArrayList of availability, which is date and number of tables available
	     */
		public ArrayList<Availability> getWeeklyAvailability() {
			String sql = "SELECT *\n" +
					"FROM (\n" +
					"SELECT reservationDate, (SELECT count(*) FROM aTableSushi) - count(*) as tablesAvailable\n" +
					"FROM ReservationSushi\n" +
					"WHERE reservationDate - CURDATE() < 7\n" +
					"GROUP BY reservationDate\n" +
					"UNION\n" +
					"SELECT adate, (SELECT count(*) FROM aTableSushi)\n" +
					"FROM dates\n" +
					"WHERE NOT EXISTS (\n" +
					"\tSELECT reservationDate\n" +
					"\tFROM ReservationSushi\n" +
					"\tWHERE reservationDate - CURDATE() < 7\n" +
					"\tGROUP BY reservationDate\n" +
					"HAVING reservationDate=adate)\n" +
					") as availability\n" +
					"ORDER BY reservationDate;";
			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ArrayList<Availability> list = new ArrayList<Availability>();
				int i = 0;
				while (rs.next()) {
					String date = rs.getDate("reservationDate").toString();
					int tablesAvailable = rs.getInt("tablesAvailable");

					Availability day = new Availability(tablesAvailable, date);
					list.add(day);
				}

				if (statement != null) statement.close();

				return list;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}
		}
	}

