/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uir;
import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.jdbc.odbc.JdbcOdbcDriver;
/**
 *
 * @author Антон
 */
public class EstablishConnection {

    private Connection myConnection;
    private ResultSet rs;
    private Statement pstmt;
    private static Database dbconnection;

    EstablishConnection (String url, String login, String password) {
        try {
            Class theDriver = JdbcOdbcDriver.class;
            myConnection = DriverManager.getConnection(url, login, password);
            try {
            dbconnection = CacheDatabase.getDatabase (url, login, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Database getDatabase() {
        return dbconnection;
    }

    public void importFile(String filename) {
         try {
                InternalIO.ParseFile(EstablishConnection.getDatabase(), filename);
                                        } catch (CacheException ex) {
                                    System.out.println(ex.getMessage());
                                 }
    }
}
