package com.app.login.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.login.mapper.LoginMapper;
import com.app.login.vo.LoginVo;

/**
 * @author dlwng
 *
 */
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
    LoginMapper loginMapper;
	
	@Override
	public List<LoginVo> getPerson() throws Exception {
		return loginMapper.getPerson();
	}


	@Value("${api.naidro.client.id}") 
	private String clientId; // 애플리케이션 클라이언트 아이디값";

	@Value("${api.naidro.client.secret}") 
	private String clientSC; // 애플리케이션 클라이언트 시크릿값";

	@Value("${api.naidro.client.callbackUrl}") 
	private String callbackUrl; // CALL BACK URL";
	

	 
	/**
	 *네이버 인증 요청문 호출
	 */
	public String getNaverLogin(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String apiURL = "";
		if(session != null) {
			String redirectURI = URLEncoder.encode(this.callbackUrl, "UTF-8");
			String state = generateState();
			apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			apiURL += "&client_id=" + clientId;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&state=" + state;
			session.setAttribute("state", state);
		}
		return apiURL;
	}
    
	/**
	 * 네아로 접근 토큰 발급 후 네이버 프로필 API 호출
	 */
	public String getNaverProfile(HttpServletRequest request) throws Exception {
		String clientId = this.clientId;//애플리케이션 클라이언트 아이디값";
		String clientSecret = this.clientSC;//애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		// 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
		String storedState = (String) request.getSession().getAttribute("state");

		if( !state.equals( storedState ) ) {
			return "error"; //401 unauthorized
		}
		
		// Naver에 등록한 CallBack URL 등록
		String redirectURI = URLEncoder.encode(this.callbackUrl, "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		String access_token = "";
		String refresh_token = "";
		String result = "";
		

		result = get(apiURL,null);
		// access token이 정상 발급 될 경우 Profile API 실행
		if(result.indexOf("access_token")>=0) {
			access_token = result.substring(result.indexOf(":")+2,result.indexOf(",")-1);
			result = getNaverInfo("bearer", access_token);
		}
		
		return result;
	}

    /**
     * Naver Profile API 호출 후 결과값 Return
     * @param token_type
     * @param access_token
     * @return
     */
    public String getNaverInfo(String token_type, String access_token) {
		String header = "Bearer " + access_token; // Bearer 다음에 공백 추가

		String apiURL = "https://openapi.naver.com/v1/nid/me";

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String result = get(apiURL,requestHeaders);
		
		System.out.println("result : " + result);

        return result;
	}

    /**
     * State 코드 생성
     * @return
     */
    public String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * URL 호출 후 결과값 Return
	 * @param apiUrl
	 * @param requestHeaders
	 * @return
	 */
	private static String get(String apiUrl, Map<String, String> requestHeaders){
		 HttpURLConnection con = connect(apiUrl);
		 try {
			con.setRequestMethod("GET");
			if(requestHeaders != null) {
				for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
					con.setRequestProperty(header.getKey(), header.getValue());
				}
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		 } catch (IOException e) {
			 throw new RuntimeException("API 요청과 응답 실패", e);
		 } finally {
			 con.disconnect();
		 }
	 }

    /**
     * URL 연결
     * @param apiUrl
     * @return
     */
    private static HttpURLConnection connect(String apiUrl){
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

    /**
     * Response 결과값 읽어들임
     * @param body
     * @return
     */
    private static String readBody(InputStream body){
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
			    responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
    }
	
}
