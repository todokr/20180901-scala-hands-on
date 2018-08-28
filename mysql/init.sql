charset utf8mb4;

CREATE USER 'root'@'%' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE SCHEMA IF NOT EXISTS `play2_hands_on` DEFAULT CHARACTER SET utf8mb4;
USE `play2_hands_on` ;

-- -----------------------------------------------------
-- Table `COMPANY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `COMPANY` (
  `COMPANY_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `COMPANY_NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `COMPANY_ID_UNIQUE` ON `COMPANY` (`COMPANY_ID` ASC);


-- -----------------------------------------------------
-- Table `USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER` (
  `USER_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `EMAIL` VARCHAR(255) NOT NULL,
  `AUTHORITY` VARCHAR(45) NOT NULL,
  `COMPANY_ID` BIGINT NOT NULL,
  PRIMARY KEY (`USER_ID`, `COMPANY_ID`),
  CONSTRAINT `fk_USER_COMPANY`
    FOREIGN KEY (`COMPANY_ID`)
    REFERENCES `COMPANY` (`COMPANY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `USER_ID_UNIQUE` ON `USER` (`USER_ID` ASC);

CREATE INDEX `fk_USER_COMPANY_idx` ON `USER` (`COMPANY_ID` ASC);


-- -----------------------------------------------------
-- Data for table `COMPANY`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `COMPANY` (`COMPANY_ID`, `COMPANY_NAME`) VALUES (1, '株式会社AAA');
INSERT INTO `COMPANY` (`COMPANY_ID`, `COMPANY_NAME`) VALUES (2, 'BBBコーポレーション');

COMMIT;

-- -----------------------------------------------------
-- Data for table `USER`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `USER` (`USER_ID`, `NAME`, `EMAIL`, `AUTHORITY`, `COMPANY_ID`) VALUES (1, '田中 太郎', 'tanaka@example.com', 'ADMIN', 1);
INSERT INTO `USER` (`USER_ID`, `NAME`, `EMAIL`, `AUTHORITY`, `COMPANY_ID`) VALUES (2, '鈴木 次郎', 'suzuki@example.com', 'READONLY', 1);
INSERT INTO `USER` (`USER_ID`, `NAME`, `EMAIL`, `AUTHORITY`, `COMPANY_ID`) VALUES (3, '佐藤 三郎', 'sato@example.com', 'EDITOR', 1);
INSERT INTO `USER` (`USER_ID`, `NAME`, `EMAIL`, `AUTHORITY`, `COMPANY_ID`) VALUES (4, '藤原 四郎', 'fujiwara@example.com', 'EDITOR', 2);
INSERT INTO `USER` (`USER_ID`, `NAME`, `EMAIL`, `AUTHORITY`, `COMPANY_ID`) VALUES (5, '野口 五郎', 'noguchi@example.com', 'READONLY', 2);

COMMIT;