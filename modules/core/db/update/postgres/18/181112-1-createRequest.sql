create table MYCABINET_REQUEST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_CATEGORY varchar(255),
    PRODUCT_TYPE text,
    BRAND varchar(1000),
    PRODUCT_DESCRIPTION text,
    PRODUCT_VOLUME text,
    DELIVERY_TIME integer,
    DELIVERY_REGION text,
    CONTACT_PERSON varchar(500),
    CONTACT_PERSON_PHONE varchar(500),
    --
    primary key (ID)
);
