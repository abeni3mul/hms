package com.hms.presentation;

import com.hms.business.MedicalRecord;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class MedicalRecordTableController {
    public TableView<MedicalRecordTable> tvMedicalRecordTable;
    public TableColumn<MedicalRecordTable, String> tcDoctorName;
    public TableColumn<MedicalRecordTable, String> tcNurseName;
    public TableColumn<MedicalRecordTable, String> tcDate;
    public TableColumn<MedicalRecordTable, String> tcTime;

    public void initialize(){
        this.tcDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        this.tcNurseName.setCellValueFactory(new PropertyValueFactory<>("nurseName"));
        this.tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    public void setMedicalRecordList(ObservableList<MedicalRecordTable> medicalRecords){
        this.tvMedicalRecordTable.setItems(medicalRecords);
    }

    public void setRowDoubleClickHandler(Callback<TableView<MedicalRecordTable>, TableRow<MedicalRecordTable>> callback){
        this.tvMedicalRecordTable.setRowFactory(callback);
    }
}
