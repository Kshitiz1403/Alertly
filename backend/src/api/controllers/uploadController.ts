import { Inject, Service } from 'typedi';
import { Result } from '../util/result';
import { INextFunction, IRequest, IResponse } from '../types/express';
import { Logger } from 'winston';
import { UploadService } from '@/services/uploadService';

@Service()
export class UploadController {
  protected uploadServiceInstance: UploadService;
  protected logger: Logger;

  constructor(uploadService: UploadService, @Inject('logger') logger: Logger) {
    this.logger = logger;
    this.uploadServiceInstance = uploadService;
  }

  public uploadMedia = async (req: IRequest, res: IResponse, next: INextFunction) => {
    this.logger.debug('Calling Upload Media endpoint with files: %o', { files: req.files });

    try {
      const data = await this.uploadServiceInstance.uploadFiles(req.currentUser.sub, req.files);
      return res.status(200).json(Result.success(data));
    } catch (e) {
      return next(e);
    }
  };
}
