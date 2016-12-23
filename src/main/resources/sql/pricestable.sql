
CREATE TABLE IF NOT EXISTS `PricesTable` (
  `Code` varchar(10) NOT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`Code`)
) ;


INSERT INTO `PricesTable` (`Code`, `Name`, `Description`, `Price`) VALUES
('GC1020', 'BMW', 'BMW Car Black', '85438'),
('GC1021', 'Fiat', 'Fiat Green ', '29328'),
('GC1022', 'Jaguar', 'Jaguar Car Red', '98289'),
('GC1040', 'Mercedes', 'Mercedes Car Blue', '99990'),
('GC1060', 'BMW', 'BMW White', '75000'),
('GC3020', 'IBM Think Pad', 'IBM V1200', '10200'),
('GC3040', 'IPAD AIR', 'IPAD AIR V3.0', '38000'),
('GC3060', 'SOFTWARE', 'WINDOWS 10', '95000'),
('GC5020', 'IPHONE', 'IPHONE V11', '28000'),
('GC5040', 'IWATCH', 'IWATCH V12', '59900'),
('GC5060', 'IBOOK', 'IBOOK V15', '110000'),
('IN7020', 'Jaguar', 'Jaguar Car Blue', '4000'),
('IN7040', 'Mercedes', 'Mercedes Car White', '16000'),
('IN7060', 'BMW', 'BMW Red', '42000'),
('IN7080', 'SOFTWARE', 'WINDOWS x', '69000'),
('SL9020', 'IPAD AIR', 'IPAD AIR V2.0', '4999'),
('SL9040', 'BMW', 'BMW Black', '9999'),
('SL9060', 'IPHONE', 'IPHONE Golden V2.0', '14999'),
('SL9080', 'IPHONE', 'IPHONE Golden V2.2', '19999');

commit;

CREATE TABLE IF NOT EXISTS  "LOG" 
   (	"LOG_ID" INT NOT NULL, 
	"USER_CODE" VARCHAR(4000), 
	"LEVEL_CODE" VARCHAR(4000), 
	"ACTION" VARCHAR(4000), 
	"ACTION_OBJECT" VARCHAR(4000), 
	"SERVER" VARCHAR(4000), 
	"MODULE" VARCHAR(4000), 
	"FILE_LINE" VARCHAR(4000), 
	"MESSAGE" VARCHAR(4000), 
	"CREATED" TIMESTAMP, 
	PRIMARY KEY ("LOG_ID")
);


Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (699,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('28-OCT-16 08.51.22.466 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (704,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('28-OCT-16 09.07.53.028 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (726,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('28-OCT-16 11.19.57.105 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (731,'sriadmin_js','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('28-OCT-16 11.20.58.155 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (801,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('01-NOV-16 01.58.13.922 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (901,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('04-NOV-16 09.23.22.461 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (902,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Root element :ohfs-config',parsedatetime('04-NOV-16 09.24.00.598 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (903,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('04-NOV-16 09.24.02.075 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (906,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('04-NOV-16 09.24.02.854 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (907,'sriadmin_js','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('04-NOV-16 09.24.19.524 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (908,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Stop timer service to send AP reminder email',parsedatetime('04-NOV-16 03.05.25.063 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (932,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Stop timer service to send AP reminder email',parsedatetime('07-NOV-16 01.25.41.763 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (933,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('07-NOV-16 01.26.01.711 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (939,'sriadmin_js','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('07-NOV-16 01.26.42.165 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (987,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('07-NOV-16 02.45.33.955 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1107,'sriadmin_js','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('08-NOV-16 08.42.23.207 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1124,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('08-NOV-16 09.43.26.605 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1125,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('08-NOV-16 09.44.52.927 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1126,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Root element :ohfs-config',parsedatetime('08-NOV-16 09.44.55.618 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1127,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('08-NOV-16 09.44.55.651 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1128,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP url : ldap:HSCDKIMCAPLP202:389',parsedatetime('08-NOV-16 09.44.55.681 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1129,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Connection username : uid=srildapadmin,ou=users,ou=HealthInfo,ou=moh,o=gov,DC=ON,DC=CA',parsedatetime('08-NOV-16 09.44.55.709 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1139,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('08-NOV-16 11.07.42.599 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1141,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('08-NOV-16 11.08.19.676 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1142,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP url : ldap:HSCDKIMCAPLP202:389',parsedatetime('08-NOV-16 11.08.19.712 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1143,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Connection username : uid=srildapadmin,ou=users,ou=HealthInfo,ou=moh,o=gov,DC=ON,DC=CA',parsedatetime('08-NOV-16 11.08.19.749 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1144,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('08-NOV-16 11.08.20.407 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1145,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('08-NOV-16 11.08.49.789 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1147,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('08-NOV-16 11.10.01.880 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1148,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('08-NOV-16 11.10.10.621 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1149,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Root element :ohfs-config',parsedatetime('08-NOV-16 11.10.15.007 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1150,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('08-NOV-16 11.10.15.033 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1151,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP url : ldap:HSCDKIMCAPLP202:389',parsedatetime('08-NOV-16 11.10.15.059 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1152,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Connection usern AMe : uid=srildapadmin,ou=users,ou=HealthInfo,ou=moh,o=gov,DC=ON,DC=CA',parsedatetime('08-NOV-16 11.10.15.088 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1154,null,'INFO','AUTO_EMAIL',null,'server1','SYSTEM',null,'Start timer service to send AP reminder email',parsedatetime('08-NOV-16 11.13.15.884 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1155,'sriadmin_js','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: sriadmin_js',parsedatetime('08-NOV-16 11.13.33.736 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1156,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Root element :ohfs-config',parsedatetime('08-NOV-16 11.13.35.481 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1157,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('08-NOV-16 11.13.35.505 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1158,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP url : ldap:HSCDKIMCAPLP202:389',parsedatetime('08-NOV-16 11.13.35.529 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1159,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Connection usern AMe : uid=srildapadmin,ou=users,ou=HealthInfo,ou=moh,o=gov,DC=ON,DC=CA',parsedatetime('08-NOV-16 11.13.35.553 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1161,'jimtsting1','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: jimtsting1',parsedatetime('08-NOV-16 11.14.21.845 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1162,'jimtsting1','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('08-NOV-16 11.14.53.759 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1163,'jimlhintesting_2','INFO','LOGIN',null,'server1','SYSTEM',null,'Creating new user profile: jimlhintesting_2',parsedatetime('08-NOV-16 11.17.56.024 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1164,'jimlhintesting_2','INFO','LOGOUT',null,'server1','USER',null,'Perform logout ...',parsedatetime('08-NOV-16 11.18.35.040 AM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1228,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Root element :ohfs-config',parsedatetime('08-NOV-16 02.43.47.953 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1229,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP Current Element :LDAP',parsedatetime('08-NOV-16 02.43.48.004 PM','dd-MMM-yy hh.mm.ss.SSS a'));
Insert into LOG (LOG_ID,USER_CODE,LEVEL_CODE,ACTION,ACTION_OBJECT,SERVER,MODULE,FILE_LINE,MESSAGE,CREATED) values (1230,null,'INFO','INIT',null,'server1','SYSTEM',null,'LDAP url : ldap:HSCDKIMCAPLP202:389',parsedatetime('08-NOV-16 02.43.48.042 PM','dd-MMM-yy hh.mm.ss.SSS a'));

commit;


