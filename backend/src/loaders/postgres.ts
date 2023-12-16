import config from '@/config';
import { Client } from 'pg';
import Container from 'typedi';

const client = new Client({
  database: config.databaseName,
  host: config.databaseHost,
  password: config.databasePassword,
  user: config.databaseUser,
  port: 5432,
});

Container.set('db', client);

export default client;
