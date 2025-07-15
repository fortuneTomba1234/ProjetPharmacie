package entities;

import java.sql.Date;

public class Medicament {
    private int id_medicament;
    private String nom_medicament;
    private String description;
    private int prix_d_achat;
    private Date date_d_expiration;
    private int Quantite;
    private String Fournisseur;

    public Medicament() {
    }
    
    public Medicament(int id_medicament, String nom_medicament, String description, int prix_d_achat, Date date_d_expiration, int Quantite, String Fournisseur ){
        this.id_medicament = id_medicament;
        this.nom_medicament = nom_medicament;
        this.description = description;
        this.prix_d_achat = prix_d_achat;
        this.date_d_expiration = date_d_expiration;
        this.Quantite = Quantite;
        this.Fournisseur = Fournisseur;

    }

    public int getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(int id_medicament) {
        this.id_medicament = id_medicament;
    }

    public String getNom_medicament() {
        return nom_medicament;
    }

    public void setNom_medicament(String nom_medicament) {
        this.nom_medicament = nom_medicament;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public int getprix_d_achat() {
        return prix_d_achat;
    }

    public void setprix_d_achat(int prix_d_achat) {
        this.prix_d_achat = prix_d_achat;
    }
    
     public Date getdate_d_expiration() {
        return date_d_expiration;
    }

    public void setdate_d_expiration(Date date_d_expiration) {
        this.date_d_expiration = date_d_expiration;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int Quantite) {
        this.Quantite = Quantite;
    }

    public String getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(String Fournisseur) {
        this.Fournisseur = Fournisseur;
    
    }

}
