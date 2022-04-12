select * from users;

select * from pothole;

select * from report;

insert into pothole (house_number, street_name, city, state, zip, verified) values (123,'hole street', 'holeville', 'holehio', 42069, false);

insert into report (pothole_id, user_id) values (20000,77);

select pothole_id from pothole where house_number = 123 AND street_name = 'hole street' AND city = 'holeville' AND state = 'holehio' AND zip = 42069;

INSERT INTO users (username,password_hash,role) VALUES ('anonymous','$$$$$$$$$$$$$$$$$$$$$$$$$$PROFIT$$$$$$$$$$$$$$$$$$$$$$$$$$$$$','ROLE_USER');