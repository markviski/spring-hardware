package edu.bbte.idde.vmim1980.web;

import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;
import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/hardwarestemplate")
public class HardwareTemplateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoginFilter.class);
    private final HardwareDao hardwareDao = DaoFactory.getInstance().getHardwareDao();
    private final SellerInfoDao sellerInfoDao = DaoFactory.getInstance().getSellerInfoDao();
    Configuration cfg = new Configuration(new Version("2.3.31"));
    Template template;

    @Override
    public void init() {
        cfg.setClassForTemplateLoading(HardwareTemplateServlet.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        try {
            template = cfg.getTemplate("template.ftl");
        } catch (IOException e) {
            LOGGER.error("Hardware template could not be obtained.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> templateData = new ConcurrentHashMap<>();
        templateData.put("Hardwares", hardwareDao.readAll());
        templateData.put("SellerInfo", sellerInfoDao.readAll());
        try {
            template.process(templateData, resp.getWriter());
        } catch (TemplateException e) {
            throw new ServletException();
        }
    }
}
