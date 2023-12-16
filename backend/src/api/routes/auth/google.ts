import { GoogleAuthController } from '@/api/controllers/auth/googleController';
import { Router } from 'express';
import Container from 'typedi';

const google_route = Router();

export default (app: Router) => {
  const ctrl: GoogleAuthController = Container.get(GoogleAuthController);
  app.use('/google', google_route);

  google_route.get('/callback', ctrl.callback);
};
