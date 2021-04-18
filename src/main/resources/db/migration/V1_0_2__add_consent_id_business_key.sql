SET search_path TO patronative;

ALTER TABLE consent ADD COLUMN consent_id NUMERIC(19, 0);
UPDATE consent SET consent_id = 1 WHERE id = 1;
UPDATE consent SET consent_id = 2 WHERE id = 11;
ALTER TABLE consent ALTER COLUMN consent_id SET NOT NULL;