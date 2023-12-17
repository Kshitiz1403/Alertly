import { Router } from 'express';
import auth from './routes/auth/auth';
import group from './routes/group';

export default () => {
  const app = Router();

  auth(app);
  group(app);
  return app;
};
