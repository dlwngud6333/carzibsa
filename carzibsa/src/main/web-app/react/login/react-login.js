import React, {Component} from 'react';

import '../../css/login.css'
import customAxios from '../../js/customAxios';

class RLogin extends React.Component {

  
  render() {

    const{params} = this.props.match;

    let searchParam = this.props.location.search;
    if(searchParam != null && searchParam != 'undefined' && searchParam.length >= 1){
      searchParam = searchParam.substring(1);
      const paramArr = searchParam.split('&');
  
      const isNaver = paramArr[0].split('=')[1];
      const code = paramArr[1].split('=')[1];
      const state = paramArr[2].split('=')[1];
  
      if(isNaver){
        customAxios(
          '/loginCB'
          , callbackCB
          , {'code' : code, 'state' : state}
        );
      }
    }
    
    // IP주소 값을 설정합니다.
    function callbackCB(data) {
      console.log(data);
      if(data.message === 'success'){
        document.location.href="http://localhost:7777/main"
      }else{
        document.location.href="http://localhost:7777"
      }
    }
    
    // IP주소 값을 설정합니다.
    function callback(data) {
      console.log(data);
      document.location.href=data
    }

    function callNaver(){
      customAxios(
        '/loginNaver'
        , callback
      );
    }

    return(
      <div className="LoginFrameWork" >
        <div className="loginFont">
          <span>로&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;인</span>
          <div className="InputDiv">
            <span>ID</span>
            <br/>
            <input id="personId"></input>
          </div>
          <div className="InputDiv">
            <span>Password</span>
            <br/>
            <input id="personPw"></input>
          </div>
          <button className="loginBtn">로그인</button>
          <br/>
          <div className="rightDiv">
            <span className="registerA"><a>회원가입</a></span>
          </div>
          <br/>
          <br/>
          <button className="naverBtn" onClick={callNaver}>네이버 아이디로 로그인</button>
        </div>
      </div>
    )
  }
}

export default RLogin