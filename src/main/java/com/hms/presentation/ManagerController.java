package com.hms.presentation;

import com.hms.business.Doctor;
import com.hms.business.Manager;
import com.hms.business.Nurse;
import com.hms.database.DoctorDB;
import com.hms.database.ManagerDB;
import com.hms.database.NurseDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ManagerController {

    @FXML
    private VBox vboxMain;
    @FXML
    private TextField txtSearchKey;
    private Parent staffTable;
    private StaffTableController staffTableController;
    public int selectedStaff;

    @FXML
    public void onBackClick(ActionEvent e){

    }

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("staffTable.fxml"));
        this.staffTable = loader.load();
        this.staffTableController = loader.getController();
        vboxMain.getChildren().clear();
        vboxMain.getChildren().add(this.staffTable);
        VBox.setVgrow(this.staffTable, Priority.ALWAYS);
    }

    @FXML
    public void onStaffSearch(ActionEvent e){
        if(this.txtSearchKey.getText() == null || this.txtSearchKey.getText().trim().equals(""))
            return;
        vboxMain.getChildren().clear();
        try {

            ArrayList<Doctor> doctors = new DoctorDB().searchDoctor(this.txtSearchKey.getText().trim());
            ArrayList<Nurse> nurses = new NurseDB().searchNurse(this.txtSearchKey.getText().trim());

            ObservableList<StaffTable> staffTableList = FXCollections.observableArrayList(StaffTable.map(nurses,doctors));



            if(this.staffTable == null || this.staffTableController == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("staffTable.fxml"));
                this.staffTable = loader.load();
                this.staffTableController = loader.getController();
            }

            this.staffTableController.setStaffList(staffTableList);
            this.staffTableController.setRowDoubleClickHandler(tv -> {
                TableRow<StaffTable> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if(event.getClickCount() != 2 || row.isEmpty())
                        return;
                    this.selectedStaff = row.getItem().getId();

                    this.vboxMain.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Box.fxml"));
                    try {
                        vboxMain.getChildren().add(loader.load());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                return row;
            });

            vboxMain.getChildren().add(this.staffTable);
        }
        catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred.");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
    }
}
