import { GroupsModel } from '@/models/Groups';
import { GroupRepository } from '@/repositories/groupRepository';
import { Service } from 'typedi';

@Service()
export class GroupService {
  protected groupRepository: GroupRepository;
  constructor(groupRepository: GroupRepository) {
    this.groupRepository = groupRepository;
  }

  public getAllGroupsForUser = (userID: string) => {};

  public createGroupByUser = async (
    userID: string,
    group_name: GroupsModel['group_name'],
    description: GroupsModel['description'],
  ) => {
    const groupID = await this.groupRepository.createGroupByUser(userID, group_name, description);

    return { groupID, group_name, description };
  };
}
