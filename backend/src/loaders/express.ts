import express from 'express';
import cors from 'cors';
import routes from '@/api';
import config from '@/config';
import LoggerInstance from './logger';
import { Result } from '@/api/util/result';
import path from 'path';
import swaggerDocs, { swaggerSpec } from './swagger';
import swaggerUI from 'swagger-ui-express';

export default ({ app }: { app: express.Application }) => {
  /**
   * Health Check endpoints
   * @TODO Explain why they are here
   */
  app.get('/status', (req, res) => {
    res.status(200).json({ status: 'OK' });
  });
  app.head('/status', (req, res) => {
    res.status(200).end();
  });

  // Useful if you're behind a reverse proxy (Heroku, Bluemix, AWS ELB, Nginx, etc)
  // It shows the real origin IP in the heroku or Cloudwatch logs
  app.enable('trust proxy');

  // The magic package that prevents frontend developers going nuts
  // Alternate description:
  // Enable Cross Origin Resource Sharing to all origins by default

  // Some sauce that always add since 2014
  // "Lets you use HTTP verbs such as PUT or DELETE in places where the client doesn't support it."
  // Maybe not needed anymore ?
  app.use(require('method-override')());

  // Transforms the raw string of req.body into json
  app.use(express.json());

  // Docs routes
  swaggerDocs(app);

  // Load API routes
  app.use(config.api.prefix, routes());

  app.use('/static', express.static(config.staticFilesPath));

  /// catch 404 and forward to error handler
  app.use((req, res, next) => {
    const err = new Error('Not Found');
    err['status'] = 404;
    next(err);
  });

  /// error handlers
  app.use((err, req, res, next) => {
    /**
     * Handles authorization errors
     */
    if (err.status === 401) {
      LoggerInstance.error('🔥 error: %o', err);
      return res.status(err.status).json(Result.error(err.message));
    }
    LoggerInstance.error(err.stack || '%o', err);
    return next(err);
  });
  app.use((err, req, res, next) => {
    res.status(err.status || 400);
    res.json(Result.error(err.message || err));
  });
};
