package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import entities.Fournisseur;
import entities.Medicament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MedicamentRepository;

@WebServlet(urlPatterns = {"/Medicament", "/ListeMedicaments"})
public class MedicamentController extends HttpServlet {

    private MedicamentRepository medicamentRepository = new MedicamentRepository();
    private TemplateEngine templateEngine;
    private JakartaServletWebApplication webApp;

    @Override
    public void init() throws ServletException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        webApp = JakartaServletWebApplication.buildApplication(this.getServletContext());
    }

  @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath(); // Devrait être "/Medicament"

    try {
        if ("/Medicament".equals(path)) {
            // Affichage du formulaire + tableau des médicaments
            List<Medicament> medicaments = medicamentRepository.listeMedicament();
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("listeMedicaments", medicaments);

            templateEngine.process("Medicament", context, resp.getWriter());

        } else if ("/ListeMedicaments".equals(path)) {
            List<Medicament> medicaments = medicamentRepository.listeMedicament();
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("listeMedicaments", medicaments);

            templateEngine.process("ListeMedicaments", context, resp.getWriter());


        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Chemin non reconnu");
        }


        
    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des médicaments");
    }
}



     public void listeMedicaments(HttpServletRequest req, HttpServletResponse resp, JakartaServletWebApplication webApp)
            throws SQLException, IOException {
        List<Medicament> medicaments = medicamentRepository.listeMedicament();

        IWebExchange webExchange = webApp.buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        context.setVariable("listeMedicaments", medicaments);

        templateEngine.process("Medicament", context, resp.getWriter());
    }

   /* public void getFormulaireMedicament(HttpServletRequest req, HttpServletResponse resp, JakartaServletWebApplication webApp)
            throws IOException {
                Medicament medicament = new Medicament();
        IWebExchange webExchange = webApp.buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        context.setVariable("medicament", medicament);

        templateEngine.process("FormulaireMedicament", context, resp.getWriter());
    }*/

  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    try {
        if ("enregistrer".equals(action)) {
            String nom = req.getParameter("nom_medicament");
            String description = req.getParameter("description");
            int prix_unitaire = Integer.parseInt(req.getParameter("prix_unitaire"));
            String dateFabrication = req.getParameter("date_fabrication");
            String dateExpiration = req.getParameter("date_expiration");
            int stock = Integer.parseInt(req.getParameter("stock"));

            medicamentRepository.ajouterMedicament(nom, description, prix_unitaire, dateFabrication, dateExpiration, stock);

            // Préparer la réponse après enregistrement
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("message", "Médicament enregistré avec succès !");
            context.setVariable("listeMedicaments", medicamentRepository.listeMedicament());

            templateEngine.process("Medicament", context, resp.getWriter());
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue pour les médicaments.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement du médicament.");
    }
}



}
