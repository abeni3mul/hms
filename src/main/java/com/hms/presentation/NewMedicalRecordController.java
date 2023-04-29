package com.hms.presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class NewMedicalRecordController {
    @FXML
    private Label lblDiagnosis;
    @FXML
    private TextArea txtDiagnosis, txtMedications;
    @FXML
    private Button btnSubmit;
    private boolean isMedicationOnly = false;
    public void setMedicationOnly(){
        this.txtDiagnosis.setVisible(false);
        this.lblDiagnosis.setVisible(false);
        this.isMedicationOnly = true;
    }

    public boolean getIsMedicationOnly(){
        return this.isMedicationOnly;
    }

    public void setOnSubmit(EventHandler<ActionEvent> handler){
        this.btnSubmit.setOnAction(handler);
    }

    public String getDiagnosis(){
        return this.txtDiagnosis.getText();
    }

    public String getMedications(){
        return this.txtMedications.getText();
    }
}
