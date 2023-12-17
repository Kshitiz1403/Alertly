import { NextFunction, Request, Response } from 'express';
import { IToken } from '@/interfaces/IToken';

type IRequest = Request & { currentUser: IToken };
type IResponse = Response;
type INextFunction = NextFunction;

export { IRequest, IResponse, INextFunction };
