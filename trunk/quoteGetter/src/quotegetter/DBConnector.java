/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quotegetter;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.CacheException;
import io.IOInternal;
import resource.TickerCovariance;

public class DBConnector {

    public static final String dbURL = "jdbc:Cache://127.0.0.1:1972/LISE_F";
    public static final String dbUser = "extConnector";
    public static final String dbPassword = "5njd_ap2xc";
    private static Database dbConnection;

    public DBConnector() {
        try {
            dbConnection = CacheDatabase.getDatabase(dbURL, dbUser, dbPassword);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    public static Database getDatabase() {
        return dbConnection;
    }

    public void importFile(String filename) {
        try {
            IOInternal.parseFile(DBConnector.getDatabase(), filename);
            TickerCovariance.callCountCovariances(DBConnector.getDatabase());
        } catch (CacheException ex) {
            //System.out.println(ex.getMessage());
        }
    }
}
