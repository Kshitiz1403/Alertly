import firebaseAdmin from '@/loaders/firebase';
import { Service } from 'typedi';

@Service()
export default class NotificationService {
  public pushNotification = async (token: string, title: string, body: string, severity: string) => {
    const sentMessage = await firebaseAdmin.messaging().send({
      token:
        'fJqfODmbQt-XmZ77u4yRN5:APA91bE1RLd5-l2fFcc5wP3WS9DtOZx1fkElgiZRgBS1QgKHq69_RQe9sOOVxVI6rBpc4vd8RZt2JC7jwou7bH2eqjpSvh5iL8Sh40uIWdJCzzwOutX1ltIJkFjNDOKLS1UEL918r4ey',
      data: {
        title: title,
        description: body,
        severity,
      },
      notification: {
        title,
        body,
      },
    });

    return sentMessage;
  };
}
