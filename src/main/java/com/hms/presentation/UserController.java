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
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private Button btnAdd, btnLogout;
    @FXML
    private AnchorPane apLogoutContainer;
    @FXML
    private Button btnUser, btnPatient;
    private Parent patientTable;
    private PatientTableController patientTableController;
    private Parent patientBox;
    private Parent medicalRecordTable;
    private MedicalRecordTableController medicalRecordTableController;
    private NewMedicalRecordController newMedicalRecordController;
    private NewPatientController newPatientController;
    private int doctorId, nurseId, selectedPatient;
    private boolean medicationPageSelected = false;
    private enum Pages{
        PATIENT,
        APPOINTMENT
    };
    private enum SubPage{
       PATIENT_TABLE,
       PATIENT_BOX,
        NEW_PATIENT,
       MEDICAL_RECORD_TABLE,
        NEW_MEDICAL_RECORD,
        MEDICAL_RECORD_INFO
    };
    private Pages activePage = Pages.PATIENT;
    private SubPage activeSubPage = SubPage.PATIENT_TABLE;

    public void setOnLogout(EventHandler<ActionEvent> e){
        this.btnLogout.setOnAction(e);
    }
    private void loadMedicalRecordTable(boolean isMedication){
        try {
            ArrayList<MedicalRecord> medicalRecords = isMedication ? MedicalRecord.getMedicationByPatientId(this.selectedPatient) :
                    MedicalRecord.getMedicalRecordByPatientId(this.selectedPatient);
            ObservableList<MedicalRecordTable> medicalRecordTableList = FXCollections.observableArrayList(MedicalRecordTable.map(medicalRecords));

            if (this.medicalRecordTable == null || this.medicalRecordTableController == null) {
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
            this.btnAdd.setVisible(true);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadMedicalRecordInfo(UUID medicalRecordId){
        try{
            MedicalRecord medicalRecord = MedicalRecord.getMedicalRecord(medicalRecordId);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("medicalRecord.fxml"));
            Parent medicalRecordInfo = loader.load();
            MedicalRecordController controller = loader.getController();

            controller.setMedicalRecord(medicalRecord);

            this.vboxMain.getChildren().clear();
            this.vboxMain.getChildren().add(medicalRecordInfo);
            VBox.setVgrow(medicalRecordInfo, Priority.ALWAYS);
            this.btnAdd.setVisible(false);
            this.activeSubPage = SubPage.MEDICAL_RECORD_INFO;
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private EventHandler<ActionEvent> newPatientSubmitHandler = e -> {
        try{
            Patient patient = new Patient();
            patient.setFirstName(this.newPatientController.getFirstName());
            patient.setMiddleName(this.newPatientController.getMiddleName());
            patient.setLastName(this.newPatientController.getLastName());
            patient.setEmail(this.newPatientController.getEmail());
            patient.setPhoneNumber(this.newPatientController.getPhoneNumber());
            patient.setDateOfBirth(this.newPatientController.getDateOfBirth());
            patient.setInsuranceCompanyName(this.newPatientController.getICName());
            patient.setInsuranceNumber(this.newPatientController.getICNumber());
            patient.setBloodType(this.newPatientController.getBloodType());
            patient.save();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Patient saved successfully");
            alert.showAndWait();
            this.newPatientController = null;
            this.loadPatientTable();
            this.activeSubPage = SubPage.NEW_PATIENT;
        }catch (NumberFormatException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Insurance Number");
            alert.setContentText("Insurance Number should be an integer.");
            alert.showAndWait();
        }
        catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    };
    private EventHandler<ActionEvent> newMedicalRecordSubmitHandler = e -> {
        try{
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPatientId(this.selectedPatient);
            if(this.nurseId == 0)
                medicalRecord.setDoctorId(this.doctorId);
            else
                medicalRecord.setNurseId(this.nurseId);
            medicalRecord.setTreatment(this.newMedicalRecordController.getMedications());
            if(!this.newMedicalRecordController.getIsMedicationOnly())
                medicalRecord.setDiagnosis(this.newMedicalRecordController.getDiagnosis());
            medicalRecord.save();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Record saved successfully.");
            alert.showAndWait();
            this.newMedicalRecordController = null;
            this.loadMedicalRecordTable(this.medicationPageSelected);
        }catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    };

    private EventHandler<ActionEvent> medicalRecordClickHandler = e -> {

        try {
            this.loadMedicalRecordTable(false);
            this.medicationPageSelected = false;
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
            this.medicationPageSelected = true;
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
            this.activeSubPage = SubPage.PATIENT_BOX;
            this.btnAdd.setVisible(false);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText("An Unexpected Error Occurred!");
            alert.showAndWait();
        }
    }

    private void loadNewMedicalRecord(){

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("newMedicalRecord.fxml"));
        try{
            Parent newMedicalRecord = loader.load();
            this.newMedicalRecordController = loader.getController();
            this.newMedicalRecordController.setOnSubmit(this.newMedicalRecordSubmitHandler);
            if(this.medicationPageSelected)
                this.newMedicalRecordController.setMedicationOnly();
            this.vboxMain.getChildren().clear();
            vboxMain.getChildren().add(newMedicalRecord);
            VBox.setVgrow(newMedicalRecord, Priority.ALWAYS);
            this.activeSubPage = SubPage.NEW_MEDICAL_RECORD;
            this.btnAdd.setVisible(false);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText("An Unexpected Error Occurred!");
            alert.showAndWait();
        }
    }

    private void loadNewPatient(){

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("newPatient.fxml"));
        try{
            Parent newPatient = loader.load();
            this.newPatientController = loader.getController();
            this.newPatientController.setOnSubmit(this.newPatientSubmitHandler);
            this.vboxMain.getChildren().clear();
            vboxMain.getChildren().add(newPatient);
            VBox.setVgrow(newPatient, Priority.ALWAYS);
            this.activeSubPage = SubPage.NEW_PATIENT;
            this.btnAdd.setVisible(false);
        }catch (IOException e){
            e.printStackTrace();
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

           this.loadMedicalRecordInfo(row.getItem().getId());
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
            this.btnAdd.setVisible(true);
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
    protected void onBackClick(ActionEvent e){
        switch (this.activeSubPage){
            case NEW_PATIENT:
            case PATIENT_BOX:
                this.loadPatientTable();
                break;
            case MEDICAL_RECORD_TABLE:
                this.loadPatientBox();
                break;
            case NEW_MEDICAL_RECORD:
            case MEDICAL_RECORD_INFO:
                this.loadMedicalRecordTable(this.medicationPageSelected);
                break;
            default:
                break;
        }
    }

    @FXML
    protected void onButtonAddClick(){
        try{
            if(this.activeSubPage == SubPage.PATIENT_TABLE) {
                this.loadNewPatient();
                return;
            }
            this.loadNewMedicalRecord();
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred");
            alert.setContentText("An Unexpected Error Occurred!");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onPatientSearch(ActionEvent e){
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
