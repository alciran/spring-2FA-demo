DROP TABLE IF EXISTS user_dto;

CREATE TABLE user_dto (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  mfa_description VARCHAR(32) NOT NULL,
  mfa_secret   VARCHAR(250) NOT NULL
);
