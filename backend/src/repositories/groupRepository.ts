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
}
