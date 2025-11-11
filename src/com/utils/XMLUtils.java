package com.utils;

import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Utility helpers for reading and writing XML files under WEB-INF/data.
 */
public class XMLUtils {

    public static File resolveDataFile(ServletContext context, String fileName) {
        String dataDir = context.getInitParameter("dataDir");
        if (dataDir == null) dataDir = "/WEB-INF/data";
        String realPath = context.getRealPath(dataDir + "/" + fileName);
        
        System.out.println("SERVER IS SAVING FILE TO: " + realPath);
        return new File(realPath);
    }

    public static synchronized Document readDocument(File file, String rootElement) throws Exception {
        if (!file.exists()) {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.newDocument();
            Element root = doc.createElement(rootElement);
            doc.appendChild(root);
            writeDocument(doc, file);
            return doc;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    public static synchronized void writeDocument(Document doc, File file) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}


