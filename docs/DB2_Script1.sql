SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `Employee`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Employee` (
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `telephone` INT(8) NOT NULL ,
  PRIMARY KEY (`email`) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group` (
  `ID` INT NOT NULL ,
  `groupName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `groupID_UNIQUE` (`ID` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MeetingRoom`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MeetingRoom` (
  `roomNumber` VARCHAR(10) NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `capacity` INT NOT NULL ,
  `notes` VARCHAR(255) NULL ,
  PRIMARY KEY (`roomNumber`) ,
  UNIQUE INDEX `roomNumber_UNIQUE` (`roomNumber` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Appointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Appointment` (
  `ID` CHAR(36) NOT NULL ,
  `description` VARCHAR(255) NOT NULL ,
  `date` DATETIME NOT NULL ,
  `duration` INT NOT NULL ,
  `location` VARCHAR(45) NULL ,
  `Meetingroom_roomNumber` VARCHAR(10) NULL ,
  `leaderEmail` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `appointmentID_UNIQUE` (`ID` ASC) ,
  INDEX `fk_Appointment_Meetingroom1_idx` (`Meetingroom_roomNumber` ASC) ,
  INDEX `fk_Appointment_Employee1_idx` (`leaderEmail` ASC) ,
  CONSTRAINT `fk_Appointment_Meetingroom1`
    FOREIGN KEY (`Meetingroom_roomNumber` )
    REFERENCES `MeetingRoom` (`roomNumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Employee1`
    FOREIGN KEY (`leaderEmail` )
    REFERENCES `Employee` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Notification`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Notification` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `creationTime` DATETIME NOT NULL ,
  `text` VARCHAR(255) NULL ,
  `Appointment_ID` CHAR(36) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `notificationID_UNIQUE` (`ID` ASC) ,
  INDEX `fk_Notification_Appointment1_idx` (`Appointment_ID` ASC) ,
  CONSTRAINT `fk_Notification_Appointment1`
    FOREIGN KEY (`Appointment_ID` )
    REFERENCES `Appointment` (`ID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Member_of`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Member_of` (
  `Group_ID` INT NOT NULL ,
  `Employee_email` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`Group_ID`, `Employee_email`) ,
  INDEX `fk_Group_has_Employee_Employee1_idx` (`Employee_email` ASC) ,
  INDEX `fk_Group_has_Employee_Group_idx` (`Group_ID` ASC) ,
  CONSTRAINT `fk_Group_has_Employee_Group`
    FOREIGN KEY (`Group_ID` )
    REFERENCES `Group` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Employee_Employee1`
    FOREIGN KEY (`Employee_email` )
    REFERENCES `Employee` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Invited_to`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Invited_to` (
  `Employee_email` VARCHAR(45) NOT NULL ,
  `Appointment_ID` CHAR(36) NOT NULL ,
  `alarm` TINYINT(1) NOT NULL DEFAULT 0 ,
  `hidden` TINYINT(1) NOT NULL DEFAULT 0 ,
  `attending` ENUM('attending','pending','declined') NOT NULL ,
  PRIMARY KEY (`Employee_email`, `Appointment_ID`) ,
  INDEX `fk_Employee_has_Appointment_Employee1_idx` (`Employee_email` ASC) ,
  INDEX `fk_Employee_has_Appointment_Appointment1_idx` (`Appointment_ID` ASC) ,
  CONSTRAINT `fk_Employee_has_Appointment_Employee1`
    FOREIGN KEY (`Employee_email` )
    REFERENCES `Employee` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_has_Appointment_Appointment1`
    FOREIGN KEY (`Appointment_ID` )
    REFERENCES `Appointment` (`ID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
