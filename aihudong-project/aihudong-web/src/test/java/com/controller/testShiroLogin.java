package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.BaseTest;
import com.model.Admin;

public class testShiroLogin extends BaseTest{
	
	@Test
	public void testLogin() {
		Admin admin=new Admin();
		admin.setUsername("yiji");
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
		System.out.println("---------"+subject.toString());
	}

}
