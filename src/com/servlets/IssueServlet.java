package com.servlets;

import com.dao.IssueDAO;
import com.models.IssueRecord;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class IssueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            IssueDAO dao = new IssueDAO(getServletContext());
            List<IssueRecord> issued = dao.getIssuedBooks();
            req.setAttribute("issued", issued);
            req.getRequestDispatcher("/jsp/issue.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            IssueDAO dao = new IssueDAO(getServletContext());
            if ("issue".equals(action)) {
                String recordId = req.getParameter("recordId");
                String bookId = req.getParameter("bookId");
                String userId = req.getParameter("userId");
                dao.issueBook(recordId, bookId, userId);
            } else if ("return".equals(action)) {
                String recordId = req.getParameter("recordId");
                dao.returnBook(recordId);
            }
            resp.sendRedirect(req.getContextPath() + "/issue");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


