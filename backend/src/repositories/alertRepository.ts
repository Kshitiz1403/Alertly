import { Client } from 'pg';
import { Inject, Service } from 'typedi';

@Service()
export class AlertRepository {
  protected db: Client;
  constructor(@Inject('db') db: Client) {
    this.db = db;
  }

  public getGroupAlerts = async (group_id: number, pageNumber: number, pageSize: number) => {
    const offset = (pageNumber - 1) * pageSize;
    const query = `SELECT alerts.alert_id, alerts.group_id, alerts.user_id AS message_sender_id,
        alerts.title, alerts.description, alerts.severity, alerts.sent_at, users.given_name AS sender_name, users.picture as sender_image_uri
      FROM alerts
      INNER JOIN users ON alerts.user_id = users.id
      WHERE alerts.group_id = $1
      ORDER BY alerts.sent_at DESC
      LIMIT $2 OFFSET $3
    `;

    const values = [group_id, pageSize, offset];
    const result = await this.db.query(query, values);

    const groupMessages: {
      alert_id: string;
      group_id: number;
      message_sender_id: string;
      sender_name: string;
      title: string;
      description: string;
      severity: string;
      sent_at: Date;
      sender_image_uri: string;
    }[] = result.rows;
    return groupMessages;
  };

  public createAlert = async (userID, groupID, title, description, severity: 'normal' | 'elevated' | 'danger') => {
    try {
      const query = `INSERT INTO alerts (group_id, user_id, title, description, severity) VALUES ($1, $2, $3, $4, $5) RETURNING alert_id, sent_at, user_id, severity as seve, description as desc, title as tit`;
      const result = await this.db.query(query, [groupID, userID, title, description, severity]);
      const { alert_id, sent_at, user_id, seve, desc, tit } = result.rows[0];

      const userQuery = `SELECT given_name, family_name FROM users WHERE id = $1`;
      const userResult = await this.db.query(userQuery, [userID]);
      const { given_name, family_name } = userResult.rows[0];

      return { alert_id, sent_at, given_name, family_name, user_id, severity: seve, description: desc, title: tit } as {
        alert_id: number;
        sent_at: Date;
        given_name: string;
        family_name: string;
        user_id: string;
        severity: string;
        description: string;
        title: string;
      };
    } catch (error) {
      throw error;
    }
  };
}
