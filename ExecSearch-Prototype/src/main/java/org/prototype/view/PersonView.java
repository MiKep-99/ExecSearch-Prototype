package org.prototype.view;

//-------------------------------------- imports --------------------------------------
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.prototype.model.Person;

import java.io.File;

//-------------------------------------- class PersonView --------------------------------------
public class PersonView {

    //-------------------------------------- attributes --------------------------------------
    private String xmlFilePath = "";
    private String excelFilePath = "";
    private TableView<Person> tableView;
    private ObservableList<Person> data;

    private Button selectXMLButton;
    private Button selectExcelButton;
    private Button loadButton;
    private Button exportButton;
    private Label xmlLabel;
    private Label excelLabel;

    //--------------------------------------function PersonView --------------------------------------
    public PersonView() {

        // Create the table view
        this.tableView = new TableView<>();
        this.data = FXCollections.observableArrayList();
        tableView.setItems(data);

        // -------------------------------------- Create the columns and fill them --------------------------------------
        TableColumn<Person, String> xmlNameCol = new TableColumn<>("XML Name");
        xmlNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getXmlName()));

        TableColumn<Person, String> excelNameCol = new TableColumn<>("Excel Name");
        excelNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExcelName()));

        TableColumn<Person, String> xmlBirthDateCol = new TableColumn<>("XML Birth Date");
        xmlBirthDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getXmlBirthDate()));

        TableColumn<Person, String> excelBirthDateCol = new TableColumn<>("Excel Birth Date");
        excelBirthDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExcelBirthDate()));

        TableColumn<Person, String> idCol = new TableColumn<>("XML ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        // The select column has checkboxes
        TableColumn<Person, Boolean> selectedCol = new TableColumn<>("Select");
        selectedCol.setCellValueFactory(cellData -> {
            SimpleBooleanProperty property = new SimpleBooleanProperty(cellData.getValue().isSelected());
            property.addListener((observable, oldValue, newValue) -> cellData.getValue().setSelected(newValue));
            return property;
        });
        // Add the checkboxes to the column and make them editable
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        selectedCol.setEditable(true);

        // Add the columns to the table view and make them editable (for the select column(checkboxes) only)
        tableView.getColumns().addAll(xmlNameCol, excelNameCol, xmlBirthDateCol, excelBirthDateCol, idCol, selectedCol);
        tableView.setEditable(true);

        // Create the buttons and labels
        selectXMLButton = new Button("Select XML File");
        selectExcelButton = new Button("Select Excel File");
        loadButton = new Button("Load Data");
        exportButton = new Button("Export Selected Rows");
        xmlLabel = new Label("No XML file selected");
        excelLabel = new Label("No Excel file selected");
    }

    //--------------------------------------function createScene --------------------------------------
    public Scene createScene(Stage primaryStage) {
        // Create the layout with the buttons, labels and table view
        VBox vbox = new VBox(10, selectXMLButton, xmlLabel, selectExcelButton, excelLabel, loadButton, exportButton, tableView);
        // Create the scene
        Scene scene = new Scene(vbox, 800, 400);

        // -------------------------------------- Set the button actions --------------------------------------
        selectXMLButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                xmlFilePath = selectedFile.getAbsolutePath();
                xmlLabel.setText(xmlFilePath);
            }
        });

        selectExcelButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                excelFilePath = selectedFile.getAbsolutePath();
                excelLabel.setText(excelFilePath);
            }
        });

        // Return the scene
        return scene;
    }

    //--------------------------------------getters --------------------------------------7^
    // Not used in this prototype
    public TableView<Person> getTableView() {
        return tableView;
    }

    public ObservableList<Person> getData() {
        return data;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public Button getExportButton() {
        return exportButton;
    }

    //--------------------------------------function showAlert --------------------------------------
    public void showAlert(String title, String message) {
        // Create an alert with the given title and message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
