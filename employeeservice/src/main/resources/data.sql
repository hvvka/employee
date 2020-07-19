insert into address(address_type)
values('HOME'),('BUSINESS'),('BILLING'),('SHIPPING');

insert into employee(first_name,last_name,age,pesel,role,supervisor_id)
values
('Lukasz','Stanislawowski',41,'79072512345','DIRECTOR',null),
('Zbigniew','Stonoga',45,'74101712345','DIRECTOR',1),
('Wojciech','Cejrowski',56,'64062712345','MANAGER',2),
('Karol','Wojtyla',85,'20051812345','DIRECTOR',null),
('Grzegorz','Braun',53,'67031112345','DIRECTOR',null),
('Adam','Ondra',27,'93020512345','CEO',3),
('Friedrich','Hayek',93,'99050812345','EMPLOYEE',3),
('Ludwig','Mises',92,'81082912345','EMPLOYEE',3),
('Milton','Friedman',94,'12073112345','EMPLOYEE',3),
('David','Hasselhoff',68,'52071712345','EMPLOYEE',3),
('Arnold','Schwarzenegger',72,'47073012345','DIRECTOR',6);

insert into employee_address(employee_id,address_id)
values
(1,1),(1,2),
(2,3),
(3,4),
(4,1),(4,4),
(5,3);
