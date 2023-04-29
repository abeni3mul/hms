package com.hms.database;

import com.hms.business.MedicalRecord;
import com.hms.exceptions.UnexpectedErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MedicalRecordDB {
    public ArrayList<MedicalRecord> getMedicalRecordByPatientId(int patientId, boolean isMedication) throws SQLException, UnexpectedErrorException {
        Connection db = SqlController.connect();
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();

        try{
            String condition = isMedication ? "MR.treatment is not null;" : "MR.diagnosis is not null;";
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
                            "left join \"Doctor\" D on D.\"doctorId\" = MR.\"doctorId\" " +
                            "left join \"Nurse\" N on N.\"nurseId\" = MR.\"nurseId\"" +
                            "where MR.\"patientId\" = ? and " + condition
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

    public ArrayList<MedicalRecord> getMedicalRecordByPatientId(int patientId) throws SQLException, UnexpectedErrorException {
        return this.getMedicalRecordByPatientId(patientId, false);
    }

    public MedicalRecord getMedicalRecord(UUID medicalRecordId) throws SQLException, UnexpectedErrorException {
        Connection db = SqlController.connect();

        try{
            PreparedStatement st = db.prepareStatement("select " +
                        "MR.*," +
                        "D.\"firstName\" as \"doctorFirstName\"," +
                        "D.\"middleName\" as \"doctorMiddleName\"," +
                        "D.\"lastName\" as \"doctorLastName\"," +
                        "N.\"firstName\" as \"nurseFirstName\"," +
                        "N.\"middleName\" as \"nurseMiddleName\"," +
                        "N.\"lastName\" as \"nurseLastName\"," +
                        "P.\"firstName\" as \"patientFirstName\"," +
                        "P.\"middleName\" as \"patientMiddleName\"," +
                        "P.\"lastName\" as \"patientLastName\" " +
                    "from \"MedicalRecord\" as MR " +
                    "left join \"Doctor\" D on D.\"doctorId\" = MR.\"doctorId\" " +
                    "left join \"Nurse\" N on N.\"nurseId\" = MR.\"nurseId\" " +
                    "join \"Patient\" P on P.\"patientId\" = MR.\"patientId\" " +
                    "where MR.\"recordId\" = ?::uuid;"
            );

            st.setString(1, medicalRecordId.toString());
            ResultSet rs = st.executeQuery();
            rs.next();

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
                    "insert into \"MedicalRecord\" (\"patientId\", \"doctorId\", \"nurseId\", diagnosis, treatment)" +
                            "values (?,?,?,?,?);"
            );

            st.setInt(1, medicalRecord.getPatientId());
            if(medicalRecord.getDoctorId() != 0)
                st.setInt(2, medicalRecord.getDoctorId());
            else
                st.setNull(2, Types.INTEGER);
            if(medicalRecord.getNurseId() != 0)
                st.setInt(3, medicalRecord.getNurseId());
            else
                st.setNull(3, Types.INTEGER);
            st.setString(4, medicalRecord.getDiagnosis());
            st.setString(5, medicalRecord.getTreatment());

            int rowsInserted = st.executeUpdate();

            if(rowsInserted == 0)
                throw new UnexpectedErrorException("Failed to register medical record. Please, try again.");
        }
        finally {
            db.close();
        }
    }
}
