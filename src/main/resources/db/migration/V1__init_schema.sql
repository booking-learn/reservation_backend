CREATE TABLE IF NOT EXISTS `accounts`(
    `id` BINARY(16) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('VETERINARIAN','VET_TECH' , 'RECEPTIONIST', 'IT_ADMIN') NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4
 COLLATE = utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `employees`(
    `id` BINARY(16) NOT NULL,
    `account_id` BINARY(16) NOT NULL,
    `first_name` VARCHAR(100) NOT NULL,
    `last_name` VARCHAR(100) NOT NULL,
    `phone_number`  VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `employee_FK` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users`(
    `id` BINARY(16) NOT NULL,
    `account_id` BINARY(16) NOT NULL,
    `first_name`  VARCHAR(100) NOT NULL,
    `last_name`  VARCHAR(100) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `phone_number`  VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `user_FK` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4
 COLLATE = utf8mb4_0900_ai_ci;
