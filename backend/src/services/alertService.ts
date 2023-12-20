import LoggerInstance from '@/loaders/logger';
import { AlertRepository } from '@/repositories/alertRepository';
import { Service } from 'typedi';

@Service()
export class AlertService {
  protected alertRepository: AlertRepository;
  constructor(alertRepo: AlertRepository) {
    this.alertRepository = alertRepo;
  }

  public createAlert = async (
    userID,
    groupID,
    alertTitle: string,
    description = '',
    severity: 'normal' | 'elevated' | 'danger',
  ) => {
    try {
      const alert = await this.alertRepository.createAlert(userID, groupID, alertTitle, description, severity);

      return alert;
    } catch (error) {
      LoggerInstance.error(error);
      throw { status: 500, message: 'error creating alert' };
    }
  };
}
