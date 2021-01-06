alter table jhi_user add column designation VARCHAR (50);
alter table jhi_user add column location_id INTEGER;
alter table jhi_user add column company_id INTEGER;
alter table jhi_user add column reporting_user_id INTEGER;

alter table audit_procedure_master add column cc_id INTEGER;