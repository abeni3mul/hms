package com.hms.presentation;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;



public class StaffTableController {
    public TableView<StaffTable> tvStaffTable;

    public TableColumn<StaffTable, String> fullName;
    public TableColumn<StaffTable, String> position;
    public TableColumn<StaffTable, String> phoneNumber;
    public TableColumn<StaffTable, String> email;
    public TableColumn<StaffTable, String> dateOfBirth;

    public void initialize(){
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

    }
    public void setStaffList(ObservableList<StaffTable> staffs) {
        tvStaffTable.setItems(staffs);
    }public void setRowDoubleClickHandler(Callback<TableView<StaffTable>, TableRow<StaffTable>> callback){
            tvStaffTable.setRowFactory(callback);
        }


}
