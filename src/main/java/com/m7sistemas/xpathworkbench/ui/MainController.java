package com.m7sistemas.xpathworkbench.ui;

import java.io.IOException;

import com.m7sistemas.xpathworkbench.MainApp;
import com.m7sistemas.xpathworkbench.core.SaxonXPathService;
import com.m7sistemas.xpathworkbench.model.PositionedNode;

import java.util.Collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import net.sf.saxon.s9api.*;
import java.io.StringWriter;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import java.util.regex.Pattern;
import java.util.Collection;
import java.util.regex.Matcher;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.flowless.VirtualizedScrollPane;
import javafx.scene.layout.VBox;


public class MainController {

    @FXML
    private VBox editorContainer;

    @FXML
    private CodeArea xmlArea;

    @FXML
    private TextField xpathField;

    @FXML
    private ListView<PositionedNode> resultList;

    private final SaxonXPathService xpathService = new SaxonXPathService();

    @FXML
    private ToggleButton textModeButton;

    @FXML
    private ToggleButton nodeModeButton;

    @FXML
    private ToggleGroup resultModeGroup;

    private static final Pattern XML_TAG =
        Pattern.compile(
                "(</?\\h*\\w+)|" +          // abertura tag
                "(</?\\h*\\w+\\h*/>)|" +    // tag vazia
                "(\\h+\\w+\\h*=)|" +        // atributo
                "(\"[^\"]*\")"              // valor atributo
        );

    @FXML
    private void onExecute() {

        resultList.getItems().clear();

        try {

            XdmValue results = xpathService.evaluateRaw(
                    xmlArea.getText(),
                    xpathField.getText()
            );

            for (XdmItem item : results) {
                if (item instanceof XdmNode xdmNode) {
                    int line = xdmNode.getUnderlyingNode().getLineNumber();
                    int column = xdmNode.getUnderlyingNode().getColumnNumber();
                    String display;

                    if (textModeButton.isSelected()) {
                        display = xdmNode.getStringValue();
                    } else {
                        display = serializeNode(xdmNode);
                    }
                    PositionedNode positioned = new PositionedNode(null, line, column, display);
                    resultList.getItems().add(positioned);
                } else {
                    PositionedNode positioned =  new PositionedNode(null, 1, 1, item.getStringValue());
                    resultList.getItems().add(positioned);
                }
            }

        } catch (Exception e) {
            PositionedNode positioned =  new PositionedNode(null, 1, 1, "Erro: " + e.getMessage());
            resultList.getItems().add(positioned);
        }
    }


    @FXML
    public void initialize() {

        VirtualizedScrollPane<CodeArea> scrollPane = new VirtualizedScrollPane<>(xmlArea);

        // Coloca o ScrollPane
        editorContainer.getChildren().setAll(scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        textModeButton.setToggleGroup(resultModeGroup);
        nodeModeButton.setToggleGroup(resultModeGroup);

        // Atalho de tela 'Ctrl + Enter' para executar
        xpathField.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
                onExecute();
            }
        });

        xmlArea.setParagraphGraphicFactory(LineNumberFactory.get(xmlArea));

        xmlArea.textProperty().addListener((obs, oldText, newText) ->
            applyHighlighting(newText)
        );

        xmlArea.currentParagraphProperty().addListener((obs, oldPar, newPar) -> {

            if (oldPar != null) {
                xmlArea.setParagraphStyle(oldPar, Collections.emptyList());
            }

            if (newPar != null) {
                xmlArea.setParagraphStyle(newPar,
                        Collections.singleton("active-paragraph"));
            }
        });


        // Atalho de tela 'Ctrl + Shift + f' para formatar xml
        xmlArea.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.isShiftDown() && event.getCode() == KeyCode.F) {
                formatXml();
            }
        });

        // Destaque na linha do resultado que o cursor do mouse passa por sima
        resultList.setCellFactory(list -> {
            ListCell<PositionedNode> cell = new ListCell<>() {
                @Override
                protected void updateItem(PositionedNode item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.toString());
                }
            };

            cell.setOnMouseEntered(e -> {
                if (!cell.isEmpty()) {
                    cell.setStyle("-fx-background-color: #d0e7ff;");
                }
            });

            cell.setOnMouseExited(e -> {
                if (!cell.isEmpty()) {
                    cell.setStyle("");
                }
            });

            return cell;
        });


        resultList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                PositionedNode selected = resultList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    int offset = toOffset(xmlArea, selected.getLine(), selected.getColumn());
                    xmlArea.moveTo(offset);
                    xmlArea.requestFocus();
                    //xmlArea.requestFollowCaret();

                    // força scroll
                    int paragraph = selected.getLine() - 1;
                    xmlArea.showParagraphAtCenter(paragraph);
                }
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
                resultList.getItems().add(
                    new PositionedNode(null, 1, 1, "XML vazio. Nada a formatar.")
                );
                return;
            }

            // Usa a classe XmlFormatter que você implementou
            String formattedXml = com.m7sistemas.xpathworkbench.core.XmlFormatter.format(originalXml);

            // Atualiza o TextArea
            xmlArea.replaceText(formattedXml);

            // Limpa mensagens de resultado
            resultList.getItems().clear();
            resultList.getItems().add(
                new PositionedNode(null, 1, 1, "XML formatado com sucesso!")
            );

        } catch (Exception e) {
            // Mostra erro no ListView
            resultList.getItems().clear();
            resultList.getItems().add(
                new PositionedNode(null, 1, 1, "Erro ao formatar XML: " + e.getMessage())
            );
            e.printStackTrace();
        }
    }

    private String serializeNode(XdmNode node) throws SaxonApiException {

        Processor processor = new Processor(false);
        StringWriter writer = new StringWriter();

        Serializer serializer = processor.newSerializer(writer);
        serializer.setOutputProperty(Serializer.Property.INDENT, "yes");
        serializer.serializeNode(node);

        return writer.toString();
    }

    private void applyHighlighting(String text) {

        Matcher matcher = XML_TAG.matcher(text);
        int lastKwEnd = 0;

        StyleSpansBuilder<Collection<String>> spansBuilder =
                new StyleSpansBuilder<>();

        while (matcher.find()) {

            spansBuilder.add(Collections.emptyList(),
                    matcher.start() - lastKwEnd);

            if (matcher.group(1) != null) {
                spansBuilder.add(Collections.singleton("tag"), matcher.end() - matcher.start());
            } else if (matcher.group(3) != null) {
                spansBuilder.add(Collections.singleton("attribute"), matcher.end() - matcher.start());
            } else if (matcher.group(4) != null) {
                spansBuilder.add(Collections.singleton("value"), matcher.end() - matcher.start());
            }

            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(),
                text.length() - lastKwEnd);

        xmlArea.setStyleSpans(0, spansBuilder.create());
    }

    public static int toOffset(CodeArea codeArea, int line, int column) {
        int paragraphIndex = line - 1;
        int columnIndex = column - 1;

        return codeArea.position(paragraphIndex, columnIndex).toOffset();
    }

}
