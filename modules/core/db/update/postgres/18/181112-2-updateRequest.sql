alter table MYCABINET_REQUEST rename column product_category to product_category__UNUSED ;
alter table MYCABINET_REQUEST add column PRODUCT_CATEGORY_ID uuid ;
alter table MYCABINET_REQUEST add column STATUS varchar(50) ;
