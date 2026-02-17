package com.m7sistemas.xpathworkbench.core;

import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SaxonXPathService {

    public List<String> evaluate(String xml, String expression) throws SaxonApiException {

        Processor processor = new Processor(false);
        DocumentBuilder builder = processor.newDocumentBuilder();

        XdmNode document = builder.build(
                new StreamSource(new StringReader(xml))
        );

        XPathCompiler compiler = processor.newXPathCompiler();
        XPathExecutable executable = compiler.compile(expression);

        XPathSelector selector = executable.load();
        selector.setContextItem(document);

        XdmValue result = selector.evaluate();

        List<String> output = new ArrayList<>();

        for (XdmItem item : result) {
            output.add(item.getStringValue());
        }

        return output;
    }
}
