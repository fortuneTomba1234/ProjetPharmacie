package repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.ServiceAchat;

public class ServiceAchatRepository {
     private Statement statement;
    private Connection connection;
    private String sql;

  public ServiceAchatRepository(){

  } 

  public void insertServiceAchat() throws SQLException{
     this.sql="INSERT INTO ServiceAchat(nom_client, quantite, prix_total, etat) values ( 'tomba', 20, 10000, 'payé')";
        connection = DataBase.connectionBD();
        statement = connection.createStatement();
        statement.executeUpdate(sql);
  }

   public void ajouterServiceAchat  (String nom_client, int quantite, int prix_total, String etat) throws SQLException{
        this.sql = "INSERT INTO ServiceAchat(nom_client, quantite, prix_total, etat) values (?, ?, ?, ?)";
        try (Connection connection = DataBase.connectionBD();
             PreparedStatement ps = connection.prepareStatement(this.sql)) {
            ps.setString(1, nom_client);
            ps.setInt(2, quantite);
            ps.setInt(3, prix_total);
            ps.setString(4, etat);
        ps.executeUpdate();
        System.out.println("service ajouté avec succès !");
   }
  }

   public void modifierServiceAchat(String nom_client, int quantite, int prix_total, String etat, int id)throws SQLException{
        this.sql="UPDATE ServiceAchat SET nom_client=?, quantite =?, prix_total =?, etat=? WHERE id=?";
        connection = DataBase.connectionBD();
        PreparedStatement ps = connection.prepareStatement(this.sql);
         ps.setString(1, nom_client);
        ps.setInt(2, quantite);
        ps.setInt(3, prix_total);
        ps.setString(4, etat);
        ps.setInt(5,id);
        ps.executeUpdate();
        System.out.println("service modifié avec succès !");
    }

     public void supprimerServiceAchat(int id) throws SQLException{
        this.sql = "DELETE FROM ServiceAchat WHERE id=?";
        connection = DataBase.connectionBD();
      PreparedStatement ps = connection.prepareStatement(this.sql);
      ps.setInt(1, id);
      ps.executeUpdate();
    }

   public List<ServiceAchat> listeServiceAchat() throws SQLException {
    List<ServiceAchat> liste = new ArrayList<>();
    this.sql = "SELECT * FROM ServiceAchat";
    connection = DataBase.connectionBD();
    PreparedStatement ps = connection.prepareStatement(this.sql);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
        ServiceAchat sa = new ServiceAchat();
        sa.setId(rs.getInt("id"));
        sa.setNomClient(rs.getString("nom_client"));
        sa.setQuantite(rs.getInt("quantite"));
        sa.setPrixTotal(rs.getInt("prix_total"));
        sa.setEtat(rs.getString("etat"));
        liste.add(sa);
    }

    return liste;
}

  public void ajouterServiceAchat(ServiceAchat sa) throws SQLException {
    ajouterServiceAchat(sa.getNomClient(), sa.getQuantite(), sa.getPrixTotal(), sa.getEtat());
}

}  