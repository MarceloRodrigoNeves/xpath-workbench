package com.m7sistemas.xpathworkbench.ui;

import java.io.IOException;

import com.m7sistemas.xpathworkbench.MainApp;
import com.m7sistemas.xpathworkbench.core.SaxonXPathService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextArea xmlArea;

    @FXML
    private TextField xpathField;

    @FXML
    private ListView<String> resultList;

    private final SaxonXPathService xpathService = new SaxonXPathService();

    @FXML
    private void onExecute() {

        resultList.getItems().clear();

        try {
            var results = xpathService.evaluate(
                    xmlArea.getText(),
                    xpathField.getText()
            );

            resultList.getItems().addAll(results);

        } catch (Exception e) {
            resultList.getItems().add("Erro: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        // Atalho de tela 'Ctrl + Enter' para executar
        xpathField.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
                onExecute();
            }
        });

        // Atalho de tela 'Ctrl + Shift + f' para formatar xml
        xmlArea.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.isShiftDown() && event.getCode() == KeyCode.F) {
                formatXml();
            }
        });

    }

    @FXML
    private void openSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/m7sistemas/xpathworkbench/ui/settings-view.fxml"));
            Parent root = loader.load();

            SettingsController controller = loader.getController();
            controller.setMainApp(MainApp.getInstance()); // cria método setMainApp(MainApp mainApp) no SettingsController

            Stage stage = new Stage();
            stage.setTitle("Configurações");
            Scene scene = new Scene(root, 500, 350); // largura, altura
            stage.setResizable(true);
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setScene(scene);

            // Modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Define dono (janela principal)
            stage.initOwner(xpathField.getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openXPathFunctions() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/m7sistemas/xpathworkbench/ui/xpath-functions-view.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Funções disponíveis no xpath 2.0");
            Scene scene = new Scene(root, 1400, 500); // largura, altura
            stage.setResizable(true);
            stage.setScene(scene);

            // Modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Define dono (janela principal)
            stage.initOwner(xpathField.getScene().getWindow());

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void formatXml() {
        try {
            String originalXml = xmlArea.getText();
            if (originalXml == null || originalXml.isBlank()) {
                resultList.getItems().clear();
                resultList.getItems().add("XML vazio. Nada a formatar.");
                return;
            }

            // Usa a classe XmlFormatter que você implementou
            String formattedXml = com.m7sistemas.xpathworkbench.core.XmlFormatter.format(originalXml);

            // Atualiza o TextArea
            xmlArea.setText(formattedXml);

            // Limpa mensagens de resultado
            resultList.getItems().clear();
            resultList.getItems().add("XML formatado com sucesso!");

        } catch (Exception e) {
            // Mostra erro no ListView
            resultList.getItems().clear();
            resultList.getItems().add("Erro ao formatar XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
