DO
$body$
BEGIN
  IF NOT EXISTS (SELECT * FROM pg_catalog.pg_user WHERE usename = 'bclient') THEN 
    CREATE USER bclient WITH ENCRYPTED PASSWORD 'bclient';
  END IF;
END
$body$;

--GRANT bclient TO dbuser;
DROP SCHEMA IF EXISTS bclient CASCADE;
CREATE SCHEMA IF NOT EXISTS bclient AUTHORIZATION bclient;
GRANT USAGE ON SCHEMA bclient TO bclient;

-- Это выполнить после создания объетов схемы bclient
GRANT ALL ON ALL TABLES IN SCHEMA bclient TO bclient;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA bclient TO bclient;
