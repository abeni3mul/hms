package com.hms.presentation;

import com.hms.business.Patient;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

public class PatientTable {
    private int id;
    private SimpleStringProperty fullName;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private SimpleStringProperty dateOfBirth;

    public PatientTable(int id, String fullName, String phoneNumber, String email, String dateOfBirth) {
        this.id = id;
        this.fullName = new SimpleStringProperty(fullName);
        this.phoneNumber =  new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
    }

    public int getId(){
        return this.id;
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

    public static ArrayList<PatientTable> map(ArrayList<Patient> patients){
        ArrayList<PatientTable> patientTableList = new ArrayList<>();
        for(Patient patient : patients){
            patientTableList.add(new PatientTable(
                    patient.getPatientId(),
                    patient.getFirstName() + " " + patient.getMiddleName().charAt(0) + ". " + patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getDateOfBirth().toString()
                    ));
        }
        return patientTableList;
    }
}
