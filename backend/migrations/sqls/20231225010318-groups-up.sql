ALTER TABLE
  groups
ADD
  COLUMN image_uri_id INT REFERENCES uploads(upload_id);