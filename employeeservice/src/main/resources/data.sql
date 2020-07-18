insert into address(address_type)
values('HOME'),('BUSINESS'),('BILLING'),('SHIPPING');

insert into employee(first_name,last_name,age,pesel,role)
values('Lukasz','Stanislawowski',41,'79072512345','DIRECTOR');

insert into employee_address(employee_id,address_id)
values(1,1),(1,2);
