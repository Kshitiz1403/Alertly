import { Router } from 'express';
import auth from './routes/auth/auth';
import group from './routes/group';
import upload from './routes/upload';

export default () => {
  const app = Router();

  auth(app);
  group(app);
  upload(app);
  return app;
};
