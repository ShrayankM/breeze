ALTER TABLE `breeze_book_details` ADD COLUMN subtitle VARCHAR(255) DEFAULT NULL;
ALTER TABLE `breeze_book_details` MODIFY COLUMN description TEXT;
ALTER TABLE breeze_user_book MODIFY COLUMN book_status ENUM('ADDED', 'READING', 'LIBRARY', 'COMPLETED', 'WISHLIST' );