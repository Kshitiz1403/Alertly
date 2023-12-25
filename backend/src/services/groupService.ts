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
    const groupsWithImageURI = groups.map(group => ({
      ...group,
      group_image_uri: group.group_image_path
        ? encodeURI(`https://alertly.kshitizagrawal.in/static/${group.group_image_path}`)
        : 'https://i.ibb.co/0cW8Bsv/group.png',
      group_image_path: undefined,
    }));
    return groupsWithImageURI;
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

  public getAccessTokenForGroup = async (userID, group_id: number) => {
    /*
    TODO
      - Generate a hash for a group_id along with a salt.
      - Convert hash to base32.
      - Use this base32 string to sign a TOTP at the current time.
      */

    try {
      const token = await this.groupRepository.getTokenForGroup(group_id);
      return token.token;
    } catch (error) {
      try {
        const token = await this.groupRepository.createTokenForGroup(this.generateOTP(6), userID, group_id);
        return token.token;
      } catch (error) {
        throw error;
      }
    }
  };

  public joinWithAccessToken = async (userId: string, accessToken: string) => {
    try {
      const group = await this.groupRepository.getGroupByToken(accessToken);

      const currentTime = new Date();
      const expirationTime = new Date(group.created_at);
      expirationTime.setTime(expirationTime.getMinutes() + 30); // 30 mins expiry

      if (currentTime < expirationTime) throw { status: 401, message: "the token doesn't exist or is expired" };

      await this.groupRepository.joinUserToGroup(userId, group.group_id);

      return { group_id: group.group_id };
    } catch (error) {
      throw { status: 401, message: "the token doesn't exist or is expired" };
    }
  };

  public updateGroupAvatar = async (group_id: number, upload_id: number) => {
    try {
      const updatedGroup = await this.groupRepository.updateGroupAvatar(group_id, upload_id);
      const groupWthImageURI = {
        ...updatedGroup,
        group_image_uri: updatedGroup.group_image_path
          ? encodeURI(`https://alertly.kshitizagrawal.in/static/${updatedGroup.group_image_path}`)
          : 'https://i.ibb.co/0cW8Bsv/group.png',
        group_image_path: undefined,
      };
      return groupWthImageURI;
    } catch (error) {
      throw error;
    }
  };

  private generateOTP = (length: number) => {
    return Math.random().toFixed(length).substr(-length);
  };
}
