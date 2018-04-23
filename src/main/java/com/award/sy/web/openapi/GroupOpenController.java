package com.award.sy.web.openapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.service.GroupDetailsService;
import com.award.sy.service.GroupService;

@Controller
public class GroupOpenController {
	
	@Autowired
	private GroupDetailsService groupDetailsService;
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * 获取用户群聊列表
	 * <p>Title: getUserGroup</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/open/getGroupList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserGroup(@RequestParam long userId){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")){
			return returnStr;
		}
		//获取User下的群组
		List<Map<String,Object>> list = groupService.getUserGroup(userId);
		if(null == list || 0 == list.size()){
			return  JsonUtils.writeJson(1, "获取成功,无群组", null, "object");
		}
		List<Map<String,Object>> resultList = new ArrayList<>();
		for(int i = 0;i < list.size();i++){
			Map<String,Object> result = new HashMap<String,Object>();
			long groupId = (long) list.get(i).get("group_id");
			result.put("groupId",groupId);
			result.put("groupName",list.get(i).get("group_name"));
			result.put("creatTime", list.get(i).get("create_time"));
			result.put("isAdmin",list.get(i).get("is_admin"));
			//获取groupId下的用户信息
			List<Map<String,Object>> userList = groupDetailsService.getUserGroupDetails(groupId);
			result.put("user",userList);
			resultList.add(result);
		}
		return  JsonUtils.writeJson(1, "获取成功", resultList, "object");
	}
	/**
	 * 用户退出群聊
	 * <p>Title: exitGroup</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/open/exitGroup",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String exitGroup(@RequestParam long userId,@RequestParam long groupId,@RequestParam int isAdmin){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")||StringUtils.isBlank(groupId+"")||StringUtils.isBlank(isAdmin+"")){
			return returnStr;
		}
		//获取User下的群组，校验防止恶意参数
		/*List<Map<String,Object>> list = groupService.getUserGroup(userId);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("group_id",groupId);
		if(null == list || 0 == list.size()||!list.contains(param)){
			return  JsonUtils.writeJson(0, 0, "参数为空");
		}*/
		List<Map<String,Object>> userList = groupDetailsService.getUserGroupDetails(groupId);
		if(userList.size() ==  3){
			//删除这个群
			int i = groupService.deleteGroup(groupId);
			if(i > 0){
				return  JsonUtils.writeJson(1, "退出成功", null, "object");
			}else {
				return JsonUtils.writeJson(1, 0, "退出失败");
			}
		}else if(userList.size() > 3){
			//删除用户在群中的信息
			if(isAdmin == 1){
				//作为管理员
				int i = groupDetailsService.deleteUserAdminFromGroup(userId,groupId);
				if(i > 0){
					return  JsonUtils.writeJson(1, "退出成功", null, "object");
				}else {
					return JsonUtils.writeJson(1, 0, "退出失败");
				}
			}else if(isAdmin == 0){
				//不作为管理员
				int i = groupDetailsService.deleteUserFromGroup(userId);
				if(i > 0){
					return  JsonUtils.writeJson(1, "退出成功", null, "object");
				}else {
					return JsonUtils.writeJson(1, 0, "退出失败");
				}
			}
		}
		
		return JsonUtils.writeJson(0, 0, "未知错误");
	}

}
