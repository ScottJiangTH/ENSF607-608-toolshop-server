#2. basic retrieval
USE toolshop;

SELECT * FROM item;
SELECT * FROM electrical_item;
SELECT * FROM supplier;
SELECT * FROM international_supplier;
SELECT * FROM customer;
SELECT * FROM order_line;
SELECT * FROM daily_order;
SELECT * FROM purchase_history;

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
INSERT INTO international_supplier VALUES (NEW.id,NULL);
END IF;

END$$
DELIMITER ;


