CREATE SCHEMA IF NOT EXISTS `cafeDataBase` DEFAULT CHARACTER SET utf8 ;
USE `cafeDataBase` ;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`users` (
    `u_id` BIGINT NOT NULL AUTO_INCREMENT,
    `u_name` VARCHAR(45) NOT NULL,
    `u_surname` VARCHAR(45) NOT NULL,
    `u_password` VARCHAR(32) NOT NULL,
    `u_login` VARCHAR(16) NOT NULL,
    `u_loyaltypoints` INT NOT NULL DEFAULT 20,
    `u_blocked` TINYINT(1) NOT NULL DEFAULT 0,
    `u_role` ENUM('GUEST', 'ADMIN', 'CUSTOMER') NOT NULL,
    PRIMARY KEY (`u_id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`orders` (
    `or_id` BIGINT NOT NULL AUTO_INCREMENT,
    `or_user` BIGINT NOT NULL,
    `or_cost` DECIMAL(8,2) NOT NULL,
    `or_creation_date` DATETIME NOT NULL,
    `or_paymenttype` ENUM('CASH', 'CREDIT_CARD') NOT NULL,
    `or_taken` TINYINT(1) NOT NULL DEFAULT 0,
    `or_serving_date` DATETIME NOT NULL,
    `or_served` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`or_id`),
    CONSTRAINT `user`
        FOREIGN KEY (`or_user`)
            REFERENCES `cafeDataBase`.`users` (`u_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`sections` (
    `s_id` INT NOT NULL AUTO_INCREMENT,
    `s_name` VARCHAR(45) NOT NULL,
    `s_hidden` TINYINT NOT NULL DEFAULT 0,
     PRIMARY KEY (`s_id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`menu_items` (
    `mi_id` BIGINT NOT NULL AUTO_INCREMENT,
    `mi_section` INT NOT NULL,
    `mi_name` VARCHAR(45) NOT NULL,
    `mi_picture` BLOB NULL,
    `mi_hidden` TINYINT NOT NULL DEFAULT 0,
    `mi_cost` DECIMAL(5,2) NOT NULL,
     PRIMARY KEY (`mi_id`),
     CONSTRAINT `section`
         FOREIGN KEY (`mi_section`)
             REFERENCES `cafeDataBase`.`sections` (`s_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`m2m_orders_menuitems` (
    `or_id` BIGINT NOT NULL,
    `mi_id` BIGINT NOT NULL,
    `mi_count` INT NOT NULL DEFAULT 1,
     CONSTRAINT `order`
         FOREIGN KEY (`or_id`)
             REFERENCES `cafeDataBase`.`orders` (`or_id`)
             ON DELETE CASCADE
             ON UPDATE CASCADE,
     CONSTRAINT `menuitem_order`
         FOREIGN KEY (`mi_id`)
             REFERENCES `cafeDataBase`.`menu_items` (`mi_id`)
             ON DELETE CASCADE
             ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`ingredients` (
    `ingr_id` BIGINT NOT NULL AUTO_INCREMENT,
    `ingr_name` VARCHAR(45) NOT NULL,
    `ingr_proteins` DECIMAL(6,3) NOT NULL,
    `ingr_fats` DECIMAL(6,3) NOT NULL,
    `ingr_carbohydrates` DECIMAL(6,3) NOT NULL,
    `ingr_calories` DECIMAL(7,3) NOT NULL,
    `ingr_picture` BLOB NULL,
    `ingr_hidden` TINYINT NOT NULL DEFAULT 0,
     PRIMARY KEY (`ingr_id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cafeDataBase`.`m2m_menuitems_ingredients` (
    `mi_id` BIGINT NOT NULL,
    `ingr_id` BIGINT NOT NULL,
    `ingr_weight` DECIMAL(5,1) NOT NULL,
     CONSTRAINT `menuitem_ingredient`
         FOREIGN KEY (`mi_id`)
             REFERENCES `cafeDataBase`.`menu_items` (`mi_id`)
             ON DELETE CASCADE
             ON UPDATE CASCADE,
     CONSTRAINT `ingredient`
         FOREIGN KEY (`ingr_id`)
             REFERENCES `cafeDataBase`.`ingredients` (`ingr_id`)
             ON DELETE CASCADE
             ON UPDATE CASCADE)
    ENGINE = InnoDB;