create database tcc_order;
use tcc_order;
drop table if exists od_order;
create table od_order (
  id char(36) not null,
  product_id char(36) not null,
  status varchar(10) not null,
  primary key (id)
) engine = Innodb, charset = utf8;

use TCC;
drop table if exists TCC_TRANSACTION_ORDER;
CREATE TABLE `TCC_TRANSACTION_ORDER` (
                                     `TRANSACTION_ID` int(11) NOT NULL AUTO_INCREMENT,
                                     `DOMAIN` varchar(100) DEFAULT NULL,
                                     `GLOBAL_TX_ID` varbinary(32) NOT NULL,
                                     `BRANCH_QUALIFIER` varbinary(32) NOT NULL,
                                     `CONTENT` varbinary(8000) DEFAULT NULL,
                                     `STATUS` int(11) DEFAULT NULL,
                                     `TRANSACTION_TYPE` int(11) DEFAULT NULL,
                                     `RETRIED_COUNT` int(11) DEFAULT NULL,
                                     `CREATE_TIME` datetime DEFAULT NULL,
                                     `LAST_UPDATE_TIME` datetime DEFAULT NULL,
                                     `VERSION` int(11) DEFAULT NULL,
                                     `IS_DELETE` tinyint(1) NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`TRANSACTION_ID`),
                                     UNIQUE KEY `UX_TX_BQ` (`GLOBAL_TX_ID`,`BRANCH_QUALIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8