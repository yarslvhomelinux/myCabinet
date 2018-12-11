alter table MYCABINET_IMAGE add constraint FK_MYCABINET_IMAGE_FILE foreign key (FILE_ID) references SYS_FILE(ID);
create index IDX_MYCABINET_IMAGE_FILE on MYCABINET_IMAGE (FILE_ID);
