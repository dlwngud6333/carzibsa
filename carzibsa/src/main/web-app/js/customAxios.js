import axios from 'axios'; // 액시오스

export default function customAxios(url, callback, paramObj) {
  axios(
    {
      url: '/rest' + url,
      method: 'post',
      baseURL: 'http://localhost:8080',
      withCredentials: true,
      params : paramObj
    }
  )
  .then(function (response) {
    callback(response.data);
  })
  .catch(function (error) {
    // handle error
    console.log(error);
  })
  .then(function () {
    // always executed
  });
}