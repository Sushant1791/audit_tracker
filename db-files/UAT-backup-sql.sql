-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               PostgreSQL 10.6 on x86_64-pc-mingw64, compiled by gcc.exe (Rev5, Built by MSYS2 project) 4.9.2, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table public.audit_plan_master: 0 rows
/*!40000 ALTER TABLE "audit_plan_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "audit_plan_master" ENABLE KEYS */;

-- Dumping data for table public.audit_plan_objective_map: 0 rows
/*!40000 ALTER TABLE "audit_plan_objective_map" DISABLE KEYS */;
/*!40000 ALTER TABLE "audit_plan_objective_map" ENABLE KEYS */;

-- Dumping data for table public.audit_procedure_master: 0 rows
/*!40000 ALTER TABLE "audit_procedure_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "audit_procedure_master" ENABLE KEYS */;

-- Dumping data for table public.audit_proc_objective_master: 0 rows
/*!40000 ALTER TABLE "audit_proc_objective_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "audit_proc_objective_master" ENABLE KEYS */;

-- Dumping data for table public.audit_testing_plan_master: 0 rows
/*!40000 ALTER TABLE "audit_testing_plan_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "audit_testing_plan_master" ENABLE KEYS */;

-- Dumping data for table public.cost_centre_master: 2 rows
/*!40000 ALTER TABLE "cost_centre_master" DISABLE KEYS */;
INSERT INTO "cost_centre_master" ("id", "cost_centre_name", "dept_id", "owner_id", "reporting_id", "is_active", "created_by", "created_date", "last_modified_by", "last_modified_date") VALUES
	(1, E'India Cost Centre', 1, 1, 2, E'true', 1, E'2019-01-09 20:58:17', 1, E'2019-01-09 20:58:28'),
	(2, E'HR', 1, 1, 3, E'true', 1, E'2019-01-09 20:58:52', 1, E'2019-01-09 20:58:55');
/*!40000 ALTER TABLE "cost_centre_master" ENABLE KEYS */;

-- Dumping data for table public.databasechangelog: 2 rows
/*!40000 ALTER TABLE "databasechangelog" DISABLE KEYS */;
INSERT INTO "databasechangelog" ("id", "author", "filename", "dateexecuted", "orderexecuted", "exectype", "md5sum", "description", "comments", "tag", "liquibase", "contexts", "labels", "deployment_id") VALUES
	(E'00000000000000', E'jhipster', E'config/liquibase/changelog/00000000000000_initial_schema.xml', E'2018-12-07 22:30:43.073131', 1, E'EXECUTED', E'7:a6235f40597a13436aa36c6d61db2269', E'createSequence sequenceName=hibernate_sequence', E'', NULL, E'3.5.4', NULL, NULL, E'4202042927'),
	(E'00000000000001', E'jhipster', E'config/liquibase/changelog/00000000000000_initial_schema.xml', E'2018-12-07 22:31:31.828065', 2, E'EXECUTED', E'7:3343715ced8c0b123b41e48e6f1f3d1b', E'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', E'', NULL, E'3.5.4', NULL, NULL, E'4202091059');
/*!40000 ALTER TABLE "databasechangelog" ENABLE KEYS */;

-- Dumping data for table public.databasechangeloglock: 1 rows
/*!40000 ALTER TABLE "databasechangeloglock" DISABLE KEYS */;
INSERT INTO "databasechangeloglock" ("id", "locked", "lockgranted", "lockedby") VALUES
	(1, E'false', NULL, NULL);
/*!40000 ALTER TABLE "databasechangeloglock" ENABLE KEYS */;

-- Dumping data for table public.dept_master: 2 rows
/*!40000 ALTER TABLE "dept_master" DISABLE KEYS */;
INSERT INTO "dept_master" ("id", "dept_name", "is_active", "created_by", "created_date", "last_modified_by", "last_modified_date") VALUES
	(2, E'HR', E'true', 1, E'2018-12-07 22:48:20', 1, E'2018-12-07 22:48:20'),
	(1, E'Production', E'true', 1, E'2019-01-09 20:59:24', 1, E'2019-01-09 20:59:35');
/*!40000 ALTER TABLE "dept_master" ENABLE KEYS */;

-- Dumping data for table public.document_master: 0 rows
/*!40000 ALTER TABLE "document_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "document_master" ENABLE KEYS */;

-- Dumping data for table public.jhi_authority: 2 rows
/*!40000 ALTER TABLE "jhi_authority" DISABLE KEYS */;
INSERT INTO "jhi_authority" ("name") VALUES
	(E'ROLE_ADMIN'),
	(E'ROLE_USER');
/*!40000 ALTER TABLE "jhi_authority" ENABLE KEYS */;

-- Dumping data for table public.jhi_date_time_wrapper: 0 rows
/*!40000 ALTER TABLE "jhi_date_time_wrapper" DISABLE KEYS */;
/*!40000 ALTER TABLE "jhi_date_time_wrapper" ENABLE KEYS */;

