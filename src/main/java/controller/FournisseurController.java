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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.FournisseurRepository;

@WebServlet(urlPatterns = {"/Fournisseur", "/ListeFournisseurs"})
public class FournisseurController extends HttpServlet {
    private FournisseurRepository fournisseurRepository;
    private TemplateEngine templateEngine;
    private JakartaServletWebApplication webApp;

    @Override
    public void init() throws ServletException {
        fournisseurRepository = new FournisseurRepository();

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        webApp = JakartaServletWebApplication.buildApplication(this.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            if ("/Fournisseur".equals(path)) {
                String mode = req.getParameter("mode");
                if ("modifier".equals(mode)) {
                    // Afficher formulaire modification avec fournisseur pré-rempli
                    int id = Integer.parseInt(req.getParameter("id"));
                    Fournisseur fournisseur = fournisseurRepository.findById(id);
                    if (fournisseur == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Fournisseur non trouvé");
                        return;
                    }
                    IWebExchange exchange = webApp.buildExchange(req, resp);
                    WebContext context = new WebContext(exchange);
                    context.setVariable("fournisseur", fournisseur);
                    context.setVariable("mode", "modifier");
                    templateEngine.process("Fournisseur", context, resp.getWriter());
                } else {
                    // Afficher formulaire ajout + liste fournisseurs
                    List<Fournisseur> fournisseurs = fournisseurRepository.listeFournisseurs();
                    IWebExchange exchange = webApp.buildExchange(req, resp);
                    WebContext context = new WebContext(exchange);
                    context.setVariable("listeFournisseurs", fournisseurs);
                    context.setVariable("mode", "ajouter");
                    templateEngine.process("Fournisseur", context, resp.getWriter());
                }
            } else if ("/ListeFournisseurs".equals(path)) {
                List<Fournisseur> fournisseurs = fournisseurRepository.listeFournisseurs();
                IWebExchange exchange = webApp.buildExchange(req, resp);
                WebContext context = new WebContext(exchange);
                context.setVariable("listeFournisseurs", fournisseurs);
                templateEngine.process("ListeFournisseurs", context, resp.getWriter());
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Chemin non reconnu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("ajouter".equals(action)) {
                // Ajouter un fournisseur
                String nom_fournisseur = req.getParameter("nom_fournisseur");
                String prenom_fournisseur = req.getParameter("prenom_fournisseur");
                String adresse = req.getParameter("adresse");
                int telephone = Integer.parseInt(req.getParameter("telephone"));
                String email = req.getParameter("email");

                fournisseurRepository.ajouterFournisseur(nom_fournisseur, prenom_fournisseur, adresse, telephone, email);
                req.setAttribute("message", "Fournisseur ajouté avec succès !");
                doGet(req, resp);
            } else if ("modifier".equals(action)) {
                // Modifier un fournisseur
                int id = Integer.parseInt(req.getParameter("id"));
                String nom_fournisseur = req.getParameter("nom_fournisseur");
                String prenom_fournisseur = req.getParameter("prenom_fournisseur");
                String adresse = req.getParameter("adresse");
                int telephone = Integer.parseInt(req.getParameter("telephone"));
                String email = req.getParameter("email");

                

                fournisseurRepository.modifierFournisseur(id, nom_fournisseur, prenom_fournisseur, adresse, telephone, email);
                req.setAttribute("message", "Fournisseur modifié avec succès !");
                doGet(req, resp);
            } else if ("supprimer".equals(action)) {
                int idFournisseur = Integer.parseInt(req.getParameter("id_fournisseur"));
                fournisseurRepository.supprimerFournisseur(idFournisseur);
                req.setAttribute("message", "Fournisseur supprimé avec succès !");
                doGet(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement de la requête");
        }
    }
}
