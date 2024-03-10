import admin from 'firebase-admin';

// var serviceAccount = require('./firebase-service-account.json');
var serviceAccount: any = {
  type: 'service_account',
  project_id: 'alertly-d8e85',
  private_key_id: '038d71c547b58e9ae2ed6d8f7fe9232355b4122d',
  private_key:
    '-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDS/9BmF9x+Aa+W\nzhau2CMqE/TfWXxW504apXMx+8+whYjBGpF2Ob/ge1lmMnRVkUdJYNGgcTJfk4NI\nLXNq6YCZOBYSSTDS+bi1rx0d00kAQa/BuFZZzK3ep8X6E/nwNLM5J+wyZCrv9t8F\n1QHb1FDstokF8qMXEl03hZqEsnJmbR/WGRbFmvBK1RVEoa6xViWPts+rUYWRPtie\npTZXrn8wOYHT3QjYbuSHx9pxE/2fkG01bWiFtg1QpKgQlADbm83En87riXlcumiR\nIoMMbrY4b9YyH8fwJ2fBo90mNsKlU5Jgz9x1x5aMEgw+kaCdDsAp7wNRJtdxhNwH\n/uNuN6QRAgMBAAECgf8D7gYPOWXkabOFVT02tCAlu7iVFFD8HNiZpMaC+3ga12bd\nofPeECSNmNRHzY39wF8W3iYHrEmWtwFNWVkp0pqYaBsq9b6PfgFr/h+6t0Lokfmd\ncK2AX7TCh3F9AH+Gkt4cWA3tf54clW5E9/7GaNe8+lCqbtSZGxSfs69Nrp8VIsQF\n0P24cY7H6+7Sfha1vPaJFGRVU5Lnj79R4f3eQBWvMkHQ3BIDbBO7cBBl51Uh0Hss\nCcjiElYxLgskJPdE23l1O+CKOxD1qSug1yj6huqHgkOwcnA4ZI2ZmTHyRU4UUSFm\ntLwEwrZpeLFRm1kpbQR15JoryYsZHRzQGogEiGECgYEA7ic/WBYKn5E/rQdaWU8x\nCY9siht56kH71qEsCNeSKSBQyTIY+sLfvgZLXLBJo/uhAS1yRKgSRJhGvAsI+gGj\naTHecwhEY776dWlyvhfyg2Oc6OLKpqaGF+nhH9Jmx6mqu5H0aPzHn8UECisy80+s\nPsZwIYwcIqbCYY09Ya9ORWkCgYEA4s+kMFZAR9P1e/VOkkce+2noZtXh2w7LFVgL\nR9tXioXSP0KJxOQg11TTDmBw8LmzH+iWHbz/VMqanTJfjYU9FsKzOsKiV7kJgV+Z\ny4KQ8HbEvBJ/7z8LWbSaVKC+zKOGD4cZpsGOfLAw69M2pmxJQ+/Dc9OSaHqP3Vgp\nrFwyTGkCgYEArMTcN1MGMex1fVkwOkoxEPZzj12PfFqSEIqsH14EMsG22b72UDS/\njGOUmJFrWwNZMjl+BEDJv+mYpyJ6SKSXn+EFt/3vX01U9YiiLTXCQX8qfejJi0yT\nSxXos+U3mvGDYr/nx6JaT5Xl8FqWg9zw4DmlbzDt2FWR/qMwPIWpCsECgYEAwnWe\nYucAM9Uxqra2R8n8cTfrTofgHc2AJuuWuGQBmT2E9kHZFts0oQoT7kaXbnz4a8oh\nMpwBGjSe/Vnb/N0YdDQ4MJABWk7GLIrHtGlcVR6vLER8NaaK1711eEtdatxkq1l8\nj7FxtcvmFbrAJ02+PyYJIHxsEXHLeM4G5MUjRskCgYEA07nQn8I24t5QUvspV+iW\naxTXdyYHqaze6P6bfthAMWblHWZRUgDRuYBQnzkNaSlPGL3ZHBgR9kClAO5MAv30\nJzHW55iite8fY/obPyc5KUd6ChOYQqYOS4dgRv/PR2omuik53Eg7LuHt368qqXWb\nfZ/UrwlHYffapxi/SmfB/G0=\n-----END PRIVATE KEY-----\n',
  client_email: 'firebase-adminsdk-hpter@alertly-d8e85.iam.gserviceaccount.com',
  client_id: '107478337807266991207',
  auth_uri: 'https://accounts.google.com/o/oauth2/auth',
  token_uri: 'https://oauth2.googleapis.com/token',
  auth_provider_x509_cert_url: 'https://www.googleapis.com/oauth2/v1/certs',
  client_x509_cert_url:
    'https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-hpter%40alertly-d8e85.iam.gserviceaccount.com',
  universe_domain: 'googleapis.com',
};

export const initializeApp = () => {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
  });
};

export default admin;
