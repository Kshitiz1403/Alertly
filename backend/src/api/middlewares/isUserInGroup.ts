import { Logger } from 'winston';
import { IGroupRequest, INextFunction, IRequest, IResponse } from '../types/express';
import Container from 'typedi';
import { GroupRepository } from '@/repositories/groupRepository';

const isUserInGroup = async (req: IGroupRequest, res: IResponse, next: INextFunction) => {
  const logger: Logger = Container.get('logger');

  const user_id = req.currentUser.sub;
  try {
    const { group_id } = req.params;
    if (isNaN(+group_id)) return next('invalid group id');

    const groupRepository: GroupRepository = Container.get(GroupRepository);

    const exists = await groupRepository.checkUserExists(user_id, +group_id);

    if (!exists)
      throw {
        status: 401,
        message: 'you are not authorized to access this resource',
      };
    req.group_id = +group_id;
    logger.info(`User ${user_id} granted access to the group ${group_id}`);
    return next();
  } catch (error) {
    logger.info(`User ${user_id} denied access to the group ${req.params.group_id}`);

    return next(error);
  }
};

export default isUserInGroup;
