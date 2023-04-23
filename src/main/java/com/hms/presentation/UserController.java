package com.hms.presentation;

import com.hms.business.Patient;
import com.hms.database.PatientDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
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
    private enum Pages{
        PATIENT,
        APPOINTMENT
    };
    private Pages activePage = Pages.PATIENT;
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientBox.fxml"));
        vboxMain.getChildren().add(loader.load());
    }

    @FXML
    public void onPatientClick (ActionEvent e){
        if(activePage == Pages.PATIENT)
            return;


    }

    @FXML
    public void onAppointmentClick (ActionEvent e){

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

            ArrayList<Patient> patients = new PatientDB().searchPatient(txtSearchKey.getText().trim());

            if(this.patientTable == null || this.patientTableController == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTable.fxml"));
                this.patientTable = loader.load();
                this.patientTableController = loader.getController();
            }

            ObservableList<Patient> patientObservableList = FXCollections.observableArrayList(patients);
            this.patientTableController.setPatientList(patientObservableList);

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
