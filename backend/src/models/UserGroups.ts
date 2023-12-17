export interface UserGroupsModel {
  user_id: string;
  group_id: number;
  is_admin: boolean;
  created_at: Date;
  pinned: boolean;
}

export enum UserGroupsTable {
  user_id = 'user_id',
  group_id = 'group_id',
  is_admin = 'is_admin',
  created_at = 'created_at',
  pinned = 'pinned',
}
