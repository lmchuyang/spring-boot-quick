
select ID,  SITE_NAME , PROVINCE,  ADDRESS,   LAT , LNG ,  concat_ws(',',LAT,Lng) as geo  from  tms_site  
