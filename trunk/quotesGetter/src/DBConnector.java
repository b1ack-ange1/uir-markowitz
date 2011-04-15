import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.CacheException;
import IO.InternalIO;
//import java.sql.*;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import sun.jdbc.odbc.JdbcOdbcDriver;

public class DBConnector {
	public static final String dbURL = "jdbc:Cache://127.0.0.1:1972/LISE_F";
	public static final String dbUser = "extConnector";
	public static final String dbPassword = "5njd_ap2xc";
	
	//private Connection myConnection;
	private static Database dbConnection;

	public DBConnector() {
		//try {
			//Class theDriver = JdbcOdbcDriver.class;
			//myConnection = DriverManager.getConnection(url, login, password);
			try {
				dbConnection = CacheDatabase.getDatabase(dbURL, dbUser, dbPassword);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		//} catch (SQLException ex) {
			//System.out.println(ex.getMessage());
			// Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null,
			// ex);
		//}
	}

	public static Database getDatabase() {
		return dbConnection;
	}

	public void importFile(String filename) {
		try {
			InternalIO.ParseFile(DBConnector.getDatabase(), filename);
		} catch (CacheException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
