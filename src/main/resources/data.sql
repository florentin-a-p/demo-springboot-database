CREATE TABLE springboot_country_tbl (
   id int primary key,
   countryName varchar(255),
   countryCapital varchar(255)
);

insert into
springboot_country_tbl (id, countryname, countrycapital)
values
(1,'Indonesia','Jakarta'),
(2,'Malaysia','Kuala Lumpur'),
(3,'Thailand','Bangkok');
