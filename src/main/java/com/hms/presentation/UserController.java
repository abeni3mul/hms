package com.hms.presentation;

import com.hms.business.MedicalRecord;
import com.hms.business.Patient;
import com.hms.database.PatientDB;
import com.hms.exceptions.UnexpectedErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class UserController {
    @FXML
    private VBox vboxMain;
    @FXML
    private TextField txtSearchKey;
    private Parent patientTable;

    private PatientTableController patientTableController;
    private Parent patientBox;
    private Parent medicalRecordTable;
    private MedicalRecordTableController medicalRecordTableController;
    private int doctorId, nurseId, selectedPatient;
    private UUID selectedMedicalRecord;
    private enum Pages{
        PATIENT,
        APPOINTMENT
    };
    private enum SubPage{
       PATIENT_TABLE,
       PATEINT_BOX,
       MEDICAL_RECORD_TABLE
    };
    private Pages activePage = Pages.PATIENT;
    private SubPage activeSubPage = SubPage.PATIENT_TABLE;

    private void loadMedicalRecordTable(boolean isMedication) throws SQLException, UnexpectedErrorException, IOException {
        ArrayList<MedicalRecord> medicalRecords = isMedication ? MedicalRecord.getMedicationByPatientId(this.selectedPatient) :
                MedicalRecord.getMedicalRecordByPatientId(this.selectedPatient);
        ObservableList<MedicalRecordTable> medicalRecordTableList = FXCollections.observableArrayList(MedicalRecordTable.map(medicalRecords));

        if(this.medicalRecordTable == null || this.medicalRecordTableController == null){
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("medicalRecordTable.fxml"));
            this.medicalRecordTable = loader.load();
            this.medicalRecordTableController = loader.getController();
        }

        this.medicalRecordTableController.setMedicalRecordList(medicalRecordTableList);
        this.medicalRecordTableController.setRowDoubleClickHandler(this.medicalRecordTableRowFactory);

        this.vboxMain.getChildren().clear();
        this.vboxMain.getChildren().add(this.medicalRecordTable);
        VBox.setVgrow(this.medicalRecordTable, Priority.ALWAYS);
        this.activeSubPage = SubPage.MEDICAL_RECORD_TABLE;
    }

    private EventHandler<ActionEvent> medicalRecordClickHandler = e -> {

        try {
            this.loadMedicalRecordTable(false);
        }catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    };

    private EventHandler<ActionEvent> medicationClickHandler = e -> {
        try{
            this.loadMedicalRecordTable(true);
        }catch (Exception error){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("An Error Occurred");
           alert.setContentText(error.getMessage());
           alert.showAndWait();
        }
    };

    private void loadPatientBox(){
        this.vboxMain.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("patientBox.fxml"));
        try {
            this.patientBox = loader.load();
            ((PatientBoxController) loader.getController()).setEventHandlers(medicalRecordClickHandler, medicationClickHandler);
            vboxMain.getChildren().add(this.patientBox);
            this.activeSubPage = SubPage.PATEINT_BOX;
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText("An Unexpected Error Occurred!");
            alert.showAndWait();
        }
    }

    private Callback<TableView<PatientTable>, TableRow<PatientTable>> patientTableRowFactory = tv -> {
        TableRow<PatientTable> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1 || row.isEmpty())
                return;
            this.selectedPatient = row.getItem().getId();

           this.loadPatientBox();
        });

        return row;
    };

    private Callback<TableView<MedicalRecordTable>, TableRow<MedicalRecordTable>> medicalRecordTableRowFactory = tv -> {
        TableRow<MedicalRecordTable> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1 || row.isEmpty())
                return;

           this.selectedMedicalRecord = row.getItem().getId();

           System.out.println(this.selectedMedicalRecord.toString());
        });

        return row;
    };

    public void initialize() throws IOException {
        this.loadPatientTable();
    }

    private void loadPatientTable() {
        try {
            if (this.patientTable == null || this.patientTableController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTable.fxml"));
                this.patientTable = loader.load();
                this.patientTableController = loader.getController();
            }
            vboxMain.getChildren().clear();
            vboxMain.getChildren().add(this.patientTable);
            VBox.setVgrow(this.patientTable, Priority.ALWAYS);
            this.activeSubPage = SubPage.PATIENT_TABLE;
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText("An Unexpected Error Occurred!");
            alert.showAndWait();
        }
    }

    public void setDoctorId(int doctorId){
        this.nurseId = 0;
        this.doctorId = doctorId;
    }

    public void setNurseId(int nurseId){
        this.doctorId = 0;
        this.nurseId = nurseId;
    }

    @FXML
    public void onPatientClick (ActionEvent e){
        if(activePage == Pages.PATIENT)
            return;
    }

    @FXML
    public void onAppointmentClick (ActionEvent e){
        if(activePage == Pages.APPOINTMENT)
            return;
    }

    @FXML
    public void onBackClick(ActionEvent e){
        switch (this.activeSubPage){
            case PATEINT_BOX:
                this.loadPatientTable();
                break;
            case MEDICAL_RECORD_TABLE:
                this.loadPatientBox();
                break;
            default:
                break;
        }
    }

    @FXML
    public void onPatientSearch(ActionEvent e){
        if(this.txtSearchKey.getText() == null || this.txtSearchKey.getText().trim().equals(""))
            return;

        try {

            ArrayList<Patient> patients = Patient.searchPatient(this.txtSearchKey.getText().trim());

            ObservableList<PatientTable> patientTableList = FXCollections.observableArrayList(PatientTable.map(patients));

            this.loadPatientTable();

            this.patientTableController.setPatientList(patientTableList);
            this.patientTableController.setRowDoubleClickHandler(this.patientTableRowFactory);

        }
        catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred.");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    }
}
