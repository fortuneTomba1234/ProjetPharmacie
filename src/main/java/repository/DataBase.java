package repository;


import java.sql.*;


public class DataBase {
    private static String url = "jdbc:mysql://mysql:3306/Gestionmedicament";
    private static String user = "root";
    private static String password = "root";
    private static Connection connectionBd;


    public static Connection connectionBD() throws SQLException{ try {
        Class.forName("com.mysql.cj.jdbc.Driver");  // Charger le driver MySQL
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException("Driver MySQL non trouvé !");
    }
    

    connectionBd = DriverManager.getConnection(url, user, password);
    return connectionBd;
    }

}
