package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import entities.Fournisseur;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FournisseurRepository {

    private String sql;

    public FournisseurRepository() {
        // Constructeur vide
    }

    // Ajouter un fournisseur
    public void ajouterFournisseur(String nom, String prenom, String adresse, int telephone, String email) throws SQLException {
        String sql = "INSERT INTO Fournisseur (nom_fournisseur, prenom_fournisseur, adresse, telephone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataBase.connectionBD();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, adresse);
            ps.setInt(4, telephone);
            ps.setString(5, email);
            ps.executeUpdate();
            System.out.println("Fournisseur ajouté avec succès !");
        }
    }

    // Modifier un fournisseur
    public void modifierFournisseur(int id, String nom_fournisseur, String prenom_fournisseur, String adresse, int telephone, String email) throws SQLException {
        String sql = "UPDATE Fournisseur SET nom_fournisseur = ?, prenom_fournisseur = ?, adresse = ?, telephone = ?, email = ? WHERE id = ?";
        try (Connection connection = DataBase.connectionBD();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nom_fournisseur);
            ps.setString(2, prenom_fournisseur);
            ps.setString(3, adresse);
            ps.setInt(4, telephone);
            ps.setString(5, email);
            ps.setInt(6, id);
            ps.executeUpdate();
            System.out.println("Fournisseur modifié");
        }
    }

    // Supprimer un fournisseur
    public void supprimerFournisseur(int id) throws SQLException {
        String sql = "DELETE FROM Fournisseur WHERE id = ?";
        try (Connection connection = DataBase.connectionBD();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Fournisseur supprimé");
        }
    }

    // Liste des fournisseurs
    public List<Fournisseur> listeFournisseurs() throws SQLException {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM Fournisseur";
        try (Connection connection = DataBase.connectionBD();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Fournisseur f = new Fournisseur();
                f.setId(rs.getInt("id"));
                f.setNom_fournisseur(rs.getString("nom_fournisseur"));
                f.setPrenom_fournisseur(rs.getString("prenom_fournisseur"));
                f.setAdresse(rs.getString("adresse"));
                f.setTelephone(rs.getInt("telephone"));
                f.setEmail(rs.getString("email"));
                fournisseurs.add(f);
            }
        }
        return fournisseurs;
    }

    // Trouver fournisseur par ID
    public Fournisseur findById(int id) throws SQLException {
        String sql = "SELECT * FROM Fournisseur WHERE id = ?";
        try (Connection connection = DataBase.connectionBD();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Fournisseur f = new Fournisseur();
                    f.setId(rs.getInt("id"));
                    f.setNom_fournisseur(rs.getString("nom_fournisseur"));
                    f.setPrenom_fournisseur(rs.getString("prenom_fournisseur"));
                    f.setAdresse(rs.getString("adresse"));
                    f.setTelephone(rs.getInt("telephone"));
                    f.setEmail(rs.getString("email"));
                    return f;
                }
            }
        }
        return null;
    }
}

// Classe séparée pour gérer le nettoyage JDBC
class MyAppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("🛑 Nettoyage du driver JDBC MySQL...");
            AbandonedConnectionCleanupThread.checkedShutdown(); // arrête le thread
            if (DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement()); // retire le driver JDBC
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Rien ici pour l'instant
    }
}
