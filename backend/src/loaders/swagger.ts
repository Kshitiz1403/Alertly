import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUI from 'swagger-ui-express';
import { version } from '@/../package.json';
import {} from '../api/index';
import express, { Router } from 'express';

const options: swaggerJsdoc.Options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Alertly REST API Docs',
      version,
    },
    components: {
      securitySchemas: {
        bearerAuth: {
          type: 'http',
          scheme: 'bearer',
          bearerFormat: 'JWT',
        },
      },
    },
    security: [
      {
        bearerAuth: [],
      },
    ],
  },
  apis: [
    // 'src/api/index.ts',
    // 'src/api/routes/auth/auth.ts',
    'src/api/routes/*/*.ts',
  ],
};

export const swaggerSpec = swaggerJsdoc(options);

function swaggerDocs(app: Router) {
  app.use('/docs', swaggerUI.serve, swaggerUI.setup(swaggerSpec));
}
export default swaggerDocs;
