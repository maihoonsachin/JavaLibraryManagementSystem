package com.dao;

import com.models.Book;
import com.utils.XMLUtils;
import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO for Books backed by books.xml using DOM read/write.
 */
public class BookDAO {
    private final ServletContext context;

    public BookDAO(ServletContext context) { this.context = context; }

    private File getFile() { return XMLUtils.resolveDataFile(context, "books.xml"); }

    public synchronized void addOrUpdate(Book book) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "books");
        Element root = doc.getDocumentElement();
        // Try to find existing by id
        Element existing = findByIdElement(doc, book.getId());
        if (existing != null) {
            root.removeChild(existing);
        }
        Element e = doc.createElement("book");
        appendText(doc, e, "id", book.getId());
        appendText(doc, e, "title", book.getTitle());
        appendText(doc, e, "author", book.getAuthor());
        appendText(doc, e, "category", book.getCategory());
        appendText(doc, e, "isbn", book.getIsbn());
        appendText(doc, e, "publishedYear", String.valueOf(book.getPublishedYear()));
        appendText(doc, e, "totalCopies", String.valueOf(book.getTotalCopies()));
        appendText(doc, e, "availableCopies", String.valueOf(book.getAvailableCopies()));
        root.appendChild(e);
        XMLUtils.writeDocument(doc, getFile());
    }

    public synchronized Book getBookById(String id) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "books");
        Element el = findByIdElement(doc, id);
        return el == null ? null : toModel(el);
    }

    public synchronized List<Book> getAllBooks() throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "books");
        NodeList list = doc.getDocumentElement().getElementsByTagName("book");
        List<Book> out = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            out.add(toModel((Element) list.item(i)));
        }
        return out;
    }

    public synchronized List<Book> search(String query) throws Exception {
        if (query == null || query.isBlank()) return getAllBooks();
        String q = query.toLowerCase();
        return getAllBooks().stream().filter(b ->
                (b.getTitle() != null && b.getTitle().toLowerCase().contains(q)) ||
                (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(q)) ||
                (b.getCategory() != null && b.getCategory().toLowerCase().contains(q))
        ).collect(Collectors.toList());
    }

    public synchronized void deleteBook(String id) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "books");
        Element el = findByIdElement(doc, id);
        if (el != null) {
            doc.getDocumentElement().removeChild(el);
            XMLUtils.writeDocument(doc, getFile());
        }
    }

    private static void appendText(Document doc, Element parent, String tag, String value) {
        Element c = doc.createElement(tag);
        c.setTextContent(value == null ? "" : value);
        parent.appendChild(c);
    }

    private static String getText(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent();
    }

    private static Element findByIdElement(Document doc, String id) {
        NodeList list = doc.getDocumentElement().getElementsByTagName("book");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (getText(e, "id").equals(id)) return e;
        }
        return null;
    }

    private static Book toModel(Element e) {
        Book b = new Book();
        b.setId(getText(e, "id"));
        b.setTitle(getText(e, "title"));
        b.setAuthor(getText(e, "author"));
        b.setCategory(getText(e, "category"));
        b.setIsbn(getText(e, "isbn"));
        try { b.setPublishedYear(Integer.parseInt(getText(e, "publishedYear"))); } catch (Exception ex) { b.setPublishedYear(0); }
        try { b.setTotalCopies(Integer.parseInt(getText(e, "totalCopies"))); } catch (Exception ex) { b.setTotalCopies(0); }
        try { b.setAvailableCopies(Integer.parseInt(getText(e, "availableCopies"))); } catch (Exception ex) { b.setAvailableCopies(0); }
        return b;
    }
}


