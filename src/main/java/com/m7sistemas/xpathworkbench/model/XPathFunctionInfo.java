package com.m7sistemas.xpathworkbench.model;

public class XPathFunctionInfo {

    private String name;
    private String category;
    private String description;
    private String model;
    private String result;

    public XPathFunctionInfo() {

    }

    public XPathFunctionInfo(
        String name,
        String category,
        String description,
        String model,
        String result) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.model = model;
        this.result = result;
    }

    // Getters e setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; 
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
