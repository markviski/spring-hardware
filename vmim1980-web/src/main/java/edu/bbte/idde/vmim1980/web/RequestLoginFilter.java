package edu.bbte.idde.vmim1980.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/hardwarestemplate/*")
public class RequestLoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String path = req.getRequestURI();
        if (path.endsWith("styles.css")) {
            chain.doFilter(req,res);
            return;
        }

        HttpSession session = req.getSession(false);
        String loginPage = "/login";
        if (session == null || session.getAttribute("user") == null) {
            RequestDispatcher reqdisp = req.getRequestDispatcher(loginPage);
            reqdisp.forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}