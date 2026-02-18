package com.m7sistemas.xpathworkbench.core;

import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class SaxonXPathService {

    public XdmValue evaluateRaw(String xml, String expression) throws SaxonApiException {

        Processor processor = new Processor(false);
        DocumentBuilder builder = processor.newDocumentBuilder();
        builder.setLineNumbering(true);
        XdmNode document = builder.build(new StreamSource(new StringReader(xml)));

        XPathCompiler compiler = processor.newXPathCompiler();
        XPathExecutable exec = compiler.compile(expression);
        XPathSelector selector = exec.load();
        selector.setContextItem(document);

        return selector.evaluate();
    }

}
