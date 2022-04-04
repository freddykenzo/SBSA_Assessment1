CREATE TABLE `retryable_action`
(
    `id`          VARCHAR(45)  NOT NULL,
    `max_attempt` INT(11)      NOT NULL DEFAULT 1,
    `frequency`   VARCHAR(45)  NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `active`      TINYINT(1)   NULL     DEFAULT 0,
    PRIMARY KEY (`id`)
);

INSERT INTO `retryable_action` (`id`, `max_attempt`, `frequency`, `description`, `active`)
VALUES ('REGISTER_USER', '4', '0 0/1 * 1/1 * ?', 'Action to retry re-registering a user', 1);
-- 0 0/2 * 1/1 * ?


CREATE TABLE `scheduled_action`
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
            REFERENCES `retryable_action` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
