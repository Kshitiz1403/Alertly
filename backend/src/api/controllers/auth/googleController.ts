import { NextFunction, Request, Response } from 'express';
import { Inject, Service } from 'typedi';
import { Logger } from 'winston';
import AuthService from '@/services/authService';
import { Result } from '@/api/util/result';

@Service()
export class GoogleAuthController {
  protected logger: Logger;
  protected authService: AuthService;
  constructor(@Inject('logger') logger: Logger, authService: AuthService) {
    this.logger = logger;
    this.authService = authService;
  }

  public callback = async (req: Request, res: Response, next: NextFunction) => {
    this.logger.debug('Calling Google Callback endpoint');

    try {
      const { code, scope, authuser, prompt, state } = req.query as {
        code: string;
        scope: string;
        authuser: string;
        prompt: string;
        state: string;
      };
      await this.authService.handleGoogleCallback({
        AuthUser: authuser,
        OneTimeCode: code,
        Prompt: prompt,
        Scope: scope,
        State: state,
      });
      return res.status(200).json(Result.success('Google Authentication Callback successfull'));
    } catch (error) {
      return next(error);
    }
  };

  public login = async (req: Request, res: Response, next: NextFunction) => {
    this.logger.debug(`Calling Login endpoint with token - ${req.body.id_token.length > 0 ? '***' : ''}`);

    try {
      const id_token = req.body.id_token as string;
      const data = await this.authService.loginByGoogle(id_token);
      return res.status(200).json(Result.success(data));
    } catch (error) {
      return next(error);
    }
  };
}
