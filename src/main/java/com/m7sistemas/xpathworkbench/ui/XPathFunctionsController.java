package com.m7sistemas.xpathworkbench.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m7sistemas.xpathworkbench.model.XPathFunctionInfo;

public class XPathFunctionsController {

        @FXML
        private TextField searchField;

        @FXML
        private TableView<XPathFunctionInfo> functionTable;

        @FXML
        private TableColumn<XPathFunctionInfo, String> categoryCol;

        @FXML
        private TableColumn<XPathFunctionInfo, String> nameCol;

        @FXML
        private TableColumn<XPathFunctionInfo, String> descriptionCol;

        @FXML
        private TableColumn<XPathFunctionInfo, String> modelCol;

        @FXML
        private TableColumn<XPathFunctionInfo, String> resultCol;

        private ObservableList<XPathFunctionInfo> allFunctions = FXCollections.observableArrayList();
        private FilteredList<XPathFunctionInfo> filteredFunctions;


        @FXML
        public void initialize() {

                // Configura cellValueFactory para cada coluna
                categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name")); // mudou de function para name
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
                resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

                // 1. Carrega todas as funções do JSON
                allFunctions.addAll(loadFunctionsFromJson("/com/m7sistemas/xpathworkbench/data/xpath-functions.json"));

                // 2. Cria lista filtrável
                filteredFunctions = new FilteredList<>(allFunctions, f -> true);
                functionTable.setItems(filteredFunctions);

                // 3. Adiciona listener para filtrar conforme digita
                searchField.textProperty().addListener((obs, oldVal, newVal) -> {
                        String lower = newVal.toLowerCase();
                        filteredFunctions.setPredicate(f -> 
                                f.getName().toLowerCase().contains(lower) ||
                                f.getDescription().toLowerCase().contains(lower)
                        );
                });

                // Evento para Ctrl + c automatico ao clicar na linha
                functionTable.setOnKeyPressed(event -> {
                        if (event.isControlDown() && event.getCode().toString().equals("C")) {
                                XPathFunctionInfo selected = functionTable.getSelectionModel().getSelectedItem();
                                if (selected != null) {
                                        // Escolha o que quer copiar: nome ou modelo
                                        //String textToCopy = selected.getName(); // para copiar a coluna Nome
                                        String textToCopy = selected.getModel(); // para copiar a coluna Modelo

                                        final javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                                        final javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                                        content.putString(textToCopy);
                                        clipboard.setContent(content);
                                }
                        }
                });

                functionTable.setRowFactory(tv -> {
                        TableRow<XPathFunctionInfo> row = new TableRow<>();
                        ContextMenu contextMenu = new ContextMenu();

                        MenuItem copyName = new MenuItem("Copiar Nome");
                        copyName.setOnAction(e -> {
                                XPathFunctionInfo item = row.getItem();
                                if (item != null) {
                                copyToClipboard(item.getName());
                                }
                        });

                        MenuItem copyModel = new MenuItem("Copiar Modelo");
                        copyModel.setOnAction(e -> {
                                XPathFunctionInfo item = row.getItem();
                                if (item != null) {
                                copyToClipboard(item.getModel());
                                }
                        });

                        contextMenu.getItems().addAll(copyName, copyModel);

                        row.setContextMenu(contextMenu);
                        return row;
                });


        }

        private List<XPathFunctionInfo> loadFunctionsFromJson(String path) {
                ObjectMapper mapper = new ObjectMapper();
                try (InputStream is = getClass().getResourceAsStream(path)) {
                        return Arrays.asList(mapper.readValue(is, XPathFunctionInfo[].class));
                } catch (IOException e) {
                        e.printStackTrace();
                        return Collections.emptyList();
                }
        }

        private void copyToClipboard(String text) {
                final javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                final javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                content.putString(text);
                clipboard.setContent(content);
        }

}
