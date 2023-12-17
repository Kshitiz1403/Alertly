export interface UsersModel {
  id: string;
  email: string;
  picture: string;
  given_name: string;
  family_name: string;
  email_verified: boolean;
  last_login: Date;
}

export enum UsersTable {
  id = 'id',
  email = 'email',
  picture = 'picture',
  given_name = 'given_name',
  family_name = 'family_name',
  email_verified = 'email_verified',
  last_login = 'last_login',
}
