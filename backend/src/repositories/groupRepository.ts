import { DB_TABLES } from '@/enums/DB_TABLES';
import { GroupTokens } from '@/models/GroupTokens';
import { GroupsModel, GroupsTable } from '@/models/Groups';
import { UserGroupsTable } from '@/models/UserGroups';
import { Client } from 'pg';
import { Inject, Service } from 'typedi';

@Service()
export class GroupRepository {
  protected db: Client;
  constructor(@Inject('db') db: Client) {
    this.db = db;
  }

  public createGroupByUser = async (
    userID: string,
    group_name: GroupsModel['group_name'],
    description: GroupsModel['description'],
  ) => {
    try {
      await this.db.query('BEGIN');

      // Insert group details into the groups table

      const groupInsertQuery = `INSERT INTO ${DB_TABLES.GROUPS} (${GroupsTable.group_name},${GroupsTable.description})VALUES ($1, $2) RETURNING group_id`;
      const groupInsertValues = [group_name, description];
      const groupInsertResult = await this.db.query(groupInsertQuery, groupInsertValues);

      const groupId: GroupsModel['group_id'] = groupInsertResult.rows[0].group_id;

      // Insert user as admin of the created group in user_groups table
      const userGroupInsertQuery = `INSERT INTO ${DB_TABLES.USER_GROUPS} (${UserGroupsTable.user_id}, ${UserGroupsTable.group_id}, ${UserGroupsTable.is_admin}) VALUES ($1, $2, $3)`;

      const userGroupInsertValues = [userID, groupId, true];

      await this.db.query(userGroupInsertQuery, userGroupInsertValues);

      await this.db.query('COMMIT');

      return groupId;
    } catch (error) {
      await this.db.query('ROLLBACK');
      throw error;
    }
  };

  public joinUserToGroup = async (userID: string, group_id: number) => {
    try {
      const userGroupInsertQuery = `INSERT INTO user_groups (user_id, group_id) VALUES ($1, $2) RETURNING group_id`;

      const userGroupInsertValues = [userID, group_id];

      const result = await this.db.query(userGroupInsertQuery, userGroupInsertValues);

      return result.rows[0] as number;
    } catch (error) {
      throw error;
    }
  };

  public getGroupsForUser = async (userID: string) => {
    try {
      const query = `SELECT groups.group_id, user_groups.pinned, groups.group_name, groups.description, user_groups.is_admin, groups.created_at, uploads.path as group_image_path FROM groups
      INNER JOIN user_groups ON groups.group_id = user_groups.group_id
      LEFT JOIN uploads ON groups.image_uri_id = uploads.upload_id
      WHERE user_groups.user_id = $1`;
      const values = [userID];
      const result = await this.db.query(query, values);

      const joinedGroups = result.rows as (GroupsModel & { pinned: boolean; group_image_path: string })[];
      return joinedGroups;
    } catch (error) {}
  };

  public pinGroupForUser = async (userID: string, groupID: number) => {
    const query = `UPDATE  user_groups SET pinned = TRUE WHERE 
    user_id = $1 AND group_id= $2;`;
    const values = [userID, groupID];
    await this.db.query(query, values);
    return;
  };

  public unpinGroupForUser = async (userID: string, groupID: number) => {
    const query = `UPDATE  user_groups SET pinned = FALSE WHERE 
    user_id = $1 AND group_id= $2;`;
    const values = [userID, groupID];
    await this.db.query(query, values);
    return;
  };

  public checkUserExists = async (user_id: string, group_id: number) => {
    const query = `SELECT EXISTS (
    SELECT 1
    FROM user_groups
    WHERE user_id = $1 AND group_id = $2
) AS user_exists_in_group;`;
    const values = [user_id, group_id];
    const result = await this.db.query(query, values);
    return result.rows[0].user_exists_in_group as boolean;
  };

  public getTokenForGroup = async (group_id: number) => {
    const query = `SELECT * FROM group_tokens WHERE group_id = $1 ORDER BY created_at DESC LIMIT 1;`;

    const result = await this.db.query(query, [group_id]);
    return result.rows[0] as GroupTokens;
  };

  public createTokenForGroup = async (token: string, created_by: string, group_id: number) => {
    const query = `INSERT INTO group_tokens (token, created_by, group_id) VALUES ($1, $2, $3) returning token, group_id`;
    const result = await this.db.query(query, [token, created_by, group_id]);

    return result.rows[0] as { token: string; group_id: number };
  };

  public getGroupByToken = async (token: string) => {
    const query = `SELECT * FROM group_tokens WHERE token = $1`;
    const result = await this.db.query(query, [token]);

    return result.rows[0] as GroupTokens;
  };

  public updateGroupAvatar = async (group_id: number, upload_id: number) => {
    const result = await this.db.query(`SELECT EXISTS (SELECT 1 FROM uploads WHERE upload_id=$1)`, [upload_id]);
    const exists = result.rows[0].exists as boolean;
    if (!exists) throw 'the requested image is not found';

    const query = `UPDATE groups SET image_uri_id=$1 WHERE group_id=$2`;
    const updateResult = await this.db.query(query, [upload_id, group_id]);

    const q = `SELECT group_id, group_name, description, path as group_image_path 
    FROM groups 
    JOIN uploads ON groups.image_uri_id = uploads.upload_id
    WHERE group_id=$1`;

    const groupResultRows = await this.db.query(q, [group_id]);
    const groupResult = groupResultRows.rows[0] as {
      group_id: string;
      group_name: string;
      description: string;
      group_image_path: string;
    };
    return groupResult;
  };
}
