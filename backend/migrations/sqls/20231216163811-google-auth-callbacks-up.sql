CREATE TABLE google_auth_callbacks (
  one_time_code text,
  scope text,
  auth_user text,
  prompt text,
  state text,
  email text,
  created_at timestamp default current_timestamp
 );
