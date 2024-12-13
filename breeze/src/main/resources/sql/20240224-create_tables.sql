CREATE TABLE `breeze_user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user_name` varchar(64) NOT NULL,
    `code` varchar(30)  NOT NULL,
    `email_address` varchar(255) NOT NULL,
    `user_id` varchar(255) NOT NULL,
    `is_email_verified` tinyint(1) DEFAULT '0',
    `is_phone_verified` tinyint(1) DEFAULT '0',
    `user_type` enum('ADMIN','STANDARD')  NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_user_code` (`code`),
    UNIQUE KEY `UK_user_id` (`user_id`)
    UNIQUE KEY `UK_user_email_address` (`email_address`)
);

CREATE TABLE `breeze_book_details` (
         `id` bigint NOT NULL AUTO_INCREMENT,
         `code` varchar(30) NOT NULL,
         `google_id` varchar(30) NOT NULL,
         `name` varchar(255) NOT NULL,
         `isbn_10` varchar(30) NOT NULL,
         `isbn_13` varchar(30) NOT NULL,
         `author` varchar(64) NOT NULL,
         `small_thumbnail` varchar(255) DEFAULT NULL,
         `thumbnail` varchar(255) DEFAULT NULL,
         `published_date` datetime DEFAULT NULL,
         `pages` bigint DEFAULT NULL,
         `category`  VARCHAR(30) NOT NULL,
        `user_rating` decimal(3, 2) DEFAULT NULL,
        `review_count` bigint DEFAULT 0,
        `description` varchar(255) DEFAULT NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        UNIQUE KEY `UK_book_code` (`code`),
        UNIQUE KEY `UK_book_isbn_small` (`isbn_10`),
        UNIQUE KEY `UK_book_isbn_large` (`isbn_13`)
);

CREATE TABLE `breeze_user_book` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(30) NOT NULL,
    `book_code` varchar(30) NOT NULL,
    `user_code` varchar(30) NOT NULL,
    `book_status` enum('ADDED', 'READING', 'COMPLETED') NOT NULL,
    `is_deleted` tinyint(1) DEFAULT '0',
    `wishlist` tinyint(1) DEFAULT '0',
    `current_page` bigint DEFAULT NULL,
    `user_rating` bigint DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_book_code` (`code`),
    UNIQUE KEY `UK_book_user_code` (`book_code`, `user_code`)
);


CREATE TABLE `breeze_user_suggestions` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(30) NOT NULL,
    `user_code` varchar(30)  NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status` enum('pending','closed') NOT NULL,
    `suggestion` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_user_suggestion_code` (`code`)
);

CREATE TABLE `breeze_user_notifications` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(30) NOT NULL,
    `data` json NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_user_notification_code` (`code`)
);

CREATE TABLE `breeze_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64)  DEFAULT NULL,
  `value` varchar(1000)  DEFAULT NULL,
  `status` varchar(12)  DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_config_name` (`name`)
);
