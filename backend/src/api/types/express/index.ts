import { NextFunction, Request, Response } from 'express';
import { IToken } from '@/interfaces/IToken';

type IRequest = Request & { currentUser: IToken };
type IResponse = Response;
type INextFunction = NextFunction;

type IGroupRequest = Request & { currentUser: IToken; group_id: number };

export { IRequest, IGroupRequest, IResponse, INextFunction };
