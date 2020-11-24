INSERT INTO roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles VALUES (2, 'ROLE_TEACHER');
INSERT INTO roles VALUES (3, 'ROLE_STUDENT');

INSERT INTO privileges VALUES (1,'ATTENDANCE_READ');
INSERT INTO privileges VALUES (2,'ATTENDANCE_WRITE');
INSERT INTO privileges VALUES (3,'EXAM_READ');
INSERT INTO privileges VALUES (4,'EXAM_WRITE');
INSERT INTO privileges VALUES (5,'LESSON_READ');
INSERT INTO privileges VALUES (6,'LESSON_WRITE');
INSERT INTO privileges VALUES (7,'GRADE_READ');
INSERT INTO privileges VALUES (8,'GRADE_WRITE');
INSERT INTO privileges VALUES (9,'ROLE_ASSIGNMENT');

-- Role Admin
INSERT INTO roles_privileges VALUES (1, 1, 1);
INSERT INTO roles_privileges VALUES (2, 1, 2);
INSERT INTO roles_privileges VALUES (3, 1, 3);
INSERT INTO roles_privileges VALUES (4, 1, 4);
INSERT INTO roles_privileges VALUES (5, 1, 5);
INSERT INTO roles_privileges VALUES (6, 1, 6);
INSERT INTO roles_privileges VALUES (7, 1, 7);
INSERT INTO roles_privileges VALUES (8, 1, 8);
INSERT INTO roles_privileges VALUES (9, 1, 9);

-- Role Teacher
INSERT INTO roles_privileges VALUES (10, 2, 1);
INSERT INTO roles_privileges VALUES (11, 2, 2);
INSERT INTO roles_privileges VALUES (12, 2, 3);
INSERT INTO roles_privileges VALUES (13, 2, 4);
INSERT INTO roles_privileges VALUES (14, 2, 5);
INSERT INTO roles_privileges VALUES (15, 2, 6);
INSERT INTO roles_privileges VALUES (16, 2, 7);
INSERT INTO roles_privileges VALUES (17, 2, 8);

-- Role Student
INSERT INTO roles_privileges VALUES (18, 3, 1);
INSERT INTO roles_privileges VALUES (19, 3, 3);
INSERT INTO roles_privileges VALUES (20, 3, 5);
INSERT INTO roles_privileges VALUES (21, 3, 7);
