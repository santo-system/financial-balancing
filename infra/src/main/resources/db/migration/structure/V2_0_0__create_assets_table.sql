CREATE TABLE IF NOT EXISTS assets (
    id BIGSERIAL PRIMARY KEY,
    goal_id BIGINT NOT NULL,
    ticker VARCHAR(100) NOT NULL,
    short_name VARCHAR(255) NOT NULL,
    long_name VARCHAR(255) NOT NULL,
    segment VARCHAR(255),
    sector VARCHAR(255),
    sub_sector VARCHAR(255),
    asset_type VARCHAR(255),
    average_price NUMERIC(10,2) NOT NULL,
    market_price_current NUMERIC(10,2),
    market_price_previous_close NUMERIC(10,2),
    quantity INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE (ticker, goal_id),
    CONSTRAINT fk_assets_goals FOREIGN KEY(goal_id)
        REFERENCES goals(id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);
