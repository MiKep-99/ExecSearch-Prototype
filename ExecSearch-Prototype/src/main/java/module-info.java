module org.prototype {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.xml;
    requires poi;
    requires poi.ooxml;

    opens org.prototype to javafx.fxml;
    exports org.prototype;
    exports org.prototype.model;
    opens org.prototype.model to javafx.fxml;
    exports org.prototype.controller;
    opens org.prototype.controller to javafx.fxml;
}