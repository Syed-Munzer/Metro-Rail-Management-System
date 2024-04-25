package MetroManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database instance;
    private Connection connection;
    private Statement statement;

    // Private constructor to prevent instantiation from outside
    private Database() throws SQLException {
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3307/metro management system";
        connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    // Static method to get the instance of Database class
    public static Database getInstance() throws SQLException {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Statement getStatement() 
    {
        return statement;
    }

    public Connection getConnection() 
    {
        return connection;
    }
}
