DROP TABLE IF EXISTS auth_user_role;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS saved_translations;
DROP TABLE IF EXISTS auth_user;

CREATE TABLE auth_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) DEFAULT NULL,
  role_desc varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE auth_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  status varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE auth_user_role (
  auth_user_id int(11) NOT NULL,
  auth_role_id int(11) NOT NULL,
  PRIMARY KEY (auth_user_id,auth_role_id),
  FOREIGN KEY (auth_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (auth_role_id) REFERENCES auth_role (id)
);

CREATE TABLE saved_translations (
  id int(11) NOT NULL AUTO_INCREMENT,
  auth_user_id int(11) NOT NULL,
  source_language varchar(255) NOT NULL,
  target_language varchar(255) NOT NULL,
  source_text TEXT,
  target_text TEXT,
  PRIMARY KEY (id),
  FOREIGN KEY (auth_user_id) REFERENCES auth_user (id)
);

INSERT INTO auth_role VALUES (1,'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO auth_role VALUES (2,'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO auth_role VALUES (3,'SITE_USER','This user has access to site, after login - normal user');

INSERT INTO auth_user (id,email,password,status) VALUES (998,'populated@hmt.com','$2a$10$Ze.v1XYdwzDvFUvomnHwkOpuTDZz8JW5C94SKF75fvZ9uLSmThl72','LIVE');
INSERT INTO auth_user (id,email,password,status) VALUES (999,'test@hmt.com','$2a$10$Ze.v1XYdwzDvFUvomnHwkOpuTDZz8JW5C94SKF75fvZ9uLSmThl72','LIVE');
INSERT INTO auth_user_role (auth_user_id, auth_role_id) VALUES (999,1);
INSERT INTO auth_user_role (auth_user_id, auth_role_id) VALUES (999,2);
INSERT INTO auth_user_role (auth_user_id, auth_role_id) VALUES (999,3);

INSERT INTO saved_translations (id, auth_user_id, source_language, target_language, source_text, target_text)
VALUES (999, 998, 'English', 'Arabic', 'This is a great morning!', 'هذا صباح رائع!');
