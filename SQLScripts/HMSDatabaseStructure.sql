create table "Patient"(
    "patientId" serial primary key,
    "firstName" varchar(255) not null,
    "middleName" varchar(255),
    "lastName" varchar(255) not null,
    "phoneNumber" varchar(15) not null,
    "email" varchar(320) not null,
    "dateOfBirth" date not null,
    "bloodType" varchar(3),
    "insuranceCompanyName" varchar(255),
    "insuranceNumber" int,
    constraint chk_validDateOfBirth check ( "dateOfBirth" < current_date ),
    constraint chk_validBloodType check ( "bloodType"  in ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'))
);

create unique index uq_insuranceNumber on "Patient"("insuranceNumber") where "insuranceCompanyName" is not null;

create table "Doctor"(
    "doctorId" serial primary key,
    "firstName"  varchar(255) not null,
    "middleName" varchar(255),
    "lastName" varchar(255) not null,
    "phoneNumber" varchar(15) not null,
    "email" varchar(320) not null,
    "dateOfBirth" date not null,
    "speciality" varchar(255) not null,
    "password" bytea not null,
    constraint uq_doctorPhoneNumber unique ("phoneNumber"),
    constraint uq_doctorEmail unique ("email"),
    constraint chk_isEighteen check ( age(current_date, "dateOfBirth") >= '18 years'::interval )
);

create table "Nurse"(
    "nurseId" serial primary key,
    "firstName" varchar(255) not null,
    "middleName" varchar(255),
    "lastName" varchar(255) not null,
    "dateOfBirth" date not null,
    "phoneNumber" varchar(15) not null,
    "email" varchar(320) not null,
    "password" bytea not null,
    constraint uq_nursePhoneNumber unique ("phoneNumber"),
    constraint uq_nurseEmail unique ("email"),
    constraint chk_isEighteen check ( age(current_date, "dateOfBirth") >= '18 years'::interval )
);


create table "MedicalRecord"(
    "recordId" uuid DEFAULT gen_random_uuid() primary key,
    "patientId" int not null,
    "doctorId" int,
    "nurseId" int,
    "dateAndTime" timestamp DEFAULT now() not null,
    "diagnosis" text,
    "treatment" text,
    constraint fk_medicalRecordPatient foreign key ("patientId") references "Patient"("patientId"),
    constraint fk_medicalRecordDoctor foreign key ("doctorId") references "Doctor"("doctorId"),
    constraint fk_medicalRecordNurse foreign key ("nurseId") references "Nurse"("nurseId"),
    constraint chk_nurseOrDoctor check ("doctorId" is not null or "nurseId" is not null),
    constraint chk_timestampNotFuture check ( "dateAndTime" <= now() ),
    constraint chk_diagnosisOrTreatment check("diagnosis" is not null or "treatment" is not null)
);

create table "Manager"(
    "managerId" serial primary key not null,
    "firstName" varchar(255) not null,
    "middleName" varchar(255),
    "lastName" varchar(255) not null,
    "dateOfBirth" date not null,
    "phoneNumber" varchar(15) not null,
    "email" varchar(320) not null,
    "password" bytea not null,
    constraint uq_managerPhoneNumber unique ("phoneNumber"),
    constraint uq_managerEmail unique ("email"),
    constraint chk_isEighteen check ( age(current_date, "dateOfBirth") >= '18 years'::interval)
)