import { NextFunction, Request, Response } from 'express';
import { Inject, Service } from 'typedi';
import { Logger } from 'winston';
import fs from 'fs';
import { GoogleAuthCallbackRepository } from '@/repositories/googleAuthCallbackRepository';
import AuthService from '@/services/authService';
import { Result } from '@/api/util/result';

@Service()
export class GoogleAuthController {
  protected logger: Logger;
  protected authService: AuthService;
  protected googleAuthCallbackRepo: GoogleAuthCallbackRepository;
  constructor(@Inject('logger') logger: Logger, authService: AuthService) {
    this.logger = logger;
    this.authService = authService;
  }
  public callback = async (req: Request, res: Response, next: NextFunction) => {
    this.logger.debug('Calling Google Callback endpoint');
    fs.writeFileSync('./callback.json', JSON.stringify(req.query));

    try {
      const { code, scope, authuser, prompt, state } = req.query as {
        code: string;
        scope: string;
        authuser: string;
        prompt: string;
        state: string;
      };
      await this.authService.saveGoogleCallback({
        AuthUser: authuser,
        OneTimeCode: code,
        Prompt: prompt,
        Scope: scope,
        State: state,
      });
      return res.status(200).json(Result.success('Google Authentication Callback successfull'));
    } catch (error) {
      this.logger.error(error.stack);
      return next(error);
    }
  };
}
