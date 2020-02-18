-- Database: cinema

-- DROP DATABASE cinema;

CREATE DATABASE cinema
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	Create table Director(
	id serial primary key,
	first_name character varying,
	last_name character varying,
	birth_date date	
	)
	
	Create table Film(
	id serial primary key,
	director_id integer references Director(id), 
	name character varying,
	release_date date,
	genre character varying
	)