--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Doctor" (
    "doctorId" integer NOT NULL,
    "firstName" character varying(255) NOT NULL,
    "middleName" character varying(255),
    "lastName" character varying(255) NOT NULL,
    "phoneNumber" character varying(15) NOT NULL,
    email character varying(320) NOT NULL,
    "dateOfBirth" date NOT NULL,
    speciality character varying(255) NOT NULL,
    password bytea NOT NULL,
    CONSTRAINT chk_iseighteen CHECK ((age((CURRENT_DATE)::timestamp with time zone, ("dateOfBirth")::timestamp with time zone) >= '18 years'::interval))
);


ALTER TABLE public."Doctor" OWNER TO postgres;

--
-- Name: Doctor_doctorId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Doctor_doctorId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Doctor_doctorId_seq" OWNER TO postgres;

--
-- Name: Doctor_doctorId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Doctor_doctorId_seq" OWNED BY public."Doctor"."doctorId";


--
-- Name: Manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Manager" (
    "managerId" integer NOT NULL,
    "firstName" character varying(255) NOT NULL,
    "middleName" character varying(255),
    "lastName" character varying(255) NOT NULL,
    "dateOfBirth" date NOT NULL,
    "phoneNumber" character varying(15) NOT NULL,
    email character varying(320) NOT NULL,
    password bytea NOT NULL,
    CONSTRAINT chk_iseighteen CHECK ((age((CURRENT_DATE)::timestamp with time zone, ("dateOfBirth")::timestamp with time zone) >= '18 years'::interval))
);


ALTER TABLE public."Manager" OWNER TO postgres;

--
-- Name: Manager_managerId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Manager_managerId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Manager_managerId_seq" OWNER TO postgres;

--
-- Name: Manager_managerId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Manager_managerId_seq" OWNED BY public."Manager"."managerId";


--
-- Name: MedicalRecord; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."MedicalRecord" (
    "recordId" uuid DEFAULT gen_random_uuid() NOT NULL,
    "patientId" integer NOT NULL,
    "doctorId" integer,
    "nurseId" integer,
    "dateAndTime" timestamp without time zone DEFAULT now() NOT NULL,
    diagnosis text,
    treatment text,
    CONSTRAINT chk_diagnosisortreatment CHECK (((diagnosis IS NOT NULL) OR (treatment IS NOT NULL))),
    CONSTRAINT chk_nurseordoctor CHECK ((("doctorId" IS NOT NULL) OR ("nurseId" IS NOT NULL))),
    CONSTRAINT chk_timestampnotfuture CHECK (("dateAndTime" <= now()))
);


ALTER TABLE public."MedicalRecord" OWNER TO postgres;

--
-- Name: Nurse; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Nurse" (
    "nurseId" integer NOT NULL,
    "firstName" character varying(255) NOT NULL,
    "middleName" character varying(255),
    "lastName" character varying(255) NOT NULL,
    "dateOfBirth" date NOT NULL,
    "phoneNumber" character varying(15) NOT NULL,
    email character varying(320) NOT NULL,
    password bytea NOT NULL,
    CONSTRAINT chk_iseighteen CHECK ((age((CURRENT_DATE)::timestamp with time zone, ("dateOfBirth")::timestamp with time zone) >= '18 years'::interval))
);


ALTER TABLE public."Nurse" OWNER TO postgres;

--
-- Name: Nurse_nurseId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Nurse_nurseId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Nurse_nurseId_seq" OWNER TO postgres;

--
-- Name: Nurse_nurseId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Nurse_nurseId_seq" OWNED BY public."Nurse"."nurseId";


