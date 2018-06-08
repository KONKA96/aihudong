package com.heart;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.controller.QianduanController;
import com.model.Record;
import com.model.Screen;
import com.service.RecordService;
import com.service.ScreenService;
import com.service.impl.ScreenServiceImpl;

@Component
public class ClientHeart extends Thread {
	
	@Autowired
	private ScreenService screenService = new ScreenServiceImpl();
	@Autowired
	private RecordService recordService;
	
	private HttpSession session;
	
	private String username;
	
	
	public ClientHeart() {
		super();
	}

	public ClientHeart(HttpSession session,String username) {
		super();
		this.session = session;
		this.username = username;
	}

	@Override
    public void run() {

        try {
            while (true) {
                /*ClientSender.getInstance().send();*/
            	
                synchronized (ClientHeart.class) {
                	Thread.sleep(1000*5);
                    // this.wait(5000);
                	if(session==null) {
                		Thread.interrupted();
                	}
                	ServletContext servletContext = session.getServletContext();
                    /*HttpSession session=(HttpSession) servletContext.getAttribute(username);*/
                    
                    String userId=(String) session.getAttribute("userId");
                    Integer count=(Integer) session.getAttribute("count");
                    int role=(int) session.getAttribute("role");
                    count++;
                    session.setAttribute("count", count);
                    System.out.println("count="+count);
                    if(count==4) {
                    	/*Screen screen=new Screen();
        				screen.setId(userId);
        				System.out.println(screenService);
        				screen=screenService.selectByPrimaryKey(screen);
        				int number=screen.getNumber();
        				String duration = screen.getDuration();
        				
        				screen.setNumber(number+1);
                    	
        				QianduanController qianduan=new QianduanController();
        				
        				int id= (int) session.getAttribute("recordId");
        				Record record=new Record();
        				record.setId(id);
        				record.setEndTime(new Date());
        				Date startTime=(Date) session.getAttribute("startTime");
//        				计算使用的时长
        				long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//        				转化为小时、分钟、秒
        				int hour=(int) (second/(60*60));
        				int minute=(int) ((second%(60*60))/60);
        				int sec=(int) (second%60);
        				screen.setDuration(qianduan.countTime(duration, hour, minute, sec));
        				screenService.updateByPrimaryKeySelective(screen);
        				recordService.updateByPrimaryKeySelective(record);*/
        				
                    	int id= (int) session.getAttribute("recordId");
        				Record record=new Record();
        				record.setId(id);
        				record.setEndTime(new Date());
                    	Date startTime=(Date) session.getAttribute("startTime");
//        				计算使用的时长
        				long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//        				转化为小时、分钟、秒
        				int hour=(int) (second/(60*60));
        				int minute=(int) ((second%(60*60))/60);
        				int sec=(int) (second%60);
                    	QianduanController qc=new QianduanController();
                    	if(role==1) {
                    		qc.teaLogout(userId, hour, minute, sec);
                    	}
                    	session.invalidate();
                    	servletContext.removeAttribute(username);
                    	
                    	Thread.currentThread().interrupt();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序的入口main方法
     * 
     * @param args
     */
   public static void main(String[] args) {
        ClientHeart client = new ClientHeart();
        client.start();
    }

}
