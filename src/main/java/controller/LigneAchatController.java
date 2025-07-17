package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import entities.LigneAchat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.LigneAchatRepository;
@WebServlet(urlPatterns = {"/LigneAchat", "/ListeLigneAchat"})
public class LigneAchatController extends jakarta.servlet.http.HttpServlet {
    private  LigneAchatRepository ligneAchatRepository = new LigneAchatRepository();
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
      String path = req.getServletPath(); // ex: /Fournisseur

    try {
        if ("/LigneAchat".equals(path)) {
            // Affichage du formulaire + tableau sur la même page
            List<LigneAchat> listeLigneAchat = ligneAchatRepository.listeLigneAchat();
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("listeLigneAchat", listeLigneAchat);

            templateEngine.process("LigneAchat", context, resp.getWriter());
        }

        // ➕ Ajouter ce bloc pour gérer /ListeFournisseurs
        else if ("/ListeLigneAchat".equals(path)) {
            List<LigneAchat> listeLigneAchat = ligneAchatRepository.listeLigneAchat();
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("listeLigneAchat", listeLigneAchat);
            System.out.println("Taille de la liste : " + listeLigneAchat.size());

            templateEngine.process("ListeLigneAchat", context, resp.getWriter());
        }

        else {
              resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Chemin non reconnu");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement");
    }
    }
    public void listeLigneAchat(HttpServletRequest req, HttpServletResponse resp, JakartaServletWebApplication webApp) throws SQLException , IOException{
        try {
            List<LigneAchat> listeLigneAchat = ligneAchatRepository.listeLigneAchat();
           IWebExchange iWebExchange = webApp.buildExchange(req, resp);
            WebContext webContext = new WebContext(iWebExchange);
            webContext.setVariable("listeLigneAchat", listeLigneAchat);
            templateEngine.process("LigneAchat", webContext, resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement de la vue");
        }
    }

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String action = req.getParameter("action");

    try {
        if ("enregistrer".equals(action)) {
            int code = Integer.parseInt(req.getParameter("code"));
            String reference = req.getParameter("reference");
            int quantite = Integer.parseInt(req.getParameter("quantite"));
            int prixUnitaire = Integer.parseInt(req.getParameter("prixUnitaire"));
            int montant = Integer.parseInt(req.getParameter("montant"));

            ligneAchatRepository.ajouterLigneAchat(code, reference, quantite, prixUnitaire, montant);
            req.setAttribute("message", "Ligne d'achat ajoutée avec succès !");
            doGet(req, resp);
        }

        else if ("supprimer".equals(action)) {
            int idLigneAchat = Integer.parseInt(req.getParameter("id_ligne_achat"));
            ligneAchatRepository.supprimerLigneAchat(idLigneAchat);
            req.setAttribute("message", "Ligne d'achat supprimée avec succès !");
            doGet(req, resp);
        }

        else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue");
        }

    } catch (NumberFormatException e) {
        e.printStackTrace();
        req.setAttribute("message", "Erreur de format dans les champs numériques.");
        doGet(req, resp);
    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement de la requête");
    }
}

}
