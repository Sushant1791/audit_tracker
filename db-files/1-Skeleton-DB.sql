/*
 Navicat Premium Data Transfer

 Source Server         : AuditTracker
 Source Server Type    : PostgreSQL
 Source Server Version : 100006
 Source Host           : localhost:5432
 Source Catalog        : AuditTracker
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100006
 File Encoding         : 65001

 Date: 18/11/2018 22:58:25
*/


-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."hibernate_sequence";
CREATE SEQUENCE "public"."hibernate_sequence" 
INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 1000
CACHE 1;

-- ----------------------------
-- Table structure for audit_plan_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."audit_plan_master";
CREATE TABLE "public"."audit_plan_master" (
  "id" int8 NOT NULL,
  "audit_plan_entity" varchar(50) COLLATE "pg_catalog"."default";
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "procedure_id" int8,
  "start_date" timestamp(6),
  "end_date" timestamp(6),
  "is_active" bool NOT NULL,
  "assign_to" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for audit_plan_objective_map
-- ----------------------------
DROP TABLE IF EXISTS "public"."audit_plan_objective_map";
CREATE TABLE "public"."audit_plan_objective_map" (
  "id" int8 NOT NULL,
  "objective_name" varchar(50) COLLATE "pg_catalog"."default",
  "objective_description" varchar(255) COLLATE "pg_catalog"."default",
  "plan_id" int8,
  "is_active" bool NOT NULL,
  "assign_to" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for audit_proc_objective_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."audit_proc_objective_master";
CREATE TABLE "public"."audit_proc_objective_master" (
  "id" int8 NOT NULL,
  "objective_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "objective_description" varchar(255) COLLATE "pg_catalog"."default",
  "procedure_id" int8,
  "is_active" bool NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for audit_procedure_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."audit_procedure_master";
CREATE TABLE "public"."audit_procedure_master" (
  "id" int8 NOT NULL,
  "procedure_title" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "procedure_description" varchar(255) COLLATE "pg_catalog"."default",
  "dept_id" int8,
  "is_active" bool NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for cost_centre_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."cost_centre_master";
CREATE TABLE "public"."cost_centre_master" (
  "id" int8 NOT NULL,
  "cost_centre_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_id" int8,
  "owner_id" int8,
  "reporting_id" int8,
  "is_active" bool NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS "public"."databasechangelog";
CREATE TABLE "public"."databasechangelog" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "author" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "filename" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dateexecuted" timestamp(6) NOT NULL,
  "orderexecuted" int4 NOT NULL,
  "exectype" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "md5sum" varchar(35) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "comments" varchar(255) COLLATE "pg_catalog"."default",
  "tag" varchar(255) COLLATE "pg_catalog"."default",
  "liquibase" varchar(20) COLLATE "pg_catalog"."default",
  "contexts" varchar(255) COLLATE "pg_catalog"."default",
  "labels" varchar(255) COLLATE "pg_catalog"."default",
  "deployment_id" varchar(10) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of databasechangelog
-- ----------------------------
INSERT INTO "public"."databasechangelog" VALUES ('00000000000000', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2018-11-14 02:02:11.913286', 1, 'EXECUTED', '7:a6235f40597a13436aa36c6d61db2269', 'createSequence sequenceName=hibernate_sequence', '', NULL, '3.5.4', NULL, NULL, '2141131830');
INSERT INTO "public"."databasechangelog" VALUES ('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2018-11-14 02:02:12.124126', 2, 'EXECUTED', '7:3343715ced8c0b123b41e48e6f1f3d1b', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '3.5.4', NULL, NULL, '2141131830');

-- ----------------------------
-- Table structure for databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS "public"."databasechangeloglock";
CREATE TABLE "public"."databasechangeloglock" (
  "id" int4 NOT NULL,
  "locked" bool NOT NULL,
  "lockgranted" timestamp(6),
  "lockedby" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of databasechangeloglock
-- ----------------------------
INSERT INTO "public"."databasechangeloglock" VALUES (1, 'f', NULL, NULL);

-- ----------------------------
-- Table structure for dept_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."dept_master";
CREATE TABLE "public"."dept_master" (
  "id" int8 NOT NULL,
  "dept_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Table structure for jhi_authority
-- ----------------------------
DROP TABLE IF EXISTS "public"."jhi_authority";
CREATE TABLE "public"."jhi_authority" (
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of jhi_authority
-- ----------------------------
INSERT INTO "public"."jhi_authority" VALUES ('ROLE_ADMIN');
INSERT INTO "public"."jhi_authority" VALUES ('ROLE_USER');

-- ----------------------------
-- Table structure for jhi_persistent_audit_event
-- ----------------------------
DROP TABLE IF EXISTS "public"."jhi_persistent_audit_event";
CREATE TABLE "public"."jhi_persistent_audit_event" (
  "event_id" int8 NOT NULL,
  "principal" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "event_date" timestamp(6),
  "event_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of jhi_persistent_audit_event
-- ----------------------------
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1001, 'admin', '2018-11-18 15:37:45.791', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1051, 'admin', '2018-11-18 16:49:31.641', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1052, 'admin', '2018-11-18 16:49:49.124', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1053, 'admin', '2018-11-18 16:51:43.286', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1054, 'admin', '2018-11-18 16:51:54.336', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1055, 'admin', '2018-11-18 16:59:09.85', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1056, 'admin', '2018-11-18 17:01:43.758', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1101, 'admin', '2018-11-18 17:27:23.402', 'AUTHENTICATION_SUCCESS');
INSERT INTO "public"."jhi_persistent_audit_event" VALUES (1102, 'user', '2018-11-18 17:27:42.443', 'AUTHENTICATION_SUCCESS');

-- ----------------------------
-- Table structure for jhi_persistent_audit_evt_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."jhi_persistent_audit_evt_data";
CREATE TABLE "public"."jhi_persistent_audit_evt_data" (
  "event_id" int8 NOT NULL,
  "name" varchar(150) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for jhi_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."jhi_user";
CREATE TABLE "public"."jhi_user" (
  "id" int8 NOT NULL,
  "login" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password_hash" varchar(60) COLLATE "pg_catalog"."default" NOT NULL,
  "first_name" varchar(50) COLLATE "pg_catalog"."default",
  "last_name" varchar(50) COLLATE "pg_catalog"."default",
  "email" varchar(254) COLLATE "pg_catalog"."default",
  "image_url" varchar(256) COLLATE "pg_catalog"."default",
  "activated" bool NOT NULL,
  "lang_key" varchar(6) COLLATE "pg_catalog"."default",
  "activation_key" varchar(20) COLLATE "pg_catalog"."default",
  "reset_key" varchar(20) COLLATE "pg_catalog"."default",
  "created_by" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6),
  "reset_date" timestamp(6),
  "last_modified_by" varchar(50) COLLATE "pg_catalog"."default",
  "last_modified_date" timestamp(6),
  "location_id" int8,
  "company_id" int8,
  "reporting_user_id" int8,
  "designation" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of jhi_user
-- ----------------------------
INSERT INTO "public"."jhi_user" VALUES (1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', '', 't', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."jhi_user" VALUES (2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', 't', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."jhi_user" VALUES (3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', 't', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."jhi_user" VALUES (4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', 't', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for jhi_user_authority
-- ----------------------------
DROP TABLE IF EXISTS "public"."jhi_user_authority";
CREATE TABLE "public"."jhi_user_authority" (
  "user_id" int8 NOT NULL,
  "authority_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of jhi_user_authority
-- ----------------------------
INSERT INTO "public"."jhi_user_authority" VALUES (1, 'ROLE_ADMIN');
INSERT INTO "public"."jhi_user_authority" VALUES (1, 'ROLE_USER');
INSERT INTO "public"."jhi_user_authority" VALUES (3, 'ROLE_ADMIN');
INSERT INTO "public"."jhi_user_authority" VALUES (3, 'ROLE_USER');
INSERT INTO "public"."jhi_user_authority" VALUES (4, 'ROLE_USER');

-- ----------------------------
-- Table structure for location_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."location_master";
CREATE TABLE "public"."location_master" (
  "id" int8 NOT NULL,
  "location_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "location_address" varchar(60) COLLATE "pg_catalog"."default" NOT NULL,
  "erp_code" varchar(50) COLLATE "pg_catalog"."default",
  "is_active" bool NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp(6),
  "last_modified_by" int8,
  "last_modified_date" timestamp(6)
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."hibernate_sequence"', 1200, true);

-- ----------------------------
-- Primary Key structure for table audit_plan_master
-- ----------------------------
ALTER TABLE "public"."audit_plan_master" ADD CONSTRAINT "audit_plan_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table audit_plan_objective_map
-- ----------------------------
ALTER TABLE "public"."audit_plan_objective_map" ADD CONSTRAINT "audit_plan_objective_map_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table audit_proc_objective_master
-- ----------------------------
ALTER TABLE "public"."audit_proc_objective_master" ADD CONSTRAINT "audit_proc_objective_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table audit_procedure_master
-- ----------------------------
ALTER TABLE "public"."audit_procedure_master" ADD CONSTRAINT "audit_procedure_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cost_centre_master
-- ----------------------------
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "cost_centre_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table databasechangeloglock
-- ----------------------------
ALTER TABLE "public"."databasechangeloglock" ADD CONSTRAINT "pk_databasechangeloglock" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dept_master
-- ----------------------------
ALTER TABLE "public"."dept_master" ADD CONSTRAINT "dept_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table jhi_authority
-- ----------------------------
ALTER TABLE "public"."jhi_authority" ADD CONSTRAINT "pk_jhi_authority" PRIMARY KEY ("name");

-- ----------------------------
-- Indexes structure for table jhi_persistent_audit_event
-- ----------------------------
CREATE INDEX "idx_persistent_audit_event" ON "public"."jhi_persistent_audit_event" USING btree (
  "principal" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "event_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table jhi_persistent_audit_event
-- ----------------------------
ALTER TABLE "public"."jhi_persistent_audit_event" ADD CONSTRAINT "pk_jhi_persistent_audit_event" PRIMARY KEY ("event_id");

-- ----------------------------
-- Indexes structure for table jhi_persistent_audit_evt_data
-- ----------------------------
CREATE INDEX "idx_persistent_audit_evt_data" ON "public"."jhi_persistent_audit_evt_data" USING btree (
  "event_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table jhi_persistent_audit_evt_data
-- ----------------------------
ALTER TABLE "public"."jhi_persistent_audit_evt_data" ADD CONSTRAINT "jhi_persistent_audit_evt_data_pkey" PRIMARY KEY ("event_id", "name");

-- ----------------------------
-- Uniques structure for table jhi_user
-- ----------------------------
ALTER TABLE "public"."jhi_user" ADD CONSTRAINT "ux_user_email" UNIQUE ("email");
ALTER TABLE "public"."jhi_user" ADD CONSTRAINT "ux_user_login" UNIQUE ("login");

-- ----------------------------
-- Primary Key structure for table jhi_user
-- ----------------------------
ALTER TABLE "public"."jhi_user" ADD CONSTRAINT "pk_jhi_user" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table jhi_user_authority
-- ----------------------------
ALTER TABLE "public"."jhi_user_authority" ADD CONSTRAINT "jhi_user_authority_pkey" PRIMARY KEY ("user_id", "authority_name");

-- ----------------------------
-- Primary Key structure for table location_master
-- ----------------------------
ALTER TABLE "public"."location_master" ADD CONSTRAINT "location_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table audit_plan_master
-- ----------------------------
ALTER TABLE "public"."audit_plan_master" ADD CONSTRAINT "fk_adt_plan_mstr_assignto" FOREIGN KEY ("assign_to") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_plan_master" ADD CONSTRAINT "fk_adt_plan_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_plan_master" ADD CONSTRAINT "fk_adt_plan_mstr_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_plan_master" ADD CONSTRAINT "fk_adt_plan_mstr_proc" FOREIGN KEY ("procedure_id") REFERENCES "public"."audit_procedure_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table audit_plan_objective_map
-- ----------------------------
ALTER TABLE "public"."audit_plan_objective_map" ADD CONSTRAINT "fk_adt_plan_obj_map_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_plan_objective_map" ADD CONSTRAINT "fk_adt_plan_obj_map_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_plan_objective_map" ADD CONSTRAINT "fk_adt_plan_obj_map_proc" FOREIGN KEY ("plan_id") REFERENCES "public"."audit_plan_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table audit_proc_objective_master
-- ----------------------------
ALTER TABLE "public"."audit_proc_objective_master" ADD CONSTRAINT "fk_adt_procobj_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_proc_objective_master" ADD CONSTRAINT "fk_adt_procobj_mstr_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_proc_objective_master" ADD CONSTRAINT "fk_adt_procobj_mstr_proc" FOREIGN KEY ("procedure_id") REFERENCES "public"."audit_procedure_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table audit_procedure_master
-- ----------------------------
ALTER TABLE "public"."audit_procedure_master" ADD CONSTRAINT "fk_adt_proc_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_procedure_master" ADD CONSTRAINT "fk_adt_proc_mstr_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."audit_procedure_master" ADD CONSTRAINT "fk_cost_centre_dept" FOREIGN KEY ("dept_id") REFERENCES "public"."dept_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table cost_centre_master
-- ----------------------------
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "fk_cost_centre_dept" FOREIGN KEY ("dept_id") REFERENCES "public"."dept_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "fk_cost_centre_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "fk_cost_centre_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "fk_cost_centre_owner" FOREIGN KEY ("owner_id") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."cost_centre_master" ADD CONSTRAINT "fk_cost_centre_reporting" FOREIGN KEY ("reporting_id") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table dept_master
-- ----------------------------
ALTER TABLE "public"."dept_master" ADD CONSTRAINT "fk_dept_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."dept_master" ADD CONSTRAINT "fk_dept_mstr_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table jhi_persistent_audit_evt_data
-- ----------------------------
ALTER TABLE "public"."jhi_persistent_audit_evt_data" ADD CONSTRAINT "fk_evt_pers_audit_evt_data" FOREIGN KEY ("event_id") REFERENCES "public"."jhi_persistent_audit_event" ("event_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table jhi_user_authority
-- ----------------------------
ALTER TABLE "public"."jhi_user_authority" ADD CONSTRAINT "fk_authority_name" FOREIGN KEY ("authority_name") REFERENCES "public"."jhi_authority" ("name") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."jhi_user_authority" ADD CONSTRAINT "fk_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table location_master
-- ----------------------------
ALTER TABLE "public"."location_master" ADD CONSTRAINT "fk_loc_mstr_created" FOREIGN KEY ("created_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."location_master" ADD CONSTRAINT "fk_loc_mstr_modified" FOREIGN KEY ("last_modified_by") REFERENCES "public"."jhi_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
