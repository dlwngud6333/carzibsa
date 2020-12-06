package com.app.login.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.login.service.LoginService;
import com.app.login.vo.LoginVo;

@RequestMapping("/rest")
@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@Value("${api.naidro.client.id}") 
	private String clientId; // 애플리케이션 클라이언트 아이디값";

	@Value("${api.naidro.client.secret}") 
	private String clientSC; // 애플리케이션 클라이언트 시크릿값";

	@Value("${api.naidro.client.callbackUrl}") 
	private String callbackUrl; // CALL BACK URL";

	@RequestMapping("/")
	public String index() {
		return "index";
	}

    @RequestMapping("/login")
	public @ResponseBody List<LoginVo> query() throws Exception {
		return loginService.getPerson();
	}
 
	@RequestMapping("/loginNaver")
	public String loginNaver(HttpServletRequest request) throws Exception {
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

    public String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
    
	@RequestMapping("/loginCB")
	public String getNaverLogin(HttpServletRequest request) throws Exception {
		System.out.println("getNaverLogin start");
		String clientId = this.clientId;//애플리케이션 클라이언트 아이디값";
		String clientSecret = this.clientSC;//애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		// 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
		String storedState = (String) request.getSession().getAttribute("state");

		if( !state.equals( storedState ) ) {
			return "error"; //401 unauthorized
		}

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
		
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if(responseCode==200) {

				/*
				 * ex)
				 * {
				 * 		"access_token":"AAAAOsP1tEYJPByCgVsCHGxtpxwJOspo0XFWth5p1h2O7Uyq7uA8dTyUWhadBidFf1XzX_mVfeb9H9qcjjfu8PqBQt0"
				 * 		,"refresh_token":"nWkTg2TnqauWt12OuvC5HJCe4UNPM6llNeC5Fgf53bNg9le6tJu3xMEIrGCO3vYGwoT7zcGOt7iiuUipPFkFlP4193b1CllnsFcA2KvQFisr4pkAAzqWHTk1Rjp18aREenb"
				 * 		,"token_type":"bearer"
				 * 		,"expires_in":"3600"
				 * }
				 * 
				 * */
				result = res.toString();
				if(result.indexOf("access_token")>=0) {
					access_token = result.substring(result.indexOf(":")+2,result.indexOf(",")-1);
					System.out.println("access_token : " + access_token);
					result = getNaverInfo("bearer", access_token);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

    public String getNaverInfo(String token_type, String access_token) {
		String header = "Bearer " + access_token; // Bearer 다음에 공백 추가

		String apiURL = "https://openapi.naver.com/v1/nid/me";

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String result = get(apiURL,requestHeaders);

        System.out.println(result);

        return result;
	}

	 private static String get(String apiUrl, Map<String, String> requestHeaders){
		 HttpURLConnection con = connect(apiUrl);
		 try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
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