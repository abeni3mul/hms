package com.hms.presentation;

import com.hms.business.Patient;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientTableController {
    public TableView<PatientTable> tvPatientTable;
    public TableColumn<PatientTable, String> tcFullName;
    public TableColumn<PatientTable, String> tcPhoneNumber;
    public TableColumn<PatientTable, String> tcEmail;
    public TableColumn<PatientTable, String> tcDateOfBirth;


    public void initialize() {
        tcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
    }

    public void setPatientList (ObservableList< PatientTable > patients) {
        tvPatientTable.setItems(patients);
    }

    public void setRowDoubleClickHandler(Callback<TableView<PatientTable>, TableRow<PatientTable>> callback){
        tvPatientTable.setRowFactory(callback);
    }
}