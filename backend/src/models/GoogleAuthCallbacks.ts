export interface GoogleAuthCallbackModel {
  one_time_code: string;
  scope: string;
  auth_user: string;
  prompt: string;
  state: string;
  email: string;
  created_at: Date;
}

export enum GoogleAuthCallbackTable {
  one_time_code = 'one_time_code',
  scope = 'scope',
  auth_user = 'auth_user',
  prompt = 'prompt',
  state = 'state',
  email = 'email',
  created_at = 'created_at',
}
