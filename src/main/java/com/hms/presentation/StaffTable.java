package com.hms.presentation;

import com.hms.business.Doctor;
import com.hms.business.Manager;
import com.hms.business.Nurse;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class StaffTable {
    private int id;

     private SimpleStringProperty fullName;
     private SimpleStringProperty position;
     private SimpleStringProperty phoneNumber;
     private SimpleStringProperty email;
     private SimpleStringProperty dateOfBirth;
     private SimpleStringProperty speciality;


    public StaffTable(int id, String fullName, String position, String phoneNumber, String email, String dateOfBirth){
        this.id = id;
        this.fullName = new SimpleStringProperty(fullName);
        this.position = new SimpleStringProperty(position);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
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

    public static ArrayList<StaffTable> map(ArrayList<Nurse> nurses, ArrayList<Doctor>doctors){
        ArrayList<StaffTable> staffTableList = new ArrayList<>();
        for(Nurse nurse : nurses){
            staffTableList.add(new StaffTable(
                    nurse.getNurseId(),
                    nurse.getFirstName() + " " + nurse.getMiddleName().charAt(0) + ". " + nurse.getLastName(),
                    "Nurse",
                    nurse.getPhoneNumber(),

                    nurse.getEmail(),
                    nurse.getDateOfBirth().toString()
            ));
        }
        for(Doctor doctor : doctors)
            staffTableList.add(new StaffTable(
                    doctor.getDoctorId(),
                    doctor.getFirstName() + " " + doctor.getMiddleName() + " " + doctor.getLastName(),
                    "doctor",
                    doctor.getPhoneNumber(),
                    doctor.getEmail(),
                    doctor.getDateOfBirth().toString()
            ));
        return staffTableList;
    }

}
