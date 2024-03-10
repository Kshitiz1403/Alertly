import LoggerInstance from '@/loaders/logger';
import { AlertRepository } from '@/repositories/alertRepository';
import { Service } from 'typedi';
import NotificationService from './notificationService';

@Service()
export class AlertService {
  protected alertRepository: AlertRepository;
  protected notificationService: NotificationService;
  constructor(alertRepo: AlertRepository, notificationService: NotificationService) {
    this.alertRepository = alertRepo;
    this.notificationService = notificationService;
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

      // now push the notification to the users of the same group
      await this.notificationService.pushNotification(groupID, alertTitle, description);

      return alert;
    } catch (error) {
      LoggerInstance.error(error);
      throw { status: 500, message: 'error creating alert' };
    }
  };
}
