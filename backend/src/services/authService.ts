import { IGoogleCallback } from '@/interfaces/IAuth';
import { GoogleAuthCallbackRepository } from '@/repositories/googleAuthCallbackRepository';
import { Service } from 'typedi';

@Service()
export default class AuthService {
  protected googleAuthCallbackRepo: GoogleAuthCallbackRepository;
  constructor(googleAuthCallbackRepo: GoogleAuthCallbackRepository) {
    this.googleAuthCallbackRepo = googleAuthCallbackRepo;
  }
  public saveGoogleCallback = async (googleCallbackDTO: IGoogleCallback) => {
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
}
