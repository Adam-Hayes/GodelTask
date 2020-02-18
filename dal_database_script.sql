CREATE DATABASE cinema;
    
	
	Create table Director(
	id serial primary key,
	first_name character varying(255),
	last_name character varying(255),
	birth_date date	
	);
	
	Create table Film(
	id serial primary key,
	director_id integer references Director(id), 
	name character varying(255),
	release_date date,
	genre character varying(255)
	)
