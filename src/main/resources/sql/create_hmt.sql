DROP TABLE IF EXISTS auth_user_role;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS auth_user;
DROP TABLE IF EXISTS saved_translations;

CREATE TABLE auth_role (
  auth_role_id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) DEFAULT NULL,
  role_desc varchar(255) DEFAULT NULL,
  PRIMARY KEY (auth_role_id)
);
INSERT INTO auth_role VALUES (1,'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO auth_role VALUES (2,'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO auth_role VALUES (3,'SITE_USER','This user has access to site, after login - normal user');

CREATE TABLE auth_user (
  auth_user_id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  status varchar(255),
  PRIMARY KEY (auth_user_id)
);

CREATE TABLE auth_user_role (
  auth_user_id int(11) NOT NULL,
  auth_role_id int(11) NOT NULL,
  PRIMARY KEY (auth_user_id,auth_role_id),
  KEY FK_user_role (auth_role_id),
  CONSTRAINT FK_auth_user FOREIGN KEY (auth_user_id) REFERENCES auth_user (auth_user_id),
  CONSTRAINT FK_auth_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id)
);

CREATE TABLE saved_translations (
  saved_translation_id int(11) NOT NULL AUTO_INCREMENT,
  auth_user_id int(11) NOT NULL,
  source_language varchar(255),
  target_language varchar(255),
  source_text TEXT CHARACTER SET utf8,
  target_text TEXT CHARACTER SET utf8,
  PRIMARY KEY (saved_translation_id),
  FOREIGN KEY (auth_user_id) REFERENCES auth_user (auth_user_id)
);

insert into auth_user (auth_user_id,email,password,status) values (1,'alisab.london@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','LIVE');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','1');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','2');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','3');