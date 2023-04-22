package com.hms.database;


import com.hms.business.Nurse;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NurseDB {
    public ArrayList<Nurse> getNurseByPage(int page) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Nurse> nurses = new ArrayList<>();

        try{
            PreparedStatement st = db.prepareStatement("select * from \"Nurse\""+
                    "limit 50 offset (? -1) * 50");
            st.setInt(1,page);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Nurse nurse = Nurse.map(rs);
                nurses.add(nurse);
            }
        }
        finally{
            db.close();
        }
        return nurses;
    }
    public ArrayList<Nurse> searchNurse(String Key) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Nurse> nurses = new ArrayList<>();
        try{
            PreparedStatement st = db.prepareStatement("select * from \"Nurse\" " +
                    "where lower(\"firstName\") like lower(concat('%', ?, '%')) or " +
                    "lower(\"lastName\") like lower(concat('%', ?, '%')) or " +
                    "\"phoneNumber\" like concat('%', ?, '%');");
            st.setString(1,Key);
            st.setString(2,Key);
            st.setString(3,Key);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Nurse nurse = Nurse.map(rs);
                nurses.add(nurse);
            }

        }
        finally{
            db.close();
        }
        return nurses;
    }
    public void addNurse(Nurse n) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        try {
            PreparedStatement st = db.prepareStatement(
                    "insert into \"Nurse\" " +
                            "(\"firstName\", \"middleName\", \"lastName\", \"phoneNumber\", " +
                            "\"email\", \"dateOfBirth\") " + "values (?,?,?,?,?,?);");
            st.setString(1, n.getFirstName());
            st.setString(2, n.getMiddleName());
            st.setString(3, n.getLastName());
            st.setString(4, n.getPhoneNumber());
            st.setString(5, n.getEmail());
            st.setDate(6, java.sql.Date.valueOf(n.getDateOfBirth().toString()));

            int rowsInserted = st.executeUpdate();
            if (rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register patient. Please, try again.");
        }
        finally {
            db.close();
        }
    }
    public Nurse getNurseInfo(int NurseID) throws InvalidIDException, UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();
        Nurse n;
        try {
            PreparedStatement st = db.prepareStatement(
                    "select * from \"Nurse\" where " +
                            "\"NurseId\" = ?;");

            st.setInt(1,NurseID);
            ResultSet rs = st.executeQuery();

            if(!rs.next())throw new InvalidIDException();
            n =Nurse.map(rs);

        }
        finally {
            db.close();
        }
        return n;
    }

    public void updateNurse(Nurse n) throws InvalidIDException, UnexpectedErrorException, SQLException{
        if(n.getNurseId() == 0)
            throw new InvalidIDException("Invalid patient id. Please, try again.");

        Connection db = SqlController.connect();

        try {
            PreparedStatement st = db.prepareStatement(
                    "update \"Nurse\" " +
                            "set \"firstName\" = ?, " +
                            "\"middleName\" = ?, " +
                            "\"lastName\" = ?, " +
                            "\"phoneNumber\" = ?, " +
                            "\"email\" = ?, " +
                            "\"dateOfBirth\" = ?, " +
                            "where \"DoctorId\" = ?;");

            st.setString(1, n.getFirstName());
            st.setString(2,n.getMiddleName());
            st.setString(3,n.getLastName());
            st.setString(4,n.getPhoneNumber());
            st.setString(5,n.getEmail());
            st.setDate(6,java.sql.Date.valueOf(n.getDateOfBirth().toString()));

            int rowsInserted = st.executeUpdate();
            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register Doctor. Please, try again.");

        }
        finally {
            db.close();
        }
    }
}
