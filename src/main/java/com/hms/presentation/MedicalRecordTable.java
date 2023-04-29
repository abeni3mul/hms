package com.hms.presentation;

import com.hms.business.MedicalRecord;
import javafx.beans.property.SimpleStringProperty;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class MedicalRecordTable {
    private UUID id;
    private SimpleStringProperty doctorName;
    private SimpleStringProperty nurseName;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public MedicalRecordTable(UUID id, String doctorName, String nurseName, String date, String time){
        this.id = id;
        this.doctorName = new SimpleStringProperty(doctorName);
        this.nurseName = new SimpleStringProperty(nurseName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public UUID getId(){
        return this.id;
    }

    public String getDoctorName(){
        return this.doctorName.get();
    }

    public SimpleStringProperty doctorNameProperty(){
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName.set(doctorName);
    }

    public String getNurseName() {
        return this.nurseName.get();
    }

    public SimpleStringProperty nurseNameProperty() {
        return this.nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName.set(nurseName);
    }

    public String getDate() {
        return this.date.get();
    }

    public SimpleStringProperty dateProperty() {
        return this.date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return this.time.get();
    }

    public SimpleStringProperty timeProperty() {
        return this.time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public static ArrayList<MedicalRecordTable> map(ArrayList<MedicalRecord> medicalRecords){
        ArrayList<MedicalRecordTable> medicalRecordTableList = new ArrayList<>();
        for(MedicalRecord medicalRecord : medicalRecords){
            medicalRecordTableList.add(new MedicalRecordTable(
                    medicalRecord.getRecordId(),
                    medicalRecord.getDoctor() != null ?
                        medicalRecord.getDoctor().getFirstName() + " " +
                            medicalRecord.getDoctor().getMiddleName().charAt(0) + ". " +
                            medicalRecord.getDoctor().getLastName() :
                        "",
                    medicalRecord.getNurse() != null ?
                        medicalRecord.getNurse().getFirstName() + " " +
                            medicalRecord.getNurse().getMiddleName().charAt(0) + ". " +
                            medicalRecord.getNurse().getLastName() :
                        "",
                    medicalRecord.getDateAndTime().toLocalDate().toString(),
                    medicalRecord.getDateAndTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mma"))
                )
            );
        }

        return medicalRecordTableList;
    }
}
