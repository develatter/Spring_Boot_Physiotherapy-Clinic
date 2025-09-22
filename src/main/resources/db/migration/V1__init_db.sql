create extension if not exists citext;
-- drop database

CREATE TABLE IF NOT EXISTS account
(
    id            uuid PRIMARY KEY,
    email         citext      NOT NULL UNIQUE,
    password_hash text        NOT NULL,
    enabled       boolean     NOT NULL DEFAULT true,
    created_at    timestamptz NOT NULL DEFAULT now(),
    updated_at    timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS role
(
    code        varchar(32) PRIMARY KEY,
    description text NOT NULL
);

CREATE TABLE IF NOT EXISTS account_role
(
    account_id uuid REFERENCES account (id) ON DELETE CASCADE,
    role_code  varchar(32) REFERENCES role (code) ON DELETE CASCADE,
    PRIMARY KEY (account_id, role_code)
);

INSERT INTO role (code, description)
VALUES ('PATIENT', 'Paciente'),
       ('THERAPIST', 'Fisioterapeuta'),
       ('ADMIN', 'Administrador')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS clinic_closure
(
    id         uuid PRIMARY KEY,
    start_time timestamptz NOT NULL,
    end_time   timestamptz NOT NULL,
    reason     text        NOT NULL
);

CREATE TABLE IF NOT EXISTS therapist
(
    id             uuid PRIMARY KEY,
    account_id     uuid UNIQUE REFERENCES account (id) ON DELETE CASCADE,
    license_number varchar(100) UNIQUE NOT NULL,
    first_name     varchar(255) NOT NULL,
    last_name      varchar(255) NOT NULL,
    active         boolean      NOT NULL default true
);

CREATE TABLE IF NOT EXISTS patient
(
    id           uuid PRIMARY KEY,
    account_id   uuid UNIQUE REFERENCES account (id) ON DELETE CASCADE,
    first_name   varchar(255) NOT NULL,
    last_name    varchar(255) NOT NULL,
    birth_date   date         NOT NULL,
    phone_number varchar(20),
    address      citext
);

CREATE TABLE IF NOT EXISTS service
(
    id                     uuid PRIMARY KEY,
    name                   varchar(255) NOT NULL,
    duration_min           integer      NOT NULL,
    buffer_time_before_min integer      NOT NULL,
    buffer_time_after_min  integer      NOT NULL,
    price_cents            integer      NOT NULL,
    description            text         NOT NULL,
    active                 boolean      NOT NULL DEFAULT true
);


CREATE TABLE IF NOT EXISTS therapist_service
(
    therapist_id uuid REFERENCES therapist (id) ON DELETE CASCADE,
    service_id   uuid REFERENCES service (id) ON DELETE CASCADE,
    PRIMARY KEY (therapist_id, service_id)
);

CREATE TABLE IF NOT EXISTS appointment_status
(
    code        varchar(32) PRIMARY KEY,
    description text NOT NULL
);

INSERT INTO appointment_status (code, description)
VALUES ('SCHEDULED', 'Agendado'),
       ('COMPLETED', 'Completado'),
       ('CANCELED', 'Cancelado'),
       ('NO_SHOW', 'No asistió');

CREATE TABLE IF NOT EXISTS appointment
(
    id                    uuid PRIMARY KEY,
    patient_id            uuid REFERENCES patient (id) ON DELETE CASCADE,
    therapist_id          uuid REFERENCES therapist (id) ON DELETE CASCADE,
    service_id            uuid REFERENCES service (id) ON DELETE CASCADE,
    created_by_account_id uuid        REFERENCES account (id) ON DELETE SET NULL,
    start_time            timestamptz NOT NULL,
    end_time              timestamptz NOT NULL,
    status_code           varchar(32) REFERENCES appointment_status (code) ON DELETE SET NULL,
    created_at            timestamptz NOT NULL DEFAULT now(),
    updated_at            timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE appointment
    ADD CONSTRAINT chk_appointment_times
        CHECK (end_time > start_time);

ALTER TABLE service
    ADD CONSTRAINT chk_service_duration
        CHECK (duration_min > 0);

CREATE INDEX idx_appointment_start_time ON appointment (start_time);
CREATE INDEX idx_appointment_therapist_status ON appointment (therapist_id, status_code);
CREATE INDEX idx_appointment_patient ON appointment (patient_id);

CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER trigger_update_updated_at_account
    BEFORE UPDATE
    ON account
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_update_updated_at_appointment
    BEFORE UPDATE
    ON appointment
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
