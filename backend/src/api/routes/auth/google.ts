import { GoogleAuthController } from '@/api/controllers/auth/googleController';
import { Router } from 'express';
import Container from 'typedi';

const google_route = Router();

export default (app: Router) => {
  const ctrl: GoogleAuthController = Container.get(GoogleAuthController);
  app.use('/google', google_route);

  /**
   * @openapi
   * /api/auth/google/callback:
   *  get:
   *    tag:
   *      - Callback
   *    description: Gets called when google authentication is successful.
   *    responses:
   *      200:
   *        description: 'OK'
   *        content:
   *          application/json:
   *            schema:
   *              type: object
   *              properties:
   *                success:
   *                  type: boolean
   *                  default: true
   *                data:
   *                  type: string
   *                  default: Google Authentication Callback successfull
   */
  google_route.get('/callback', ctrl.callback);

  /**
   * @openapi
   * /api/auth/google/login:
   *  post:
   *    consumes:
   *      - application/json
   *    tag:
   *      - Login
   *    description: Recieves a google id_token after google signin is successful. Returns a unique id, email, jwt encoded payload.
   *    parameters:
   *      - in: body
   *        name: id_token
   *        required:
   *          - id_token
   *        schema:
   *          type: object
   *          properties:
   *            id_token:
   *              type: string
   *              default: eyJhbGciOiJSUzI1NiIsImtpZCI6IjBhZDFmZWM3ODUwNGY0NDdiYWU2NWJjZjVhZmFlZGI2NWVlYzllODEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyODIzMTI2MDQwMzEtbDQyNzQzMWltMjJnM3Q0YmJuM2VvaGFxdHMzZDRobmQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyODIzMTI2MDQwMzEtaWlpcTI0aTQ0a2RyZjJyZmM2dWg1dWI5YWJqNjBpbGwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MDQyMDU3Nzg3MzI1MTUzMzMiLCJlbWFpbCI6Im5pcmFqcGF0aWRhcjgxQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiTmlyYWogUGF0aWRhciIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJNDFfNXhjNllaNW9SNmlrZ3Y2bDhYVzd6Z2NxRkRwYW9aaU9uUy1KVUFabTlHPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik5pcmFqIiwiZmFtaWx5X25hbWUiOiJQYXRpZGFyIiwibG9jYWxlIjoiZW4tR0IiLCJpYXQiOjE3MDI3OTE2MzQsImV4cCI6MTcwMjc5NTIzNH0.mMvcsmU3dqf7ywv6ij0ghQCwvLDxQApxsSpbPVaElQzQdIAmDYQMDf3wDXyHtcexYB3yeSFR9ObTw-YFYwTU2HyFULcoFYvWV35PUhHFZ98X8a_5zfiB5ivn_Wb8do_eO_1z64v7H8icHYbBs1aejLrWWnBgARzRz-aU4aMorOtPYxwPxgU_2913qK30oKOCckUXUTwqZgr0rrj-DQZBYXQWV05ZRhvYXNhYhkpHv_r41kROLnJUtbitkO1xaDWnM13L_GKHuLMbwTd2TTKT4d6inweYQc1262lF_Q78Lc-qJVVWicSajFxDLf369jNdJH0vMCADK3VFRvuj9Nn5mA
   *    responses:
   *      200:
   *        description: 'OK'
   *        content:
   *          application/json:
   *            schema:
   *              type: object
   *              properties:
   *                success:
   *                  type: boolean
   *                  default: true
   *                data:
   *                  type: object
   *                  properties:
   *                    sub:
   *                      type: string
   *                      default: 102904205778732515333
   *                    email:
   *                      type: string
   *                      default: nirajpatidar81@gmail.com
   *                    token:
   *                      type: string
   *                      default: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDI5MDQyMDU3Nzg3MzI1MTUzMzMiLCJlbWFpbCI6Im5pcmFqcGF0aWRhcjgxQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJNDFfNXhjNllaNW9SNmlrZ3Y2bDhYVzd6Z2NxRkRwYW9aaU9uUy1KVUFabTlHPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik5pcmFqIiwiZmFtaWx5X25hbWUiOiJQYXRpZGFyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlhdCI6MTcwMjc5NDc2MSwiZXhwIjoxNzAzMzk5NTYxfQ.Tz8XtQNRl3guwsZcgpTilnYWKWAS_suBc2lnLeAaBzk
   */
  google_route.post('/login', ctrl.login);
};
