alter table patronative.roles_in_project add user_id NUMERIC(19, 0) REFERENCES patronative.user (id);