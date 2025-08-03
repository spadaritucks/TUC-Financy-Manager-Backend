ALTER TABLE transactions
ADD COLUMN transaction_date DATE NOT NULL,
ADD COLUMN recurrent BOOLEAN NOT NULL,
ADD COLUMN recurrence_frequency VARCHAR(50) NOT NULL;