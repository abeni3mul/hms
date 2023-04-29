package com.hms.presentation;

import com.hms.business.MedicalRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.format.DateTimeFormatter;

public class MedicalRecordController {
    @FXML
    private Label lblStaffType, lblStaffName, lblDateAndTime, lblPatientName, lblDiagnosis;
    @FXML
    private TextArea txtDiagnosis, txtTreatment;

    public void setMedicalRecord(MedicalRecord medicalRecord){
        if(medicalRecord.getNurseId() != 0)
            this.lblStaffType.setText("Nurse:");
        this.lblStaffName.setText(medicalRecord.getDoctor() != null?
                medicalRecord.getDoctor().getFirstName() + " " +
                        medicalRecord.getDoctor().getMiddleName()  + " " +
                        medicalRecord.getDoctor().getLastName() :
                medicalRecord.getNurse().getFirstName() + " " +
                        medicalRecord.getNurse().getMiddleName() + " " +
                        medicalRecord.getNurse().getLastName());
        this.lblDateAndTime.setText(medicalRecord.getDateAndTime().format(
                DateTimeFormatter.ofPattern("mm/dd/yyyy hh:MMa")
        ));
        this.lblPatientName.setText(
                medicalRecord.getPatient().getFirstName() + " " +
                        medicalRecord.getPatient().getMiddleName() + " " +
                        medicalRecord.getPatient().getLastName()
        );
        if(medicalRecord.getDiagnosis() == null || medicalRecord.getDiagnosis().trim().equals("")){
            this.txtDiagnosis.setVisible(false);
            this.lblDiagnosis.setVisible(false);
        }
        this.txtDiagnosis.setText(medicalRecord.getDiagnosis());
        this.txtTreatment.setText(medicalRecord.getTreatment());
    }
}
