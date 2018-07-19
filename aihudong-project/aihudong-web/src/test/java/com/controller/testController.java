package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.BaseTest;
import com.model.Admin;
import com.model.Logger;
import com.model.Teacher;
import com.service.AdminService;

public class testController extends BaseTest{
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired AdminService adminService;
	
	/**
	 * 根级管理员登录测试
	 */
	@Test
	public void genjiadminLogin(){
		Admin admin=new Admin();
		admin.setUsername("genji");
		admin.setPassword("123");
		admin = adminService.adminLogin(admin);
		
		if(admin!=null){
			logger.info(admin.getUsername()+"登录系统");
		}
	}
	/**
	 * 一级管理员登录测试
	 */
	@Test
	public void yijiadminLogin(){
		Admin admin=new Admin();
		admin.setUsername("yiji");
		admin.setPassword("123");
		admin = adminService.adminLogin(admin);
		
		if(admin!=null){
			logger.info(admin.getUsername()+"登录系统");
		}
	}
	
	/**
	 * 错误登录测试
	 */
	@Test
	public void adminLogin(){
		Admin admin=new Admin();
		admin.setUsername("123");
		admin.setPassword("123");
		admin = adminService.adminLogin(admin);
		
		if(admin!=null){
			logger.info(admin.getUsername()+"登录系统");
		}else {
			logger.info("用户不存在!");
		}
	}
	
	/*@Test
	public void frontLogin(HttpServletResponse response,HttpServletRequest request) throws IOException {
		Teacher teacher=new Teacher();
		teacher.setUsername("2");
		teacher.setPassword("2");
		QianduanController q=new QianduanController();
		q.UserLogin("2",response,request);
	}*/
	
	/**
	 * 密码加密算法
	 */
	@Test
	public void testMD5() {
		String password = "kj966111";
		String username = "konka2";
		String md5 = new Md5Hash(password, username ,2).toString();
		System.out.println("-----------------"+md5);
	}
	
}
