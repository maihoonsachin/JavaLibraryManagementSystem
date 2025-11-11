package com.servlets;

import com.dao.IssueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            IssueDAO dao = new IssueDAO(getServletContext());
            List<IssueDAO.OverdueView> overdues = dao.getOverdues();
            req.setAttribute("overdues", overdues);
            req.getRequestDispatcher("/jsp/reports.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


