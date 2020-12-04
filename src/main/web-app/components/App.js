import React from 'react';
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom';

import Home from '../react/react-login';

function App() {
  return (
    <div className="App">
      <Router>
        <div className='Menu-wrapper'>
          <ul>
            <Link to='/login'><li>Login</li></Link>
            <Link to='/login2'><li>Login2</li></Link>
          </ul>
        </div>
        <div className='Contents-wrapper'>
          <Switch>
            <Route exact path='/login' component={Home} />
          </Switch>
        </div>
      </Router>
    </div>
  );
}

export default App;