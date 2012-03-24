package edu.ncsu.csc.csc440.project1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class DBConnection {
    
    private static final String filename = "app.config";
    private static boolean s_loaded = false;
    private static String s_username = null;
    private static String s_password = null;
    private static String s_url = null;
    
    
    public DBConnection() throws Exception {
        // Load the driver. This creates an instance of the driver
	    // and calls the registerDriver method to make Oracle Thin
	    // driver available to clients.
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        if (!s_loaded) {
            // Load the connection config from the properties file.
            Properties prop = new Properties();
            InputStream is = null;
            try {
                is = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                throw e;
            }
            prop.load(is);
            
            s_username = prop.getProperty("app.db.username");
            s_password = prop.getProperty("app.db.password");
            s_url = prop.getProperty("app.db.url");
            s_loaded = true;
            
            System.out.printf("s_username: %s\n", s_username);
            System.out.printf("s_password: %s\n", s_password);
            System.out.printf("s_url:      %s\n", s_url);
        }
    }
    
    public Connection getConnection() throws SQLException {
        // Get a connection from the first driver in the
		// DriverManager list that recognizes the URL jdbcURL
        return DriverManager.getConnection(s_url, s_username, s_password);
    }
}

