import config from '@/config';
import { IGoogleCallback } from '@/interfaces/IAuth';
import { GoogleAuthCallbackRepository } from '@/repositories/googleAuthCallbackRepository';
import { UserRepository } from '@/repositories/userRepository';
import axios from 'axios';
import { OAuth2Client } from 'google-auth-library';
import { Algorithm, decode as decode_jwt, sign } from 'jsonwebtoken';
import { Service } from 'typedi';

@Service()
export default class AuthService {
  protected googleAuthCallbackRepo: GoogleAuthCallbackRepository;
  protected usersRepo: UserRepository;
  protected client: OAuth2Client;
  constructor(googleAuthCallbackRepo: GoogleAuthCallbackRepository, usersRepo: UserRepository) {
    this.googleAuthCallbackRepo = googleAuthCallbackRepo;
    this.client = new OAuth2Client(config.googleAuth.clientID);
    this.usersRepo = usersRepo;
  }

  /**@deprecated */
  public handleGoogleCallback = async (googleCallbackDTO: IGoogleCallback) => {
    const state = googleCallbackDTO.State;
    const emailRegex = /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/;
    const extractedEmail = state.match(emailRegex);
    let userEmail = '';

    if (extractedEmail) {
      userEmail = extractedEmail[0];
    }
    await this.googleAuthCallbackRepo.saveCallbackData({ ...googleCallbackDTO, Email: userEmail });
    return;
  };

  public loginByGoogle = async (id_token: string) => {
    // const ticket = await this.client.verifyIdToken({ idToken: id_token, audience: config.googleAuth.clientID });
    const t = decode_jwt(id_token);
    console.log(t);
    // const ticket = await this.client.verifyIdToken({ idToken: id_token, audience: config.googleAuth.clientID });
    // const payload = ticket.getPayload();
    const payload = t;

    const { email, picture, given_name, family_name, email_verified, sub } = payload;
    const token = this.generateToken(sub, email, { picture, given_name, family_name, email_verified });

    await this.usersRepo.createUser(sub, email, { picture, email_verified, family_name, given_name });

    return { sub, email, token };
  };

  private generateToken = (sub, email, { picture, given_name, family_name, email_verified }) => {
    const token = sign({ sub, email, picture, given_name, family_name, email_verified }, config.jwtSecret, {
      algorithm: config.jwtAlgorithm as Algorithm,
      expiresIn: '7d',
    });
    return token;
  };

  /**@deprecated */
  private getGoogleAccessToken = async (one_time_code: string) => {
    try {
      const resp = await axios.post('https://oauth2.googleapis.com/token', {
        code: one_time_code,
        redirect_uri: config.googleAuth.redirect_uri,
        client_id: config.googleAuth.clientID,
        client_secret: config.googleAuth.client_secret,
        grant_type: 'authorization_code',
      });
      const data = resp.data;
      const { access_token, expires_in, refresh_token, scope, token_type, id_token } = data;

      const decoded_token = decode_jwt(id_token, { json: true });
      console.log(decoded_token);
      return {
        refresh_token,
        data: decoded_token,
      };
    } catch (error) {
      throw error.response.data;
    }
  };
}
