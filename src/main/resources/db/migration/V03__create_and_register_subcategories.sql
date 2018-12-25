CREATE TABLE subcategory(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	parent_category_id BIGINT(20) NOT NULL,
	child_category_id BIGINT(20) NOT NULL,
	FOREIGN KEY (parent_category_id) REFERENCES category(id),
	FOREIGN KEY (child_category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO subcategory (parent_category_id, child_category_id) VALUES (3, 1);
INSERT INTO subcategory (parent_category_id, child_category_id) VALUES (3, 2);