--
-- Name: Patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Patient" (
    "patientId" integer NOT NULL,
    "firstName" character varying(255) NOT NULL,
    "middleName" character varying(255),
    "lastName" character varying(255) NOT NULL,
    "phoneNumber" character varying(15) NOT NULL,
    email character varying(320) NOT NULL,
    "dateOfBirth" date NOT NULL,
    "bloodType" character varying(3),
    "insuranceCompanyName" character varying(255),
    "insuranceNumber" integer,
    CONSTRAINT chk_validbloodtype CHECK ((("bloodType")::text = ANY ((ARRAY['A+'::character varying, 'A-'::character varying, 'B+'::character varying, 'B-'::character varying, 'AB+'::character varying, 'AB-'::character varying, 'O+'::character varying, 'O-'::character varying])::text[]))),
    CONSTRAINT chk_validdateofbirth CHECK (("dateOfBirth" < CURRENT_DATE))
);


ALTER TABLE public."Patient" OWNER TO postgres;

--
-- Name: Patient_patientId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Patient_patientId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Patient_patientId_seq" OWNER TO postgres;

--
-- Name: Patient_patientId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Patient_patientId_seq" OWNED BY public."Patient"."patientId";


--
-- Name: Doctor doctorId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Doctor" ALTER COLUMN "doctorId" SET DEFAULT nextval('public."Doctor_doctorId_seq"'::regclass);


--
-- Name: Manager managerId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Manager" ALTER COLUMN "managerId" SET DEFAULT nextval('public."Manager_managerId_seq"'::regclass);


--
-- Name: Nurse nurseId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Nurse" ALTER COLUMN "nurseId" SET DEFAULT nextval('public."Nurse_nurseId_seq"'::regclass);


--
-- Name: Patient patientId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Patient" ALTER COLUMN "patientId" SET DEFAULT nextval('public."Patient_patientId_seq"'::regclass);


--
-- Data for Name: Doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Doctor" ("doctorId", "firstName", "middleName", "lastName", "phoneNumber", email, "dateOfBirth", speciality, password) FROM stdin;
1	Test1	Test2	Test3	748348324	test@ttest.com	1955-01-01	something	\\xa9a738351febdf95c90991cceff89ebe22d5dd638076019ea12473c74746992c
\.


--
-- Data for Name: Manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Manager" ("managerId", "firstName", "middleName", "lastName", "dateOfBirth", "phoneNumber", email, password) FROM stdin;
1	Test	Test	Test	1944-01-01	3421231234	test@tt3.com	\\x0231093472e6fefaf5316db0fc05a4dfea1abbf5b1994a364f90a405f91759fb
\.


--
-- Data for Name: MedicalRecord; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."MedicalRecord" ("recordId", "patientId", "doctorId", "nurseId", "dateAndTime", diagnosis, treatment) FROM stdin;
1988431f-f4b1-4177-8d4a-07b18ab7bf83	1	1	\N	2023-04-25 22:30:50.160624	He was alright	\N
dad1109f-dbb7-494d-b5f3-4704f75355da	1	1	\N	2023-04-29 10:02:02.736359	akdsfjlkajsdlfkjalkf\tkjlkajdflkaj	adsjflkajsdl;f\nasdkflja;ljdf;sl
7dc5d432-056e-4a03-aed2-eb453b0551c7	1	1	\N	2023-04-29 10:07:34.452466	aksdfjlaksjdfljalsf\t	akdsjflakjsdlkfj;lajsd;ffjkak
7c2a27a9-77c1-4c5c-92c0-602d133ee7b5	1	1	\N	2023-04-29 10:13:56.54764	asdfjlasjdfl;kjalk;fdj\t	askdjflaskjdfljalsdjfl
399f23e1-0fb4-4f00-ad19-a6825bd6bd37	1	1	\N	2023-04-29 10:22:47.901882	asdjflkajsdlfkj\tasjdflkj	kfasjdlfkjalsjdf
ade5dc4e-a3e3-4068-a48d-d07db8cafd2f	1	1	\N	2023-04-29 14:33:04.441462	\N	lasjdlfjlasjd;fj;asdjf
1a6eaf89-0880-47fc-8b8c-2d95e11640a8	1	1	\N	2023-04-29 14:45:02.817892	\N	asdfkjskjdflkajsdkfj\t
79d53a7a-28ec-4e11-8787-f4b3a16fd98b	1	1	\N	2023-04-29 14:51:28.378038	\N	asldfjlaskjdfl;jkla;fd\nadfjlasjdfkasldf;as\ndfjlasdjlf
\.


