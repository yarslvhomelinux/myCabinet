alter table MYCABINET_REQUEST_USER_LINK add constraint FK_REQUSE_REQUEST foreign key (REQUEST_ID) references MYCABINET_REQUEST(ID);
alter table MYCABINET_REQUEST_USER_LINK add constraint FK_REQUSE_USER foreign key (USER_ID) references SEC_USER(ID);
