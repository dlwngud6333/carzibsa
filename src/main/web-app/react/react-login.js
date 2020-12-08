import React, { useState, useEffect } from 'react';
import customAxios from '@/js/customAxios';

function App() {
  // IP주소 변수 선언
  const [ip, setIp] = useState('');


  // IP주소 값을 설정합니다.
  function callback(data) {
    alert(data[0].personid + ' / ' + data[0].password);
  }

  // 첫번째 렌더링을 다 마친 후 실행합니다.
  useEffect(
    () => {
      // 클라이언트의 IP주소를 알아내는 백엔드의 함수를 호출합니다.
      customAxios('/login', callback);
    }, []
  );

  return (
    <div className="App">
      <header className="App-header">
        이 기기의 IP주소는 입니다.
      </header>
    </div>
  );
}

export default App;