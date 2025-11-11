package com.dao;

import com.models.User;
import com.utils.XMLUtils;
import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

/**
 * DAO for Users backed by users.xml.
 */
public class UserDAO {
    private final ServletContext context;
    public UserDAO(ServletContext context) { this.context = context; }
    private File getFile() { return XMLUtils.resolveDataFile(context, "users.xml"); }

    public synchronized User validateUser(String username, String password) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "users");
        NodeList list = doc.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            String u = getText(e, "username");
            String p = getText(e, "password");
            if (u.equals(username) && p.equals(password)) {
                return toModel(e);
            }
        }
        return null;
    }

    public synchronized String getUserRole(String userId) throws Exception {
        Document doc = XMLUtils.readDocument(getFile(), "users");
        NodeList list = doc.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (getText(e, "id").equals(userId)) {
                return getText(e, "role");
            }
        }
        return null;
    }

    private static String getText(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent();
    }

    private static User toModel(Element e) {
        User u = new User();
        u.setId(getText(e, "id"));
        u.setUsername(getText(e, "username"));
        u.setPassword(getText(e, "password"));
        u.setRole(getText(e, "role"));
        u.setFullName(getText(e, "fullName"));
        u.setEmail(getText(e, "email"));
        return u;
    }
}


