import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import postgresLoader from './postgres';
import Logger from './logger';
import admin, { initializeApp } from './firebase';

export default async ({ expressApp }) => {
  Logger.info('✌️ Migrations ran');
  await postgresLoader.connect();

  Logger.info('✌️ DB loaded and connected!');

  await initializeApp();
  Logger.info('✌️ Firebase loaded');

  await dependencyInjectorLoader();
  Logger.info('✌️ Dependency Injector loaded');

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};
