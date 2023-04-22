package com.hms.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Patient {
    private int patientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String bloodType;
    private String insuranceCompanyName;
    private int insuranceNumber;

    public int getPatientId() {
        return this.patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodType() {
        return this.bloodType;
    }
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getInsuranceCompanyName() {
        return this.insuranceCompanyName;
    }
    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public int getInsuranceNumber() {
        return this.insuranceNumber;
    }
    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public static Patient map(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patientId"));
        patient.setFirstName(rs.getString("firstName"));
        patient.setMiddleName(rs.getString("middleName"));
        patient.setLastName(rs.getString("lastname"));
        patient.setPhoneNumber(rs.getString("phoneNumber"));
        patient.setEmail(rs.getString("email"));
        patient.setDateOfBirth(rs.getDate("dateOfBirth"));
        patient.setBloodType(rs.getString("bloodType"));
        patient.setInsuranceCompanyName(rs.getString("insuranceCompanyName"));
        patient.setInsuranceNumber(rs.getInt("insuranceNumber"));
        return patient;
    }
}
