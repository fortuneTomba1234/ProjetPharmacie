package entities;

public class ServiceAchat {
    private int id;
    private String nom_client;
    private int quantite;
    private int prix_total;
    private String etat;

    public ServiceAchat() {
    }

    public ServiceAchat(int id, String nom_client, int quantite, int prix_total, String etat) {
        this.id = id;
        this.nom_client = nom_client;
        this.quantite = quantite;
        this.prix_total = prix_total;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public String getNomClient() {
        return nom_client;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getPrixTotal() {
        return prix_total;
    }

    public String getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomClient(String nom_client) {
        this.nom_client = nom_client;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrixTotal(int prix_total) {
        this.prix_total = prix_total;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

}
