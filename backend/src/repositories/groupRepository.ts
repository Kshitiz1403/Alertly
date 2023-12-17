import { DB_TABLES } from '@/enums/DB_TABLES';
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

  public getGroupsForUser = async (userID: string) => {
    try {
      const query = `SELECT groups.group_id, user_groups.pinned, groups.group_name, groups.description, user_groups.is_admin, groups.created_at FROM groups
      INNER JOIN user_groups ON groups.group_id = user_groups.group_id
      WHERE user_groups.user_id = $1`;
      const values = [userID];
      const result = await this.db.query(query, values);

      const joinedGroups = result.rows as (GroupsModel & { pinned: boolean })[];
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
}
