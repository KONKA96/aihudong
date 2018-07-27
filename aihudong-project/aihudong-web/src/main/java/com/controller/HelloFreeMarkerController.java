package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.Message;
import com.service.MessageService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/testFreeMarker")
public class HelloFreeMarkerController {
	
	@Autowired
	private MessageService messageService;
    
    @RequestMapping("/helloFreeMarker")
    public String helloFreeMarker(ModelMap model) {
    	Map<String,Object> map=new HashMap<>();
    	
    	Message message=new Message();
    	map.put("id", 5);
    	List<Message> messageList = messageService.selectAllMessage(map);
    	String messagePic = messageList.get(0).getMessagePic();
    	String[] picString = messagePic.split(",");
    	
    	List<String> picStringList=new ArrayList<>();
    	for (int i = 0; i < picString.length; i++) {
    		picStringList.add(picString[i]);
		}
    	model.addAttribute("picStringList",picStringList);
       
    	/*picStringList(picStringList);*/
        return "helloFreeMarker";
    }
    
    /**
     * 根据String模板生成HTML，模板中存在List循环
     */
    public static void picStringList(List<String> picStringList) {
    	Map<String,Object> map=new HashMap<>();
    	map.put("picStringList", picStringList);
    	
        BufferedInputStream in = null;
        FileWriter out = null;
        try {
            //模板文件
            File file = new File("F:\\apache-tomcat-8.5.20-windows-x64\\apache-tomcat-8.5.20\\webapps\\aihudong-web\\WEB-INF\\views\\templates\\helloFreeMarker.ftl");
            //构造输入流
            in = new BufferedInputStream(new FileInputStream(file));
            int len;
            byte[] bytes = new byte[1024];
            //模板内容
            StringBuilder content = new StringBuilder();
            while((len = in.read(bytes)) != -1) {
                content.append(new String(bytes, 0, len, "utf-8"));
            }
            
            //构造Configuration
            Configuration configuration = new Configuration();
            //构造StringTemplateLoader
            StringTemplateLoader loader = new StringTemplateLoader();
            //添加String模板
            loader.putTemplate("test", content.toString());
            //把StringTemplateLoader添加到Configuration中
            configuration.setTemplateLoader(loader);
            
            //构造Model
            //获取模板
            Template template = configuration.getTemplate("test");
            //构造输出路
            out = new FileWriter("F:\\apache-tomcat-8.5.20-windows-x64\\apache-tomcat-8.5.20\\webapps\\aihudong-web\\upload\\result.html");
            //生成HTML
            template.process(map , out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

}
