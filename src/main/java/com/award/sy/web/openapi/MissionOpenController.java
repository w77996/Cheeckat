package com.award.sy.web.openapi;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.common.DateUtil;
import com.award.sy.entity.Mission;
import com.award.sy.entity.User;
import com.award.sy.service.MissionService;
import com.award.sy.service.UserService;


@Controller
public class MissionOpenController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;
	
	@Resource
	private MissionService missionService;
	
	/**
	 * 查询附近的人
	 * 
	 * @param request
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/open/getAllMission", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAllMission(HttpServletRequest request) {
		String start = request.getParameter("start");
		String count = request.getParameter("count");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(start) && !StringUtils.isBlank(count)) {
			List<Map<String,Object>> list = missionService.getAllMissionLimit(Integer.parseInt(start), Integer.parseInt(count));
			returnStr = JsonUtils.writeJson(1, "获取成功", list, "object");
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/getMyMission", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMyMission(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId)) {
			User user = userService.getUserById(Long.parseLong(userId));
		    if(user != null) {
		    	List<Map<String,Object>> list = missionService.getMyMission(Long.parseLong(userId));			    
				returnStr = JsonUtils.writeJson(1, "获取成功", list, "object");
		    }else {
		    	returnStr = JsonUtils.writeJson(0, 4, "用户不存在");			
		    }
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/acceptMission", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String acceptMission(HttpServletRequest request) {
		String userId =  request.getParameter("userId");
		String missionId = request.getParameter("missionId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(missionId)) {
			User user = userService.getUserById(Long.parseLong(userId));
		    if(user != null) {
		    	Mission mission = missionService.getMissionById(Long.parseLong(missionId));
		    	if(mission != null) {
		    		if(mission.getSex() == user.getSex()) {
		    			if(mission.getPublish_id() != user.getUser_id()) {
		    			if(mission.getAccept_id() == null) {
		    				mission.setAccept_id(user.getUser_id());
			    			mission.setStatus(1);
			    			mission.setAccept_time(DateUtil.getNowTime());
			 			    missionService.editMission(mission);
			 			    returnStr = JsonUtils.writeJson("设置成功", 1);
		    			}else {
		    				returnStr = JsonUtils.writeJson(0, 9, "该任务已被领取");
		    			}
		    			}else {
		    				returnStr = JsonUtils.writeJson(0, 11, "不能领取自己的任务");
		    			}
		    			
		    		}else {
		    			returnStr = JsonUtils.writeJson(0, 6, "该任务不适合你");
		    		}
		    	}else {
		    		returnStr = JsonUtils.writeJson(0, 8, "任务不存在");
		    	}
		    }else {
		    	returnStr = JsonUtils.writeJson(0, 4, "用户不存在");			
		    }
		   }
		return returnStr;
	}
	
	@RequestMapping(value = "/open/completeMission", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String completeMission(HttpServletRequest request) {
		String userId =  request.getParameter("userId");
		String missionId = request.getParameter("missionId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(missionId)) {
			User user = userService.getUserById(Long.parseLong(userId));
		    if(user != null) {
		    	Mission mission = missionService.getMissionById(Long.parseLong(missionId));
		    	if(mission != null) {
		    		if(mission.getAccept_id() == Long.parseLong(userId) || mission.getPublish_id() == Long.parseLong(userId)) {
		    			if(mission.getAccept_id() == Long.parseLong(userId)) {
		    				mission.setStatus(2);
		    			}else if(mission.getPublish_id() == Long.parseLong(userId) && mission.getStatus() == 2) {
		    				mission.setStatus(3);
		    				mission.setFinish_time(DateUtil.getNowTime());
		    			}else {
		    				returnStr = JsonUtils.writeJson(0, 12, "无法确认完成");
		    			}
		 			    missionService.editMission(mission);
		 			    returnStr = JsonUtils.writeJson("设置成功", 1);
		    		}else {
		    			returnStr = JsonUtils.writeJson(0, 10, "无权限操作");
		    		}
		    	}else {
		    		returnStr = JsonUtils.writeJson(0, 8, "任务不存在");
		    	}
		    }else {
		    	returnStr = JsonUtils.writeJson(0, 4, "用户不存在");			
		    }
		   }
		return returnStr;
	}
	
	@RequestMapping(value = "/open/closeMission", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closeMission(HttpServletRequest request) {
		String userId =  request.getParameter("userId");
		String missionId = request.getParameter("missionId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(missionId)) {
			User user = userService.getUserById(Long.parseLong(userId));
		    if(user != null) {
		    	Mission mission = missionService.getMissionById(Long.parseLong(missionId));
		    	if(mission != null) {
		    		if(mission.getPublish_id() == Long.parseLong(userId)) {
                        if(mission.getStatus() == 0) {
		    				mission.setStatus(4);
		    			}else {
		    				returnStr = JsonUtils.writeJson(0, 12, "无法关闭");
		    			}
		 			    missionService.editMission(mission);
		 			    returnStr = JsonUtils.writeJson("设置成功", 1);
		    		}else {
		    			returnStr = JsonUtils.writeJson(0, 10, "无权限操作");
		    		}
		    	}else {
		    		returnStr = JsonUtils.writeJson(0, 8, "任务不存在");
		    	}
		    }else {
		    	returnStr = JsonUtils.writeJson(0, 4, "用户不存在");			
		    }
		   }
		return returnStr;
	}

	
	@RequestMapping(value = "/open/publishMission", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String publishMission(HttpServletRequest request) {
		String userId =  request.getParameter("userId");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		String sex = request.getParameter("sex");
		String money = request.getParameter("money");
		String address = request.getParameter("address");
		String startTime = request.getParameter("startTime");
		String to = request.getParameter("to");
		String anonymous = request.getParameter("anonymous");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(content)&& !StringUtils.isBlank(type)&& !StringUtils.isBlank(sex)&& !StringUtils.isBlank(money)&& !StringUtils.isBlank(address)&& !StringUtils.isBlank(startTime)) {
			User user = userService.getUserById(Long.parseLong(userId));
		    if(user != null) {
		    	Mission mission = new Mission();
		    	mission.setAddress(address);
		    	mission.setContent(content);
		    	mission.setCreate_time(DateUtil.getNowTime());
		    	mission.setMoney(Double.parseDouble(money));
		    	mission.setPublish_id(user.getUser_id());
		    	mission.setSex(Integer.parseInt(sex));
		    	mission.setStart_time(startTime);
		    	mission.setStatus(0);
		    	mission.setType(Integer.parseInt(type));
		    	mission.setTo_user(Long.parseLong(to));
		    	mission.setAnonymous(Integer.parseInt(anonymous));
		    	missionService.addMission(mission);
		    	returnStr = JsonUtils.writeJson("发布成功", 1);
		    }else {
		    	returnStr = JsonUtils.writeJson(0, 4, "用户不存在");			
		    }
		   }
		return returnStr;
	}


}
