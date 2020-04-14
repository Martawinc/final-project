create table color
(
	id serial not null
		constraint color_pkey
			primary key,
	color_name varchar(100) not null
);

alter table color owner to tee_shirt_admin;

create table blank_shirt
(
	id varchar(20) not null
		constraint blank_shirt_pkey
			primary key,
	size varchar(10) not null,
	color_id bigint not null
		constraint blank_shirt_color_id_fk
			references color
				on update cascade on delete cascade,
	quantity integer not null,
	price double precision not null
);

alter table blank_shirt owner to tee_shirt_admin;

create table design_shirt
(
	id serial not null
		constraint design_shirt_pkey
			primary key,
	blank_shirt_id varchar(20) not null
		constraint design_shirt_blank_shirt_id_fk
			references blank_shirt,
	text varchar(1000),
	image_link varchar(1000),
	total_price real default 0 not null,
	creation_date date default '2020-01-01'::date not null,
	is_deleted boolean default false not null
);

alter table design_shirt owner to tee_shirt_admin;

create unique index color_color_name_uindex
	on color (color_name);

create table role
(
	id bigserial not null
		constraint role_pk
			primary key,
	role_name varchar not null
);

alter table role owner to tee_shirt_admin;

create table m_user
(
	id serial not null
		constraint m_user_pkey
			primary key,
	login varchar(100) not null,
	password varchar(1000) not null,
	full_name varchar(100) not null,
	city varchar(100) not null,
	street varchar(100) not null,
	zip bigint not null,
	phone_number varchar(100) not null,
	mail varchar(100) not null,
	birth_date date not null,
	role_id bigint not null
		constraint m_user_role_id_fk
			references role
);

alter table m_user owner to tee_shirt_admin;

create unique index m_user_login_uindex
	on m_user (login);

create table m_order
(
	id serial not null
		constraint order_pkey
			primary key,
	city varchar(100) not null,
	street varchar(100) not null,
	zip varchar not null,
	card_number varchar not null,
	card_expiration varchar not null,
	cardcvv varchar not null,
	placed_at date not null,
	user_id bigint not null
		constraint order_m_user_id_fk
			references m_user
);

alter table m_order owner to tee_shirt_admin;

create table order_design_shirt
(
	order_id bigint not null
		constraint order_design_shirt_order_id_fk
			references m_order
				on update cascade on delete cascade,
	design_shirt_id bigint not null
		constraint order_design_shirt_design_shirt_id_fk
			references design_shirt
);

alter table order_design_shirt owner to tee_shirt_admin;

create unique index role_id_uindex
	on role (id);

create unique index role_role_name_uindex
	on role (role_name);

