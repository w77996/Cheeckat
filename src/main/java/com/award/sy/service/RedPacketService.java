package com.award.sy.service;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.RedPacket;
/**
 * 红包service接口
 * @ClassName:       RedPacketService
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月25日        上午9:59:11
 */
public interface RedPacketService {
	/**
	 * 通过Id获取红包
	 * @Title:           getRedPacketById
	 * @Description:     TODO
	 * @param:           @param redpacketId
	 * @param:           @return   
	 * @return:          RedPacket   
	 * @throws
	 */
	RedPacket getRedPacketById(long redpacketId);
	/**
	 * 生成红包订单,充值
	 * @Title:           addRedPacketOrder
	 * @Description:     TODO
	 * @param:           @param parseLong
	 * @param:           @return   
	 * @return:          int   
	 * @throws
	 */
	//String addRedPacketOrder(long userId);
	/**
	 * 抢红包
	 * @Title:           getRedPacket
	 * @Description:     TODO
	 * @param:           @param redPacket   
	 * @return:          void   
	 * @throws
	 */
	 List<Map<String, Object>> getRedPacket(RedPacket redPacket);
	 
	 /**
	  * 更新红包状态
	  * @Title:           editRedPacket
	  * @Description:     TODO
	  * @param:           @param redPacket   
	  * @return:          void   
	  * @throws
	  */
	 boolean editRedPacketSendMessage(String  record_sn,int pay_status);
	 /**
	  * 生成订单和红包记录
	  * @Title:           addRedPacketOrderRecord
	  * @Description:     TODO
	  * @param:           @param userId
	  * @param:           @return   
	  * @return:          String   
	  * @throws
	  */
	 String addRedPacketOrderRecord(long userId,String money,int pay_type);

}
