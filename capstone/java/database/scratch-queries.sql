select * from users;

select * from pothole;

select * from report;

insert into pothole (house_number, street_name, city, state, zip, verified) values (123,'hole street', 'holeville', 'holehio', 42069, false);

insert into report (pothole_id, user_id) values (20000,77)