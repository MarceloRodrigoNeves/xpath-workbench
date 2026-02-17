package com.m7sistemas.xpathworkbench.ui;

import com.m7sistemas.xpathworkbench.core.SaxonXPathService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

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

    }

}
