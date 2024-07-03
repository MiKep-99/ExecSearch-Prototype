package org.prototype.controller;

// -------------------------------------- imports --------------------------------------
import org.prototype.model.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// -------------------------------------- class XMLReader --------------------------------------
public class XMLReader {
    // -------------------------------------- function readXML --------------------------------------
    // constructor
    public static List<Person> readXML(String filePath) {

        List<Person> personList = new ArrayList<>();

        // Try-with-resources to auto-close the FileInputStream
        try {
            // Get the XML file and path
            String XmlFilePath = XmlFile(new File(filePath));
            File inputFile = new File(XmlFilePath);
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Create a DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Parse the XML file
            Document doc = dBuilder.parse(inputFile);
            // Normalize the XML structure
            doc.getDocumentElement().normalize();

            // Get the list of "Subjekt" nodes
            NodeList nList = doc.getElementsByTagName("Subjekt");

            // Iterate over the "Subjekt" nodes
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                // Check if the node is an element node
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Check if the "Subjekt" node has the attribute "TypID" with value "RČ"
                    if (eElement.getAttribute("TypID").equals("RČ")) {
                        String id = eElement.getAttribute("ID");

                        // Check if the ID is valid
                        if (id.length() < 9) {
                            // Throw an exception if the ID is invalid
                            throw new IllegalArgumentException("Invalid ID detected: " + id);
                        }

                        // Extract the year, month, and day from the ID - the last  4 digits are ignored
                        int year = Integer.parseInt(id.substring(0, 2));
                        int month = Integer.parseInt(id.substring(2, 4));
                        int day = Integer.parseInt(id.substring(4, 6));

                        // Adjust year and month values
                        // This terminal determines that 24 is the cutoff for the year 2000, so 24 is 2024 and 25 is 1925
                        year += (year <= 24 ? 2000 : 1900);
                        if (month > 50) {
                            month -= 50;
                        }
                        /* A function can be written to duplicate the person entry to check e.g. 1924 and 2024,
                        /* however, this would harm the performance of the program and require further adjustments.
                         */

                        // Format the birth date in 4 digits year, 2 digits month, and 2 digits day
                        String birthDate = String.format("%04d-%02d-%02d", year, month, day);
                        // Get the name of the person
                        String xmlName = eElement.getAttribute("Nazev");

                        // Add the person to the list
                        personList.add(new Person(xmlName, null, birthDate, null, id));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the list of persons from the XML file
        return personList;
    }

    // -------------------------------------- function XmlFile --------------------------------------
    // function to create a temporary file as to not modify the original XML file and thereby increase the speed of the program
    private static String XmlFile(File xmlFile) throws IOException {

        // Create a temporary file
        File tempFile = File.createTempFile("clean", ".xml");

        // Try-with-resources to auto-close the Scanner and PrintWriter
        try (Scanner scanner = new Scanner(new FileInputStream(xmlFile));
             // Create a PrintWriter to write to the temporary file
             PrintWriter writer = new PrintWriter(tempFile)) {
            // Boolean flag to check if the XML declaration is found
            boolean xmlDeclFound = false;
            // Iterate over the lines of the XML file
            while (scanner.hasNextLine()) {
                // Read the next line
                String line = scanner.nextLine().trim();
                // Write the line to the temporary file
                writer.println(line);
            }
        }
        // Return the path of the temporary file
        return tempFile.getAbsolutePath();
    }
}
