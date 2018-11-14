alter table MYCABINET_ATTACHMENT rename column request_id to request_id__UNUSED ;
drop index IDX_MYCABINET_ATTACHMENT_REQUEST ;
alter table MYCABINET_ATTACHMENT drop constraint FK_MYCABINET_ATTACHMENT_REQUEST ;
