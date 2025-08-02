ALTER TABLE transactions
ADD COLUMN transaction_date TIMESTAMP NOT NULL AFTER description,
ADD COLUMN recurrent BOOLEAN NOT NULL AFTER transaction_date,
ADD COLUMN recurrence_frequency VARCHAR(50) NOT NULL AFTER transaction_status;