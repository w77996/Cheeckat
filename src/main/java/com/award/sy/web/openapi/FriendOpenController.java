package com.award.sy.web.openapi;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.Friend;
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
	public String getFriendIndex(@RequestParam String userId){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId)){
			return returnStr;
		}
		
		List<Map<String,Object>> list = friendService.getUserFriends(Long.parseLong(userId));
		
		return  JsonUtils.writeJson(1, "获取成功", list, "object");
	}

	/**
	 * 添加好友
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@RequestMapping(value = "/open/addFriend")
	@ResponseBody
	public String addFriend(@RequestParam String userId,@RequestParam String friendId){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(friendId)){
			return returnStr;
		}

		Friend friend = friendService.findFriends(Long.parseLong(userId),Long.parseLong(friendId));
		if(null != friend){
			return  JsonUtils.writeJson(0, 38, "好友已添加");
		}
		Friend useraddFriend = new Friend();
		useraddFriend.setUser_id_fr1(Long.parseLong(userId));
		useraddFriend.setUser_id_fr2(Long.parseLong(friendId));
		useraddFriend.setStatus(2);

		int i = friendService.addFriends(useraddFriend);
		if(0 < i){
			return JsonUtils.writeJson("添加成功", 1);
		}else{
			return JsonUtils.writeJson(0, 39, "参数为空");
		}
	}
}
