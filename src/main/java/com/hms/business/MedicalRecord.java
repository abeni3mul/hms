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
    private Patient patient;

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

    public Patient getPatient(){
        return this.patient;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public static ArrayList<MedicalRecord> getMedicalRecordByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        return new MedicalRecordDB().getMedicalRecordByPatientId(patientId);
    }

    public static ArrayList<MedicalRecord> getMedicationByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        return new MedicalRecordDB().getMedicalRecordByPatientId(patientId, true);
    }

    public static MedicalRecord getMedicalRecord(UUID medicalRecordId) throws SQLException, UnexpectedErrorException {
        return new MedicalRecordDB().getMedicalRecord(medicalRecordId);
    }

    public void save() throws SQLException, UnexpectedErrorException {
        new MedicalRecordDB().addMedicalRecord(this);
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
        boolean patientColExists = false;
        boolean diagnosisExists = false;
        boolean treatmentExists = false;
        for(int i = 1; i < rs.getMetaData().getColumnCount(); i++){
            String columnName = rs.getMetaData().getColumnName(i);
            if(columnName.equals("patientFirstName"))
                patientColExists = true;
            if(columnName.equals("diagnosis"))
                diagnosisExists = true;
            if(columnName.equals("treatment"))
                treatmentExists = true;
        }
        if(patientColExists && (rs.getString("patientFirstName") != null || !rs.getString("patientFirstName").trim().equals(""))){
            Patient p = new Patient();
            p.setFirstName(rs.getString("patientFirstName"));
            p.setMiddleName(rs.getString("patientMiddleName"));
            p.setLastName(rs.getString("patientLastName"));
            medicalRecord.setPatient(p);
        }
        if(diagnosisExists)
            medicalRecord.setDiagnosis(rs.getString("diagnosis"));
        if(treatmentExists)
            medicalRecord.setTreatment(rs.getString("treatment"));
        return medicalRecord;
    }
}
