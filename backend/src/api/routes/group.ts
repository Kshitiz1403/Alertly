import { Router } from 'express';
import middlewares from '../middlewares';
import { GroupController } from '../controllers/groupController';
import Container from 'typedi';

const group_routes = Router();

export default (app: Router) => {
  const ctrl: GroupController = Container.get(GroupController);
  app.use('/groups', group_routes);

  /**
   * @openapi
   * /api/groups/create:
   *  post:
   *    consumes:
   *      - application/json
   *    tag:
   *      - Create Group
   *    description: Creates a group with putting the user creating group as admin. Recieves a group name and a group description.
   *    requestBody:
   *      content:
   *        application/json:
   *          name: Create Group
   *          schema:
   *            type: object
   *            properties:
   *              group_name:
   *                type: string
   *                default: test-group
   *              description:
   *                type: string
   *                default: yoyo
   *    responses:
   *      200:
   *        description: 'OK'
   *        content:
   *          application/json:
   *            schema:
   *              type: object
   *              properties:
   *                success:
   *                  type: boolean
   *                  default: true
   *                data:
   *                  type: object
   *                  properties:
   *                    groupID:
   *                      type: number
   *                      default: 7
   *                    group_name:
   *                      type: string
   *                      default: test-group
   *                    description:
   *                      type: string
   *                      default: yoyo
   */
  group_routes.post('/create', middlewares.isAuth, ctrl.createGroup);
};
