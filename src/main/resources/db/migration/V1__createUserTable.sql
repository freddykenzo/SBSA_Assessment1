CREATE TABLE `user`
(
    `id`            INT(11)     NOT NULL AUTO_INCREMENT,
    `firstname`     VARCHAR(45) NULL,
    `surname`       VARCHAR(45) NULL,
    `mobile_number` VARCHAR(45) NULL,
    `email`         VARCHAR(45) NULL,
    `status`         VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
