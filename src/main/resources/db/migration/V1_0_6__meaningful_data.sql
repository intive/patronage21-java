SET search_path TO patronative;

DELETE FROM group_technology;
DELETE FROM technology;

INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Java');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'JavaScript');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Selenium');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'React');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Spring Boot');
INSERT INTO technology(id, name) VALUES(NEXTVAL('technology_seq'), 'Kotlin');

DELETE FROM technology_group_user;
DELETE FROM technology_group;

INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'Grupa Java', 'Java');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Java'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Spring Boot'));

INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'Grupa Java', 'JavaScript');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'JavaScript'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'React'));

INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'Grupa QA', 'QA');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Selenium'));

INSERT INTO technology_group(id, description, name) VALUES(NEXTVAL('technology_group_seq'), 'Grupa Android', 'Mobile (Android)');
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Kotlin'));
INSERT INTO group_technology VALUES (CURRVAL('technology_group_seq'), (SELECT id FROM technology WHERE name = 'Java'));

INSERT INTO project_role(id, name) VALUES(NEXTVAL('project_role_seq'), 'Tester');

DELETE FROM project_user;
DELETE FROM roles_in_project;
DELETE FROM project;

INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'Ametyst', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Scrum Master'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Tester'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));

INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'StarGuard', 'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Tester'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Product Owner'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));

INSERT INTO project(id, name, description, year) VALUES(NEXTVAL('project_seq'), 'SinkIn', '"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.', 2021);
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Scrum Master'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Product Owner'));
INSERT INTO roles_in_project VALUES (CURRVAL('project_seq'), (SELECT id FROM project_role WHERE name = 'Developer'));

DELETE FROM profile;
DELETE FROM patronative.user;

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'janio123', 'Jan', 'Kowalski', 'jan.kowalski@gmail.com', '951852753', 'www.github.com/JanKowalski', (SELECT id FROM role WHERE name = 'LEADER'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'JavaScript'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'Lightforgee', 'Luiza', 'Tomaszewska', 'LuizaTomaszewska@teleworm.us', '535296517', 'www.github.com/ltomaszewska', (SELECT id FROM role WHERE name = 'LEADER'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Mobile (Android)'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Product Owner'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'znowak', 'Zdzisława', 'Nowakowska', 'znowakowska@gmail.com', '886605061', 'http://www.github.com/znowak', (SELECT id FROM role WHERE name = 'LEADER'), (SELECT id FROM status WHERE name = 'INACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Mobile (Android)'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Scrum Master'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Scrum Master'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Scrum Master'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'anowak', 'Anna', 'Nowak', 'anna.nowak@gmail.com', '668461783', 'https://www.github.com/AnnaNowak', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'JavaScript'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'cmarek', 'Marek', 'Ćma', 'cmarek@gmail.com', '612345773', 'https://www.github.com/cmarek', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'JavaScript'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Mobile (Android)'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'tomprob', 'Tomasz', 'Problem', 'tomProb123@interia.com', '546745773', 'http://www.github.com/tompro', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Mobile (Android)'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Tester'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'czmaj', 'Czesława', 'Majewska', 'CzeslawaMajewska@armyspy.com', '666745773', 'http://www.github.com/czmaj', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'Ametyst'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Mobile (Android)'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'Ametyst'), (SELECT id FROM project_role WHERE name = 'Tester'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'serwerus', 'Sewerus', 'Sznejk', 'serwerus@gmail.com', '612345773', 'https://www.github.com/serwerus', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'INACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'gdudek', 'Genowefa', 'Dudek', 'gdudek@gmail.com', '987456321', 'https://www.github.com/gdudek', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'waletyna', 'Walentyna', 'Kwiatkowska', 'waletyna@gmail.com', '357456852', 'https://www.github.com/waletyna', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'INACTIVE'), (SELECT id FROM gender WHERE name = 'FEMALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'JavaScript'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'jsina', 'Arkadiusz', 'Jasiński', 'jasina@gmail.com', '354855952', 'https://www.github.com/jasina', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'INACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'SinkIn'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'JavaScript'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'SinkIn'), (SELECT id FROM project_role WHERE name = 'Developer'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'emeryt', 'Emeryk', 'Tarnowski', 'EmeryT@gmail.com', '985236471', 'https://www.github.com/emeryt', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'MALE'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Tester'), CURRVAL('user_seq'));

INSERT INTO patronative.user(id, login, first_name, last_name, email, phone_number, github_url, role_id, status_id, gender_id) VALUES (NEXTVAL('user_seq'), 'pawlowska1', 'Judyta', 'Pawłowska', 'jpawlowska@interia.com', '654123698', 'https://www.github.com/pawlowskaJusdyta', (SELECT id FROM role WHERE name = 'CANDIDATE'), (SELECT id FROM status WHERE name = 'ACTIVE'), (SELECT id FROM gender WHERE name = 'Female'));
INSERT INTO profile(user_id, bio) VALUES (CURRVAL('user_seq'), 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus');
INSERT INTO project_user VALUES (CURRVAL('user_seq'), (SELECT id FROM project WHERE name = 'StarGuard'));
INSERT INTO technology_group_user VALUES (CURRVAL('user_seq'), (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO roles_in_project VALUES ((SELECT id FROM project WHERE name = 'StarGuard'), (SELECT id FROM project_role WHERE name = 'Tester'), CURRVAL('user_seq'));