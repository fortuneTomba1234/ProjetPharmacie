package entities;
import java.time.LocalDate;



public class Medicament {
    private String id;
    private String nom_medicament;
    private String description;
    private int prix_unitaire;
    private LocalDate date_fabrication;
    private LocalDate date_expiration;
    private int stock;


    public Medicament() {
    }
    // contructeur avec paramètre
    public Medicament(String id, String nom_medicament, String description, int prix_unitaire, LocalDate date_fabrication, LocalDate date_expiration, int stock){
        this.id = id;
        this.nom_medicament = nom_medicament;
        this.description = description;
        this.prix_unitaire = prix_unitaire;
        this.date_fabrication = date_fabrication;
        this.date_expiration = date_expiration;
        this.stock = stock;
    }
    //methodes get
    public String getid(){
        return id;
    }
    public String getNom_medicament() {
        return nom_medicament;
    }
    public String getDescription() {
        return description;
    }
    public int getPrix_unitaire() {
        return prix_unitaire;
    }
    public LocalDate getDate_fabrication() {
        return date_fabrication;
    }
    public LocalDate getDate_expiration() {
        return date_expiration;
    }
    public int getStock() {
        return stock;
    }
    //methodes set
    public void setid(String  id){
        this.id = id;
    }
    public void setNom_medicament(String nom_medicament) {
        this.nom_medicament = nom_medicament;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrix_unitaire(int prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public void setDate_fabrication(LocalDate date_fabrication) {
        this.date_fabrication = date_fabrication;
    }

    public void setDate_expiration(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}