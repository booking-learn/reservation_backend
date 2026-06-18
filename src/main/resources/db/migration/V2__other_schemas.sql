CREATE TABLE IF NOT EXISTS `care_offerings`(
    `id` BINARY(16) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DOUBLE NOT NULL,
    `duration` INT NOT NULL,
    `care_service` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
    )ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `pets`(
                                     `id` BINARY(16) NOT NULL,
    `owner_id` BINARY(16) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `species` VARCHAR(100) NOT NULL,
    `breed` VARCHAR(100),
    `gender` VARCHAR(20),
    `birth_date` DATE NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `pet_owner_FK`
    FOREIGN KEY (`owner_id`)
    REFERENCES `users`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    )ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `time_slots`(
    `id` BINARY(16) NOT NULL,
    `vetenerian_id` BINARY(16) NOT NULL,
    `start_time` TIME NOT NULL,
    `end_time` TIME NOT NULL,
    `date` DATE NOT NULL,
    `available` BOOLEAN NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `time_slot_veterinarian_FK`
    FOREIGN KEY (`vetenerian_id`)
    REFERENCES `employees`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    )ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bookings`(
     `id` BINARY(16) NOT NULL,
    `user_id` BINARY(16) NOT NULL,
    `service_id` BINARY(16) NOT NULL,
    `pet_id` BINARY(16) NOT NULL,
    `veterinarian_id` BINARY(16),
    `time_slot_id` BINARY(16) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `notes` TEXT,
    `booking_created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),

    CONSTRAINT `booking_user_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `users`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

    CONSTRAINT `booking_service_FK`
    FOREIGN KEY (`service_id`)
    REFERENCES `care_offerings`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

    CONSTRAINT `booking_pet_FK`
    FOREIGN KEY (`pet_id`)
    REFERENCES `pets`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

    CONSTRAINT `booking_veterinarian_FK`
    FOREIGN KEY (`veterinarian_id`)
    REFERENCES `employees`(`id`)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

    CONSTRAINT `booking_time_slot_FK`
    FOREIGN KEY (`time_slot_id`)
    REFERENCES `time_slots`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    )ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;