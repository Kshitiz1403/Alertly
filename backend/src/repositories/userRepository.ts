import { DB_TABLES } from '@/enums/DB_TABLES';
import { UsersModel, UsersTable } from '@/models/Users';
import { Client } from 'pg';
import { Inject, Service } from 'typedi';

@Service()
export class UserRepository {
  protected db: Client;
  constructor(@Inject('db') db: Client) {
    this.db = db;
  }

  public async createUser(id, email, { picture, given_name, family_name, email_verified }) {
    const query = `INSERT INTO ${DB_TABLES.USERS} (
            ${UsersTable.id},
            ${UsersTable.email},
            ${UsersTable.picture},
            ${UsersTable.given_name},
            ${UsersTable.family_name},
            ${UsersTable.email_verified}
        ) VALUES($1, $2, $3, $4, $5, $6) RETURNING *`;
    const res = await this.db.query(query, [id, email, picture, given_name, family_name, email_verified]);
    return res.rows[0] as UsersModel;
  }
}
