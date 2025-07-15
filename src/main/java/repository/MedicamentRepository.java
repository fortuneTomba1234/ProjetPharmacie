package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Medicament;

public class MedicamentRepository {
    private static  Connection  MyDataBase;

  public static void InsertMedicament(String nom_medicament , String description , int prix_d_achat , Date date_d_expiration , int Quantite , String Fournisseur) throws SQLException{
    String sql = "INSERT INTO Medicament (nom_medicament , description , prix_d_achat , date_d_expiration , Quantite , Fournisseur) value (?,?,?,?,?,?)"; // rediger la requête sql
      MyDataBase = DataBase.connectDB();
      // preparer la requête
      PreparedStatement preparedStatement = MyDataBase.prepareStatement(sql);
// remplacer les paramètre de la requête
      preparedStatement.setString(1, nom_medicament);
      preparedStatement.setString(2, description);
      preparedStatement.setInt(3, prix_d_achat);
      preparedStatement.setDate(4, date_d_expiration );
      preparedStatement.setInt(5, Quantite);
      preparedStatement.setString(6, Fournisseur);


      preparedStatement.executeUpdate(); // excécution de la requête
  }

  public  List<Medicament> getMedicamentlist() throws SQLException{
    String sql = "SELECT * FROM Medicament";
    MyDataBase = DataBase.connectDB();
    PreparedStatement preparedStatement = MyDataBase.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    List<Medicament>  medicamentlist = new ArrayList<Medicament>();
    while(resultSet.next()){
        int id_medicament = resultSet.getInt("id_medicament");
         String nom_medicament = resultSet.getString("nom_medicament");
         String description =resultSet.getString("description");
         int prix_d_achat = resultSet.getInt("prix_d_achat");
         Date date_d_expiration = resultSet.getDate("date_d_expiration");
         int Quantite = resultSet.getInt("Quantite");
         String Fournisseur = resultSet.getString("Fournisseur");
         Medicament medicament = new Medicament(id_medicament, nom_medicament, description, prix_d_achat, date_d_expiration, Quantite, Fournisseur);
         medicamentlist.add(medicament);

    }
    return medicamentlist;

  }

  public static void InsertMedicament(Medicament medicament) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'InsertMedicament'");
  }
}
