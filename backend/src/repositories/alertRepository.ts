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
        alerts.title, alerts.description, alerts.severity, alerts.sent_at, users.given_name AS sender_name
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
    }[] = result.rows;
    return groupMessages;
  };
}
