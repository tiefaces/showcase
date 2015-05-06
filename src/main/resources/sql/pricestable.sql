
CREATE TABLE IF NOT EXISTS `PricesTable` (
  `Code` varchar(10) NOT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`Code`)
) ;


INSERT INTO `PricesTable` (`Code`, `Name`, `Description`, `Price`) VALUES
('GC1020', 'BMW', 'BMW Car Black', '85438'),
('GC1021', 'Fiat', 'Fiat Green ', '29328'),
('GC1022', 'Jaguar', 'Jaguar Car Red', '98289'),
('GC1040', 'Mercedes', 'Mercedes Car Blue', '99990'),
('GC1060', 'BMW', 'BMW White', '75000'),
('GC3020', 'IBM Think Pad', 'IBM V1200', '10200'),
('GC3040', 'IPAD AIR', 'IPAD AIR V3.0', '38000'),
('GC3060', 'SOFTWARE', 'WINDOWS 10', '95000'),
('GC5020', 'IPHONE', 'IPHONE V11', '28000'),
('GC5040', 'IWATCH', 'IWATCH V12', '59900'),
('GC5060', 'IBOOK', 'IBOOK V15', '110000'),
('IN7020', 'Jaguar', 'Jaguar Car Blue', '4000'),
('IN7040', 'Mercedes', 'Mercedes Car White', '16000'),
('IN7060', 'BMW', 'BMW Red', '42000'),
('IN7080', 'SOFTWARE', 'WINDOWS x', '69000'),
('SL9020', 'IPAD AIR', 'IPAD AIR V2.0', '4999'),
('SL9040', 'BMW', 'BMW Black', '9999'),
('SL9060', 'IPHONE', 'IPHONE Golden V2.0', '14999'),
('SL9080', 'IPHONE', 'IPHONE Golden V2.2', '19999');

commit;