package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.LigneAchat;

public class LigneAchatRepository {
    private java.sql.Statement statement;
    private Connection connection;
    private String sql;
    private int prix_unitaireire;

    public LigneAchatRepository(){

    }

    public void insertLigneAchat() throws SQLException{
        this.sql="INSERT INTO LigneAchat(reference, quantite, prix_unitaire, montant) values ( 'medicament', 100, 100, 10000)";
        connection = DataBase.connectionBD();
        statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
   public void ajouterLigneAchat(int code, String reference, int quantite, int prix_unitaire, int montant) throws SQLException {
    this.sql = "INSERT INTO LigneAchat(code, reference, quantite, prix_unitaire, montant) values (?, ?, ?, ?, ?)";
    connection = DataBase.connectionBD();
    PreparedStatement ps = connection.prepareStatement(this.sql);
    ps.setInt(1, code);
    ps.setString(2, reference);
    ps.setInt(3, quantite);
    ps.setInt(4, prix_unitaire);
    ps.setInt(5, montant);
    int result = ps.executeUpdate();
    
    System.out.println("Résultat de l'insertion : " + result); // ➕ Ajouté
    System.out.println("Ajouté : " + code + ", " + reference + ", " + quantite + ", " + prix_unitaire + ", " + montant);
}

    public void modifierLigneAchat(int code, String reference, int quantite, int prix_unitaire, int montant, int id)throws SQLException{
        this.sql="UPDATE LigneAchat SET code=?, reference=?, quantite =?, prix_unitaire =?, montant =? WHERE id=?";
        connection = DataBase.connectionBD();
        PreparedStatement ps = connection.prepareStatement(this.sql);
        ps.setInt(1, code);
        ps.setString(2, reference);
        ps.setInt(3, quantite);
        ps.setInt(4, prix_unitaire);
        ps.setInt(5, montant);
        ps.setInt(6,id);
        ps.executeUpdate();
        System.out.println("ligne modifié avec succès !");
    }
    public void supprimerLigneAchat(int id) throws SQLException{
        this.sql = "DELETE FROM LigneAchat WHERE id=?";
        connection = DataBase.connectionBD();
      PreparedStatement ps = connection.prepareStatement(this.sql);
      ps.setInt(1, id);
      ps.executeUpdate();
    }
    public List<LigneAchat>  listeLigneAchat() throws SQLException{
     this.sql = "SELECT * FROM LigneAchat";
      connection = DataBase.connectionBD();
      PreparedStatement ps = connection.prepareStatement(this.sql);
      ResultSet resultSet = ps.executeQuery();
      List<LigneAchat> listeLigneAchat = new ArrayList<>();

        while (resultSet.next()) {
        int id = resultSet.getInt("id");
        int code = resultSet.getInt("code");
        String reference = resultSet.getString("reference");
        int quantite= resultSet.getInt("quantite");
        int prix_unitaire= resultSet.getInt("prix_unitaire");
        int montant= resultSet.getInt("montant");


        LigneAchat ligneAchat = new LigneAchat();
        ligneAchat.setId(id);
        ligneAchat.setCode(code);
        ligneAchat.setReference(reference);
        ligneAchat.setQuantite(quantite);
        ligneAchat.setPrix_unitaire(prix_unitaire);
        ligneAchat.setMontant(montant);
        listeLigneAchat.add( ligneAchat);
      }
      return listeLigneAchat;
    }
}
