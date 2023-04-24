package com.hms.presentation;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerTableController {
    public TableView<ManagerTable> tvManagerTable;

    public TableColumn<ManagerTable, String> fullName;
    public TableColumn<ManagerTable, String> position;
    public TableColumn<ManagerTable, String> phoneNumber;
    public TableColumn<ManagerTable, String> email;
    public TableColumn<ManagerTable, String> dateOfBirth;

    public void initialize(){
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

    }
    public void setManagerList (ObservableList< ManagerTable > managers) {
        tvManagerTable.setItems(managers);
    }

}
