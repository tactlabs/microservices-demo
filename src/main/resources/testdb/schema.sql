drop table T_ACCOUNT if exists;

create table T_ACCOUNT (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;



drop table T_WEAPON if exists;

create table T_WEAPON (
	ID bigint identity primary key, 
	WEAPONID bigint,
    NAME varchar(50) not null, 
    BALANCE int, 
    IMAGE varchar(200),
    DESCRIPTION varchar(400)    
);
                        
ALTER TABLE T_WEAPON ALTER COLUMN BALANCE SET DEFAULT 45;
