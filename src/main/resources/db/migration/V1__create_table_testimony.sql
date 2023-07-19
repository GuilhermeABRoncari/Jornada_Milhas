CREATE TABLE testimony (
   id VARCHAR(255) PRIMARY KEY,
   profile_picture VARCHAR(255),
   description VARCHAR(255),
   author VARCHAR(255),
   post_time TIMESTAMP WITHOUT TIME ZONE,
   edited BOOLEAN
);