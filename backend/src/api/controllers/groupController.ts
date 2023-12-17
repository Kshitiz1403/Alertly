import { Inject, Service } from 'typedi';
import { INextFunction, IRequest, IResponse } from '../types/express';
import { Logger } from 'winston';
import { Result } from '../util/result';
import { GroupService } from '@/services/groupService';

@Service()
export class GroupController {
  protected logger: Logger;
  protected groupService: GroupService;
  constructor(@Inject('logger') logger: Logger, groupService: GroupService) {
    this.logger = logger;
    this.groupService = groupService;
  }

  public getAllGroups = (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Get All Groups endpoint with query: %o', req.query);
    try {
      const userID = req.currentUser.sub;

      const groups = [];

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
}
