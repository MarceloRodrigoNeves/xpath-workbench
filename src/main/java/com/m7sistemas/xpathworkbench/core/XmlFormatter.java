package com.m7sistemas.xpathworkbench.core;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XmlFormatter {

    /**
     * Formata o XML, mantendo elementos textuais inline.
     */
    public static String format(String xml) {
        try {
            // 1. Carrega o XML em DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            // 2. Normaliza o documento (remove quebras de linha dentro de elementos)
            normalizeTextNodes(doc.getDocumentElement());

            // 3. Prepara transformador para pretty print
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            // 4. Gera a string formatada
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return xml; // fallback caso algo dê errado
        }
    }

    /**
     * Normaliza text nodes: elementos com apenas texto ficam inline, sem quebras.
     */
    private static void normalizeTextNodes(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getTextContent().trim();
                if (!text.isEmpty()) {
                    child.setTextContent(text); // remove quebras e espaços extras
                }
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                normalizeTextNodes(child); // recursivo
            }
        }
    }
}
