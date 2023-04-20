package com.hms.business;

import java.time.LocalDateTime;
import java.util.UUID;

public class MedicalRecord {
    private UUID recordId;
    private int patientId;
    private int doctorId;
    private int nurseId;
    private LocalDateTime dateAndTime;
    private String diagnosis;
    private String treatment;

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

}
