CREATE TYPE user_type AS ENUM (‘student’, ‘teacher’, ‘admin’, ‘group_head’);

CREATE TABLE Groups (
  id bigserial PRIMARY KEY,
  name varchar(50) NOT NULL );

CREATE TABLE Users (
  login varchar(50) primary key,
  name varchar(50) NOT NULL, 
  group_id bigserial REFERENCES groups(id),
  user_type user_type NOT NULL, 
  password_salt varchar(50) NOT NULL, 
  password_hash varchar(50) NOT NULL );

ALTER TABLE Groups ADD grouphead_login varchar(50);
ALTER TABLE Groups ADD CONSTRAINT fk_grouphead_login FOREIGN KEY (grouphead_login) REFERENCES Users(login);

CREATE TABLE Lessons (
  id bigserial PRIMARY KEY,
  date date NOT NULL,
  time time NOT NULL,
  homework text,
  discipline varchar(30) NOT NULL,
  group_id bigserial NOT NULL REFERENCES Groups(id),
  description text,
  teacher_login varchar(50) NOT NULL REFERENCES Users(login) );

CREATE TABLE Presents (
  lesson_id bigserial REFERENCES Lessons(id),
  login varchar(50) REFERENCES Users(login),
  PRIMARY KEY(lesson_id, login) );

