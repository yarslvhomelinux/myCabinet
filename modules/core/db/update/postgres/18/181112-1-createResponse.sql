create table MYCABINET_RESPONSE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRICE integer,
    DELIVERY_PRICE integer,
    MANUFACTURER_COMMENT text,
    IS_PRICE_SATISFIED text,
    CONTACT text,
    CUSTOMER_COMMENT text,
    CLOSE_COMMENT text,
    --
    primary key (ID)
);
