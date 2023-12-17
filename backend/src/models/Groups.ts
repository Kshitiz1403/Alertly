export interface GroupsModel {
  group_id: number;
  group_name: string;
  description: string;
  created_at: Date;
}

export enum GroupsTable {
  group_id = 'group_id',
  group_name = 'group_name',
  description = 'description',
  created_at = 'created_at',
}
