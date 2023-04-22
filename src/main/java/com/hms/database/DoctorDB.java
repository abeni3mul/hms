package com.hms.database;
import com.hms.business.Doctor;
import com.hms.business.Patient;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDB {
    public ArrayList<Doctor> getDoctorByPage(int page) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Doctor> doctors = new ArrayList<>();

        try{
            PreparedStatement st = db.prepareStatement("select * from \"Doctor\""+
                    "limit 50 offset (? -1) * 50");
            st.setInt(1,page);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Doctor doctor = Doctor.map(rs);
                doctors.add(doctor);
            }
        }
            finally{
                db.close();
            }
            return doctors;
    }
    public ArrayList<Doctor> searchDoctor(String Key) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Doctor> doctors = new ArrayList<>();
        try{
            PreparedStatement st = db.prepareStatement("select * from \"Patient\" " +
                    "where lower(\"firstName\") like lower(concat('%', ?, '%')) or " +
                    "lower(\"lastName\") like lower(concat('%', ?, '%')) or " +
                    "\"phoneNumber\" like concat('%', ?, '%');");
            st.setString(1,Key);
            st.setString(2,Key);
            st.setString(3,Key);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Doctor doctor = Doctor.map(rs);
                doctors.add(doctor);
            }

        }
        finally{
            db.close();
        }
        return doctors;
        }
    public void addDoctor(Doctor d) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        try {
            PreparedStatement st = db.prepareStatement(
                    "insert into \"Doctor\" " +
                            "(\"firstName\", \"middleName\", \"lastName\", \"phoneNumber\", " +
                            "\"email\", \"dateOfBirth\") " + "values (?,?,?,?,?,?);");
            st.setString(1, d.getFirstName());
            st.setString(2, d.getMiddleName());
            st.setString(3, d.getLastName());
            st.setString(4, d.getPhoneNumber());
            st.setString(5, d.getEmail());
            st.setString(6, d.getDateOfBirth());

            int rowsInserted = st.executeUpdate();
            if (rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register patient. Please, try again.");
        }
        finally {
            db.close();
        }
    }
    public Doctor getDoctorInfo(int DoctorID) throws InvalidIDException, UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();
        Doctor d;
        try {
            PreparedStatement st = db.prepareStatement(
                    "select * from \"Doctor\" where " +
                            "\"DoctorId\" = ?;");
            st.setString(1,DoctorID);
            ResultSet rs = st.executeQuery();

            if(!rs.next())throw new InvalidIDException();
            d =Doctor.map(rs);

        }
        finally {
            db.close();
        }
        return d;
        }

    public void updateDoctor(Doctor d) throws InvalidIDException, UnexpectedErrorException, SQLException{
        if(d.getDoctorId() == 0)
            throw new InvalidIDException("Invalid patient id. Please, try again.");

        Connection db = SqlController.connect();

        try {
            PreparedStatement st = db.prepareStatement(
                    "update \"Doctor\" " +
                            "set \"firstName\" = ?, " +
                            "\"middleName\" = ?, " +
                            "\"lastName\" = ?, " +
                            "\"phoneNumber\" = ?, " +
                            "\"email\" = ?, " +
                            "\"dateOfBirth\" = ?, " +
                            "where \"DoctorId\" = ?;");

            st.setString(1, d.getFirstName());
            st.setString(2,d.getMiddleName());
            st.setString(3,d.getLastName());
            st.setString(4,d.getPhoneNumber());
            st.setString(5,d.getEmail());
            st.setDate(6,java.sql.Date.valueOf(d.getDateOfBirth().toString());

            int rowsInserted = st.executeUpdate();
            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register Doctor. Please, try again.");

        }
        finally {
            db.close();
        }
        }
    }