-- Migration V8: Adicionar user_id nas tabelas categories e subcategories para torná-las privadas por usuário

-- Adicionar coluna user_id na tabela categories
ALTER TABLE categories ADD COLUMN user_id UUID;

-- Adicionar foreign key para users na tabela categories
ALTER TABLE categories ADD CONSTRAINT fk_categories_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- Adicionar coluna user_id na tabela subcategories
ALTER TABLE subcategories ADD COLUMN user_id UUID ;

-- Adicionar foreign key para users na tabela subcategories
ALTER TABLE subcategories ADD CONSTRAINT fk_subcategories_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
