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
INSERT INTO privileges VALUES (9,'ASSIGNMENT_ROLE');
INSERT INTO privileges VALUES (10,'SWAGGER_ACCESS');

-- Role Admin
INSERT INTO roles_privileges VALUES (1, 1);
INSERT INTO roles_privileges VALUES (1, 2);
INSERT INTO roles_privileges VALUES (1, 3);
INSERT INTO roles_privileges VALUES (1, 4);
INSERT INTO roles_privileges VALUES (1, 5);
INSERT INTO roles_privileges VALUES (1, 6);
INSERT INTO roles_privileges VALUES (1, 7);
INSERT INTO roles_privileges VALUES (1, 8);
INSERT INTO roles_privileges VALUES (1, 9);
INSERT INTO roles_privileges VALUES (1, 10);

-- Role Teacher
INSERT INTO roles_privileges VALUES (2, 1);
INSERT INTO roles_privileges VALUES (2, 2);
INSERT INTO roles_privileges VALUES (2, 3);
INSERT INTO roles_privileges VALUES (2, 4);
INSERT INTO roles_privileges VALUES (2, 5);
INSERT INTO roles_privileges VALUES (2, 6);
INSERT INTO roles_privileges VALUES (2, 7);
INSERT INTO roles_privileges VALUES (2, 8);

-- Role Student
INSERT INTO roles_privileges VALUES (3, 1);
INSERT INTO roles_privileges VALUES (3, 3);
INSERT INTO roles_privileges VALUES (3, 5);
INSERT INTO roles_privileges VALUES (3, 7);
