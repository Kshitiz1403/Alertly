import firebaseAdmin from '@/loaders/firebase';
import { Service } from 'typedi';

@Service()
export default class NotificationService {
  public pushNotification = async (token: string, title: string, body: string) => {
    const sentMessage = await firebaseAdmin.messaging().send({
      token,
      topic: '',
      notification: {
        title,
        body,
      },
    });

    return sentMessage;
  };
}
