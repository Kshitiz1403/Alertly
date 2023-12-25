import { Router } from 'express';
import Container from 'typedi';
import middlewares from '../middlewares';
import { UploadController } from '../controllers/uploadController';

const route = Router();

export default (app: Router) => {
  const ctrl: UploadController = Container.get(UploadController);

  app.use('/uploads', route);

  route.post(
    '/media',
    middlewares.isAuth,
    middlewares.upload.fields([{ name: 'photos', maxCount: 1 }]),
    ctrl.uploadMedia,
  );
};
