module com.hms.hms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.hms.presentation to javafx.fxml;
    exports com.hms.presentation;
}