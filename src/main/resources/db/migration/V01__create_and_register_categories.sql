CREATE TABLE category (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	subcategory BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name, subcategory, active) VALUES ('Alimentação', false, true);
INSERT INTO category (name, subcategory, active) VALUES ('Saúde', false, true);
INSERT INTO category (name, subcategory, active) VALUES ('Pagamentos', false, true);
