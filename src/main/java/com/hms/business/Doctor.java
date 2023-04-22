package com.hms.business;

import com.hms.database.DoctorDB;
import com.hms.exceptions.UnexpectedErrorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Doctor {
    private int doctorId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String speciality;
    private String password;
    private byte[] hashedPassword;

    public int getDoctorId() {
        return this.doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public byte[] getHashedPassword() {
        return this.hashedPassword;
    }

    public String getSpeciality() {
        return this.speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void saveDoctor() throws UnexpectedErrorException, SQLException {
        try {
            this.hashedPassword = MessageDigest.getInstance("SHA-256").digest(this.password.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException e){
            throw new UnexpectedErrorException();
        }

        new DoctorDB().addDoctor(this);
    }

    public static Doctor map(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getInt("doctorId"));
        doctor.setFirstName(rs.getString("firstName"));
        doctor.setMiddleName(rs.getString("middleName"));
        doctor.setLastName(rs.getString("lastname"));
        doctor.setPhoneNumber(rs.getString("phoneNumber"));
        doctor.setEmail(rs.getString("email"));
        doctor.setDateOfBirth(rs.getDate("dateOfBirth"));

        return doctor;
    }
}
