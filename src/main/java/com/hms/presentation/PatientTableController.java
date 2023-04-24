package com.hms.presentation;

import com.hms.business.Patient;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientTableController {
    public TableView<PatientTable> tvPatientTable;
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

    public void setPatientList (ObservableList< PatientTable > patients) {
        tvPatientTable.setItems(patients);
    }
}