-- Dumping data for table public.jhi_persistent_audit_event: 0 rows
/*!40000 ALTER TABLE "jhi_persistent_audit_event" DISABLE KEYS */;
/*!40000 ALTER TABLE "jhi_persistent_audit_event" ENABLE KEYS */;

-- Dumping data for table public.jhi_persistent_audit_evt_data: 0 rows
/*!40000 ALTER TABLE "jhi_persistent_audit_evt_data" DISABLE KEYS */;
/*!40000 ALTER TABLE "jhi_persistent_audit_evt_data" ENABLE KEYS */;

-- Dumping data for table public.jhi_user: 6 rows
/*!40000 ALTER TABLE "jhi_user" DISABLE KEYS */;
INSERT INTO "jhi_user" ("id", "login", "password_hash", "first_name", "last_name", "email", "image_url", "activated", "lang_key", "activation_key", "reset_key", "created_by", "created_date", "reset_date", "last_modified_by", "last_modified_date", "company_id", "designation", "location_id", "reporting_user_id") VALUES
	(6001, E'sushant.parkhe@gmail.com', E'$2a$10$pOpNjV2ERMI5spfL75FaouFGrke3yKUjhuWmd7aE0/z/7cE1P7qua', E'Sushant Parkhe', E'', E'sushant.parkhe@gmail.com', NULL, E'true', E'en', NULL, E'99609892862139564252', E'admin', E'2018-12-19 16:45:35.033', E'2018-12-19 16:45:33.159', E'admin', E'2018-12-19 16:45:35.033', NULL, E'Developer', 1, 1),
	(1, E'system', E'$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', E'System', E'System', E'system@localhost', E'', E'true', E'en', NULL, NULL, E'system', NULL, NULL, E'admin', E'2019-01-19 15:23:41.542', NULL, NULL, 1, 1),
	(3, E'admin', E'$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', E'Administrator', E'Administrator', E'admin@localhost', E'', E'true', E'en', NULL, NULL, E'system', NULL, NULL, E'system', NULL, NULL, NULL, 1, 1),
	(4, E'user', E'$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', E'User', E'User', E'user@localhost', E'', E'true', E'en', NULL, NULL, E'system', NULL, NULL, E'system', NULL, NULL, NULL, 1, 1),
	(11201, E'b@b.com', E'$2a$10$rVHCDSJAaVoJ4SyQKUUFaOnKENGyaHdQxD07Lp1NOvJJXMc.3a0BS', E'Test User', E'', E'b@b.com', NULL, E'true', E'en', NULL, E'51839449185171717810', E'admin', NULL, E'2019-01-19 15:59:29.953', E'anonymousUser', E'2019-01-19 15:59:29.972', NULL, E'Developer', 2, 6001),
	(2, E'anonymoususer', E'$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', E'Anonymous', E'User', E'kk@gmail.com', E'', E'true', E'en', NULL, NULL, E'system', NULL, NULL, E'system', NULL, NULL, NULL, 1, 1);
/*!40000 ALTER TABLE "jhi_user" ENABLE KEYS */;

-- Dumping data for table public.jhi_user_authority: 7 rows
/*!40000 ALTER TABLE "jhi_user_authority" DISABLE KEYS */;
INSERT INTO "jhi_user_authority" ("user_id", "authority_name") VALUES
	(1, E'ROLE_ADMIN'),
	(1, E'ROLE_USER'),
	(3, E'ROLE_ADMIN'),
	(3, E'ROLE_USER'),
	(4, E'ROLE_USER'),
	(6001, E'ROLE_ADMIN'),
	(11201, E'ROLE_USER');
/*!40000 ALTER TABLE "jhi_user_authority" ENABLE KEYS */;

-- Dumping data for table public.location_master: 3 rows
/*!40000 ALTER TABLE "location_master" DISABLE KEYS */;
INSERT INTO "location_master" ("id", "location_name", "location_address", "erp_code", "is_active", "created_by", "created_date", "last_modified_by", "last_modified_date") VALUES
	(1, E'Mumbai (HQ)', E'Mumbai', E'123', E'true', 1, E'2019-01-19 20:15:53', 1, E'2019-01-19 20:15:56'),
	(2, E'Vadodara', E'Vadodara', E'123', E'true', 1, E'2019-01-19 20:16:28', 1, E'2019-01-19 20:16:29'),
	(3, E'Surat', E'Surat', E'123', E'true', 1, E'2019-01-19 20:16:57', 1, E'2019-01-19 20:16:58');
/*!40000 ALTER TABLE "location_master" ENABLE KEYS */;

-- Dumping data for table public.testing_plan_observation_master: 0 rows
/*!40000 ALTER TABLE "testing_plan_observation_master" DISABLE KEYS */;
/*!40000 ALTER TABLE "testing_plan_observation_master" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
