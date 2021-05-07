CREATE TABLE `ping-pong`.`users` (
  `userID` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `totalPoints` INT NOT NULL,
  `wins` INT NOT NULL,
  PRIMARY KEY (`userID`));
  