import path from 'path';
import fs from 'fs';
import multer from 'multer';
import config from '@/config';

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    const dir = config.staticFilesPathTemporary;

    if (!fs.existsSync(dir)) {
      fs.mkdirSync(dir);
    }
    cb(null, dir);
  },
  filename: function (req, file, cb) {
    cb(null, Date.now() + path.extname(file.originalname)); //Appending extension
  },
});

const upload = multer({ storage });

export default upload;
