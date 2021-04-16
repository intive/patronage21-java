SET search_path TO patronative;

INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Lorem');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Ipsum');
INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'grupa1', 'Java');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Lorem'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Ipsum'));
INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'grupa1', 'JavaScript');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Lorem'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Ipsum'));
INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'grupa1', 'QA');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Lorem'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Ipsum'));
INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'grupa1', 'Mobile (Android)');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Lorem'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Ipsum'));

INSERT INTO project_role(id, name) VALUES(NEXTVAL('project_role_seq'), 'Scrum Master');
INSERT INTO project_role(id, name) VALUES(NEXTVAL('project_role_seq'), 'Product Owner');
INSERT INTO project_role(id, name) VALUES(NEXTVAL('project_role_seq'), 'Developer');
INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'Projekt I', 'Lorem ipsum dolor sit amet', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Scrum Master'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Product Owner'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));
INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'Projekt II', 'Sed ut perspiciatis', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Scrum Master'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Product Owner'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));
INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'Projekt III', ' consectetur adipiscing elit', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Scrum Master'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Product Owner'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));

INSERT INTO consent(id, text, required) VALUES(NEXTVAL('consent_seq'), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', true);
INSERT INTO consent(id, text, required) VALUES(NEXTVAL('consent_seq'), 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. ', false);
INSERT INTO role(id, name) VALUES(NEXTVAL('role_seq'), 'LEADER');
INSERT INTO role(id, name) VALUES(NEXTVAL('role_seq'), 'CANDIDATE');
INSERT INTO status(id, name) VALUES(NEXTVAL('status_seq'), 'ACTIVE');
INSERT INTO status(id, name) VALUES(NEXTVAL('status_seq'), 'INACTIVE');
INSERT INTO gender(id, name) VALUES(NEXTVAL('gender_seq'), 'MALE');
INSERT INTO gender(id, name) VALUES(NEXTVAL('gender_seq'), 'FEMALE');

INSERT INTO patronative.user(id, login, user_name, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'JanKowalski', 'Jan Kowalski', 'Jan', 'Kowalski', 'jan.kowalski@gmail.com', '111111111', 'GitHub/JanKowalski', (SELECT id FROM role WHERE name = 'LEADER'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident');
INSERT INTO user_consent VALUES (CURRVAL('user_seq'), 1);
INSERT INTO user_consent VALUES (CURRVAL('user_seq'), 2);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 1);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 2);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 3);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 1);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 2);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 3);

INSERT INTO patronative.user(id, login, user_name, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'AnnaNowak', 'Anna Nowak', 'Anna', 'Nowak', 'anna.nowak@gmail.com', '222222222', 'GitHub/AnnaNowak', (SELECT id FROM role WHERE name = 'LEADER'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO user_consent VALUES (CURRVAL('user_seq'), 1);
INSERT INTO user_consent VALUES (CURRVAL('user_seq'), 2);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 1);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 2);
INSERT INTO project_user VALUES (CURRVAL('user_seq'), 3);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 2);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 3);
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), 4);

