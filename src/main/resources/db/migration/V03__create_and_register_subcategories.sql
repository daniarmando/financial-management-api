CREATE TABLE subcategory(
	parent BIGINT(20) PRIMARY KEY NOT NULL,
	children BIGINT(20) NOT NULL,
	FOREIGN KEY (parent) REFERENCES category(id),
	FOREIGN KEY (children) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO subcategory VALUES (1, 3);
INSERT INTO subcategory VALUES (2, 3);

