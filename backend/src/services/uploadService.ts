import config from '@/config';
import { UploadRepository } from '@/repositories/uploadRepository';
import { readFile, unlink, writeFile } from 'fs/promises';
import { resolve } from 'path';
import { Inject } from 'typedi';
import { Logger } from 'winston';

export class UploadService {
  protected logger: Logger;
  protected uploadRepository: UploadRepository;
  constructor(@Inject('logger') logger: Logger, uploadRepository: UploadRepository) {
    this.logger = logger;
    this.uploadRepository = uploadRepository;
  }

  public uploadFiles = async (
    userId: string,
    files: { [fieldname: string]: Express.Multer.File[] } | Express.Multer.File[],
  ) => {
    const photosPromises = [];

    if (files && files['photos']) {
      const photos = files['photos'];
      photos.map(photo => {
        const fileName = photo.originalname;
        const path = resolve(photo.path);
        const metaData = { userId, original_name: photo.originalname };
        const fileType = photo.mimetype;
        photosPromises.push(this.uploadFile(userId, path, fileName, JSON.stringify(metaData)));
      });
    }

    const photos = await Promise.all(photosPromises);

    return { photos };
  };

  private uploadFile = async (userId: string, originalFilePath: string, sourceFileName: string, metadata: string) => {
    const fileBytes = await readFile(originalFilePath);

    const { extension, originalFileName } = this.extractFileNameAndExtension(sourceFileName);
    const fileName = originalFileName + '-' + Date.now() + '.' + extension;
    const fileToWritePath = config.staticFilesPath + fileName;

    await writeFile(fileToWritePath, fileBytes);
    unlink(originalFilePath);

    const uploadRecord = await this.uploadRepository.createUploadRecord(userId, fileName, metadata);

    const uploadRecordHostedPath = {
      ...uploadRecord,
      uri: encodeURI(`https://alertly.kshitizagrawal.in/static/${uploadRecord.path}`),
    };

    return uploadRecordHostedPath;
  };

  private extractFileNameAndExtension = filename => {
    const parts = filename.split('.');
    const extension = parts.pop(); // Get the extension (last part after splitting by '.')

    // Join the remaining parts to get the original file name
    const originalFileName = parts.join('.');

    return { originalFileName, extension };
  };
}
