import React from 'react';
import ReactDOM from 'react-dom';
import App from '../components/App';
import RouterLogin from '../router/router-login';

const rootElement = document.getElementById('root');

ReactDOM.render(
    <RouterLogin></RouterLogin>
    , document.getElementById('root')
);