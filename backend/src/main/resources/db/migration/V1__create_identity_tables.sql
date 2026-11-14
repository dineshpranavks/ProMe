CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(320),
    display_name VARCHAR(255) NOT NULL,
    profile_image_url TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE linked_accounts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    provider VARCHAR(32) NOT NULL,
    provider_user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_linked_accounts_provider_identity UNIQUE (provider, provider_user_id)
);

CREATE INDEX idx_linked_accounts_user_id ON linked_accounts(user_id);
CREATE INDEX idx_users_email ON users(email);
