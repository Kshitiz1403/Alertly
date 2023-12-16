import config from '@/config';
import { Client } from 'pg';

console.log(config.databaseHost);
console.log(config.databasePassword);
console.log(config.databaseName);
const client = new Client({
  database: config.databaseName,
  host: config.databaseHost,
  password: config.databasePassword,
  user: config.databaseUser,
  port: 5432,
});

export default client;
