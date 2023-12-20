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

  /**
   * @openapi
   * /api/groups/:
   *  get:
   *    tag:
   *      - Get Groups
   *    description: Gets all groups associated with a user.
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
  group_routes.get('/', middlewares.isAuth, ctrl.getAllGroups);

  group_routes.get('/:group_id', middlewares.isAuth, middlewares.isUserInGroup, ctrl.getGroupAlerts);

  group_routes.get('/:group_id/pin', middlewares.isAuth, middlewares.isUserInGroup, ctrl.pinGroup);

  group_routes.get('/:group_id/unpin', middlewares.isAuth, middlewares.isUserInGroup, ctrl.unpinGroup);

  group_routes.get(
    '/:group_id/access_token',
    middlewares.isAuth,
    middlewares.isUserInGroup,
    ctrl.getAccessTokenForGroup,
  );

  group_routes.post('/join', middlewares.isAuth, ctrl.joinGroupByToken);

  group_routes.post('/:group_id/alert', middlewares.isAuth, middlewares.isUserInGroup, ctrl.createAlert);
};
