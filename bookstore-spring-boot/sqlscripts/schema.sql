create table category (
    id serial,
    name character varying(45),
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

create table book (
    id serial,
    title character varying(255),
    description character varying(255),
    price decimal,
    year integer,
    author character varying(45),
    isbn character varying(45),
    category_id integer UNIQUE, 
    CONSTRAINT book_pkey PRIMARY KEY (id)
);

create table address (
    id serial,
    street character varying(45),
    houseNumber character varying(45),
    boxNumber character varying(45),
	city character varying(45),
    postalCode character varying(45),
    country character varying(45),
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

create table account (
	id serial,
	firstName character varying(45),
    lastName character varying(45),
	dateOfBirth date,
    address_id integer REFERENCES address (id),
    emailAddress character varying(45),
	username character varying(45),
	password character varying(45),
    CONSTRAINT account_pkey PRIMARY KEY (id)
);

create table role (
	id serial,
	role character varying(45),
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

create table account_role (
	id serial,
	account_id integer REFERENCES account (id),
	role_id integer REFERENCES role (id),
    CONSTRAINT account_role_pkey PRIMARY KEY (id)
);

create table permission (
	id serial,
	permission character varying(45),
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);

create table role_permission (
	id serial,
	role_id integer REFERENCES role (id),
	permission_id integer REFERENCES permission (id),
    CONSTRAINT role_permission_pkey PRIMARY KEY (id)
);

create table order_details (
	id serial,
	book_id integer,
	quantity decimal,
	orders_id integer,
    CONSTRAINT order_details_pkey PRIMARY KEY (id)
);

create table orders (
	id serial,
	shipping_address_id integer null,
	billing_address_id integer null,
	account_id integer,
	is_in_cart boolean,
    CONSTRAINT order_pkey PRIMARY KEY (id)
);

insert into category values (1, 'Fiction'); 
insert into book values (1, 'Who framed Roger Rabbit', 'Who framed Roger Rabbit', 45, 1976, 'Krishna', '23456', 1); 