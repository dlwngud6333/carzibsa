import React from 'react';
import ReactDOM from 'react-dom';
import App from '../components/App';
//import RLogin from '../react/login/react-login';
//import RBoard from '../react/board/react-board';
import RouterLogin from '../router/router-login';

const rootElement = document.getElementById('root');
//ReactDOM.render(<App />, rootElement);

ReactDOM.render(
    <RouterLogin></RouterLogin>
    , document.getElementById('root')
);