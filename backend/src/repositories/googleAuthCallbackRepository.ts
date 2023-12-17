import { DB_TABLES } from '@/enums/DB_TABLES';
import { IGoogleCallback } from '@/interfaces/IAuth';
import { GoogleAuthCallbackModel, GoogleAuthCallbackTable } from '@/models/GoogleAuthCallbacks';
import { Client } from 'pg';
import { Inject, Service } from 'typedi';

const GOOGLE_AUTH_CALLBACKS = DB_TABLES.GOOGLE_AUTH_CALLBACKS;
const GACTable = GoogleAuthCallbackTable;
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
    const query = `INSERT INTO ${GOOGLE_AUTH_CALLBACKS}(
      ${GACTable.one_time_code}, 
      ${GACTable.scope}, 
      ${GACTable.auth_user},
      ${GACTable.prompt},
      ${GACTable.state}, 
      ${GACTable.email}
      ) 
      VALUES($1, $2, $3, $4, $5, $6) RETURNING *`;
    const res = await this.db.query(query, [OneTimeCode, Scope, AuthUser, Prompt, State, Email]);
    return res.rows[0] as GoogleAuthCallbackModel;
  }

  public async getLatestOTCByEmail(email: string) {
    const query = `SELECT ${GACTable.one_time_code} FROM ${GOOGLE_AUTH_CALLBACKS} WHERE ${GACTable.email}=$1 ORDER BY ${GACTable.created_at} DESC LIMIT 1;`;
    const res = await this.db.query(query, [email]);
    return res.rows[0][GACTable.one_time_code] as GoogleAuthCallbackModel['one_time_code'];
  }
}
