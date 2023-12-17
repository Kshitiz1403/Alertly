import { Logger } from 'winston';
import { INextFunction, IRequest, IResponse } from '../types/express';
import Container from 'typedi';
import { GroupRepository } from '@/repositories/groupRepository';

const isUserInGroup = async (req: IRequest, res: IResponse, next: INextFunction) => {
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
    return next();
  } catch (error) {
    return next(error);
  }
};

export default isUserInGroup;
