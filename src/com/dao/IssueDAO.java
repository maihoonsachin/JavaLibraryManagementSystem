package com.dao;

import com.models.IssueRecord;
import com.utils.XMLUtils;
import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Issue/Return records backed by issuedBooks.xml.
 * Default loan period 7 days, fine INR 5/day overdue.
 */
public class IssueDAO {
    private final ServletContext context;
    private final int loanDays = 7;
    private final int finePerDay = 5;

    public IssueDAO(ServletContext context) { this.context = context; }
    private File getFile() { return XMLUtils.resolveDataFile(context, "issuedBooks.xml"); }

    public synchronized void issueBook(String recordId, String bookId, String userId) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "issuedRecords");
        Element root = doc.getDocumentElement();
        LocalDate issue = LocalDate.now();
        LocalDate due = issue.plusDays(loanDays);
        Element e = doc.createElement("record");
        appendText(doc, e, "id", recordId);
        appendText(doc, e, "bookId", bookId);
        appendText(doc, e, "userId", userId);
        appendText(doc, e, "issueDate", issue.toString());
        appendText(doc, e, "dueDate", due.toString());
        appendText(doc, e, "returnDate", "");
        appendText(doc, e, "fineAmount", "0");
        root.appendChild(e);
        XMLUtils.writeDocument(doc, getFile());
    }

    public synchronized void returnBook(String recordId) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "issuedRecords");
        Element rec = findByIdElement(doc, recordId);
        if (rec == null) return;
        String dueStr = getText(rec, "dueDate");
        LocalDate due = LocalDate.parse(dueStr);
        LocalDate now = LocalDate.now();
        long days = Math.max(0, ChronoUnit.DAYS.between(due, now));
        int fine = (int) (days * finePerDay);
        setText(doc, rec, "returnDate", now.toString());
        setText(doc, rec, "fineAmount", String.valueOf(fine));
        XMLUtils.writeDocument(doc, getFile());
    }

    public synchronized List<IssueRecord> getIssuedBooks() throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "issuedRecords");
        NodeList list = doc.getDocumentElement().getElementsByTagName("record");
        List<IssueRecord> out = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            out.add(toModel((Element) list.item(i)));
        }
        return out;
    }

    public synchronized List<OverdueView> getOverdues() throws Exception {
        List<OverdueView> views = new ArrayList<>();
        for (IssueRecord r : getIssuedBooks()) {
            if (r.getReturnDate() == null) {
                long days = Math.max(0, ChronoUnit.DAYS.between(r.getDueDate(), LocalDate.now()));
                if (days > 0) {
                    int fine = (int) (days * finePerDay);
                    views.add(new OverdueView(r.getId(), r.getBookId(), r.getUserId(), r.getDueDate().toString(), days, fine));
                }
            }
        }
        return views;
    }

    public static class OverdueView {
        public final String id; public final String bookId; public final String userId; public final String dueDate; public final long daysOverdue; public final int fine;
        public OverdueView(String id, String bookId, String userId, String dueDate, long daysOverdue, int fine) {
            this.id = id; this.bookId = bookId; this.userId = userId; this.dueDate = dueDate; this.daysOverdue = daysOverdue; this.fine = fine;
        }
    }

    private static void appendText(Document doc, Element parent, String tag, String value) {
        Element c = doc.createElement(tag);
        c.setTextContent(value == null ? "" : value);
        parent.appendChild(c);
    }

    private static void setText(Document doc, Element parent, String tag, String value) {
        NodeList nl = parent.getElementsByTagName(tag);
        Element el;
        if (nl.getLength() == 0) { el = doc.createElement(tag); parent.appendChild(el); }
        else { el = (Element) nl.item(0); }
        el.setTextContent(value);
    }

    private static String getText(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent();
    }

    private static Element findByIdElement(Document doc, String id) {
        NodeList list = doc.getDocumentElement().getElementsByTagName("record");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (getText(e, "id").equals(id)) return e;
        }
        return null;
    }

    private static IssueRecord toModel(Element e) {
        IssueRecord r = new IssueRecord();
        r.setId(getText(e, "id"));
        r.setBookId(getText(e, "bookId"));
        r.setUserId(getText(e, "userId"));
        r.setIssueDate(LocalDate.parse(getText(e, "issueDate")));
        r.setDueDate(LocalDate.parse(getText(e, "dueDate")));
        String rd = getText(e, "returnDate");
        r.setReturnDate(rd == null || rd.isBlank() ? null : LocalDate.parse(rd));
        try { r.setFineAmount(Integer.parseInt(getText(e, "fineAmount"))); } catch (Exception ex) { r.setFineAmount(0); }
        return r;
    }
}


