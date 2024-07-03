package org.prototype.controller;

// ----------------------- imports ------------------------------
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.prototype.model.Person;
import org.prototype.view.PersonView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// ----------------------- class PersonController ------------------------------
public class PersonController {
    // This view object is used to access the view components
    private PersonView view;

    // Constructor
    public PersonController(PersonView view) {
        this.view = view;
    }

    // ----------------------- function loadData --------------------------------
    public void loadData() {
        // Try to read the XML and Excel files
        try {
            List<Person> xmlPersons = XMLReader.readXML(view.getXmlFilePath());
            List<Person> excelPersons = ExcelReader.readExcel(view.getExcelFilePath());

            // Create a map of XML persons with birth date as key
            Map<String, Person> personMap = new HashMap<>();

            // Add XML persons to the map
            for (Person xmlPerson : xmlPersons) {
                personMap.put(xmlPerson.getXmlBirthDate(), xmlPerson);
            }

            // Match Excel persons with XML persons
            for (Person excelPerson : excelPersons) {
                // If the map, thereby the XML persons, contains the Excel person's birthdate
                if (personMap.containsKey(excelPerson.getExcelBirthDate())) {
                    // Get the XML person from the map
                    Person matchedPerson = personMap.get(excelPerson.getExcelBirthDate());
                    // Set the XML person's Excel name and -birthdate to the matched persons Excel name and -birthdate
                    matchedPerson.setExcelName(excelPerson.getExcelName());
                    matchedPerson.setExcelBirthDate(excelPerson.getExcelBirthDate());
                }
            }

            // Filter out persons that do not have both XML and Excel names - without this filter, the view(table) would show all persons
            List<Person> matchedPersons = personMap.values().stream()
                    .filter(person -> person.getXmlName() != null && person.getExcelName() != null)
                    .collect(Collectors.toList());

            // Clear the view data and add the matched persons
            view.getData().clear();
            view.getData().addAll(matchedPersons);
            // Show an alert that the data was loaded successfully
            view.showAlert("Success", "Data loaded successfully!");
        }
        // Catch any exceptions and show an alert with the error message
        catch (Exception e) {
            e.printStackTrace();
            view.showAlert("Error", "Failed to load data: " + e.getMessage());
        }
    }

    // ----------------------- function exportSelectedRows ------------------------------
    public void exportSelectedRows(Stage stage) {
        // Try to export the selected rows to an Excel file
        try {
            // Create a file chooser to select the save location
            FileChooser fileChooser = new FileChooser();
            // Set the file extension filter to Excel files
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            // Show the save dialog and get the selected file
            File selectedFile = fileChooser.showSaveDialog(stage);

            // If a file was selected
            if (selectedFile != null) {
                // Create a new Excel workbook and sheet
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Matches found");

                // Create the header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("XML Name");
                headerRow.createCell(1).setCellValue("Excel Name");
                headerRow.createCell(2).setCellValue("XML Birth Date");
                headerRow.createCell(3).setCellValue("Excel Birth Date");
                headerRow.createCell(4).setCellValue("XML ID");

                // Add all the selected (marked) rows from the view (table) to the sheet
                int rowNum = 1;
                for (Person person : view.getData()) {
                    if (person.isSelected()) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(person.getXmlName());
                        row.createCell(1).setCellValue(person.getExcelName());
                        row.createCell(2).setCellValue(person.getXmlBirthDate());
                        row.createCell(3).setCellValue(person.getExcelBirthDate());
                        row.createCell(4).setCellValue(person.getId());
                    }
                }

                // Write the workbook to the selected file and close the workbook
                try (FileOutputStream fileOut = new FileOutputStream(selectedFile)) {
                    workbook.write(fileOut);
                }
                // Close the workbook
                workbook.close();
                // Show an alert that the data was exported successfully
                view.showAlert("Success", "Data exported successfully!");
            }
            // If no file/safe location was selected, show an alert that the export was cancelled
            else {
                view.showAlert("Info", "Export cancelled!");
            }
        }
        // Catch any exceptions and show an alert with the error message
        catch (Exception e) {
        e.printStackTrace();
        view.showAlert("Error", "Failed to export data: " + e.getMessage());
        }
    }
}

