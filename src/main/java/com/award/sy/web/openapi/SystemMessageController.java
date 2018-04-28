package com.award.sy.web.openapi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.entity.Message;
import com.award.sy.entity.User;
import com.award.sy.service.MessageService;
import com.award.sy.service.UserService;
@Controller
public class SystemMessageController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;
	
	@Resource
	private MessageService messageService;
	
	/**
	 * 查询官方公告
	 * 
	 * @param request
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/open/getNotice", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAllMission(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId)) {
			User user = userService.getUserById(Long.parseLong(userId));
			if(user != null) {
				Message message = messageService.getLastMessage(0);
				returnStr = JsonUtils.writeJson(1, "获取成功", message, "object");
			}else {
				returnStr = JsonUtils.writeJson(0, 4, "用户不存在");
			}
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/getSystemMessage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSystemMessage(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId)) {
			User user = userService.getUserById(Long.parseLong(userId));
			if(user != null) {
				Message message = messageService.getLastMessage(1);
				returnStr = JsonUtils.writeJson(1, "获取成功", message, "object");
			}else {
				returnStr = JsonUtils.writeJson(0, 4, "用户不存在");
			}
		}
		return returnStr;
	}
	

}
