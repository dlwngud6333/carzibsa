import React from 'react';
import { Link } from 'react-router'

import LoginPage from '@/react/login/react-login';

/**
 * 라우터는 URL을 파라미터로 받고 해당 URL에 따라 페이지 이동하여 보여주는것으로 진행
 * 
 * 
 */

function App() {
  return (
    <LoginPage></LoginPage>
  );
}

export default App;