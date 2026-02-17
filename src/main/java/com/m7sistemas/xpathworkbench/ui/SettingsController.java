package com.m7sistemas.xpathworkbench.ui;

import java.io.IOException;

import com.m7sistemas.xpathworkbench.MainApp;
import com.m7sistemas.xpathworkbench.model.AppConfig;
import com.m7sistemas.xpathworkbench.service.ConfigService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private ComboBox<String> themeComboBox;

    private final ConfigService configService = new ConfigService();

    private MainApp mainApp; // referência à aplicação principal

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {

        themeComboBox.setItems(
                FXCollections.observableArrayList("Dark", "Light")
        );

        try {
            AppConfig config = configService.load();
            themeComboBox.getSelectionModel().select(config.getTheme());
        } catch (IOException e) {
            e.printStackTrace();
            themeComboBox.getSelectionModel().select("Dark");
        }
    }

    @FXML
    private void saveSettings() {

        String selectedTheme = themeComboBox.getValue();

        AppConfig config = new AppConfig(selectedTheme);

        try {
            configService.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Atualiza tema em tempo real
        if (this.mainApp != null && selectedTheme != null) {
            this.mainApp.applyTheme(this.mainApp.getScene(), selectedTheme);
        }

        ((Stage) themeComboBox.getScene().getWindow()).close();
    }
}
