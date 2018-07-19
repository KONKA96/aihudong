package com.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Admin;
import com.model.Logger;
import com.service.AdminService;

@Controller
@RequestMapping("/login")
public class test {
	
	protected Logger logger = Logger.getLogger(this.getClass());  
	
	@Autowired AdminService adminService;
	
	@RequestMapping("/testjsp")
	public String testjsp(){
		return "/test";
	}
	
	
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	/**
	 * 管理员登录
	 * @param admin
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adminLogin")
	public String adminLogin(Admin admin,HttpSession session){
		/*admin = adminService.adminLogin(admin);
		
		if(admin!=null){
			logger.info(admin.getUsername()+"登录系统");
			session.setAttribute("admin", admin);
			return "success";
		}*/
		UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),
				new Md5Hash(admin.getPassword(), admin.getUsername() ,2).toString());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			admin = adminService.adminLogin(admin);
			
			logger.info(admin.getUsername()+"登录系统");
			session.setAttribute("admin", admin);
			return "success";
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * aaa
	 */
	@RequestMapping("/testLoginaa")
	public void testLogin() {
		Admin admin=new Admin();
		admin.setUsername("genji");
		admin.setPassword("123");
		UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),
				admin.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---------"+subject.getPrincipal());
	}
}
