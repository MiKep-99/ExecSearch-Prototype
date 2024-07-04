# ExecSearch-Prototype

04.07.2024 - MK
Um den Prototyp lokal ausführen zu können muss das gesamte Projekt heruntergeladen werden - Code Button -> Download ZIP

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Im Ordner befinden sich diese README datei, der Source-code "ExecSearch-Prototype" und der "out" Folder, indem sich der ausführbare Prototyp "ExecSearch-Prototype-GitHub.jar" befindet. Diese .jar Datei ist ausführbar, wird allerdings eine Sicherheitswarung auslösen, da sie von mir (einem nicht verifiziertem Entwickler) stammt. Ich empfehle die .jar Datei im Ordner zu lassen und falls gewünscht eine Verknüpfung zu erstellen.

Der Sourcecode ist ausführlich kommentiert und formatiert um gute Lesbarkeit zu gewährleisten. Es handelt sich um ein Maven Projekt das auf Java Version 17 (coretto-17.0.6) gebaut wurde. Für das Frontend wurde JavaFX verwendet.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Anleitung für den Prototyp (in Englisch damit es euer Kollege vielleicht auch lesen kann):

This Prototype in its current state only deals with Persons (TypID = RČ)

When opening the Application you are greeted with a Window titled "ExecSearch Prototype" coontaining some Buttons with labels and an empty table at the bottom.

1. The first step will be to select the inquiry (XML File) by clicking on "Select XML File". A file explorer dialogue will open asking you to select an XML file. Once the XML file has successfully been selected, the filepath will show underneath the Button (where it previously said "No XML file selected".
2. Next the Database file (customers from the banking System exported to .xlsx) are to be selected by usind the button "Select Excel File". Again, a dialogue will open to select a .xlsx file). Once the Excel file has successfully been selected, the filepath will show underneath the Button (where it previously said "No Excel file selected".
3. After selecting both files, press the "Load data" button - this can take a while, depending on how many entries are in the XML and Excel file. After the data has been loaded and matched, a pop up with "Data loaded successfully!" will appear - this informs you that the import was successfull and the matches could be made correctly.
You will now see that the table on the bottom has been filled with potential matches - or none if none were found. You can scroll though the matches and click on the correct ones - indicated by a checkbox in the "Select" column.
4. Lastly you can export the selected matches by clicking "Export Selected Rows". A dialogue will appear, asking you where to save the generated Excel file. After successfully saving the file, a pop up with "Data exported succesfully!" will appear, informing you that the export was successfull. This exported file contains the previously selected matches as they were shown in the table in the application.
To go though another XML file, simply select a new XML file - you do not need to select the Excel file again, unless you want to use a different one - and repeat from step 3 by clicking "Load data".

