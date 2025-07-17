package repository;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Medicament;

public class MedicamentRepository {
    private Statement statement;
    private Connection connection;
    private String sql;

     public MedicamentRepository(){
     }

    public void insertMedicament() throws SQLException{
        this.sql = "INSERT INTO Medicament (nom_medicament, description, prix_unitaire, date_fabrication, date_expiration, stock) values ('paracetamol', 'en etat','1000', '2010/02/12', '2020/02/12', 100 )";
        connection = DataBase.connectionBD();
        statement =connection.createStatement();
        statement.executeUpdate(sql);
        
    }

    public void ajouterMedicament(String nom, String description, int prix_unitaire, String dateFabrication, String dateExpiration, int stock) throws SQLException {
    String sql = "INSERT INTO Medicament (nom_medicament, description, prix_unitaire, date_fabrication, date_expiration, stock) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection connection = DataBase.connectionBD();
         java.sql.PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, nom);
        ps.setString(2, description);
        ps.setInt(3, prix_unitaire);
        ps.setDate(4, java.sql.Date.valueOf(dateFabrication));   // conversion vers java.sql.Date
        ps.setDate(5, java.sql.Date.valueOf(dateExpiration));    // conversion vers java.sql.Date
        ps.setInt(6, stock);

        ps.executeUpdate();
        System.out.println("✅ Médicament ajouté avec succès !");
    }
}

   
    public List<Medicament> listeMedicament() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT * FROM Medicament";
        connection = DataBase.connectionBD();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medicament medicament = new Medicament();
                medicament.setNom_medicament(rs.getString("nom_medicament"));
                medicament.setDescription(rs.getString("description"));
                medicament.setPrix_unitaire(rs.getInt("prix_unitaire"));

                // Conversion de java.sql.Date → java.time.LocalDate
                medicament.setDate_fabrication(rs.getDate("date_fabrication").toLocalDate());
                medicament.setDate_expiration(rs.getDate("date_expiration").toLocalDate());

                medicament.setStock(rs.getInt("stock"));

                medicaments.add(medicament);
            }
        }

        return medicaments;
    }
}

    
