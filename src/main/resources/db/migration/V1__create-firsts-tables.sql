CREATE TABLE users (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_photo TEXT,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   phone VARCHAR(20) NOT NULL,
   monthly_income DECIMAL(10,2) NOT NULL,
   password VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   category_name VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subcategories (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   category_id UUID NOT NULL,
   subcategory_name VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_id UUID NOT NULL,
   subcategory_id UUID,
   transaction_type VARCHAR(50) NOT NULL,
   transaction_value DECIMAL(10,2) NOT NULL,
   description TEXT,
   transaction_status VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE future_transactions (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_id UUID NOT NULL,
   subcategory_id UUID,
   transaction_type VARCHAR(50) NOT NULL,
   transaction_value DECIMAL(10,2) NOT NULL,
   description TEXT,
   recurrent BOOLEAN NOT NULL,
   transaction_status VARCHAR(50) NOT NULL,
   recurrence_frequency VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_ft FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory_ft FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE goals (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_id UUID NOT NULL,
   subcategory_id UUID,
   goal_name VARCHAR(50) NOT NULL,
   target_value DECIMAL(10,2) NOT NULL,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   goal_status VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_goal FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_subcategory_goal FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE
);

CREATE TABLE reports (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_id UUID NOT NULL,
   report_type VARCHAR(50) NOT NULL,
   transaction_id UUID,
   future_transaction_id UUID,
   goal_id UUID,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_report FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_transaction_report FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_future_transaction_report FOREIGN KEY (future_transaction_id) REFERENCES future_transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_goal_report FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   user_id UUID NOT NULL,
   notification_type VARCHAR(50) NOT NULL,
   transaction_id UUID,
   future_transaction_id UUID,
   goal_id UUID,
   message VARCHAR(500) NOT NULL,
   notification_status BOOLEAN NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT fk_transaction_notification FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_future_transaction_notification FOREIGN KEY (future_transaction_id) REFERENCES future_transactions(id) ON DELETE CASCADE,
   CONSTRAINT fk_goal_notification FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
);

-- Criar função para atualizar o updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Criar triggers para atualizar updated_at automaticamente
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_categories_updated_at BEFORE UPDATE ON categories FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_subcategories_updated_at BEFORE UPDATE ON subcategories FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_transactions_updated_at BEFORE UPDATE ON transactions FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_future_transactions_updated_at BEFORE UPDATE ON future_transactions FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_goals_updated_at BEFORE UPDATE ON goals FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_reports_updated_at BEFORE UPDATE ON reports FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_notifications_updated_at BEFORE UPDATE ON notifications FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
