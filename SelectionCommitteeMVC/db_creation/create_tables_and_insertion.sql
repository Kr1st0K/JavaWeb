use selection_committee;

create table universities
(
	id int not null auto_increment,
	name varchar(200) not null,
	town varchar(50) not null,
	primary key(id)
);

create table subjects
(
	id int not null auto_increment,
	name varchar(25) not null,
	primary key(id)
);

create table users
(
	id int not null auto_increment,
	`role` varchar(9) not null,
	login varchar(25) not null,
	encrypted_password varchar(100) not null, 
	blocked bool default false,
	primary key(id)
);

create table applicants
(
	user_id int not null,
	name varchar(50) not null,
	age int not null,
	email varchar(50) not null,
	phone_number varchar(15) not null,
	town varchar(50) not null,
	region varchar(50) not null,
	school_name varchar(50) not null,
	primary key(user_id),
	foreign key (user_id) references users (id)
);

create table faculties
(
	id int not null auto_increment,
	name varchar(100) not null,
	all_places_count int not null,
	budget_places_count int not null,
	university_id int not null,
	primary key (id),
	foreign key (university_id) references universities(id)
);

create table faculties_coefficients
(
	faculty_id int not null,
	subject_id int not null,
	coefficient decimal(3,2) not null,
	foreign key (faculty_id) references faculties(id),
	foreign key (subject_id) references subjects(id)
);

create table marks
(
	user_id int not null,
	subject_id int not null, 
	`type` varchar(11) not null,
	mark int not null,
	foreign key (user_id) references users(id),
	foreign key (subject_id) references subjects(id)
);

create table applicants_assigned
(	
	user_id int not null,
	faculty_id int not null, 
	priority int not null,
	foreign key (user_id) references users(id),
	foreign key (faculty_id) references faculties(id)
);

create table applicants_enroll_results
(	
	user_id int not null,
	faculty_id int not null, 
	form varchar(8) not null,
	enrolled bool not null,
	foreign key (user_id) references users(id),
	foreign key (faculty_id) references faculties(id)
);

drop table applicants_enroll_results;

insert into subjects(name) values
("Algebra"), ("Geometry"), ("Maths"), ("Ukrainian History"), ("World History"),
("Ukrainian"), ("English"), ("German"), ("French"), ("Spanish"), ("Italian"),
("Polish"), ("Information Technology"), ("Physical Education"),
("Law"), ("Biology"), ("Chemistry"), ("Astronomy"), ("Physics"), 
("Ukrainian Literature"), ("World Literature"), ("Geography"),
("Music"), ("Art"), ("Rhetoric"), ("Health Basics"), ("Art Culture"),
("Applied Art"), ("Economic"), ("National Defence");

insert into universities(name, town) values
("Taras Shevchenko National University of Kyiv", "Kyiv"),
("National Technical University of Ukraine \"Igor Sikorsky Kyiv Polytechnic Institute\"", "Kyiv"),
("Ivan Franko National University of Lviv", "Lviv"),
("V. N. Karazin Kharkiv National University", "Kharkiv"),
("Odesa I. I. Mechnykov National University", "Odesa"),
("National University of Kyiv-Mohyla Academy", "Kyiv"),
("Kyiv National Economic University", "Kyiv");