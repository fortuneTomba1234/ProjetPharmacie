package entities;

public class Fournisseur {
    private int id;
    private String nom_fournisseur;
    private String prenom_fournisseur;
    private String adresse;
    private int telephone;
    private String email;

    public Fournisseur (){

    }

    public Fournisseur(int id, String nom_fournisseur, String prenom_fournisseur, String adresse, int telephone, String email) {
        this.id = id;
        this.nom_fournisseur = nom_fournisseur;
        this.prenom_fournisseur = prenom_fournisseur;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    //methode get
    public int getId() {
        return id;
    }
    public String getNom_fournisseur() {
        return nom_fournisseur;
    }
    public String getPrenom_fournisseur() {
        return prenom_fournisseur;
    }
    public String getAdresse() {
        return adresse;
    }
    public int getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }

    //methiodes set
    public void setId(int id) {
        this.id = id;
    }
    public void setNom_fournisseur(String nom_fournisseur) {
        this.nom_fournisseur = nom_fournisseur;
    }
    public void setPrenom_fournisseur(String prenom_fournisseur) {
        this.prenom_fournisseur = prenom_fournisseur;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
