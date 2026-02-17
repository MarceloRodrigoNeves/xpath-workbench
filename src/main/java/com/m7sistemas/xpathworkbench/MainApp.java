package com.m7sistemas.xpathworkbench;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.m7sistemas.xpathworkbench.model.AppConfig;
import com.m7sistemas.xpathworkbench.service.ConfigService;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/m7sistemas/xpathworkbench/ui/main-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 1200, 700);

        // ==========================
        // Carregar configuração
        // ==========================

        ConfigService configService = new ConfigService();
        AppConfig config;

        try {
            config = configService.load();
        } catch (Exception e) {
            config = new AppConfig("Dark");
        }

        String theme = config.getTheme();

        String cssPath;
        if ("Light".equalsIgnoreCase(theme)) {
            cssPath = "/com/m7sistemas/xpathworkbench/ui/style/light-theme.css";
        } else {
            cssPath = "/com/m7sistemas/xpathworkbench/ui/style/dark-theme.css";
        }

        scene.getStylesheets().add(
            getClass().getResource(cssPath).toExternalForm()
        );

        stage.setTitle("XPathWorkbench");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
