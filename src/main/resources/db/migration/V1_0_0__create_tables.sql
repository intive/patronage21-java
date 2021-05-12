CREATE SCHEMA IF NOT EXISTS patronative;

SET search_path TO patronative;

CREATE TABLE IF NOT EXISTS patronative.role
(
    id   NUMERIC(19, 0) PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS role_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.status
(
    id   NUMERIC(19, 0) PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS status_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.gender
(
    id   NUMERIC(19, 0) PRIMARY KEY,
    name VARCHAR(16) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS gender_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.user
(
    id           NUMERIC(19, 0) PRIMARY KEY,
    login        VARCHAR(32) NOT NULL UNIQUE,
    user_name    VARCHAR(32) NOT NULL,
    first_name   VARCHAR(64) NOT NULL,
    last_name    VARCHAR(64) NOT NULL,
    email        VARCHAR(64) NOT NULL,
    phone_number VARCHAR(16) NOT NULL,
    github_url   VARCHAR(256),
    role_id      NUMERIC(19, 0) REFERENCES role (id),
    status_id    NUMERIC(19, 0) REFERENCES status (id),
    gender_id    NUMERIC(19, 0) REFERENCES gender (id)
);
CREATE SEQUENCE IF NOT EXISTS user_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.profile
(
    user_id NUMERIC(19, 0) PRIMARY KEY REFERENCES patronative.user (id),
    bio     VARCHAR(512),
    image   BYTEA
);

CREATE TABLE IF NOT EXISTS patronative.consent
(
    id       NUMERIC(19, 0) PRIMARY KEY,
    text     VARCHAR(256) NOT NULL,
    required BOOLEAN      NOT NULL DEFAULT FALSE
);
CREATE SEQUENCE IF NOT EXISTS consent_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.user_consent
(
    user_id    NUMERIC(19, 0) REFERENCES patronative.user (id),
    consent_id NUMERIC(19, 0) REFERENCES consent (id)
);

CREATE TABLE IF NOT EXISTS patronative.project_role
(
    id   NUMERIC(19, 0) PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS project_role_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.project
(
    id          NUMERIC(19, 0) PRIMARY KEY,
    name        VARCHAR(64)  NOT NULL,
    description VARCHAR(256),
    year        INTEGER      NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS project_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.project_user
(
    user_id    NUMERIC(19, 0) REFERENCES patronative.user (id),
    project_id NUMERIC(19, 0) REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS patronative.roles_in_project
(
    project_id      NUMERIC(19, 0) REFERENCES project (id),
    project_role_id NUMERIC(19, 0) REFERENCES project_role (id)
);

CREATE TABLE IF NOT EXISTS patronative.technology_group
(
    id          NUMERIC(19, 0) PRIMARY KEY,
    name        VARCHAR(32)  NOT NULL UNIQUE,
    description VARCHAR(256)
);
CREATE SEQUENCE IF NOT EXISTS technology_group_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.technology_group_user
(
    user_id             NUMERIC(19, 0) REFERENCES patronative.user (id),
    technology_group_id NUMERIC(19, 0) REFERENCES technology_group (id)
);

CREATE TABLE IF NOT EXISTS patronative.technology
(
    id   NUMERIC(19, 0) PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS technology_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS patronative.group_technology
(
    technology_group_id NUMERIC(19, 0) REFERENCES technology_group (id),
    technology_id       NUMERIC(19, 0) REFERENCES technology (id)
);