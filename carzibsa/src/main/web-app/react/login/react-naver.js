import React, {Component} from 'react';

import customAxios from '../../js/customAxios';

/**
 * 라우터는 URL을 파라미터로 받고 해당 URL에 따라 페이지 이동하여 보여주는것으로 진행
 * 
 * 
 */

function App() {
    // IP주소 값을 설정합니다.
    function callback(data) {
      console.log(data);
      window.open(data);
    }

    customAxios('/loginCB', callback)

  return (
      ''
  );
}

export default App;