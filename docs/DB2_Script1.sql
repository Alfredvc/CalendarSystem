SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Employee` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `telephone` INT(8) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Group` (
  `ID` INT NOT NULL,
  `groupName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `groupID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Meetingroom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Meetingroom` (
  `roomNumber` VARCHAR(10) NOT NULL,
  `name` VARCHAR(45) NULL,
  `capacity` INT NOT NULL,
  `notes` VARCHAR(255) NULL,
  PRIMARY KEY (`roomNumber`),
  UNIQUE INDEX `roomNumber_UNIQUE` (`roomNumber` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Appointment` (
  `ID` INT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `date` DATETIME NOT NULL,
  `duration` INT NOT NULL,
  `location` VARCHAR(45) NULL,
  `Meetingroom_roomNumber` VARCHAR(10) NULL,
  `leaderEmail` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `appointmentID_UNIQUE` (`ID` ASC),
  INDEX `fk_Appointment_Meetingroom1_idx` (`Meetingroom_roomNumber` ASC),
  INDEX `fk_Appointment_Employee1_idx` (`leaderEmail` ASC),
  CONSTRAINT `fk_Appointment_Meetingroom1`
    FOREIGN KEY (`Meetingroom_roomNumber`)
    REFERENCES `mydb`.`Meetingroom` (`roomNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Employee1`
    FOREIGN KEY (`leaderEmail`)
    REFERENCES `mydb`.`Employee` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Notification` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NOT NULL,
  `text` VARCHAR(255) NULL,
  `Appointment_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `notificationID_UNIQUE` (`ID` ASC),
  INDEX `fk_Notification_Appointment1_idx` (`Appointment_ID` ASC),
  CONSTRAINT `fk_Notification_Appointment1`
    FOREIGN KEY (`Appointment_ID`)
    REFERENCES `mydb`.`Appointment` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Member_of`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Member_of` (
  `Group_ID` INT NOT NULL,
  `Employee_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Group_ID`, `Employee_email`),
  INDEX `fk_Group_has_Employee_Employee1_idx` (`Employee_email` ASC),
  INDEX `fk_Group_has_Employee_Group_idx` (`Group_ID` ASC),
  CONSTRAINT `fk_Group_has_Employee_Group`
    FOREIGN KEY (`Group_ID`)
    REFERENCES `mydb`.`Group` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Employee_Employee1`
    FOREIGN KEY (`Employee_email`)
    REFERENCES `mydb`.`Employee` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Invited_to`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Invited_to` (
  `Employee_email` VARCHAR(45) NOT NULL,
  `Appointment_ID` INT NOT NULL,
  `alarm` TINYINT(1) NOT NULL DEFAULT 0,
  `hidden` TINYINT(1) NOT NULL DEFAULT 0,
  `attending` ENUM('attending','pending','declined') NOT NULL,
  PRIMARY KEY (`Employee_email`, `Appointment_ID`),
  INDEX `fk_Employee_has_Appointment_Employee1_idx` (`Employee_email` ASC),
  INDEX `fk_Employee_has_Appointment_Appointment1_idx` (`Appointment_ID` ASC),
  CONSTRAINT `fk_Employee_has_Appointment_Employee1`
    FOREIGN KEY (`Employee_email`)
    REFERENCES `mydb`.`Employee` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_has_Appointment_Appointment1`
    FOREIGN KEY (`Appointment_ID`)
    REFERENCES `mydb`.`Appointment` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
