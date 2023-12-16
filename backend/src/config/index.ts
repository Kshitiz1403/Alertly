import dotenv from 'dotenv';

const envFound = dotenv.config();

export default {
  node_env: process.env.NODE_ENV || 'development',

  host: process.env.HOST,

  port: parseInt(process.env.PORT, 10),

  databaseHost: process.env.DB_HOST,

  databasePassword: process.env.DB_PASSWORD,

  databaseUser: process.env.DB_USER,

  databaseName: process.env.DB_NAME,
  /**
   * Your secret sauce
   */
  jwtSecret: process.env.JWT_SECRET,
  jwtAlgorithm: process.env.JWT_ALGO,

  /**
   * Used by winston logger
   */
  logs: {
    level: process.env.LOG_LEVEL || 'silly',
  },

  /**
   * API configs
   */
  api: {
    prefix: '/api',
  },
};
