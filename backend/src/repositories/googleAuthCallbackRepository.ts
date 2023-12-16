import { DB_TABLES } from '@/enums/DB_TABLES';
import { IGoogleCallback } from '@/interfaces/IAuth';
import { GoogleAuthCallbackModel, GoogleAuthCallbackTable } from '@/models/GoogleAuthCallbacks';
import { Client } from 'pg';
import { Inject, Service } from 'typedi';

@Service()
export class GoogleAuthCallbackRepository {
  protected db: Client;
  constructor(@Inject('db') db: Client) {
    this.db = db;
  }
  public async saveCallbackData({
    AuthUser,
    OneTimeCode,
    Prompt,
    Scope,
    State,
    Email,
  }: IGoogleCallback & { Email: string }) {
    const query = `INSERT INTO ${DB_TABLES.GOOGLE_AUTH_CALLBACKS}(
      ${GoogleAuthCallbackTable.one_time_code}, 
      ${GoogleAuthCallbackTable.scope}, 
      ${GoogleAuthCallbackTable.auth_user},
      ${GoogleAuthCallbackTable.prompt},
      ${GoogleAuthCallbackTable.state}, 
      ${GoogleAuthCallbackTable.email}
      ) 
      VALUES($1, $2, $3, $4, $5, $6) RETURNING *`;
    const res = await this.db.query(query, [OneTimeCode, Scope, AuthUser, Prompt, State, Email]);
    return res.rows[0] as GoogleAuthCallbackModel;
  }
}
