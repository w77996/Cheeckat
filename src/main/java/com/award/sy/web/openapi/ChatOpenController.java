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

import com.award.core.util.ImUtils;
import com.award.core.util.JsonUtils;
import com.award.sy.common.DateUtil;
import com.award.sy.entity.Group;
import com.award.sy.entity.GroupDetails;
import com.award.sy.entity.User;
import com.award.sy.service.FriendService;
import com.award.sy.service.GroupDetailsService;
import com.award.sy.service.GroupService;
import com.award.sy.service.UserService;

@Controller
public class ChatOpenController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;
	
	@Resource
	private FriendService friendService;
	
	@Resource
	private GroupService groupService;
	
	@Resource
	private GroupDetailsService groupDetailsService;
	
	/**
	 * 查询附近的人
	 * 
	 * @param request
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/open/addGroup", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addGroup(HttpServletRequest request) {
		String groupName = request.getParameter("groupName");
		String userId = request.getParameter("userId");
		String members = request.getParameter("members");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		boolean isFriend = true;
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(members) && !StringUtils.isBlank(groupName)) {
			String[] member = members.split(",");
			if(member.length >= 2) {
			for(int i = 0; i < member.length; i++) {
				List<Map<String,Object>> list = friendService.getFriendByTwoId(Long.parseLong(userId), Long.parseLong(member[i]));
				if(list.size() > 0) {
					for(Map<String,Object> map : list) {
						int status = (Integer)map.get("status");
						if(status != 2) {
							isFriend = false;
						}
					}
				}
			}
			if(isFriend) {
				String[] ids = userId.concat(",").concat(members).split(",");
				List<User> userList = userService.getUserByIds(ids);
				String memberNames = "";
				String admin = "";
				for(User user:userList) {
					if(user.getUser_id() == Long.parseLong(userId)) {
						admin = user.getUser_name();
					}
					String[] memberStr = members.split(",");
					for(int i = 0; i < memberStr.length; i++) {
						if(user.getUser_id() == Long.parseLong(memberStr[i])) {
							if(memberNames.equals("")) {
								memberNames = user.getUser_name();
							}else {
								memberNames = memberNames.concat(",").concat(user.getUser_name());
							}
						}
					}
				}
				Group group = groupService.getGroupByNameAndAdminId(groupName,Long.parseLong(userId));
				if(group != null) {
					return JsonUtils.writeJson(0,33, "该群已存在");
				}
				String imGroupId = "";
				if(!admin.equals("") && !memberNames.equals("")) {
					imGroupId = ImUtils.addGroup(groupName, memberNames.split(","), admin);
					if(imGroupId.equals("")) {
						return JsonUtils.writeJson(0,27, "环信群创建失败");
					}
				}else {
					return JsonUtils.writeJson(0,26, "群成员不存在");
				}				
				group = new Group();
				group.setGroup_name(groupName);
				group.setCreate_time(DateUtil.getNowTime());
				group.setIm_group_id(imGroupId);
				group.setAdmin_id(Long.parseLong(userId));
				groupService.addGroup(group);
				Group group2 = groupService.getGroupByNameAndAdminId(groupName,Long.parseLong(userId));
				GroupDetails gd = new GroupDetails();
				gd.setMember_id(Long.parseLong(userId));
				gd.setIs_admin(1);
				gd.setJoin_time(DateUtil.getNowTime());
				gd.setGroup_id(group2.getGroup_id());
				groupDetailsService.addGroupDetails(gd);
				for(int i = 0; i < member.length; i++) {
					GroupDetails gd2 = new GroupDetails();
					gd2.setMember_id(Long.parseLong(member[i]));
					gd2.setIs_admin(0);
					gd2.setJoin_time(DateUtil.getNowTime());
					gd2.setGroup_id(group2.getGroup_id());
					groupDetailsService.addGroupDetails(gd2);
				}
				returnStr = JsonUtils.writeJson("创建成功",1);
			}else {
				returnStr = JsonUtils.writeJson(0,7, "存在与你不是互关的成员");
			}
			}else {
				returnStr = JsonUtils.writeJson(0,32, "群组成员不能小于3个");
			}
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/delMember", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delMember(HttpServletRequest request) {
		String member = request.getParameter("member");
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(member) && !StringUtils.isBlank(groupId)) {
			Group group = groupService.getGroupById(Long.parseLong(groupId));
			if(group != null) {
			GroupDetails gd = groupDetailsService.getGroupDetailsByGroupIdAndUserId(Long.parseLong(groupId), Long.parseLong(userId));
			if(gd != null) {
				if(gd.getIs_admin() == 0) {
					returnStr = JsonUtils.writeJson(0, 14, "你不是群主");
				}else {
					GroupDetails gd1 = groupDetailsService.getGroupDetailsByGroupIdAndUserId(Long.parseLong(groupId), Long.parseLong(member));
					if(gd1 != null) {
						User user = userService.getUserById(Long.parseLong(member));
						if(user != null) {
						boolean isSuccess = ImUtils.delSingleMember(group.getIm_group_id(), user.getUser_name());
						if(isSuccess) {
							groupDetailsService.removeGroupDetails(gd1.getDetails_id());
						    returnStr = JsonUtils.writeJson("删除成功",1);
						}else {
							returnStr = JsonUtils.writeJson(0, 29, "环信群成员删除失败");
						}
						}else {
							returnStr = JsonUtils.writeJson(0, 26, "成员账号不存在");
						}
					}else {
						returnStr = JsonUtils.writeJson(0, 15, "成员ID不存在");
					}
				}
			}else {
				returnStr = JsonUtils.writeJson(0, 13, "群主ID不存在");
			}
		}else {
			returnStr = JsonUtils.writeJson(0, 25, "群ID不存在");
		}
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/addMember", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addMember(HttpServletRequest request) {
		String member = request.getParameter("member");
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(member) && !StringUtils.isBlank(groupId)) {
			Group group = groupService.getGroupById(Long.parseLong(groupId));
			if(group != null) {
			GroupDetails gd = groupDetailsService.getGroupDetailsByGroupIdAndUserId(Long.parseLong(groupId), Long.parseLong(userId));
			if(gd != null) {
					GroupDetails gd1 = groupDetailsService.getGroupDetailsByGroupIdAndUserId(Long.parseLong(groupId), Long.parseLong(member));
					if(gd1 == null) {
						User user = userService.getUserById(Long.parseLong(member));
						if(user != null) {
						boolean isSuccess = ImUtils.addSingleMember(group.getIm_group_id(), user.getUser_name());
						if(isSuccess) {
							gd1 = new GroupDetails();
							gd1.setGroup_id(Long.parseLong(groupId));
							gd1.setIs_admin(0);
							gd1.setJoin_time(DateUtil.getNowTime());
							gd1.setMember_id(Long.parseLong(member));
							groupDetailsService.addGroupDetails(gd1);
						    returnStr = JsonUtils.writeJson("添加成功",1);
						}else {
							returnStr = JsonUtils.writeJson(0, 31, "环信群成员添加失败");
						}
						}else {
							returnStr = JsonUtils.writeJson(0, 26, "成员账号不存在");
						}
					}else {
						returnStr = JsonUtils.writeJson(0, 30, "成员已经存在");
					}
			}else {
				returnStr = JsonUtils.writeJson(0, 15, "邀请人不存在");
			}
		}else {
			returnStr = JsonUtils.writeJson(0, 25, "群ID不存在");
		}
		}
		return returnStr;
	}
	
	@RequestMapping(value = "/open/updateGroup", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateGroup(HttpServletRequest request) {
		String groupName = request.getParameter("groupName");
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(!StringUtils.isBlank(userId) && !StringUtils.isBlank(groupId) && !StringUtils.isBlank(groupName)) {
			Group group = groupService.getGroupById(Long.parseLong(groupId));
			if(group != null) {
				GroupDetails gd = groupDetailsService.getGroupDetailsByGroupIdAndUserId(Long.parseLong(groupId), Long.parseLong(userId));
				if(gd != null) {
					if(gd.getIs_admin() == 1) {
						boolean isSuccess = ImUtils.updateGroup(groupName, group.getIm_group_id());
						if(!isSuccess) {
							return JsonUtils.writeJson(0, 28, "修改失败");
						}
						group.setGroup_name(groupName);
						groupService.editGroup(group);
						returnStr = JsonUtils.writeJson("修改成功",1);
					}else {
						returnStr = JsonUtils.writeJson(0, 14, "无权限修改");
					}
				}else {
					returnStr = JsonUtils.writeJson(0, 13, "群ID不存在");
				}
			}else {
				returnStr = JsonUtils.writeJson(0, 25, "群ID不存在");
			}
		}
		return returnStr;
	}

}
