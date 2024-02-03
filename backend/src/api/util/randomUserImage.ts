// const userImageTemplate
function getRandomUserImage() {
  const imageNumber = Math.floor(Math.random() * 50);
  const gender = Math.floor(Math.random() * 2) == 0 ? 'men' : 'women';

  const template = `https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`;
  return template;
}

export default getRandomUserImage;
