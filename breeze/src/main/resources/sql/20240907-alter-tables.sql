ALTER TABLE `breeze_book_details` ADD COLUMN subtitle VARCHAR(255) DEFAULT NULL;
ALTER TABLE `breeze_book_details` MODIFY COLUMN description TEXT;
ALTER TABLE `breeze_book_details` ADD COLUMN language VARCHAR(16) DEFAULT NULL;
ALTER TABLE breeze_user_book MODIFY COLUMN book_status ENUM('ADDED', 'READING', 'LIBRARY', 'COMPLETED', 'WISHLIST' );

UPDATE UPDATE breeze_config  SET value = 'new-key' WHERE name = 'GOOGLE_BOOKS_API_HEADER_VALUE';
UPDATE breeze_config  SET value = 'https://content-books.googleapis.com/books/v1/volumes' WHERE name = 'GOOGLE_BOOKS_API_URL';