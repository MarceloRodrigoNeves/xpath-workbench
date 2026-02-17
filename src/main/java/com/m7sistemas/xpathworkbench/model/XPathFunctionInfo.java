package com.m7sistemas.xpathworkbench.model;

public class XPathFunctionInfo {

    private final String function;
    private final String description;
    private final String model;
    private final String result;

    public XPathFunctionInfo(String function,
                             String description,
                             String model,
                             String result) {
        this.function = function;
        this.description = description;
        this.model = model;
        this.result = result;
    }

    public String getFunction() {
        return function;
    }

    public String getDescription() {
        return description;
    }

    public String getModel() {
        return model;
    }

    public String getResult() {
        return result;
    }
}
