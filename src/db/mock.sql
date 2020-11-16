INSERT INTO Groups(id, name) VALUES(1, 'First Group');
INSERT INTO Groups(id, name) VALUES(2, 'Second Group');
INSERT INTO Groups(id, name) VALUES(3, 'Third Group');

-- 1 group
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘vasya092’, 'Ivaniuk V.O.', 1, ‘group_head’, ‘sdf323fs!F$@2f’, ‘dssdfds1124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘dominar3000’, 'Naluvayko R.I.', 1, ‘student’, ‘ssdfd23fsd@2f’, ‘d23rfw124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘wqa092’, 'Pokolyuk W.K.', 1, ‘student’, ‘dsfhsfdh4’, ‘sdfhsdfdsf124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘eeesya092’, 'Ivanko K.O.', 1, ‘student’, ‘sdf34tgergf’, ‘ds34tertd4’);

-- 2 group
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘sssya092’, 'Ivator V.O.', 2, ‘group_head’, ‘sdf323fs!ht5F$@2f’, ‘dssdfdffs1124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘dominat00’, 'Nalutol R.I.', 2, ‘student’, ‘ssdfd23fs4yd@2f’, ‘d2ff3rfw124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘wireas34’, 'Zelensky P.P.', 2, ‘student’, ‘dsfhsfgfdh4’, ‘sdfhsdsdfdsf124’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘va9rkw2’, 'Ilonov K.E.', 2, ‘student’, ‘sdsdfsertgf’, ‘dssdfdertd4’);

-- 3 group
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘fsf1467’, 'Josepe S.A.', 1, ‘group_head’, ‘123sacQW@asd’, ‘123456789’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘nsddw002’, 'Naluvayko R.I.', 1, ‘student’, ‘#$d123SA@dA2’, ‘sdf1123’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘port92’, 'Pokolyuk W.K.', 1, ‘student’, ‘dsfhsfdh4’, ‘asdwasd2000’);
INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES(‘kolya073’, 'Ivanko K.O.', 1, ‘student’, ‘sdf34tgergf’, ‘kolya073123’);

-- teachers
INSERT INTO Users(login, name, user_type, password_salt, password_hash) VALUES(‘upa1221’, 'Bandera S.A.',‘teacher’, ‘slava@asd’, ‘kwev3es’);
INSERT INTO Users(login, name, user_type,  password_salt, password_hash) VALUES(‘directoria1’, 'Petliura S.V.', ‘teacher’, ‘UNRsd8@asd’, ‘f;owejg89’);

-- admins
INSERT INTO Users(login, name, user_type, password_salt, password_hash) VALUES(‘julius44’, 'Julius Caesar', ‘admin’, ‘rome3000’, ‘ADAsdas!3!@!#asd’);
INSERT INTO Users(login, name, user_type, password_salt, password_hash) VALUES(‘napoleon123’, 'Napoleon Bonaparte', ‘admin’, ‘lovefrance’, ‘123SA@q231’);



-- lessons
INSERT INTO Lessons(id, date, time, homework, discipline, group_id, description, teacher_login) VALUES(1, date ‘2020-11-16’, time ‘08:30-10:00’, NULL, ‘Mathematics’, 1, NULL, ‘upa1221’);
INSERT INTO Lessons(id, date, time, homework, discipline, group_id, description, teacher_login) VALUES(2, date ‘2020-11-16’,  time ‘08:05-09:00’, NULL, ‘Databases’, 1, NULL, ‘upa1221’);
INSERT INTO Lessons(id, date, time, homework, discipline, group_id, description, teacher_login) VALUES(3, date ‘2020-11-16’,  time ‘09:05-10:00’, ‘lab5’, ‘OOP’, 2, NULL, ‘directoria1’);
INSERT INTO Lessons(id, date, time, homework, discipline, group_id, description, teacher_login) VALUES(4, date ‘2020-11-16’,  time ‘10:05-11:00’, ‘Test’, ‘English’, 2, NULL, ‘directoria1’);
INSERT INTO Lessons(id, date, time, homework, discipline, group_id, description, teacher_login) VALUES(5, date ‘2020-11-17’,  time ‘11:05-12:00’, NULL, ‘National defence’, 3, NULL, ‘upa1221’);

-- presents for 12 users
INSERT INTO Presents(lesson_id, login) VALUES(1, ‘dominar3000’);
INSERT INTO Presents(lesson_id, login) VALUES(1, ‘wqa092’);
INSERT INTO Presents(lesson_id, login) VALUES(1, ‘vasya092’);
INSERT INTO Presents(lesson_id, login) VALUES(1, ‘eeesya092’);
INSERT INTO Presents(lesson_id, login) VALUES(2, ‘dominar3000’);
INSERT INTO Presents(lesson_id, login) VALUES(2, ‘wqa092’);
INSERT INTO Presents(lesson_id, login) VALUES(2, ‘vasya092’);
INSERT INTO Presents(lesson_id, login) VALUES(2, ‘eeesya092’);
INSERT INTO Presents(lesson_id, login) VALUES(3, ‘sssya092’);
INSERT INTO Presents(lesson_id, login) VALUES(3, ‘dominat00’);
INSERT INTO Presents(lesson_id, login) VALUES(3, ‘va9rkw2’);
INSERT INTO Presents(lesson_id, login) VALUES(3, ‘wireas34’);
INSERT INTO Presents(lesson_id, login) VALUES(4, ‘wireas34’);
INSERT INTO Presents(lesson_id, login) VALUES(4, ‘dominat00’);
INSERT INTO Presents(lesson_id, login) VALUES(4, ‘va9rkw2’);
INSERT INTO Presents(lesson_id, login) VALUES(4, ‘sssya092’);
INSERT INTO Presents(lesson_id, login) VALUES(5, ‘port92’);
INSERT INTO Presents(lesson_id, login) VALUES(5, ‘kolya073’);
INSERT INTO Presents(lesson_id, login) VALUES(5, ‘fsf1467’);
INSERT INTO Presents(lesson_id, login) VALUES(5, ‘nsddw002’);

-- update group heads
UPDATE Groups set grouphead_login = ‘vasya092’ WHERE id = 1;
UPDATE Groups set grouphead_login = ‘sssya092’ WHERE id = 2;
UPDATE Groups set grouphead_login = ‘fsf1467’ WHERE id = 3;