package com.hms.database;

import com.hms.business.Patient;
import com.hms.exceptions.UnexpectedErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

final class SqlController {

    private SqlController(){}
     static Connection connect() throws UnexpectedErrorException, SQLException {
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
}