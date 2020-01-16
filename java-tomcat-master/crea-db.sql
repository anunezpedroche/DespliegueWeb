DROP DATABASE IF EXISTS ajedrez;
-- REVOKE ALL PRIVILEGES , GRANT OPTION ON ajedrez.* FROM tomcat@localhost;
-- delete from mysql.db where user = 'tomcat';
-- DROP USER tomcat@localhost;
FLUSH PRIVILEGES;
CREATE DATABASE cervezas;




CREATE USER IF NOT EXISTS tomcat@localhost IDENTIFIED BY 'tomcat';
GRANT ALL PRIVILEGES ON cervezas.* TO 'tomcat'@'localhost';
FLUSH PRIVILEGES;

USE cervezas ;

CREATE TABLE ventaCervezas (
 idCerveza INT NOT NULL AUTO_INCREMENT,
 nombre VARCHAR(255) NOT NULL,
 precio  INT NOT NULL,
 imagen VARCHAR(255) NOT NULL,
 PRIMARY KEY (idCerveza));

INSERT INTO ventaCervezas VALUES (NULL, "Westvleteren",1,"westvleteren.png");
INSERT INTO ventaCervezas VALUES (NULL, "Westmallen Triple",2,"westmallen.png");
INSERT INTO ventaCervezas VALUES (NULL, "La Trappe Quadruple",2,"trappeQ.png");
INSERT INTO ventaCervezas VALUES (NULL, "Orval",3,"orval.png");
INSERT INTO ventaCervezas VALUES (NULL, "Malheur",4,"malheur.png");
INSERT INTO ventaCervezas VALUES (NULL, "Rochefort 10",10,"rochefort.png");

