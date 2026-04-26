DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS schedule_times;
DROP TABLE IF EXISTS times;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS schedules;

CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE times (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    time DATE NOT NULL
);

CREATE TABLE schedule_times (
    schedule_id BIGINT,
    time_id BIGINT,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    FOREIGN KEY (time_id) REFERENCES times(id),
    PRIMARY KEY (schedule_id, time_id)
);

CREATE TABLE doctors (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL,
speciality VARCHAR(30) NOT NULL,
schedule_id BIGINT,
FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

CREATE TABLE appointments (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
doctor_id BIGINT,
patient_name VARCHAR(30) NOT NULL,
schedule_time_id BIGINT,
FOREIGN KEY (doctor_id) REFERENCES doctors(id),
FOREIGN KEY (schedule_time_id) REFERENCES times(id) ON DELETE CASCADE
);

INSERT INTO schedules (id) VALUES (1);
INSERT INTO schedules (id) VALUES (2);
INSERT INTO schedules (id) VALUES (3);

INSERT INTO times (time) VALUES ('2026-04-25');
INSERT INTO times (time) VALUES ('2026-04-26');
INSERT INTO times (time) VALUES ('2026-04-27');
INSERT INTO times (time) VALUES ('2026-04-23');

INSERT INTO schedule_times (schedule_id, time_id) VALUES (1, 1), (1, 4);
INSERT INTO schedule_times (schedule_id, time_id) VALUES (2, 1), (2, 2), (2, 3);
INSERT INTO schedule_times (schedule_id, time_id) VALUES (3, 3);

INSERT INTO doctors (name, speciality, schedule_id) VALUES ('Ivan Ivanov', 'pediatrician', 1);
INSERT INTO doctors (name, speciality, schedule_id) VALUES ('Maksym Maksymenko', 'traumatologist', 2);
INSERT INTO doctors (name, speciality, schedule_id) VALUES ('Petro Petrov', 'surgeon', 3);

INSERT INTO appointments (doctor_id, patient_name, schedule_time_id) VALUES (1, 'Petro Petrov', 4);
INSERT INTO appointments (doctor_id, patient_name, schedule_time_id) VALUES (3, 'Ivan Ivanov', 3);

ALTER TABLE appointments
MODIFY schedule_time_id BIGINT NULL;

ALTER TABLE appointments
DROP FOREIGN KEY appointments_ibfk_1;  

ALTER TABLE appointments
ADD CONSTRAINT appointments_ibfk_1
FOREIGN KEY (schedule_time_id) REFERENCES times(id) ON DELETE SET NULL;

ALTER TABLE schedule_times
DROP FOREIGN KEY schedule_times_ibfk_2;

ALTER TABLE schedule_times
ADD CONSTRAINT schedule_times_ibfk_2
FOREIGN KEY (time_id) REFERENCES times(id) ON DELETE CASCADE;

ALTER TABLE doctors
DROP FOREIGN KEY doctors_ibfk_1;

ALTER TABLE doctors
ADD CONSTRAINT doctors_ibfk_1
FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE SET NULL;