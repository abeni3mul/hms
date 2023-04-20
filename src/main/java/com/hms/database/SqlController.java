package com.hms.database;

import com.hms.business.Patient;
import com.hms.exceptions.UnexpectedErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

abstract class SqlController {

    private static Connection connect() throws UnexpectedErrorException, SQLException {
        String connectionStringPath = SqlController.class.getResource(".databaseConfig.properties").getPath();
        Properties connectionProp = new Properties();
        try {
            connectionProp.load(new FileInputStream(connectionStringPath));
        } catch (IOException e) {
            throw new UnexpectedErrorException();
        }
        String connectionString = connectionProp.getProperty("connectionString");
        return DriverManager.getConnection(connectionString);
    }

    public static ResultSet executeQuery(String sqlQuery) throws UnexpectedErrorException, SQLException {
        Connection db = SqlController.connect();
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            return rs;
        } finally {
            db.close();
        }
    }
}