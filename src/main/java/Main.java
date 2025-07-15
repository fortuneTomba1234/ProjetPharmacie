import java.sql.Connection;
import java.sql.SQLException;

import repository.DataBase;

public class Main {
     private static Connection database;
     
     public static void main(String[] args) {
        try {
            database =DataBase.connectDB();
            System.out.println("Connexion à la base de données reussie !");       
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Echec de la connexion!");
        }
        
     }
    }
     