package edu.bbte.idde.vmim1980.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;
import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.vmim1980.backend.model.SellerInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/sellerinfos")
public class SellerInfoServlet extends HttpServlet {
    JdbcDaoFactory jdbcDaoFactory = new JdbcDaoFactory();
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerInfoServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SellerInfoDao dao =  DaoFactory.getInstance().getSellerInfoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        BufferedReader reader = new BufferedReader(req.getReader());
        try {
            SellerInfo sellerInfo = objectMapper.readValue(reader, SellerInfo.class);
            if (sellerInfo.getHardwareid() == null || sellerInfo.getSeller() == null
                    || sellerInfo.getQuantity() == null || sellerInfo.getPhonenumber() == null
                    || sellerInfo.getShippingfee() == null) {
                resp.setStatus(SC_BAD_REQUEST);
                LOGGER.info("Insert failed, one or more elements are empty.");
            }
            dao.create(sellerInfo);
            LOGGER.info("New sellerInfo successfully inserted.");
            resp.getWriter().println("New sellerInfo successfully inserted.");
        } catch (JsonParseException | MismatchedInputException e) {
            LOGGER.error("SellerInfo could not be inserted.");
            resp.setStatus(SC_BAD_REQUEST);
            resp.getWriter().println("SellerInfo could not be inserted, check if all elements are correct.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        String id = req.getParameter("id");
        if (id == null) {
            Collection<SellerInfo> sellerInfos = dao.readAll();
            objectMapper.writeValue(resp.getOutputStream(), sellerInfos);
            LOGGER.info("There are {} sellerInfo items", sellerInfos.size());
        } else {
            try {
                SellerInfo sellerInfo = dao.read(Long.parseLong(id));
                if (sellerInfo == null) {
                    resp.setStatus(SC_NOT_FOUND);
                    resp.getWriter().println("There is no sellerInfo with id " + id);
                    LOGGER.info("There is no sellerInfo with id {}", id);
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), sellerInfo);
                    LOGGER.info("SellerInfo with id {} displayed.", id);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("{} is not a valid id number!", id);
                resp.setStatus(SC_BAD_REQUEST);
                resp.getWriter().println(id + " is not a valid id number!");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(SC_BAD_REQUEST);
            resp.getWriter().println("No id given to update. Please specify an id to update.");
            LOGGER.info("No id given to update.");
        } else {
            SellerInfo sellerInfo = dao.read(Long.parseLong(id));
            if (sellerInfo == null) {
                resp.setStatus(SC_NOT_FOUND);
                LOGGER.info("There is no sellerInfo with id {}", id);
                resp.getWriter().println("There is no sellerInfo with id " + id);
            } else {
                BufferedReader reader = new BufferedReader(req.getReader());
                try {
                    SellerInfo newSellerInfo = objectMapper.readValue(reader, SellerInfo.class);
                    if (newSellerInfo.getHardwareid() == null || newSellerInfo.getSeller() == null
                            || newSellerInfo.getQuantity() == null || newSellerInfo.getPhonenumber() == null
                            || newSellerInfo.getShippingfee() == null) {
                        resp.setStatus(SC_BAD_REQUEST);
                        LOGGER.info("Update failed, one or more elements are empty.");
                        resp.getWriter().println("Update failed, one or more elements are empty.");
                    }
                    newSellerInfo.setId(Long.parseLong(id));
                    dao.update(newSellerInfo, Long.parseLong(id));
                    LOGGER.info("SellerInfo successfully updated.");
                    resp.getWriter().println("SellerInfo updated.");
                } catch (JsonParseException | MismatchedInputException | NumberFormatException  e) {
                    LOGGER.error("SellerInfo could not be updated, "
                            + "check if all elements of the request body are correct.");
                    resp.setStatus(SC_BAD_REQUEST);
                    resp.getWriter().println("SellerInfo could not be updated.");
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        String id = req.getParameter("id");
        if (id == null) {
            resp.getWriter().println("No id given to delete. Please specify an id to delete.");
            resp.setStatus(SC_BAD_REQUEST);
            LOGGER.info("No id given to delete.");
        } else {
            try {
                SellerInfo sellerInfo = dao.read(Long.parseLong(id));
                if (sellerInfo == null) {
                    resp.setStatus(SC_NOT_FOUND);
                    LOGGER.info("There is no sellerInfo with id {}", id);
                } else {
                    dao.delete(Long.parseLong(id));
                    resp.getWriter().println("SellerInfo with id " + id + " deleted");
                    LOGGER.info("SellerInfo with id {} deleted.", id);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("{} is not a valid id number!", id);
                resp.setStatus(SC_BAD_REQUEST);
                resp.getWriter().println(id + " is not a valid id number!");
            }
        }
    }
}
