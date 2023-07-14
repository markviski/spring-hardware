package edu.bbte.idde.vmim1980.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;
import edu.bbte.idde.vmim1980.backend.model.Hardware;
import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
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

@WebServlet("/hardwares")
public class HardwareServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HardwareDao dao = DaoFactory.getInstance().getHardwareDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        BufferedReader reader = new BufferedReader(req.getReader());
        try {
            Hardware hardware = objectMapper.readValue(reader, Hardware.class);
            if (hardware.getPrice() == null || hardware.getBrand() == null
                    || hardware.getModel() == null || hardware.getYear() == null
                    || hardware.getMemory() == null) {
                resp.setStatus(SC_BAD_REQUEST);
                LOGGER.info("Insert failed, one or more elements are empty.");
            }
            dao.create(hardware);
            LOGGER.info("New hardware successfully inserted.");
            resp.getWriter().println("New hardware successfully inserted.");
        } catch (JsonParseException | MismatchedInputException e) {
            LOGGER.error("Hardware could not be inserted.");
            resp.setStatus(SC_BAD_REQUEST);
            resp.getWriter().println("Hardware could not be inserted, check if all elements are correct.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        String id = req.getParameter("id");
        if (id == null) {
            Collection<Hardware> hardwares = dao.readAll();
            objectMapper.writeValue(resp.getOutputStream(), hardwares);
            LOGGER.info("There are {} hardwares", hardwares.size());
        } else {
            try {
                Hardware hardware = dao.read(Long.parseLong(id));
                if (hardware == null) {
                    resp.setStatus(SC_NOT_FOUND);
                    resp.getWriter().println("There is no hardware with id " + id);
                    LOGGER.info("There is no hardware with id {}", id);
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), hardware);
                    LOGGER.info("Hardware with id {}  displayed.", id);
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
            Hardware hardware = dao.read(Long.parseLong(id));
            if (hardware == null) {
                resp.setStatus(SC_NOT_FOUND);
                LOGGER.info("There is no hardware with id {}", id);
                resp.getWriter().println("There is no hardware with id " + id);
            } else {
                BufferedReader reader = new BufferedReader(req.getReader());
                try {
                    Hardware newHardware = objectMapper.readValue(reader, Hardware.class);
                    if (hardware.getPrice() == null || hardware.getBrand() == null
                            || hardware.getModel() == null || hardware.getYear() == null
                            || hardware.getMemory() == null) {
                        resp.setStatus(SC_BAD_REQUEST);
                        LOGGER.info("Update failed, one or more elements are empty.");
                        resp.getWriter().println("Update failed, one or more elements are empty.");
                    }
                    newHardware.setId(Long.parseLong(id));
                    dao.update(newHardware, Long.parseLong(id));
                    LOGGER.info("Hardware successfully updated.");
                    resp.getWriter().println("Hardware updated.");
                } catch (JsonParseException | MismatchedInputException | NumberFormatException e) {
                    LOGGER.error("Hardware could not be updated, "
                            + "check if all elements of the request body are correct.");
                    resp.setStatus(SC_BAD_REQUEST);
                    resp.getWriter().println("Hardware could not be updated.");
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
                Hardware hardware = dao.read(Long.parseLong(id));
                if (hardware == null) {
                    resp.setStatus(SC_NOT_FOUND);
                    LOGGER.info("There is no hardware with id {}", id);
                } else {
                    dao.delete(Long.parseLong(id));
                    resp.getWriter().println("Hardware with id " + id + " deleted");
                    LOGGER.info("Hardware with id {} deleted.", id);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("{} is not a valid id number!", id);
                resp.setStatus(SC_BAD_REQUEST);
                resp.getWriter().println(id + " is not a valid id number!");
            }
        }
    }
}
