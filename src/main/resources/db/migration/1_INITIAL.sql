create table roles
(
    id               serial
        constraint roles_pk
            primary key,
    role_name        varchar,
    role_description text
);

create unique index roles_role_name_uindex
    on roles (role_name);

create table rights
(
    id                serial
        constraint rights_pk
            primary key,
    right_name        varchar,
    right_description text
);

create unique index rights_right_name_uindex
    on rights (right_name);

create table roles_rights
(
    role_id  int
        constraint roles_rights_roles_id_fk
            references roles,
    right_id int
        constraint roles_rights_rights_id_fk
            references rights
);

alter table roles_rights add primary key (role_id, right_id);

create table users
(
    id          serial
        constraint users_pk
            primary key,
    external_id varchar,
    username    varchar,
    user_role   int
        constraint users_roles_id_fk
            references roles
);

create unique index users_external_id_uindex
    on users (external_id);

create unique index users_username_uindex
    on users (username);

