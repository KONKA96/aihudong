package com.heart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.controller.QianduanController;
import com.model.Record;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ServerHeart extends Thread implements ServletContextListener {
	private ServerSocket server = null;

	private ServletContext servletContext;

	public ServerHeart() {
		super();
	}

	public ServerHeart(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
	}

	Object obj = new Object();

	@Override
	public void run() {
		try {
			server = new ServerSocket(12306);

			while (true) {
				Socket client = server.accept();
				synchronized (obj) {
					new Thread(new Client(client, servletContext)).start();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 客户端线程
	 * 
	 * @author USER
	 *
	 */
	class Client implements Runnable {
		Socket client;

		private ServletContext servletContext;

		public Client(Socket client, ServletContext servletContext) {
			super();
			this.client = client;
			this.servletContext = servletContext;
		}

		@Override
		public void run() {
			String username = null;
			// base64转码
			BASE64Encoder encoder = new BASE64Encoder();
			// base64解码
			BASE64Decoder decoder = new BASE64Decoder();
			/*
			 * BufferedReader bufferedReader=null; try { bufferedReader = new
			 * BufferedReader(new InputStreamReader(client.getInputStream())); } catch
			 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
			 * PrintWriter printWriter = null; try { printWriter=new PrintWriter(new
			 * OutputStreamWriter(client.getOutputStream())); } catch (IOException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); }
			 */
			
			HttpSession session =null;
			int role=0;
			
			String userId=null;
			try {
				client.setSoTimeout(20000);
				while (true) {
					
					
					
					DataInputStream dis = new DataInputStream(client.getInputStream());
					InputStream inputStream = client.getInputStream();
					int read = inputStream.available();
					byte[] b = new byte[read];
					
					
					while(dis.read(b)!=-1) {
						if(b.length!=0) {
							username = new String(decoder.decodeBuffer(new String(b)), "UTF-8");
							if(username.substring(0, username.length()/2).equals(username.substring(username.length()/2, username.length()))) {
								username=username.substring(0, username.length()/2);
								session = (HttpSession) servletContext.getAttribute(username.substring(0, username.length()/2));
							}else {
								session = (HttpSession) servletContext.getAttribute(username);
							}
							if(session!=null) {
								session.setAttribute("count", 0);
							}
							System.out.println("username="+username);
						}else {
							if(username!=null) {
								session = (HttpSession) servletContext.getAttribute(username);
								Integer count = (Integer) session.getAttribute("count");
								
								userId = (String) session.getAttribute("userId");
								role =(int) session.getAttribute("role");
								if(count==4) {
									
									
									int id= (int) session.getAttribute("recordId");
			        				Record record=new Record();
			        				record.setId(id);
			        				record.setEndTime(new Date());
			                    	Date startTime=(Date) session.getAttribute("startTime");
//			        				计算使用的时长
			        				long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//			        				转化为小时、分钟、秒
			        				int hour=(int) (second/(60*60));
			        				int minute=(int) ((second%(60*60))/60);
			        				int sec=(int) (second%60);
			                    	QianduanController qc=new QianduanController();
			                    	if(role==1){
			            				qc.teaLogout(userId,hour,minute,sec);
			            			}else if(role==2){
			            				qc.stuLogout(userId,hour,minute,sec);
			            				
			            			}else if(role==4){
			            				qc.screenLogout(userId,hour,minute,sec);
			            			}
			                    	session.invalidate();
			                    	servletContext.removeAttribute(username);
									
									Thread.currentThread().interrupt();
								}
								session.setAttribute("count", ++count);
								System.out.println("count="+count);
							}else {
								continue;
							}
						}
						//向客户端发送当前时间，如果获取不到输出流对象则认为已经断开连接
						try {
							DataOutputStream dos = new DataOutputStream(client.getOutputStream());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							dos.write(sdf.format(new Date()).getBytes());
							dos.flush();
							Thread.sleep(1000 * 20);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							int id= (int) session.getAttribute("recordId");
	        				Record record=new Record();
	        				record.setId(id);
	        				record.setEndTime(new Date());
	                    	Date startTime=(Date) session.getAttribute("startTime");
//	        				计算使用的时长
	        				long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//	        				转化为小时、分钟、秒
	        				int hour=(int) (second/(60*60));
	        				int minute=(int) ((second%(60*60))/60);
	        				int sec=(int) (second%60);
	                    	QianduanController qc=new QianduanController();
	                    	if(role==1){
	            				qc.teaLogout(userId,hour,minute,sec);
	            			}else if(role==2){
	            				qc.stuLogout(userId,hour,minute,sec);
	            				
	            			}else if(role==4){
	            				qc.screenLogout(userId,hour,minute,sec);
	            			}
	                    	session.invalidate();
	                    	servletContext.removeAttribute(username);
							
							Thread.currentThread().interrupt();
							e.printStackTrace();
							break;
						}
					}
					
				}

				/*
				 * String username=(String) in.readObject(); System.out.println(username);
				 */
			} catch (Exception e) {
				e.printStackTrace();
				int id= (int) session.getAttribute("recordId");
				Record record=new Record();
				record.setId(id);
				record.setEndTime(new Date());
            	Date startTime=(Date) session.getAttribute("startTime");
//				计算使用的时长
				long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//				转化为小时、分钟、秒
				int hour=(int) (second/(60*60));
				int minute=(int) ((second%(60*60))/60);
				int sec=(int) (second%60);
            	QianduanController qc=new QianduanController();
            	if(role==1){
    				qc.teaLogout(userId,hour,minute,sec);
    			}else if(role==2){
    				qc.stuLogout(userId,hour,minute,sec);
    				
    			}else if(role==4){
    				qc.screenLogout(userId,hour,minute,sec);
    			}
            	session.invalidate();
            	servletContext.removeAttribute(username);
				
				Thread.currentThread().interrupt();
			}
		}
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println("开始检测客户端是否在线..."); //ServletContext servletContext =
	 * sce.getServletContext(); new ServerHeart().start(); }
	 */

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 程序的入口方法
	 * 
	 * @param args
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("开始检测客户端是否在线...");
		ServletContext servletContext = sce.getServletContext();
		new ServerHeart(servletContext).start();
	}
}
