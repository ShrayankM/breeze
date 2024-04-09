CREATE TABLE `breeze_user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(64)  NOT NULL,
    `code` varchar(30)  NOT NULL,
    `email_address` varchar(255) NOT NULL,
    `phone_number` varchar(20) NOT NULL,
    `password` varchar(255) NOT NULL,
    `is_email_verified` tinyint(1) DEFAULT '0',
    `is_phone_verified` tinyint(1) DEFAULT '0',
    `access_type` enum('admin','standard')  NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_user_code` (`code`),
    UNIQUE KEY `UK_user_email_address` (`email_address`)
);

CREATE TABLE `breeze_book_details` (
         `id` bigint NOT NULL AUTO_INCREMENT,
         `code` varchar(30) NOT NULL,
         `book_name` varchar(64) NOT NULL,
         `isbn` varchar(30) NOT NULL,
         `author_name` varchar(64) NOT NULL,
         `s3_image_link` varchar(255) DEFAULT NULL,
         `year_published` datetime DEFAULT NULL,
         `no_of_pages` bigint DEFAULT NULL,
         `book_genre` enum('FICTION', 'NON_FICTION', 'POETRY', 'DRAMA', 'ROMANCE', 'MYSTERY_THRILLER', 'SCIENCE_FICTION', 'FANTASY', 'HORROR', 'ADVENTURE', 'CHILDRENS_YOUNG_ADULT',
                           'HISTORICAL', 'BIOGRAPHY_AUTOBIOGRAPHY', 'HUMOR_SATIRE', 'DYSTOPIAN') NOT NULL,
        `user_rating` decimal(3, 2) DEFAULT NULL,
        `review_count` bigint DEFAULT 0,
        `description` varchar(255) DEFAULT NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        UNIQUE KEY `UK_book_code` (`code`),
        UNIQUE KEY `UK_book_isbn` (`isbn`)
);

CREATE TABLE `breeze_user_book` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `book_code` varchar(30) NOT NULL,
    `user_code` varchar(30) NOT NULL,
    `book_status` enum('reading','read') NOT NULL,
    `current_page` bigint DEFAULT NULL,
    `user_rating` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
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

CREATE TABLE `breeze_user_book_approval` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(30) NOT NULL,
    `user_code` varchar(30)  NOT NULL,
    `data` json NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `approval_status` enum('PENDING', 'SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED') NOT NULL,
    `approved_at` datetime DEFAULT NULL,
    `rejected_at` datetime DEFAULT NULL,
    `rejection_reason` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_user_approval_request_code` (`code`)
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