--
-- Data for Name: Nurse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Nurse" ("nurseId", "firstName", "middleName", "lastName", "dateOfBirth", "phoneNumber", email, password) FROM stdin;
\.


--
-- Data for Name: Patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Patient" ("patientId", "firstName", "middleName", "lastName", "phoneNumber", email, "dateOfBirth", "bloodType", "insuranceCompanyName", "insuranceNumber") FROM stdin;
1	Test	Test	Test	3738492	test@test.com	2002-01-01	O+	someone	2232
2	Update	Test	First	12352	test@tt2.com	1965-01-01	O+	Comp1	32539
3	Esrom	Mulugeta	Tadesse	3524532453	esrom@hms.com	1995-10-25	O+	Here	345432
\.


--
-- Name: Doctor_doctorId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Doctor_doctorId_seq"', 1, true);


--
-- Name: Manager_managerId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Manager_managerId_seq"', 1, true);


--
-- Name: Nurse_nurseId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Nurse_nurseId_seq"', 1, false);


--
-- Name: Patient_patientId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Patient_patientId_seq"', 3, true);


--
-- Name: Doctor Doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Doctor"
    ADD CONSTRAINT "Doctor_pkey" PRIMARY KEY ("doctorId");


--
-- Name: Manager Manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Manager"
    ADD CONSTRAINT "Manager_pkey" PRIMARY KEY ("managerId");


--
-- Name: MedicalRecord MedicalRecord_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MedicalRecord"
    ADD CONSTRAINT "MedicalRecord_pkey" PRIMARY KEY ("recordId");


--
-- Name: Nurse Nurse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Nurse"
    ADD CONSTRAINT "Nurse_pkey" PRIMARY KEY ("nurseId");


--
-- Name: Patient Patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Patient"
    ADD CONSTRAINT "Patient_pkey" PRIMARY KEY ("patientId");


--
-- Name: Doctor uq_doctoremail; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Doctor"
    ADD CONSTRAINT uq_doctoremail UNIQUE (email);


--
-- Name: Doctor uq_doctorphonenumber; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Doctor"
    ADD CONSTRAINT uq_doctorphonenumber UNIQUE ("phoneNumber");


--
-- Name: Manager uq_manageremail; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Manager"
    ADD CONSTRAINT uq_manageremail UNIQUE (email);


--
-- Name: Manager uq_managerphonenumber; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Manager"
    ADD CONSTRAINT uq_managerphonenumber UNIQUE ("phoneNumber");


--
-- Name: Nurse uq_nurseemail; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Nurse"
    ADD CONSTRAINT uq_nurseemail UNIQUE (email);


--
-- Name: Nurse uq_nursephonenumber; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Nurse"
    ADD CONSTRAINT uq_nursephonenumber UNIQUE ("phoneNumber");


--
-- Name: uq_insurancenumber; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX uq_insurancenumber ON public."Patient" USING btree ("insuranceNumber") WHERE ("insuranceCompanyName" IS NOT NULL);


--
-- Name: MedicalRecord fk_medicalrecorddoctor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MedicalRecord"
    ADD CONSTRAINT fk_medicalrecorddoctor FOREIGN KEY ("doctorId") REFERENCES public."Doctor"("doctorId");


--
-- Name: MedicalRecord fk_medicalrecordnurse; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MedicalRecord"
    ADD CONSTRAINT fk_medicalrecordnurse FOREIGN KEY ("nurseId") REFERENCES public."Nurse"("nurseId");


--
-- Name: MedicalRecord fk_medicalrecordpatient; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MedicalRecord"
    ADD CONSTRAINT fk_medicalrecordpatient FOREIGN KEY ("patientId") REFERENCES public."Patient"("patientId");


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

