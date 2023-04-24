package com.hms.presentation;

import javafx.beans.property.SimpleStringProperty;

import java.nio.channels.Pipe;

public class ManagerTable {
     private SimpleStringProperty fullName;
     private SimpleStringProperty position;
     private SimpleStringProperty phoneNumber;
     private SimpleStringProperty email;
     private SimpleStringProperty dateOfBirth;



    public ManagerTable (String fullName, String position, String phoneNumber, String email, String dateOfBirth){
        this.fullName = new SimpleStringProperty(fullName);
        this.position = new SimpleStringProperty(position);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);

    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

}
