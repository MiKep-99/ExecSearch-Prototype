package org.prototype;


//-------------------------------------- imports --------------------------------------
import javafx.application.Application;
import javafx.stage.Stage;
import org.prototype.controller.PersonController;
import org.prototype.view.PersonView;

//-------------------------------------- class ExecSearchApplication --------------------------------------
public class ExecSearchApplication extends Application {

    //--------------------------------------function start --------------------------------------
    @Override
    public void start(Stage primaryStage) {
        // Create view and controller
        PersonView view = new PersonView();
        PersonController controller = new PersonController(view);

        // Set the scene
        primaryStage.setScene(view.createScene(primaryStage));

        // Set button actions after view creation
        view.getLoadButton().setOnAction(e -> controller.loadData());
        view.getExportButton().setOnAction(e -> controller.exportSelectedRows(primaryStage));

        // Set the stage title and show the stage
        primaryStage.setTitle("ExecSearch Prototype");
        primaryStage.show();
    }

    //--------------------------------------function main --------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}
