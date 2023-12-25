import { Client } from 'pg';
import { Inject, Service } from 'typedi';

@Service()
export class UploadRepository {
  protected db: Client;
  constructor(@Inject('db') db: Client) {
    this.db = db;
  }

  public createUploadRecord = async (user_id: string, file_path: string, metadata: string = '') => {
    const query = `INSERT INTO uploads (user_id, metadata, path) VALUES ($1, $2, $3) RETURNING upload_id, user_id, metadata, path, created_at`;
    const result = await this.db.query(query, [user_id, metadata, file_path]);

    return result.rows[0] as { upload_id: string; user_id: string; metadata: string; path: string; created_at: Date };
  };
}
