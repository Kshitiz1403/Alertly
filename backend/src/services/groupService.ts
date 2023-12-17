import { GroupsModel } from '@/models/Groups';
import { AlertRepository } from '@/repositories/alertRepository';
import { GroupRepository } from '@/repositories/groupRepository';
import { Service } from 'typedi';

@Service()
export class GroupService {
  protected groupRepository: GroupRepository;
  protected alertRepository: AlertRepository;
  constructor(groupRepository: GroupRepository, alertRepo: AlertRepository) {
    this.groupRepository = groupRepository;
    this.alertRepository = alertRepo;
  }

  public getAllGroupsForUser = async (userID: string) => {
    const groups = await this.groupRepository.getGroupsForUser(userID);

    return groups;
  };

  public createGroupByUser = async (
    userID: string,
    group_name: GroupsModel['group_name'],
    description: GroupsModel['description'],
  ) => {
    const groupID = await this.groupRepository.createGroupByUser(userID, group_name, description);

    return { groupID, group_name, description };
  };

  public getAlertsInGroup = async (group_id: number, pageNumber: number, pageSize: number) => {
    if (isNaN(pageNumber)) pageNumber = 1;
    if (isNaN(pageSize)) pageSize = 20;
    const alerts = await this.alertRepository.getGroupAlerts(group_id, pageNumber, pageSize);
    return alerts;
  };

  public pinGroupForUser = async (group_id: number, user_id: string) => {
    await this.groupRepository.pinGroupForUser(user_id, group_id);
    return;
  };
  public unpinGroupForUser = async (group_id: number, user_id: string) => {
    await this.groupRepository.unpinGroupForUser(user_id, group_id);
    return;
  };
}
