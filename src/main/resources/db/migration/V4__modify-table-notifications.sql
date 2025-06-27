
ALTER TABLE notifications DROP FOREIGN KEY fk_future_transaction_notification;
ALTER TABLE notifications DROP FOREIGN KEY fk_goal_notification;
ALTER TABLE notifications DROP FOREIGN KEY fk_transaction_notification;


ALTER TABLE notifications
DROP COLUMN transaction_id,
DROP COLUMN future_transaction_id,
DROP COLUMN goal_id,
DROP COLUMN notification_status,
ADD COLUMN title VARCHAR(225) NOT NULL AFTER notification_type;
