#2. basic retrieval
USE toolshop;

SELECT * FROM item WHERE quantity=10;
SELECT * FROM electrical_item WHERE power_type='220V';
SELECT * FROM supplier WHERE sales_contact='Wendy';

#3. retrieval query with ordered results
SELECT * from item ORDER BY price DESC;

SELECT * from international_supplier ORDER BY import_tax DESC;

#4. nested query
#select electrical items that have power type of 110V
SELECT I.id, I.iname FROM item as I WHERE I.id IN (SELECT E.iid FROM electrical_item as E WHERE power_type="110V");
#select suppliers that international suppliers with import tax 10.75
SELECT S.id , S.company_name FROM supplier as S WHERE S.id IN (SELECT I.sid FROM international_supplier as I WHERE import_tax= 10.75);

#5.query using joined tables
SELECT iname FROM (electrical_item JOIN item ON iid=id) WHERE power_type="110V";

SELECT O.oid,S.company_name FROM order_line AS O LEFT OUTER JOIN supplier AS S ON O.sid=S.id WHERE S.stype="domestic";

#6.update operation with necessary triggers
DROP TRIGGER IF EXISTS `toolshop`.`supplier_AFTER_INSERT`;

DELIMITER $$
USE `toolshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `toolshop`.`supplier_AFTER_INSERT` AFTER INSERT ON `supplier` FOR EACH ROW
BEGIN
IF (NEW.stype='international') THEN
INSERT INTO international_supplier VALUES (NEW.id,10.75);
END IF;

END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `toolshop`.`item_BEFORE_DELETE`;

DELIMITER $$
USE `toolshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `toolshop`.`item_BEFORE_DELETE` BEFORE DELETE ON `item` FOR EACH ROW
BEGIN
IF (OLD.itype='electrical') THEN
DELETE FROM electrical_item AS E WHERE OLD.id=E.iid;
END IF;
END
$$
DELIMITER ;


DROP TRIGGER IF EXISTS `toolshop`.`supplier_BEFORE_DELETE`;

DELIMITER $$
USE `toolshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `toolshop`.`supplier_BEFORE_DELETE` BEFORE DELETE ON `supplier` FOR EACH ROW
BEGIN
IF (OLD.stype='international') THEN
DELETE FROM international_supplier AS I WHERE OLD.id=I.sid;
END IF;
END
$$
DELIMITER ;
