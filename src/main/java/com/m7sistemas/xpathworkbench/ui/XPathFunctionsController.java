package com.m7sistemas.xpathworkbench.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.m7sistemas.xpathworkbench.model.XPathFunctionInfo;

public class XPathFunctionsController {

    @FXML
    private TableView<XPathFunctionInfo> functionTable;

    @FXML
    private TableColumn<XPathFunctionInfo, String> functionCol;

    @FXML
    private TableColumn<XPathFunctionInfo, String> descriptionCol;

    @FXML
    private TableColumn<XPathFunctionInfo, String> modelCol;

    @FXML
    private TableColumn<XPathFunctionInfo, String> resultCol;

    @FXML
    public void initialize() {

        functionCol.setCellValueFactory(new PropertyValueFactory<>("function"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

        ObservableList<XPathFunctionInfo> data = FXCollections.observableArrayList(
                new XPathFunctionInfo(
                        "string-length()",
                        "Retorna o tamanho da string",
                        "string-length('abc')",
                        "3"
                ),
                new XPathFunctionInfo(
                        "upper-case()",
                        "Converte para maiúsculas",
                        "upper-case('abc')",
                        "ABC"
                )
        );

        functionTable.setItems(data);
    }
}
