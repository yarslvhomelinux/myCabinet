alter table MYCABINET_REQUEST_EXT_USER_LINK add constraint FK_REQEXTUSE_REQUEST foreign key (REQUEST_ID) references MYCABINET_REQUEST(ID);
alter table MYCABINET_REQUEST_EXT_USER_LINK add constraint FK_REQEXTUSE_EXT_USER foreign key (EXT_USER_ID) references SEC_USER(ID);
