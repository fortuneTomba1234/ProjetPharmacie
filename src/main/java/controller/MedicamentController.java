package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import entities.Medicament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MedicamentRepository;

@WebServlet("/medicament/*")
public class MedicamentController extends HttpServlet {

private MedicamentRepository medicamentRepository = new MedicamentRepository();
private TemplateEngine templateEngine;
private JakartaServletWebApplication webApplication;

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getPathInfo();

    if ("/list".equals(path)) {
        listMedicament(req, resp);
    } else if ("/form".equals(path)) {
        getForm(req, resp);
    } else {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Méthode non prise en charge.");
    }
}

public void listMedicament(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        List<Medicament> listMedicaments = medicamentRepository.getMedicamentlist();
        IWebExchange iWebExchange = webApplication.buildExchange(req, resp);
        WebContext webContext = new WebContext(iWebExchange);
        webContext.setVariable("listMedicaments", listMedicaments);
        templateEngine.process("medicament", webContext, resp.getWriter());
    } catch (SQLException e) {
        e.printStackTrace();
        resp.sendError(500, "Erreur serveur : impossible de récupérer les médicaments.");
    }
}

public void getForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Medicament medicament = new Medicament();
    IWebExchange iWebExchange = webApplication.buildExchange(req, resp);
    WebContext webContext = new WebContext(iWebExchange);
    webContext.setVariable("medicament", medicament);
    templateEngine.process("form", webContext, resp.getWriter());
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        String nom_medicament = req.getParameter("nom_medicament");
        String description = req.getParameter("description");
        int prix_d_achat = Integer.parseInt(req.getParameter("prix_d_achat"));
        Date date_d_expiration = Date.valueOf(req.getParameter("date_d_expiration")); // Format : yyyy-MM-dd
        int quantite = Integer.parseInt(req.getParameter("quantite"));
        String fournisseur = req.getParameter("fournisseur");

        Medicament medicament = new Medicament(0, nom_medicament, description, prix_d_achat, date_d_expiration, quantite, fournisseur);
        MedicamentRepository.InsertMedicament(medicament);
        listMedicament(req, resp);

    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(500, "Erreur lors de l'enregistrement du médicament.");
    }
}

@Override
public void init() throws ServletException {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCharacterEncoding("UTF-8");

    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    webApplication = JakartaServletWebApplication.buildApplication(this.getServletContext());
}
}

