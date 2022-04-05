CREATE TABLE `scheduled_action`
(
    `id`          VARCHAR(45)  NOT NULL,
    `max_attempt` INT(11)      NULL DEFAULT 0,
    `frequency`   VARCHAR(45)  NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `active`      TINYINT(1)   NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

INSERT INTO `scheduled_action` (`id`, `max_attempt`, `frequency`, `description`, `active`)
VALUES ('REGISTER_USER', '4', '0 0/1 * 1/1 * ?', 'Action to retry re-registering a user', 1),
       ('USER_FILE_UPLOAD', '0', '0 0/2 * 1/1 * ?', 'Action to read a csv file and save the user from that file', 1);
-- 0 0/2 * 1/1 * ?


CREATE TABLE `retry_action`
(
    `id`                 INT(11)      NOT NULL AUTO_INCREMENT,
    `action`             VARCHAR(45)  NOT NULL,
    `request`            VARCHAR(255) NULL,
    `entity_id`          INT(11)      NULL,
    `number_of_attempts` INT(11)      NULL DEFAULT 0,
    `status`             VARCHAR(45)  NULL,
    PRIMARY KEY (`id`),
    INDEX `scheduled_retryable_action_fk_idx` (`action` ASC),
    CONSTRAINT `scheduled_retryable_action_fk`
        FOREIGN KEY (`action`)
            REFERENCES `scheduled_action` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
