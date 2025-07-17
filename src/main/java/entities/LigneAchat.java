package entities;

public class LigneAchat {
    private int id;
    private int code;
    private String reference;
    private int quantite;
    private int prix_unitaire;
    private int montant;
    
    public LigneAchat() {
    }

    public LigneAchat(int id, int code, String reference, int quantite, int prix_unitaire, int montant) {
        this.id = id;
        this.code = code;
        this.reference = reference;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.montant = montant;
    }
     public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public String getReference() {
        return reference;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getPrix_unitaire() {
        return prix_unitaire;
    }

    public int getMontant() {
        return montant;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix_unitaire(int prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }


}
