create type actor_role as enum ('USER', 'PLUGIN_SUPPLIER');

create table image
(
    id       uuid primary key,
    location varchar(256) not null
);

create table medium
(
    id                uuid primary key,
    email             varchar(320) null unique,
    password_hash     varchar(60) not null,
    is_active         boolean     not null,
    created_timestamp timestamp   not null
);

create table plugin
(
    id          uuid primary key,
    name        varchar(64) not null,
    script_path varchar(320) null unique
);

create table medium_to_plugin
(
    user_id    uuid references medium,
    tag_id     uuid references plugin on delete cascade,
    primary key (subject_id, tag_id)
);

create table plugin_supplier
(
    id                uuid primary key,
    email             varchar(320) null unique,
    password_hash     varchar(60) not null,
    is_active         boolean     not null,
    created_timestamp timestamp   not null
);

create table plugin_supplier_to_plugin
(
    user_id    uuid references medium,
    tag_id     uuid references plugin on delete cascade,
    primary key (subject_id, tag_id)
);