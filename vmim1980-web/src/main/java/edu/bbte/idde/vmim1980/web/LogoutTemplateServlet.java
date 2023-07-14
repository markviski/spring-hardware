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

@WebServlet("/logout")
public class LogoutTemplateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareServlet.class);
    Configuration cfg = new Configuration(new Version("2.3.31"));
    Template template;

    @Override
    public void init() {
        cfg.setClassForTemplateLoading(LoginTemplateServlet.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        try {
            template = cfg.getTemplate("logout.ftl");
        } catch (IOException e) {
            LOGGER.error("Logout template could not be obtained.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();

        Map<String, Object> templateData = new ConcurrentHashMap<>();
        try {
            template.process(templateData, resp.getWriter());
            LOGGER.info("Logout successful");
        } catch (TemplateException e) {
            throw new ServletException();
        }
    }
}