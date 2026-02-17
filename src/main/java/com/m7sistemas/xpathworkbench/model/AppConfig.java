package com.m7sistemas.xpathworkbench.model;

public class AppConfig {

    private String theme;

    public AppConfig() {
    }

    public AppConfig(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}