import { Router } from 'express';
import google from './google';

const auth_route = Router();

export default (app: Router) => {
  app.use('/auth', auth_route);

  google(auth_route);
};
