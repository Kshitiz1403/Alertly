import config from '@/config';
import { Client } from 'pg';

const client = new Client({
  database: config.databaseName,
  host: config.databaseHost,
  password: config.databasePassword,
  user: config.databaseUser,
  port: 5432,
});

export default client;
