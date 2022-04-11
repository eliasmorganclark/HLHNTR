BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP TABLE IF EXISTS pothole;
DROP SEQUENCE IF EXISTS seq_pothole_id;
DROP TABLE IF EXISTS report;
DROP SEQUENCE IF EXISTS seq_report_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_report_id
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 20000
    CACHE 1;

CREATE SEQUENCE seq_pothole_id
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 10000
    CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE pothole (
	pothole_id int DEFAULT nextval('seq_pothole_id'::regclass) NOT NULL,
	house_number int NOT NULL,
	street_name varchar (100) NOT NULL,
	city varchar (50) NOT NULL,
	state varchar (50) NOT NULL,
	zip int NOT NULL,
	verified BOOLEAN NOT NULL,
	repair_status varchar (50),
	severity varchar (50),
	CONSTRAINT PK_pothole PRIMARY KEY (pothole_id)
);

CREATE TABLE report (
	report_id int DEFAULT nextval('seq_report_id'::regclass) NOT NULL,
	pothole_id int NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT PK_report PRIMARY KEY (report_id),
    CONSTRAINT FK_pothole_id FOREIGN KEY (pothole_id) REFERENCES pothole(pothole_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);


INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


COMMIT TRANSACTION;
