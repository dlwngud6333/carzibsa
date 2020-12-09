import React, {Component} from 'react';

import '@/css/login.css'
import customAxios from '@/js/customAxios';
import {properties} from '@/js/properties.js';

class RLogin extends React.Component {

  
  render() {


    // 네아로 실행 후 CALLBACK URL ?isNaver=true&code=CLIENT_CODE&state=CLIENT_STATE 
    const{params} = this.props.match;

    let searchParam = this.props.location.search;
    if(searchParam != null && searchParam != 'undefined' && searchParam.length >= 1){
      searchParam = searchParam.substring(1);
      const paramArr = searchParam.split('&');
  
      const isNaver = paramArr[0].split('=')[1]; // isNaver
      const code = paramArr[1].split('=')[1]; // code
      const state = paramArr[2].split('=')[1]; // state
  
      // 네아로 접근 토큰 발급 후 네이버 프로필 API 호출
      if(isNaver){
        customAxios(
          '/loginCB'
          , callbackCB
          , {'code' : code, 'state' : state}
        );
      }
    }
    
    
    // 네아로 정상 호출 시 Main 화면 이동
    function callbackCB(data) {
      if(data.message === 'success'){
        document.location.href=properties.frontEndUrl+"/main"
      }else{
        document.location.href=properties.frontEndUrl
      }
    }
    
    // 네아로 화면
    function callback(data) {
      document.location.href=data
    }

    // 로그인 페이지에서 네이버 인증 요청문 API 호출
    function callNaver(e){
      e.preventDefault();
      customAxios(
        '/loginNaver'
        , callback
      );
    }

    // 로그인 페이지에서 네이버 인증 요청문 API 호출
    function goRegistUser(e){
      e.preventDefault();
      document.location.href=properties.frontEndUrl+"/registUser"
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
            <span className="registerA">
              <a href="#" onClick={goRegistUser}>회원가입</a>
            </span>
          </div>
          <br/>
          <br/>
          <a href="#" onClick={callNaver}>
            <img src="../images/btn_naver_login.png" width="100%"></img>
          </a>
        </div>
      </div>
    )
  }
}

export default RLogin