package com.hms.presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PatientBoxController {
    @FXML
    private Button btnMedicalRecord, btnMedication;


    public void setEventHandlers(EventHandler<ActionEvent> onMedicalRecordClick, EventHandler<ActionEvent> onMedicationClick){
       btnMedicalRecord.setOnAction(onMedicalRecordClick);
       btnMedication.setOnAction(onMedicationClick);
    }
}
