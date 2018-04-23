package com.award.sy.web.openapi;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.service.FriendService;

@Controller
public class FriendOpenController {

	@Autowired
	private FriendService friendService;
	/**
	 * 获取好友列表，头像，昵称，性别，好友ID
	 * <p>Title: getFriend</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/open/getFriend",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFriend(@RequestParam long userId){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")){
			return returnStr;
		}
		
		List<Map<String,Object>> list = friendService.getUserFriends(userId);
		
		return returnStr = JsonUtils.writeJson(1, "获取成功", list, "object");
	}
	/**
	 * 获取好友主页
	 * <p>Title: getFriendIndex</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/open/getFriendIndex",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFriendIndex(@RequestParam long userId){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")){
			return returnStr;
		}
		
		List<Map<String,Object>> list = friendService.getUserFriends(userId);
		
		return returnStr = JsonUtils.writeJson(1, "获取成功", list, "object");
	}
}
