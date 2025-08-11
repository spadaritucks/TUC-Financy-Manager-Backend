ALTER TABLE transactions
DROP COLUMN IF EXISTS transaction_status,
ADD COLUMN transaction_date DATE NOT NULL,
ADD COLUMN recurrent BOOLEAN NOT NULL,
ADD COLUMN recurrence_frequency VARCHAR(50);