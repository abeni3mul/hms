package com.hms.business;

import com.hms.database.MedicalRecordDB;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class MedicalRecord {
    private UUID recordId;
    private int patientId;
    private int doctorId;
    private int nurseId;
    private LocalDateTime dateAndTime;
    private String diagnosis;
    private String treatment;
    private Doctor doctor;
    private Nurse nurse;

    public UUID getRecordId() {
        return this.recordId;
    }
    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public int getPatientId() {
        return this.patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return this.doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getNurseId() {
        return this.nurseId;
    }
    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public LocalDateTime getDateAndTime() {
        return this.dateAndTime;
    }
    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return this.treatment;
    }
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Doctor getDoctor(){
        return this.doctor;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return this.nurse;
    }
    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public static ArrayList<MedicalRecord> getMedicalRecordByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        return new MedicalRecordDB().getMedicalRecordByPatientId(patientId);
    }

    public static ArrayList<MedicalRecord> getMedicationByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        return new MedicalRecordDB().getMedicalRecordByPatientId(patientId, true);
    }

    public static MedicalRecord map(ResultSet rs) throws SQLException {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(UUID.fromString(rs.getString("recordId")));
        medicalRecord.setDateAndTime(rs.getTimestamp("dateAndTime").toLocalDateTime());
        if(rs.getString("doctorFirstName") != null || !rs.getString("doctorFirstName").trim().equals(""))
        {
            Doctor d = new Doctor();
            d.setFirstName(rs.getString("doctorFirstName"));
            d.setMiddleName(rs.getString("doctorMiddleName"));
            d.setLastName(rs.getString("doctorLastName"));
            medicalRecord.setDoctor(d);
        }else{
            Nurse n = new Nurse();
            n.setFirstName(rs.getString("nurseFirstName"));
            n.setMiddleName(rs.getString("nurseMiddleName"));
            n.setLastName(rs.getString("nurseLastName"));
            medicalRecord.setNurse(n);
        }

        return medicalRecord;
    }
}
