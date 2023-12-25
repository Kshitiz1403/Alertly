import { Inject, Service } from 'typedi';
import { IGroupRequest, INextFunction, IRequest, IResponse } from '../types/express';
import { Logger } from 'winston';
import { Result } from '../util/result';
import { GroupService } from '@/services/groupService';
import { AlertService } from '@/services/alertService';

@Service()
export class GroupController {
  protected logger: Logger;
  protected groupService: GroupService;
  protected alertService: AlertService;
  constructor(@Inject('logger') logger: Logger, groupService: GroupService, alertService: AlertService) {
    this.logger = logger;
    this.groupService = groupService;
    this.alertService = alertService;
  }

  public getAllGroups = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get All Groups endpoint with query: %o', req.query);
    try {
      const userID = req.currentUser.sub;

      const groups = await this.groupService.getAllGroupsForUser(userID);

      return res.status(200).json(Result.success(groups));
    } catch (error) {
      return next(error);
    }
  };

  public createGroup = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get All Groups endpoint with body: %o', req.body);
    try {
      const userID = req.currentUser.sub;
      const { group_name, description } = req.body;

      const group = await this.groupService.createGroupByUser(userID, group_name, description);

      return res.status(200).json(Result.success(group));
    } catch (error) {
      return next(error);
    }
  };

  public getGroupAlerts = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get Group Alerts endpoint with query: %o', req.params);
    try {
      const { group_id } = req.params;
      const { pageNumber, pageSize } = req.query;

      const alerts = await this.groupService.getAlertsInGroup(+group_id, +pageNumber, +pageSize);
      return res.status(200).json(Result.success(alerts));
    } catch (error) {
      return next(error);
    }
  };

  public pinGroup = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get Group Alerts endpoint with query: %o', req.params);
    try {
      const { group_id } = req.params;
      await this.groupService.pinGroupForUser(+group_id, req.currentUser.sub);
      return res.status(200).json(Result.success());
    } catch (error) {
      return next(error);
    }
  };

  public unpinGroup = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get Group Alerts endpoint with query: %o', req.params);
    try {
      const { group_id } = req.params;
      await this.groupService.unpinGroupForUser(+group_id, req.currentUser.sub);
      return res.status(200).json(Result.success());
    } catch (error) {
      return next(error);
    }
  };

  public getAccessTokenForGroup = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get Acess Token for Group endpoint with query: %o', req.params);
    try {
      const { group_id } = req.params;
      if (isNaN(+group_id)) throw 'invalid group id';

      const access_token = await this.groupService.getAccessTokenForGroup(req.currentUser.sub, +group_id);

      return res.status(200).json(Result.success(access_token));
    } catch (error) {
      return next(error);
    }
  };

  public joinGroupByToken = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Join Group by Acess Token with body: %o', req.body);
    try {
      const accessToken = req.body.access_token;
      const { sub } = req.currentUser;
      const group = await this.groupService.joinWithAccessToken(sub, accessToken);

      return res.status(200).json(Result.success(group));
    } catch (error) {
      return next(error);
    }
  };

  public createAlert = async (req: IGroupRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Create Alert endpoint with body %o', req.body);

    try {
      const { title, description, severity } = req.body;
      const alert = await this.alertService.createAlert(
        req.currentUser.sub,
        req.group_id,
        title,
        description,
        severity,
      );
      return res.status(200).json(Result.success(alert));
    } catch (error) {
      return next(error);
    }
  };

  public updateGroupAvatar = async (req: IGroupRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Update Group Avatar endpoint with body %o', req.body);

    try {
      const { upload_id } = req.body;
      if (isNaN(+upload_id)) throw 'invalid upload id';
      const group = await this.groupService.updateGroupAvatar(req.group_id, +upload_id);
      return res.status(200).json(Result.success(group));
    } catch (error) {
      return next(error);
    }
  };
}
