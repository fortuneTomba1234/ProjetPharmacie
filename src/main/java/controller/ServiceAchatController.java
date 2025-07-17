package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


import entities.ServiceAchat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.ServiceAchatRepository;

@WebServlet(urlPatterns = {"/ServiceAchat", "/ListeServiceAchat"})
public class ServiceAchatController extends HttpServlet {
    private ServiceAchatRepository serviceAchatRepository = new ServiceAchatRepository();
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
        String path = req.getServletPath(); // ex: /ServiceAchat

        try {

            if ("/ServiceAchat".equals(path)) {
                // Affichage du formulaire + tableau sur la même page
                List<ServiceAchat> services = serviceAchatRepository.listeServiceAchat();
                IWebExchange exchange = webApp.buildExchange(req, resp);
                WebContext context = new WebContext(exchange);
                context.setVariable("listeServiceAchat", services);

            templateEngine.process("ServiceAchat", context, resp.getWriter());
        }

        // ➕ Ajouter ce bloc pour gérer /ListeServiceAchat
        else if ("/ListeServiceAchat".equals(path)) {
            List<ServiceAchat> services = serviceAchatRepository.listeServiceAchat();
            IWebExchange exchange = webApp.buildExchange(req, resp);
            WebContext context = new WebContext(exchange);
            context.setVariable("listeServiceAchat", services);

            templateEngine.process("ListeServiceAchat", context, resp.getWriter());
        }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des services d'achat");
        }
    }

    public void listeServiceAchat(HttpServletRequest req, HttpServletResponse resp, JakartaServletWebApplication webApp)
            throws SQLException, IOException {

        List<ServiceAchat> liste = serviceAchatRepository.listeServiceAchat();
        IWebExchange exchange = webApp.buildExchange(req, resp);
        WebContext context = new WebContext(exchange);
        context.setVariable("listeServiceAchat", liste);

        templateEngine.process("ServiceAchat", context, resp.getWriter());
    }

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    try {
    if ("enregistrer".equals(action)) {
        String nomClient = req.getParameter("nom_client");
        int quantite = Integer.parseInt(req.getParameter("quantite"));
        int prixTotal = Integer.parseInt(req.getParameter("prix_total"));
        String etat = req.getParameter("etat");

        ServiceAchat service = new ServiceAchat();
        service.setNomClient(nomClient);
        service.setQuantite(quantite);
        service.setPrixTotal(prixTotal);
        service.setEtat(etat);
      
        serviceAchatRepository.ajouterServiceAchat(service);
        req.setAttribute("message", "Service d'achat ajouté avec succès !");
        doGet(req, resp);
    } 
     else if ("supprimer".equals(action)) {
            int idService = Integer.parseInt(req.getParameter("id_service"));
            serviceAchatRepository.supprimerServiceAchat(idService);
            req.setAttribute("message", "Service d'achat supprimé avec succès !");
            doGet(req, resp);
        }
        else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue");
        }
    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement de la requête");
    }
}

}

