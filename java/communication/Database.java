package communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
	private static final String	URL		= "jdbc:mysql://localhost/projectc?user=root&password=root";
	private Connection			conn	= null;
	private static Database		_db;

	public Database(){
		connect();
	}

	public static Database getDatabaseInstance(){
		if (_db == null)
			_db = new Database();
		return _db;
	}

	private void connect(){

		try{
			conn = DriverManager.getConnection(URL);

		} catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public ResultSet querySelect(String sql){
		Statement stm;
		ResultSet rs = null;
		try{
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		} catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return rs;
	}

	public int queryUpdate(String sql){
		Statement stm;
		int rs = 0;
		try{
			stm = conn.createStatement();
			rs = stm.executeUpdate(sql);
		} catch (SQLException ex){

		}
		return rs;
	}

	public boolean insertMessage(String username, String message){

		try{
			// get userID
			ResultSet user = querySelect("SELECT * FROM `users` WHERE `username`='"
					+ username + "'");
			user.first();
			int id = user.getInt("id");
			// sql to insert message into db
			// INSERT INTO {TABLE} ({COLUMS}) VALUES ({VALUES})
			String time = "blarg time";
			String sql = "INSERT INTO game_messages (`to_user_id`,`message`,`time`) VALUES ('"
					+ id + "','" + message + "','" + time + "')";
			queryUpdate(sql);
			sql = "UPDATE users SET `has_private`='1' WHERE `id`=" + id;
			queryUpdate(sql);

		} catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return false;
	}

}
