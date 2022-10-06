CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS wallets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    main BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS goals (
    id BIGSERIAL PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    goalPercent NUMERIC(5,2) NOT NULL,
    currentPercent NUMERIC(5,2) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_goals_wallets FOREIGN KEY(wallet_id)
        REFERENCES wallets(id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);
