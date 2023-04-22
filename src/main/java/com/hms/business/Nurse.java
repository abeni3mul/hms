package com.hms.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Nurse {
    private int nurseId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;

    public int getNurseId() {
        return this.nurseId;
    }
    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
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

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static Nurse map(ResultSet rs) throws SQLException {
        Nurse nurse = new Nurse();
        nurse.setNurseId(rs.getInt("doctorId"));
        nurse.setFirstName(rs.getString("firstName"));
        nurse.setMiddleName(rs.getString("middleName"));
        nurse.setLastName(rs.getString("lastname"));
        nurse.setPhoneNumber(rs.getString("phoneNumber"));
        nurse.setEmail(rs.getString("email"));
        nurse.setDateOfBirth(rs.getDate("dateOfBirth"));

        return nurse;
    }
}
