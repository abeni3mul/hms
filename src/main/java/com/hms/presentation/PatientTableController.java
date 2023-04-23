package com.hms.presentation;

import com.hms.business.Patient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class PatientTableController {
    @FXML
    private TableView<Patient> tvPatientTable;

    public void setPatientList(ObservableList<Patient> patients){
        tvPatientTable.setItems(patients);

    }
}
