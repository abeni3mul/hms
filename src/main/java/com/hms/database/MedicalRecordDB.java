package com.hms.database;

import com.hms.business.MedicalRecord;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MedicalRecordDB {
    public ArrayList<MedicalRecord> getMedicalRecordByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        Connection db = SqlController.connect();
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();

        try{
            PreparedStatement st = db.prepareStatement(
                    "select MR.\"recordId\"," +
                                "D.\"firstName\" as \"doctorFirstName\", " +
                                "D.\"middleName\" as \"doctorMiddleName\"," +
                                "D.\"lastName\" as \"doctorLastName\"," +
                                "N.\"firstName\" as \"nurseFirstName\"," +
                                "N.\"middleName\" as \"nurseMiddleName\"," +
                                "N.\"lastName\" as \"nurseLastName\"," +
                                "MR.\"dateAndTime\" " +
                            "from \"MedicalRecord\" as MR " +
                            "join \"Doctor\" D on D.\"doctorId\" = MR.\"doctorId\" " +
                            "join\"Nurse\" N on N.\"nurseId\" = MR.\"nurseId\"" +
                            "where MR.\"patientId\" = ? and MR.diagnosis != null;"
            );

            st.setInt(1, patientId);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                MedicalRecord medicalRecord = MedicalRecord.map(rs);
                medicalRecords.add(medicalRecord);
            }

            return medicalRecords;
        }
        finally {
            db.close();
        }
    }

    public MedicalRecord getMedicalRecord(UUID medicalRecordId) throws SQLException, UnexpectedErrorException {
        Connection db = SqlController.connect();

        try{
            PreparedStatement st = db.prepareStatement("select " +
                        "MR.*," +
                        "D.\"firstName\" as \"doctorFirstName\", " +
                        "D.\"middleName\" as \"doctorMiddleName\"," +
                        "D.\"lastName\" as \"doctorLastName\"," +
                        "N.\"firstName\" as \"nurseFirstName\"," +
                        "N.\"middleName\" as \"nurseMiddleName\"," +
                        "N.\"lastName\" as \"nurseLastName\"" +
                    "from \"MedicalRecord\" as MR " +
                    "join \"Doctor\" D on D.\"doctorId\" = MR.\"doctorId\" " +
                    "join \"Nurse\" N on N.\"nurseId\" = MR.\"nurseId\"" +
                    "where MR.\"recordId\" = ? and Mr.diagnosis != null;"
            );

            st.setString(1, medicalRecordId.toString());
            ResultSet rs = st.executeQuery();

            MedicalRecord medicalRecord = MedicalRecord.map(rs);
            medicalRecord.setRecordId(medicalRecordId);

            return medicalRecord;
        }
        finally {
            db.close();
        }
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws UnexpectedErrorException, SQLException {
        if(medicalRecord.getDoctorId() == 0 && medicalRecord.getNurseId() == 0)
            throw new UnexpectedErrorException();

        Connection db = SqlController.connect();

        try{
            PreparedStatement st = db.prepareStatement(
                    "insert into \"MedicalRecord\" (\"patientId\", \"doctorId\", \"nurseId\", diagnosis)" +
                            "values (?,?,?,?);"
            );

            st.setInt(1, medicalRecord.getPatientId());
            st.setInt(2, medicalRecord.getDoctorId());
            st.setInt(3, medicalRecord.getNurseId());
            st.setString(4, medicalRecord.getDiagnosis());

            int rowsInserted = st.executeUpdate();

            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register medical record. Please, try again.");
        }
        finally {
            db.close();
        }
    }
}
