CREATE TABLE schedule_date (
    ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 自動生成唯一的ID
    name VARCHAR(20) NOT NULL, -- name 欄位不能為空
    currency VARCHAR(10) NOT NULL, -- cur 欄位不能為空
    execute_time_at TIME NOT NULL, -- time 欄位格式為 HH:MM:SS，不能為空
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 預設為當前時間
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL -- 預設為當前時間
);

-- 建立 TRIGGER FUNCTION，當更新行時自動更新 update_at 欄位
CREATE OR REPLACE FUNCTION set_update_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 建立 TRIGGER，觸發 set_update_at_column 函數
CREATE TRIGGER trigger_update_at
BEFORE UPDATE ON schedule_date
FOR EACH ROW
EXECUTE FUNCTION set_update_at_column();

--INSERT INTO public.schedule_date(name, currency ,execute_time_at)
--VALUES( 'autoAPI', 'USD/NTD', '18:00:00')

