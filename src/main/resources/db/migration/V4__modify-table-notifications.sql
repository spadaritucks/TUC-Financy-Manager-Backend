
ALTER TABLE notifications DROP CONSTRAINT IF EXISTS fk_future_transaction_notification;
ALTER TABLE notifications DROP CONSTRAINT IF EXISTS fk_goal_notification;
ALTER TABLE notifications DROP CONSTRAINT IF EXISTS fk_transaction_notification;


ALTER TABLE notifications
DROP COLUMN IF EXISTS transaction_id,
DROP COLUMN IF EXISTS future_transaction_id,
DROP COLUMN IF EXISTS goal_id,
DROP COLUMN IF EXISTS notification_status,
ADD COLUMN title VARCHAR(225) NOT NULL;
