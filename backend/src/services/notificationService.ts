import firebaseAdmin from '@/loaders/firebase';
import LoggerInstance from '@/loaders/logger';
import { Service } from 'typedi';

@Service()
export default class NotificationService {
  public pushNotification = async (groupID: string, title: string, body: string, severity: string) => {
    const sentMessage = await firebaseAdmin.messaging().send({
      data: {
        title: title,
        description: body,
        severity,
      },
      notification: {
        title,
        body,
      },
      topic: groupID,
    });
    LoggerInstance.info(`Notification sent successfully for groupID - ${groupID}`);

    return sentMessage;
  };
}
