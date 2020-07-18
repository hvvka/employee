insert into address(address_type)
values('HOME'),('BUSINESS'),('BILLING'),('SHIPPING');

insert into employee(first_name,last_name,age,pesel,role,employee_above_id)
values
('Lukasz','Stanislawowski',41,'79072512345','DIRECTOR',null),
('Zbigniew','Stonoga',45,'74101712345','CEO',1),
('Wojciech','Cejrowski',56,'64062712345','MANAGER',2);

insert into employee_address(employee_id,address_id)
values
(1,1),(1,2),
(2,3),
(3,4);
