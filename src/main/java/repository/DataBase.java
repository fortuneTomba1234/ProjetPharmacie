package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
private static  String url = "jdbc:mysql://172.20.0.3:3306/Pharmacie_Saint_Scott";
private static  String user = "root";
private static  String password = "rootpass";
private static Connection connection;

public static Connection connectDB() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Charge le driver
    } catch (ClassNotFoundException e) {
        throw new SQLException("MySQL JDBC Driver non trouvé !", e);
    }

    connection = DriverManager.getConnection(url, user, password);
    return connection;
}
}
