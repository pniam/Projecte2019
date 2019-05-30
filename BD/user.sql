drop database if exists pronova;
create database pronova;
CREATE USER 'nouUsuari'@'localhost' IDENTIFIED BY 'nou';
CREATE USER 'nouUsuari'@'%' IDENTIFIED BY 'nou';
GRANT ALL PRIVILEGES on pronova.* TO nouUsuari@'localhost' IDENTIFIED BY 'nou';
GRANT ALL PRIVILEGES on pronova.* TO nouUsuari@'%' IDENTIFIED BY 'nou';
FLUSH PRIVILEGES;