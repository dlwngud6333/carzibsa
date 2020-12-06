# 개발환경

소분류 | 세부내용
---- | ----
BE Language | Java : open jdk 11 (Java 1.8)
FE Language | React : 17.0.1
BE Server | Tomcat : 9.0.39 (내장)
FE Server | webpack-dev-server : 2.9.7
Framework | Spring Boot : 2.4.0
Build Tool | Maven : 3.6.3
Build Tool  | node.js : 14.15.1
Build Tool  | babel : 6
SCM | Git : 2.29.2.2
DataBase | PostgreSql : 11.10
Tool | Eclipse : SpringToolSuite4 4.8.1
|| VS Code : 1.51.1
Tool | Dbvear : 7.3.0.202011291229
Tool | Lombok : 1.18.16

<br/>

# 소스 구조

Back-end 단
<br/>java.com.app
<br/> common : 공통 및 Config
<br/> filter : Web Filter
<br/> login : Controller, Service, Mapper, VO
<br/>
<br/>resource
<br/> mybatis.mapper.login : Database mapper xml
<br/> application.properties : DB 및 각종 설정
<br/>
<br/>Front-end 단
<br/>web-app
<br/> commponents : Component 파일
<br/> css : css 파일
<br/> js : funtion 파일
<br/> react : react 화면 파일
<br/> router : url mapping router 파일
<br/>
<br/>package.json : 의존성 관리
<br/>webpack.config.js : webpack 환경설정

<br/>

# 라우터 구성

webpack.config.js
<br/> - historyApiFallback:true 추가
<br/> - 구문 추가하여 URL뒤에 값이 추가되어도 Index Page로 돌아올수 있게 하여 404 에러가 발생되지 않게 한 이후 라우터 구성

<br/>Router 추가
<br/> - 요청 URL별 Page 매핑
<br/> - router-login.js
 
<br/>Index.js에 생성한 Router Render

