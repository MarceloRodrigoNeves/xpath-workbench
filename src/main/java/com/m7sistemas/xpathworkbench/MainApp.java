package com.m7sistemas.xpathworkbench;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.m7sistemas.xpathworkbench.model.AppConfig;
import com.m7sistemas.xpathworkbench.service.ConfigService;


public class MainApp extends Application {

    private static MainApp instance;
    private Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this; // guarda a instância para acesso posterior

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/m7sistemas/xpathworkbench/ui/main-view.fxml")
        );

        mainScene = new Scene(loader.load(), 1200, 700);

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

        if ("Light".equalsIgnoreCase(theme)) {
            applyTheme(mainScene, "Light"); 
        } else {
            applyTheme(mainScene, "Dark"); 
        }

        stage.setTitle("XPathWorkbench");
        // Adiciona o ícone da aplicação
        Image icon = new Image(getClass().getResourceAsStream("/com/m7sistemas/xpathworkbench/icons/icone.png"));
        stage.getIcons().add(icon);
        stage.setScene(mainScene);
        stage.show();
    }

    public static MainApp getInstance() {
        return instance;
    }

    public Scene getScene() {
        return mainScene;
    }

    public void applyTheme(Scene scene, String theme) {
        // Remove CSS anterior
        scene.getStylesheets().clear();

        String cssPath;
        if ("Light".equalsIgnoreCase(theme)) {
            cssPath = "/com/m7sistemas/xpathworkbench/ui/style/light-theme.css";
        } else {
            cssPath = "/com/m7sistemas/xpathworkbench/ui/style/dark-theme.css";
        }

        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
    }


    public static void main(String[] args) {
        launch();
    }
}
