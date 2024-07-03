package org.prototype.controller;

//-------------------------------------- imports --------------------------------------
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.prototype.model.Person;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

//-------------------------------------- class ExcelReader --------------------------------------
public class ExcelReader {
    //--------------------------------------function readExcel --------------------------------------
    // constructor
    public static List<Person> readExcel(String filePath) {

        List<Person> personList = new ArrayList<>();

        // Try-with-resources to auto-close the FileInputStream and Workbook
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            int nameCol = -1;
            int birthDateCol = -1;

            // Find the columns with the required headers
            Row headerRow = sheet.getRow(0);

            for (Cell cell : headerRow) {
                // Check if the cell contains the required header KND_KURZNAME or KND_GEBDATUM
                if (cell.getStringCellValue().equals("KND_KURZNAME")) {
                    // Get the column index of the cell KND_KURZNAME
                    nameCol = cell.getColumnIndex();
                } else if (cell.getStringCellValue().equals("KND_GEBDATUM")) {
                    // Get the column index of the cell KND_GEBDATUM
                    birthDateCol = cell.getColumnIndex();
                }
            }

            // Check if the required columns were found
            if (nameCol == -1 || birthDateCol == -1) {
                // Throw an exception if the required columns were not found
                throw new IllegalArgumentException("Required columns not found in Excel file.");
            }

            // Iterate over the rows and extract the required data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Get the cells with the required data
                Cell nameCell = row.getCell(nameCol);
                Cell birthDateCell = row.getCell(birthDateCol);

                // Get the string values of the cells
                String excelName = getCellStringValue(nameCell);
                String birthDateRaw = getCellStringValue(birthDateCell);

                // Check if the birth date is valid and in the correct format (YYYYMMDD)
                if (birthDateRaw != null && birthDateRaw.matches("\\d{8}")) {
                    // Convert birth date from YYYYMMDD to YYYY-MM-DD
                    String year = birthDateRaw.substring(0, 4);
                    String month = birthDateRaw.substring(4, 6);
                    String day = birthDateRaw.substring(6, 8);
                    String birthDate = year + "-" + month + "-" + day;

                    // Add the person to the list
                    personList.add(new Person(null, excelName, null, birthDate, null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the list of persons from the Excel file
        return personList;
    }

    //--------------------------------------function getCellStringValue --------------------------------------
    private static String getCellStringValue(Cell cell) {
        // Check if the cell is null
        if (cell == null) {
            return null;
        }

        // Get the cell value based on the cell type - not all are used currently
        switch (cell.getCellType()) {
            case STRING:    // This is used to get the name value
                return cell.getStringCellValue();
            case NUMERIC:   // This is used to get the date value, the numeric value is currently not used
                // Check if the cell is formatted as a date
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Convert the date to a string in the format YYYYMMDD
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString().replace("-", "");
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:   // This is not used currently
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: // This is not used currently
                return cell.getCellFormula();
            default:    // This is used for empty cells
                return null;
        }
    }
}
