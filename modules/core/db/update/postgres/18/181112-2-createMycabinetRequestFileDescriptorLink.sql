alter table MYCABINET_REQUEST_FILE_DESCRIPTOR_LINK add constraint FK_REQFILDES_REQUEST foreign key (REQUEST_ID) references MYCABINET_REQUEST(ID);
alter table MYCABINET_REQUEST_FILE_DESCRIPTOR_LINK add constraint FK_REQFILDES_FILE_DESCRIPTOR foreign key (FILE_DESCRIPTOR_ID) references SYS_FILE(ID);
