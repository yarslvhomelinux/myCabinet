-- begin SEC_USER
alter table SEC_USER add column BIRTHDAY date ^
alter table SEC_USER add column PHONE_NUMBER varchar(500) ^
alter table SEC_USER add column ORGANIZATION_NAME varchar(600) ^
alter table SEC_USER add column ACTIVITY_TYPE varchar(1000) ^
alter table SEC_USER add column LEGAL_ADDRESS varchar(600) ^
alter table SEC_USER add column ACTUAL_ADDRESS varchar(600) ^
alter table SEC_USER add column BUSINESS_CATEGORY varchar(50) ^
alter table SEC_USER add column MANUFACTURER_LEGAL_ADRESS varchar(255) ^
alter table SEC_USER add column FIRM_AGE integer ^
alter table SEC_USER add column PRODUCTION_VOLUME varchar(600) ^
alter table SEC_USER add column USER_TYPE varchar(50) ^
alter table SEC_USER add column GOODS_CATEGORY varchar(600) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'mycabinet$ExtUser' where DTYPE is null ^
-- end SEC_USER
-- begin MYCABINET_REQUEST
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
    PRODUCT_CATEGORY_ID uuid,
    CREATOR_ID uuid,
    REQUEST_NUMBER varchar(255),
    PRODUCT_TYPE text,
    BRAND varchar(1000),
    PRODUCT_DESCRIPTION text,
    PRODUCT_VOLUME text,
    DELIVERY_TIME integer,
    DELIVERY_REGION text,
    CONTACT_PERSON varchar(500),
    CONTACT_PERSON_PHONE varchar(500),
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end MYCABINET_REQUEST
-- begin MYCABINET_PRODUCT_CATEGORY
create table MYCABINET_PRODUCT_CATEGORY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(500),
    CODE varchar(255),
    --
    primary key (ID)
)^
-- end MYCABINET_PRODUCT_CATEGORY
-- begin MYCABINET_RESPONSE
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
    CREATOR_ID uuid,
    DELIVERY_PRICE integer,
    MANUFACTURER_COMMENT text,
    IS_PRICE_SATISFIED text,
    CONTACT text,
    CUSTOMER_COMMENT text,
    CLOSE_COMMENT text,
    REQUEST_ID uuid,
    STATE varchar(50),
    MANUFACTURER_INFO text,
    --
    primary key (ID)
)^
-- end MYCABINET_RESPONSE

-- begin MYCABINET_RESPONSE_FILE_DESCRIPTOR_LINK
create table MYCABINET_RESPONSE_FILE_DESCRIPTOR_LINK (
    RESPONSE_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (RESPONSE_ID, FILE_DESCRIPTOR_ID)
)^
-- end MYCABINET_RESPONSE_FILE_DESCRIPTOR_LINK
-- begin MYCABINET_ATTACHMENT
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
)^
-- end MYCABINET_ATTACHMENT

-- begin MYCABINET_REQUEST_EXT_USER_LINK
create table MYCABINET_REQUEST_EXT_USER_LINK (
    REQUEST_ID uuid,
    EXT_USER_ID uuid,
    primary key (REQUEST_ID, EXT_USER_ID)
)^
-- end MYCABINET_REQUEST_EXT_USER_LINK
-- begin MYCABINET_USER_COMMENT
create table MYCABINET_USER_COMMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COMMENT_ text,
    --
    primary key (ID)
)^
-- end MYCABINET_USER_COMMENT
-- begin MYCABINET_IMAGE
create table MYCABINET_IMAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FILE_ID uuid,
    --
    primary key (ID)
)^
-- end MYCABINET_IMAGE
