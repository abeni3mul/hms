package com.hms.database;

import com.hms.business.User;
import com.hms.business.UserTypes;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public byte[] getUserPasswordHash(User user) throws UnexpectedErrorException, SQLException, InvalidIDException {
        Connection db = SqlController.connect();
        String tableName, idColumn;

        switch (user.getUserType()){
            case DOCTOR:
                tableName = "\"Doctor\"";
                idColumn = "\"doctorId\"";
                break;
            case NURSE:
                tableName = "\"Nurse\"";
                idColumn = "\"nurseId\"";
                break;
            case MANAGER:
                tableName = "\"Manager\"";
                idColumn = "\"managerId\"";
                break;
            default:
                throw new UnexpectedErrorException("Invalid user type.");
        }

        try{
            PreparedStatement st = db.prepareStatement("select password from " + tableName +
                    " where " + idColumn + " = ?;"
            );
            st.setInt(1, user.getUserId());

            ResultSet rs = st.executeQuery();

            if(!rs.next())
                throw new InvalidIDException("Invalid user id or password");

            return rs.getBytes("password");
        }
        finally {
            db.close();
        }
    }
}
