package com.hms.database;


import com.hms.business.Manager;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class managerDB {
    public ArrayList<Manager> getManagerByPage(int page) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Manager> managers = new ArrayList<>();

        try{
            PreparedStatement st = db.prepareStatement("select * from \"Manager\""+
                    "limit 50 offset (? -1) * 50");
            st.setInt(1,page);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Manager manager = Manager.map(rs);
                managers.add(manager);
            }
        }
        finally{
            db.close();
        }
        return managers;
    }
    public ArrayList<Manager> searchManager(String Key) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        ArrayList<Manager> managers = new ArrayList<>();
        try{
            PreparedStatement st = db.prepareStatement("select * from \"Manager\" " +
                    "where lower(\"firstName\") like lower(concat('%', ?, '%')) or " +
                    "lower(\"lastName\") like lower(concat('%', ?, '%')) or " +
                    "\"phoneNumber\" like concat('%', ?, '%');");
            st.setString(1,Key);
            st.setString(2,Key);
            st.setString(3,Key);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Manager manager = Manager.map(rs);
                managers.add(manager);
            }

        }
        finally{
            db.close();
        }
        return managers;
    }
    public void addManager(Manager m) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        try {
            PreparedStatement st = db.prepareStatement(
                    "insert into \"Manager\" " +
                            "(\"firstName\", \"middleName\", \"lastName\", \"phoneNumber\", " +
                            "\"email\", \"dateOfBirth\") " + "values (?,?,?,?,?,?);");
            st.setString(1, m.getFirstName());
            st.setString(2, m.getMiddleName());
            st.setString(3, m.getLastName());
            st.setString(4, m.getPhoneNumber());
            st.setString(5, m.getEmail());
            st.setDate(6, java.sql.Date.valueOf(m.getDateOfBirth().toString()));

            int rowsInserted = st.executeUpdate();
            if (rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register patient. Please, try again.");
        }
        finally {
            db.close();
        }
    }
    public Manager getManagerInfo(int ManagerID) throws InvalidIDException, UnexpectedErrorException, SQLException{
        Connection db = SqlController.connect();
        Manager m;
        try {
            PreparedStatement st = db.prepareStatement(
                    "select * from \"Manager\" where " +
                            "\"ManagerId\" = ?;");

            st.setInt(1,ManagerID);
            ResultSet rs = st.executeQuery();

            if(!rs.next())throw new InvalidIDException();
            m =Manager.map(rs);

        }
        finally {
            db.close();
        }
        return m;
    }

    public void updateNurse(Manager m) throws InvalidIDException, UnexpectedErrorException, SQLException{
        if(m.getManagerId() == 0)
            throw new InvalidIDException("Invalid patient id. Please, try again.");

        Connection db = SqlController.connect();

        try {
            PreparedStatement st = db.prepareStatement(
                    "update \"Manager\" " +
                            "set \"firstName\" = ?, " +
                            "\"middleName\" = ?, " +
                            "\"lastName\" = ?, " +
                            "\"phoneNumber\" = ?, " +
                            "\"email\" = ?, " +
                            "\"dateOfBirth\" = ?, " +
                            "where \"ManagerId\" = ?;");

            st.setString(1, m.getFirstName());
            st.setString(2,m.getMiddleName());
            st.setString(3,m.getLastName());
            st.setString(4,m.getPhoneNumber());
            st.setString(5,m.getEmail());
            st.setDate(6,java.sql.Date.valueOf(m.getDateOfBirth().toString()));

            int rowsInserted = st.executeUpdate();
            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register Manager. Please, try again.");

        }
        finally {
            db.close();
        }
    }
}
