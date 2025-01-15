CREATE TABLE exchange_rate (
    ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 自動生成唯一的ID
    currency VARCHAR(10) NOT NULL, -- cur 欄位不能為空
    exchange_rate NUMERIC(10, 6) NOT NULL, -- 匯率
    exchange_rate_date date NOT NULL, -- exchange_rate_date 欄位格式為 YYYY-MM-DD，不能為空
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 預設為當前時間
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL -- 預設為當前時間
);

-- 建立 exchange_rate 的 TRIGGER FUNCTION，當更新行時自動更新 update_at 欄位
CREATE OR REPLACE FUNCTION set_exchange_rate_update_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 為 exchange_rate 表格建立 TRIGGER
CREATE TRIGGER trigger_exchange_rate_update_at
BEFORE UPDATE ON exchange_rate
FOR EACH ROW
EXECUTE FUNCTION set_exchange_rate_update_at();

--INSERT INTO public.exchange_rate
--( currency, exchange_rate, exchange_rate_date)
--VALUES( 'USD/TWD', 35.210000, '2024-10-20')