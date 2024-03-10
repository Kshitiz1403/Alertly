import admin from 'firebase-admin';
import serviceAccount from 'firebase-service-account.json';

export const initializeApp = () => {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
  });
};

export default admin;
