package id.pbotubes.utils;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    public Connection connection;

    public Connection getConnection() {
        String database = "db_sistem_tambak";
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/" + database;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return connection;
    }
}