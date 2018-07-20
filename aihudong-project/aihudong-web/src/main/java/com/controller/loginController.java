package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.TeacherService;
import com.util.HttpUtil;
import com.util.JsonUtils;
import com.util.SNSUserInfo;
import com.util.WeiXinUtil;
import com.util.WeixinOauth2Token;
import com.util.XMLUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/index")
public class loginController {
	@Value("${appid}")
	private String appid;
	
	@Value("${redirect_uri}")
	private String redirect_uri;
	
	@Value("${appSecret}")
	private String appSecret;
	
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "/info";
	}
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * 获取参数
	 * @return
	 */
	@ResponseBody()
	@RequestMapping(value="/getWeiChatInfo",produces = "text/json;charset=UTF-8")
	public String getWeiChatInfo() {
		/*Map<String,String> map=new HashMap<>();
		map.put("appid", appid);
		map.put("redirect_uri", redirect_uri);*/
		String url="https://open.weixin.qq.com/connect/qrconnect?"
				+ "appid="+appid+"redirect_uri="+redirect_uri
				+ "response_type=code&scope=snsapi_login#wechat_redirect";
		return JsonUtils.objectToJson(url);
	}
	
	/**
	 * 获取AccessToken
	 * @param code
	 */
	@RequestMapping("/getAccessToken")
	public void getAccessToken(@RequestParam String code) {
		String url="https://api.weixin.qq.com/sns/oauth2/access_token";
		String param="appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		param=param.replace("APPID", appid);
		param=param.replace("SECRET", appSecret);
		param=param.replace("CODE", code);
		String sendGET = HttpUtil.sendGET(url, param);
		JSONObject jsonObject = JSONObject.fromObject(sendGET);
		String refresh_token=jsonObject.getString("refresh_token");
		String openid=jsonObject.getString("openid");
		//刷新AccessToken
		reflushAccessToken(refresh_token);
		
		
		
		System.out.println(sendGET);
	}
	
	/**
	 * 刷新AccessToken
	 * @param refresh_token
	 */
	public void reflushAccessToken(String refresh_token) {
		String url="https://api.weixin.qq.com/sns/oauth2/refresh_token";
		String param="appid=wx9261a9ef39996ff4&grant_type=refresh_token&refresh_token="+refresh_token;
		HttpUtil.sendGET(url, param);
	}
	
}
