alter table MYCABINET_ATTACHMENT add constraint FK_MYCABINET_ATTACHMENT_ATTACHMENT foreign key (ATTACHMENT_ID) references SYS_FILE(ID);
alter table MYCABINET_ATTACHMENT add constraint FK_MYCABINET_ATTACHMENT_REQUEST foreign key (REQUEST_ID) references MYCABINET_REQUEST(ID);
create index IDX_MYCABINET_ATTACHMENT_ATTACHMENT on MYCABINET_ATTACHMENT (ATTACHMENT_ID);
create index IDX_MYCABINET_ATTACHMENT_REQUEST on MYCABINET_ATTACHMENT (REQUEST_ID);
