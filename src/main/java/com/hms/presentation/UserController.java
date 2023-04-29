package com.hms.presentation;

import com.hms.business.Manager;
import com.hms.business.Patient;
import com.hms.database.ManagerDB;
import com.hms.database.PatientDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class UserController {
    @FXML
    private VBox vboxMain;
    @FXML
    private TextField txtSearchKey;
    private Parent patientTable;
    private PatientTableController patientTableController;
    private int doctorId, nurseId, selectedPatient;
    private enum Pages{
        PATIENT,
        APPOINTMENT
    };
    private Pages activePage = Pages.PATIENT;
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTable.fxml"));
        this.patientTable = loader.load();
        this.patientTableController = loader.getController();
        vboxMain.getChildren().clear();
        vboxMain.getChildren().add(this.patientTable);
        VBox.setVgrow(this.patientTable, Priority.ALWAYS);
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

    }

    @FXML
    public void onPatientSearch(ActionEvent e){
        if(this.txtSearchKey.getText() == null || this.txtSearchKey.getText().trim().equals(""))
            return;
        vboxMain.getChildren().clear();
        try {

            ArrayList<Patient> patients = new PatientDB().searchPatient(this.txtSearchKey.getText().trim());

            ObservableList<PatientTable> patientTableList = FXCollections.observableArrayList(PatientTable.map(patients));

            if(this.patientTable == null || this.patientTableController == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTable.fxml"));
                this.patientTable = loader.load();
                this.patientTableController = loader.getController();
            }

            this.patientTableController.setPatientList(patientTableList);
            this.patientTableController.setRowDoubleClickHandler(tv -> {
                TableRow<PatientTable> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if(event.getClickCount() != 2 || row.isEmpty())
                        return;
                    this.selectedPatient = row.getItem().getId();

                    this.vboxMain.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("patientBox.fxml"));
                    try {
                        vboxMain.getChildren().add(loader.load());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                return row;
            });

            vboxMain.getChildren().add(this.patientTable);
        }
        catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred.");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    }


    }


