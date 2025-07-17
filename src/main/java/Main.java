import java.sql.SQLException;

import repository.FournisseurRepository;
import repository.LigneAchatRepository;
import repository.ServiceAchatRepository;

public class Main {

    public static void main(String[] args) throws SQLException {
    // Scanner scanner = new Scanner (System.in);
       FournisseurRepository Fournisseur1 = new FournisseurRepository();
       Fournisseur1.ajouterFournisseur("meila", "luc", "melen", 658665680, "meilaluc@gmail.com");

        //Fournisseur1.listeFournisseurs();

        /*MedicamentRepository Medicament1 = new MedicamentRepository();
        //Medicament1.insertMedicament();
        LocalDate date_fabrication = LocalDate.of(2010, 2, 12);
        LocalDate date_expiration = LocalDate.of(2020, 2, 12);
        Medicament1.ajouterMedicament("paracetamol", "en etat", 1000, date_fabrication, date_expiration, 100);
        System.out.println("Medicament ajouté avec succès !");*/
        
        
        LigneAchatRepository ligne1 = new LigneAchatRepository();
        ligne1.insertLigneAchat();
        ligne1.ajouterLigneAchat(0, "médicament", 20, 100, 2000);
        ligne1.modifierLigneAchat(0, "metronidazole", 30, 300, 9000, 2);
        ligne1.supprimerLigneAchat(2);
        ligne1.listeLigneAchat();

        ServiceAchatRepository serviceAchat1 = new ServiceAchatRepository();
        serviceAchat1.insertServiceAchat();
        serviceAchat1.ajouterServiceAchat("tamfo", 20, 100, "en cours");
        serviceAchat1.modifierServiceAchat("landry", 30, 300, "non payé", 2);
        serviceAchat1.supprimerServiceAchat(2);
        serviceAchat1.listeServiceAchat();
     }


}
