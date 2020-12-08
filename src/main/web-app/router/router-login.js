import React, { useState, useEffect } from 'react';

import LoginPage from '@/react/login/react-login';
import MainPage from '@/react/main/react-main';
import BoardPage from '@/react/board/react-board';
import { BrowserRouter as Router, Route, Redirect, Switch} from "react-router-dom";

class RLogin extends React.Component {

  render() {
    return(
      <Router>
          <Switch>
              <Route path="/" exact component={LoginPage} />
              <Route path="/login" component={LoginPage} />
              <Route path="/main" component={MainPage} />
              <Route path="/board" component={BoardPage} />
          </Switch>
      </Router>
    )
  }
}

export default RLogin;