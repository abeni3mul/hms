package com.hms.presentation;

import javafx.beans.property.SimpleStringProperty;

public class PatientTable {
    private SimpleStringProperty fullName;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private SimpleStringProperty dateOfBirth;

    public PatientTable(String fullName, String phoneNumber, String email, String dateOfBirth) {
        this.fullName = new SimpleStringProperty(fullName);
        this.phoneNumber =  new SimpleStringProperty(phoneNumber);
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
