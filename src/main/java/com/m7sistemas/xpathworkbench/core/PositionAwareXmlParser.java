package com.m7sistemas.xpathworkbench.core;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class PositionAwareXmlParser {

    private final Map<Node, int[]> nodePositions = new HashMap<>();

    public Map<Node, int[]> getNodePositions() {
        return nodePositions;
    }

    public Document parse(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();

        reader.setContentHandler(new DefaultHandler() {

            private Locator locator;
            private Node currentNode = document;

            @Override
            public void setDocumentLocator(Locator locator) {
                this.locator = locator;
            }

            @Override
            public void startElement(String uri, String localName,
                                     String qName, Attributes attributes) {

                Element element = document.createElement(qName);

                for (int i = 0; i < attributes.getLength(); i++) {
                    element.setAttribute(
                        attributes.getQName(i),
                        attributes.getValue(i)
                    );
                }

                currentNode.appendChild(element);

                nodePositions.put(element,
                        new int[]{locator.getLineNumber(), locator.getColumnNumber()});

                currentNode = element;
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                currentNode = currentNode.getParentNode();
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                String text = new String(ch, start, length).trim();
                if (!text.isEmpty()) {
                    currentNode.appendChild(document.createTextNode(text));
                }
            }
        });

        reader.parse(new InputSource(new StringReader(xml)));

        return document;
    }
}
