create table MYCABINET_ATTACHMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ATTACHMENT_ID uuid,
    STATE varchar(50),
    REQUEST_ID uuid,
    --
    primary key (ID)
);
