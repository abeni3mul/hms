package com.hms.presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NewPatientController {
    @FXML
    private TextField txtFirstName, txtMiddleName, txtLastName, txtEmail, txtPhoneNumber,
        txtBloodType, txtICName, txtINumber;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private Button btnSubmit;

    @FXML
    public void initialize(){
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if(localDate != null){
                    return formatter.format(localDate);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String s) {
                if(s != null && !s.isEmpty())
                    return LocalDate.parse(s, formatter);
                return null;
            }
        };

        this.dpDateOfBirth.setConverter(converter);
    }

    public String getFirstName(){
        return this.txtFirstName.getText();
    }

    public String getMiddleName(){
        return this.txtMiddleName.getText();
    }

    public String getLastName(){
        return this.txtLastName.getText();
    }

    public String getEmail(){
        return this.txtEmail.getText();
    }

    public String getPhoneNumber(){
        return this.txtPhoneNumber.getText();
    }

    public Date getDateOfBirth(){
       return Date.from(this.dpDateOfBirth.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getICName(){
        return this.txtICName.getText();
    }

    public int getICNumber() throws NumberFormatException{
        return Integer.parseInt(this.txtINumber.getText());
    }

    public String getBloodType(){
       return this.txtBloodType.getText();
    }

    public void setOnSubmit(EventHandler<ActionEvent> e){
        this.btnSubmit.setOnAction(e);
    }
}
