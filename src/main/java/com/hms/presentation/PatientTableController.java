package com.hms.presentation;

import com.hms.business.Patient;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientTableController{
    public TableView<PatientTable> tableView;
    public TableColumn<PatientTable, String> fullName;
    public TableColumn<PatientTable, String> phoneNumber;
    public TableColumn<PatientTable, String> email;
    public TableColumn<PatientTable, String> dateOfBirth;

    public void initialize() {
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

    }
}
