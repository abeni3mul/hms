package com.hms.database;

import com.hms.business.Patient;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.*;
import java.util.ArrayList;

public class PatientDB {

    public ArrayList<Patient> getPatientByPage(int page) throws  UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            PreparedStatement st = db.prepareStatement("select * from \"Patient\"" +
                    "limit 50 offset (? - 1) * 50");

            st.setInt(1, page);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Patient patient = Patient.map(rs);
                patients.add(patient);
            }
        }finally {
            db.close();
        }
        return patients;

    }
    public ArrayList<Patient> searchPatient(String key) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Patient> patients = new ArrayList<>();

        try {

            PreparedStatement st = db.prepareStatement(
                    "select * from \"Patient\" " +
                            "where lower(\"firstName\") like lower(concat('%', ?, '%')) or " +
                            "lower(\"lastName\") like lower(concat('%', ?, '%')) or " +
                            "\"phoneNumber\" like concat('%', ?, '%');"
            );

            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Patient patient = Patient.map(rs);
                patients.add(patient);
            }
        }
        finally {
           db.close();
        }
        return patients;
    }

    public void addPatient(Patient p) throws UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();

        try{
            PreparedStatement st = db.prepareStatement(
                    "insert into \"Patient\" " +
                            "(\"firstName\", \"middleName\", \"lastName\", \"phoneNumber\", " +
                            "\"email\", \"dateOfBirth\", \"bloodType\", \"insuranceCompanyName\", \"insuranceNumber\") " +
                            "values (?,?,?,?,?,?,?,?,?);"
            );

            st.setString(1, p.getFirstName());
            st.setString(2, p.getMiddleName());
            st.setString(3, p.getLastName());
            st.setString(4, p.getPhoneNumber());
            st.setString(5, p.getEmail());
            st.setDate(6, java.sql.Date.valueOf(p.getDateOfBirth().toString()));
            st.setString(7, p.getBloodType());
            st.setString(8, p.getInsuranceCompanyName());
            st.setInt(9, p.getInsuranceNumber());

            int rowsInserted = st.executeUpdate();
            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register patient. Please, try again.");

        }
        finally {
            db.close();
        }
    }

    public Patient getPatientInfo(int patientId) throws InvalidIDException, UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();
        Patient p;

        try{
            PreparedStatement st = db.prepareStatement(
                    "select * from \"Patient\" where " +
                            "\"patientId\" = ?;"
            );

            st.setInt(1, patientId);

            ResultSet rs = st.executeQuery();
            if(!rs.next())
                throw new InvalidIDException();

            p = Patient.map(rs);
        }
        finally {
            db.close();
        }

        return p;
    }

    public void updatePatient(Patient p) throws InvalidIDException, UnexpectedErrorException, SQLException{
        if(p.getPatientId() == 0)
            throw new InvalidIDException("Invalid patient id. Please, try again.");

        Connection db = SqlController.connect();

        try{
            PreparedStatement st = db.prepareStatement(
                    "update \"Patient\" " +
                            "set \"firstName\" = ?, " +
                            "\"middleName\" = ?, " +
                            "\"lastName\" = ?, " +
                            "\"phoneNumber\" = ?, " +
                            "\"email\" = ?, " +
                            "\"dateOfBirth\" = ?, " +
                            "\"bloodType\" = ?, " +
                            "\"insuranceCompanyName\" = ?, " +
                            "\"insuranceNumber\" = ? " +
                            "where \"patientId\" = ?;"
            );

            st.setString(1, p.getFirstName());
            st.setString(2, p.getMiddleName());
            st.setString(3, p.getLastName());
            st.setString(4, p.getPhoneNumber());
            st.setString(5, p.getEmail());
            st.setDate(6, java.sql.Date.valueOf(p.getDateOfBirth().toString()));
            st.setString(7, p.getBloodType());
            st.setString(8, p.getInsuranceCompanyName());
            st.setInt(9, p.getInsuranceNumber());
            st.setInt(10, p.getPatientId());

            int rowsUpdated = st.executeUpdate();

            if(rowsUpdated == 0)
                throw new UnexpectedErrorException("Failed to update patient. Please, try again.");
        }
        finally {
            db.close();
        }
    }
}
