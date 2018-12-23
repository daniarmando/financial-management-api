CREATE TABLE category (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	subcategory BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL,
	person_id BIGINT(20) NOT NULL,
	FOREIGN KEY (person_id) REFERENCES person(id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name, subcategory, active, person_id) VALUES ('Alimentação', false, true, 1);
INSERT INTO category (name, subcategory, active, person_id) VALUES ('Saúde', false, true, 1);
INSERT INTO category (name, subcategory, active, person_id) VALUES ('Pagamentos', false, true, 1);
