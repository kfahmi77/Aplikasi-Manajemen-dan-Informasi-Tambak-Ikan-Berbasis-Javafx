module id.pbotubes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;
    requires fontawesomefx;


    opens id.pbotubes to javafx.fxml;
    exports id.pbotubes;
    exports id.pbotubes.models;

}