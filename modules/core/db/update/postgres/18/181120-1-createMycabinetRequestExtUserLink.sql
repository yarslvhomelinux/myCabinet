create table MYCABINET_REQUEST_EXT_USER_LINK (
    REQUEST_ID uuid,
    EXT_USER_ID uuid,
    primary key (REQUEST_ID, EXT_USER_ID)
);
