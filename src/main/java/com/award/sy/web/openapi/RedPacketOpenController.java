package com.award.sy.web.openapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.entity.User;
import com.award.sy.service.UserService;

@Controller
public class RedPacketOpenController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/open/getRedPacket")
	@ResponseBody
	public String getRedPacket(@RequestParam String user_id,@RequestParam String redpacket_id){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(user_id)|| StringUtils.isBlank(redpacket_id)){
			return returnStr;
		}
		User user = userService.getUserById(Long.parseLong(user_id));
		if(null == user){
			
		}
		return null;
	}
}
