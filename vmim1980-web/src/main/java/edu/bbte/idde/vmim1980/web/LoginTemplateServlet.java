package edu.bbte.idde.vmim1980.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LoginTemplateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoginFilter.class);
    Configuration cfg = new Configuration(new Version("2.3.31"));
    Template template;

    @Override
    public void init() {
        cfg.setClassForTemplateLoading(LoginTemplateServlet.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        try {
            template = cfg.getTemplate("login.ftl");
        } catch (IOException e) {
            LOGGER.error("Login template could not be obtained.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> templateData = new ConcurrentHashMap<>();
        templateData.put("Error", "");
        try {
            template.process(templateData, resp.getWriter());
        } catch (TemplateException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String username = "admin";
        final String password = "12345";

        String userFromReq = req.getParameter("user");
        String passFromReq = req.getParameter("pass");

        if (username.equals(userFromReq) && password.equals(passFromReq)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", userFromReq);
            session.setMaxInactiveInterval(30 * 60);
            LOGGER.info("Login successful.");
            res.sendRedirect("./hardwarestemplate");

        } else {
            cfg.setClassForTemplateLoading(LoginTemplateServlet.class, "/");
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate("login.ftl");

            Map<String, Object> templateData = new ConcurrentHashMap<>();
            templateData.put("Error", "Login failed, please try again.");
            try {
                template.process(templateData, res.getWriter());
                LOGGER.warn("Login failed");
            } catch (TemplateException e) {
                throw new ServletException();
            }
        }
    }
}