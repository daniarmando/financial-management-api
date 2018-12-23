CREATE TABLE person (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(35) NOT NULL,
	active BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO person (name, active) VALUES ('Daniel Armando Brandão', true);
INSERT INTO person (name, active) VALUES ('João Testes', true);
INSERT INTO person (name, active) VALUES ('Maria Testes', false);
