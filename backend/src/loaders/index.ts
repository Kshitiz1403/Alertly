import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import postgresLoader from './postgres';
import Logger from './logger';
import fs from 'fs';
import config from '@/config';

export default async ({ expressApp }) => {
  Logger.info('✌️ Migrations ran');
  await postgresLoader.connect();

  Logger.info('✌️ DB loaded and connected!');

  await dependencyInjectorLoader();
  Logger.info('✌️ Dependency Injector loaded');

  if (!fs.existsSync(config.staticFilesPathTemporary)) fs.mkdirSync(config.staticFilesPathTemporary);

  if (!fs.existsSync(config.staticFilesPath)) fs.mkdirSync(config.staticFilesPath);

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};
