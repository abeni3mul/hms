package com.hms.business;

import com.hms.database.UserDB;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class User {
    private UserTypes userType;
    private int userId;
    private String password;

    public User(UserTypes userType, int userId, String password){
        this.userType = userType;
        this.userId = userId;
        this.password = password;
    }

    public UserTypes getUserType(){
        return this.userType;
    }

    public int getUserId(){
        return this.userId;
    }
    public boolean login() throws UnexpectedErrorException, SQLException, InvalidIDException {
        byte[] passwordHash = new UserDB().getUserPasswordHash(this);

        try {
            byte[] passwordHash2 = MessageDigest.getInstance("SHA-256").digest(this.password.getBytes(StandardCharsets.UTF_8));
            if(MessageDigest.isEqual(passwordHash, passwordHash2))
                return true;
        }catch (NoSuchAlgorithmException e)
        {
            throw new UnexpectedErrorException();
        }
        return false;
    }
}
