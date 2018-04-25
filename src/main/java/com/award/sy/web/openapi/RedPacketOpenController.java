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
import com.award.sy.common.Constants;
import com.award.sy.common.DateUtil;
import com.award.sy.entity.RedPacket;
import com.award.sy.entity.User;
import com.award.sy.service.RedPacketService;
import com.award.sy.service.UserService;
import com.award.sy.service.WalletRecordService;

@Controller
public class RedPacketOpenController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private WalletRecordService walletRecordService;
	
	@Autowired
	private RedPacketService redPacketService;
	/**
	 * 抢红包
	 * @Title:           getRedPacket
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param redpacket_id
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	@RequestMapping("/open/getRedPacket")
	@ResponseBody
	public String getRedPacket(@RequestParam String user_id,@RequestParam String redpacket_id){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(user_id)|| StringUtils.isBlank(redpacket_id)){
			return returnStr;
		}
		//查询领取的用户信息
		User user = userService.getUserById(Long.parseLong(user_id));
		if(null == user){
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		//查询红包信息
		RedPacket redPacket = redPacketService.getRedPacketById(Long.parseLong(redpacket_id));
		if(null == redPacket){
			return JsonUtils.writeJson(0, 16, "红包id不存在");
		}
		if(redPacket.getStatus() == Constants.FETCH_SUCCESS){
			return JsonUtils.writeJson(0, 17, "红包被领取");
		}
		
		redPacket.setAccept_id(Long.parseLong(user_id));
		redPacket.setStatus(Constants.FETCH_SUCCESS);
		//redPacket.setAccept_time(DateUtil.getNowTime());
		/*if(!redPacketService.getRedPacket(redPacket)){
			return JsonUtils.writeJson(0, 18, "红包领取失败");
		}*/
		List<Map<String, Object>> list = redPacketService.getRedPacket(redPacket);
		if(null != list){
			return JsonUtils.writeJson(1, "领取成功", list, "object");
		}else {
			return JsonUtils.writeJson(0, 18, "红包领取失败");
		}
		//List<Map<String, Object>> list = redPacketService.getRedPacketById(redpacketId)
		
	}
	
	/**
	 * 生成红包订单
	 * @Title:           genRedPacketOrder
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	/*@RequestMapping(value="/open/genRedPacketOrder")
	@ResponseBody
	public String genRedPacketOrder(@RequestParam String user_id){
		if(StringUtils.isBlank(user_id)){
			return JsonUtils.writeJson(0, 0, "参数为空");
		}
		
		if(redPacketService.addRedPacketOrder(Long.parseLong(user_id))){
			return   JsonUtils.writeJson("红包订单生成成功", 1);
		}else{
			return JsonUtils.writeJson(0, 19, "订单生成失败");
		}
		
	}*/
	
	/*@RequestMapping(value="/open/payRedPacketOrder")
	@ResponseBody
	public String payRedPacketOrder(){
		return null;
	}*/
}
