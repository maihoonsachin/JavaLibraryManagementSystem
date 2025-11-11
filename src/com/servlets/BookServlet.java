package com.servlets;

import com.dao.BookDAO;
import com.models.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        try {
            BookDAO dao = new BookDAO(getServletContext());
            List<Book> books = dao.search(q);
            req.setAttribute("books", books);
            req.getRequestDispatcher("/jsp/books.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            BookDAO dao = new BookDAO(getServletContext());
            if ("save".equals(action)) {
                Book b = new Book();
                b.setId(req.getParameter("id"));
                b.setTitle(req.getParameter("title"));
                b.setAuthor(req.getParameter("author"));
                b.setCategory(req.getParameter("category"));
                b.setIsbn(req.getParameter("isbn"));
                b.setPublishedYear(parseInt(req.getParameter("publishedYear")));
                b.setTotalCopies(parseInt(req.getParameter("totalCopies")));
                b.setAvailableCopies(parseInt(req.getParameter("availableCopies")));
                dao.addOrUpdate(b);
            } else if ("delete".equals(action)) {
                String id = req.getParameter("id");
                dao.deleteBook(id);
            }
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}


