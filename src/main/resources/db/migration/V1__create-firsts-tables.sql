CREATE TABLE users (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_photo TEXT,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   phone VARCHAR(20) NOT NULL,
   monthly_income DECIMAL(10,2) NOT NULL,
   password VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categories (
   id BINARY(16) PRIMARY KEY NOT NULL,
   category_name VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE subcategories (
   id BINARY(16) PRIMARY KEY NOT NULL,
   category_id BINARY(16) NOT NULL,
   subcategory_name VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_id BINARY(16) NOT NULL,
   subcategory_id BINARY(16),
   transaction_type VARCHAR(50) NOT NULL,
   transaction_value DECIMAL(10,2) NOT NULL,
   description TEXT,
   transaction_status VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE future_transactions (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_id BINARY(16) NOT NULL,
   subcategory_id BINARY(16),
   transaction_type VARCHAR(50) NOT NULL,
   transaction_value DECIMAL(10,2) NOT NULL,
   description TEXT,
   recurrent BOOLEAN NOT NULL,
   transaction_status VARCHAR(50) NOT NULL,
   recurrence_frequency VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_ft FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory_ft FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE goals (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_id BINARY(16) NOT NULL,
   subcategory_id BINARY(16),
   goal_name VARCHAR(50) NOT NULL,
   target_value DECIMAL(10,2) NOT NULL,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   goal_status VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_goal FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory_goal FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE reports (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_id BINARY(16) NOT NULL,
   report_type VARCHAR(50) NOT NULL,
   transaction_id BINARY(16),
   future_transaction_id BINARY(16),
   goal_id BINARY(16),
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_report FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_transaction_report FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_future_transaction_report FOREIGN KEY (future_transaction_id) REFERENCES future_transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_goal_report FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
   id BINARY(16) PRIMARY KEY NOT NULL,
   user_id BINARY(16) NOT NULL,
   notification_type VARCHAR(50) NOT NULL,
   transaction_id BINARY(16),
   future_transaction_id BINARY(16),
   goal_id BINARY(16),
   message VARCHAR(500) NOT NULL,
   notification_status BOOLEAN NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_transaction_notification FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_future_transaction_notification FOREIGN KEY (future_transaction_id) REFERENCES future_transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_goal_notification FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
);
