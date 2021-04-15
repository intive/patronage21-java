CREATE SCHEMA patronative

    CREATE TABLE role
    (
        id   NUMERIC(19, 0) PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    )
    CREATE SEQUENCE role_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE status
    (
        id   NUMERIC(19, 0) PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    )
    CREATE SEQUENCE status_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE gender
    (
        id   NUMERIC(19, 0) PRIMARY KEY,
        name VARCHAR(16) NOT NULL UNIQUE
    )
    CREATE SEQUENCE gender_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE patronative.user
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
    )
    CREATE SEQUENCE user_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE profile
    (
        user_id NUMERIC(19, 0) PRIMARY KEY REFERENCES patronative.user (id),
        bio     VARCHAR(512),
        image   BYTEA
    )

    CREATE TABLE consent
    (
        id       NUMERIC(19, 0) PRIMARY KEY,
        text     VARCHAR(256) NOT NULL,
        required BOOLEAN      NOT NULL DEFAULT FALSE
    )
    CREATE SEQUENCE consent_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE user_consent
    (
        user_id    NUMERIC(19, 0) REFERENCES patronative.user (id),
        consent_id NUMERIC(19, 0) REFERENCES consent (id)
    )

    CREATE TABLE project_role
    (
        id   NUMERIC(19, 0) PRIMARY KEY,
        name VARCHAR(64) NOT NULL UNIQUE
    )
    CREATE SEQUENCE project_role_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE project
    (
        id          NUMERIC(19, 0) PRIMARY KEY,
        name        VARCHAR(64)  NOT NULL,
        description VARCHAR(256),
        year        INTEGER      NOT NULL
    )
    CREATE SEQUENCE project_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE project_user
    (
        user_id    NUMERIC(19, 0) REFERENCES patronative.user (id),
        project_id NUMERIC(19, 0) REFERENCES project (id)
    )

    CREATE TABLE roles_in_project
    (
        project_id      NUMERIC(19, 0) REFERENCES project (id),
        project_role_id NUMERIC(19, 0) REFERENCES project_role (id)
    )

    CREATE TABLE technology_group
    (
        id          NUMERIC(19, 0) PRIMARY KEY,
        name        VARCHAR(32)  NOT NULL UNIQUE,
        description VARCHAR(256)
    )
    CREATE SEQUENCE technology_group_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE technology_group_user
    (
        user_id             NUMERIC(19, 0) REFERENCES patronative.user (id),
        technology_group_id NUMERIC(19, 0) REFERENCES technology_group (id)
    )

    CREATE TABLE technology
    (
        id   NUMERIC(19, 0) PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    )
    CREATE SEQUENCE technology_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE

    CREATE TABLE group_technology
    (
        technology_group_id NUMERIC(19, 0) REFERENCES technology_group (id),
        technology_id       NUMERIC(19, 0) REFERENCES technology (id)
    );