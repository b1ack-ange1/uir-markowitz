package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	protected static final Logger LOGGER = Logger
			.getLogger("ru.softlab.rsdh.webService");

	private static DataSource dataSource;

	public static Connection getConnection(String userName, String password)
			throws SQLException {
		return getDataSource().getConnection(userName, password);
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			Context initContext = null;
			try {
				try {
					initContext = new InitialContext();
					dataSource = (DataSource) initContext
							.lookup("java:comp/env/jdbc/rsdhDB");
				} finally {
					if (initContext != null)
						initContext.close();
				}
			} catch (NamingException e) {
				LOGGER.log(Level.SEVERE, ConnectionManager.class.getName(), e);
			}
		}
		return dataSource;
	}
}