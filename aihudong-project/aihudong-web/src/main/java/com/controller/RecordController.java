package com.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Admin;
import com.model.Record;
import com.service.RecordService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private RecordService recordService;
	
	
	@ResponseBody
	@RequestMapping(value="/getOrderByTime",produces = "text/json;charset=UTF-8")
	public String getOrderByTime(HttpSession session) {
		List<Map<String, Object>> teacherRecordList = getTeacherOrderByTime(session);
		List<Map<String, Object>> studentRecordList = getStudentOrderByTime(session);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("teacherRecordList", teacherRecordList);
		jsonObject.put("studentRecordList", studentRecordList);
		return jsonObject.toString();
	}
	/**
	 * 查询用产品时长最多的前几位教师
	 * @param session
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value="/getTeacherOrderByTime",produces = "text/json;charset=UTF-8")*/
	public List<Map<String,Object>> getTeacherOrderByTime(HttpSession session){
		Record record=new Record();
		record.setRole(1);
		Admin admin=(Admin) session.getAttribute("admin");
		Map<String,Object> adminMap=new HashMap<>();
		adminMap.put("role", record.getRole());
		if(admin.getPower()==1){
			adminMap.put("adminId1", admin.getId());
		}else if(admin.getPower()==2){
			adminMap.put("adminId2", admin.getId());
		}
		List<Map<String,Object>> teacherRecordList = recordService.selectTeacherOrderByTime(adminMap);
		for (int i = 0; i < teacherRecordList.size(); i++) {
			Map<String, Object> map = teacherRecordList.get(0);
			teacherRecordList.remove(0);
			Object object = map.get("time");
			String time=object.toString(); 
			if(time.contains(".")) {
				time=time.substring(0,time.indexOf("."));
			}
//			截字符串 00:00:00 只获取小时，不足一小时的按一小时算，没有用过的记0
			if(time.length()>4){
				time=time.substring(0, time.length()-4);
			}else if(time.length()>0){
				time="1";
			}else{
				time="0";
			}
			map.put("time", time);
			teacherRecordList.add(map);
		}
		
		
		return teacherRecordList;
	}
	/**
	 * 查询用产品时长最多的前几名学生
	 * @param session
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value="/getStudentOrderByTime",produces = "text/json;charset=UTF-8")*/
	public List<Map<String, Object>> getStudentOrderByTime(HttpSession session){
		Record record=new Record();
		record.setRole(2);
		Admin admin=(Admin) session.getAttribute("admin");
		Map<String,Object> adminMap=new HashMap<>();
		adminMap.put("role", record.getRole());
		if(admin.getPower()==1){
			adminMap.put("adminId1", admin.getId());
		}else if(admin.getPower()==2){
			adminMap.put("adminId2", admin.getId());
		}
		List<Map<String,Object>> studentRecordList = recordService.selectStudentOrderByTime(adminMap);
		for (int i = 0; i < studentRecordList.size(); i++) {
			Map<String, Object> map = studentRecordList.get(0);
			studentRecordList.remove(0);
			Object object = map.get("time");
			String time=object.toString();
			if(time.contains(".")) {
				time=time.substring(0,time.indexOf("."));
			}
			if(time.length()>4){
				time=time.substring(0, time.length()-4);
			}else if(time.length()>0){
				time="1";
			}else{
				time="0";
			}
			map.put("time", time);
			studentRecordList.add(map);
		}
		
		return studentRecordList;
	}

}
