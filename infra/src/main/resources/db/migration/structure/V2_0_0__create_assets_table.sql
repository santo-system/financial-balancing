CREATE TABLE IF NOT EXISTS assets (
    id BIGSERIAL PRIMARY KEY,
    goal_id BIGINT NOT NULL,
    ticker VARCHAR(100) NOT NULL,
    name VARCHAR(255),
    segment VARCHAR(255),
    sector VARCHAR(255),
    sub_sector VARCHAR(255),
    asset_type VARCHAR(255),
    average_price NUMERIC(10,2) NOT NULL,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_assets_goals FOREIGN KEY(goal_id)
        REFERENCES goals(id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);