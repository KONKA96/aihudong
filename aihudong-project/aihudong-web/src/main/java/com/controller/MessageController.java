package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Logger;
import com.model.Message;
import com.model.Zone;
import com.service.MessageService;
import com.service.ZoneService;
import com.util.PageUtil;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MessageService messageService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private PageUtil pageUtil;
	
	/**
	 * 查询所有消息
	 * @param message
	 * @param modelMap
	 * @param index
	 * @param pageSize
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/showAllMessage")
	public String showAllMessage(Message message,ModelMap modelMap,@RequestParam(required=true,defaultValue="1") Integer index,
            @RequestParam(required=false,defaultValue="15") Integer pageSize,HttpServletRequest request,HttpSession session) {
		
		PageHelper.startPage(index, pageSize);
		
		Map<String,Object> map=new HashMap<>();
		
		Page<Message> messageList = (Page<Message>) messageService.selectAllMessage(map);
		pageUtil.setPageInfo(messageList, index, pageSize,request);
		
		modelMap.put("messageList", messageList);
		
		return "/message/list-message";
	}
	
	/**
	 * 跳转到添加、修改页面
	 * @param message
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toUpdateMessage")
	public String toUpdateMessage(Message message,ModelMap modelMap) {
		Map<String,Object> map=new HashMap<>();
		if(message.getId()!=null) {
			List<Message> messageList = messageService.selectAllMessage(map);
			modelMap.put("message", messageList.get(0));
		}
		List<Zone> zoneList = zoneService.selectAllZone(null);
		modelMap.put("zoneList", zoneList);
		
		return "/message/edit-message";
	}
	
	@RequestMapping("/updateMessage")
	public String updateMessage(@RequestParam(value="file") MultipartFile file,Message message) {
		System.out.println(file.getName());
		System.out.println(message);
		return "";
	}
	
	/**
	 * 删除消息
	 * 
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteMessage")
	public String deleteMessage(Message message) {
		if(messageService.deleteByPrimaryKey(message)>0) {
			return "success";
		}
		return "";
	}
	
}
