import { Router } from 'express';
import auth from './routes/auth/auth';

export default () => {
  const app = Router();

  auth(app);
  return app;
};
