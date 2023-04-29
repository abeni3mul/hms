package com.hms.business;

import com.hms.database.ManagerDB;
import com.hms.exceptions.UnexpectedErrorException;
import javafx.scene.Node;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Manager extends Node {
    private int managerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;
    private byte[] hashedPassword;
    public int getManagerId() {
        return this.managerId;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
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

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void save() throws SQLException, UnexpectedErrorException {
        try {
            this.hashedPassword = MessageDigest.getInstance("SHA-256").digest(this.password.getBytes((StandardCharsets.UTF_8)));
        }catch (NoSuchAlgorithmException e){
            throw  new UnexpectedErrorException();
        }
        new ManagerDB().addManager(this);
    }

    public static Manager map(ResultSet rs) throws SQLException {
        Manager manager = new Manager();
        manager.setManagerId(rs.getInt("doctorId"));
        manager.setFirstName(rs.getString("firstName"));
        manager.setMiddleName(rs.getString("middleName"));
        manager.setLastName(rs.getString("lastname"));
        manager.setPhoneNumber(rs.getString("phoneNumber"));
        manager.setEmail(rs.getString("email"));
        manager.setDateOfBirth(rs.getDate("dateOfBirth"));

        return manager;
    }
}
