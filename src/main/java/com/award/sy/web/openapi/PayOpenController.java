package com.award.sy.web.openapi;

import java.math.BigDecimal;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.award.core.util.JsonUtils;
import com.award.sy.common.Constants;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.common.WxPayUtil;
import com.award.sy.entity.Wallet;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.RedPacketService;
import com.award.sy.service.WalletRecordService;
import com.award.sy.service.WalletService;
/**
 * 充值，提现，订单生成API
 * @ClassName:       PayOpenController
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月25日        下午4:50:37
 */
@Controller
public class PayOpenController {
	
	
	@Autowired
	private RedPacketService redPacketService;
	
	@Autowired
	private WalletRecordService walletRecordService;
	
	@Autowired
	private WalletService walletService;
	
	
	/**
	 * 统一生成订单
	 * @Title:           genOrder
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param trade_type
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	
	/*@RequestMapping("/open/genOrder")
	@ResponseBody
	public String genOrder(@RequestParam String user_id,@RequestParam String trade_type){
		if(StringUtils.isBlank(user_id)||StringUtils.isBlank(trade_type)){
			return JsonUtils.writeJson(0, 0, "参数错误");
		}
		String record_sn = "";
		String recordSn = PayCommonUtil.createOutTradeNo(); 
		long userId = Long.parseLong(user_id);
		int orderType = Integer.parseInt(trade_type);
		if(Constants.ORDER_TYPE_TRADE == orderType ){
			//交易充值
			//walletRecord.setTo_uid(0L);
			
			walletRecordService.addWalletRecordOrder(userId, recordSn, orderType);
			return  JsonUtils.writeJson(1, "订单生成成功", recordSn, "object");
			
		}else if (Constants.ORDER_TYPE_REDPACKET == orderType){
			//红包
			record_sn = redPacketService.addRedPacketOrder(userId);
			return  JsonUtils.writeJson(1, "订单生成成功", record_sn, "object");
		}else if(Constants.ORDER_TYPE_TASK == orderType){
			//任务
			
		}else if(Constants.ORDER_TYPE_WITHDRAWLS == orderType){
			//提现
			walletRecordService.addWalletRecordOrder(userId, recordSn, orderType);
			return  JsonUtils.writeJson(1, "订单生成成功", recordSn, "object");
		}else if(Constants.ORDER_TYPE_BACK == orderType){
			//退款
			walletRecordService.addWalletRecordOrder(userId, recordSn, orderType);
			return  JsonUtils.writeJson(1, "订单生成成功", recordSn, "object");
		}else{
			 return JsonUtils.writeJson(0, 19, "订单生成失败");
		}
		return JsonUtils.writeJson(0, 19, "订单生成失败");
	}*/
	/**
	 * 微信生成preperId
	 * @Title:           payByWx
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param record_sn
	 * @param:           @param pay_type
	 * @param:           @param money
	 * @param:           @param request
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	/*@RequestMapping(value="/open/payByWx")
	@ResponseBody
	public String payByWx(@RequestParam String user_id,@RequestParam String record_sn,@RequestParam String pay_type,@RequestParam String money,HttpServletRequest request){
		if(StringUtils.isBlank(user_id)||StringUtils.isBlank(record_sn)||StringUtils.isBlank(pay_type)||StringUtils.isBlank(money)){
			return JsonUtils.writeJson(0, 0, "参数错误");
		}
		long userId = Long.parseLong(user_id);
		int payType = Integer.parseInt(pay_type);
		if(Constants.PAY_TYPE_BALANCE == payType){//余额支付
			Wallet wallet = walletService.findWalletByUserId(userId);
			if(null == wallet){
				return JsonUtils.writeJson(0, 4, "用户不存在");
			}
			if(new BigDecimal(money).compareTo(wallet.getMoney()) > 0){
				return JsonUtils.writeJson(0, 21, "余额不足");
			}
			
			if(walletService.payByBalance(user_id,record_sn,money)){
				
			}
			
		}else if(Constants.PAY_TYPE_WECHAT == payType){//选择微信支付，返回preperId
			//查询本地记录
			WalletRecord walletRecord = walletRecordService.findWallerOrderByRecordSN(record_sn);
			if(null != walletRecord){
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
			//更新订单的金额和支付方式
			walletRecord.setMoney(new BigDecimal(money));
			walletRecord.setPay_type(Constants.PAY_TYPE_WECHAT);
			walletRecordService.editWalletOrder(walletRecord);
			if(new BigDecimal(money).compareTo(walletRecord.getMoney().multiply(new BigDecimal(100))) != 0){
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
			SortedMap<Object,Object> result  = WxPayUtil.getPreperIdFromWX(record_sn,PayCommonUtil.getIpAddress(request),walletRecord.getMoney().doubleValue());
			return  JsonUtils.writeJson(1, "请求成功", result, "object");
			
		}
		return JsonUtils.writeJson(0, 0, "参数错误");
	}*/

	@RequestMapping(value="/open/payByWX")
	@ResponseBody
	public String payByWX(@RequestParam String user_id,@RequestParam String pay_type,@RequestParam String money,@RequestParam String type,HttpServletRequest request){
		if(StringUtils.isBlank(user_id)||StringUtils.isBlank(pay_type)||StringUtils.isBlank(money)||StringUtils.isBlank(type)){
			return JsonUtils.writeJson(0, 0, "参数错误");
		}
		String result = JsonUtils.writeJson(0, 0, "参数错误");
		int payType = Integer.parseInt(pay_type);
		int taskType = Integer.parseInt(type); 
		long userId = Long.parseLong(user_id);
		double price = Double.parseDouble(money);
		//微信支付
		if(Constants.PAY_TYPE_WECHAT == payType){
			
			if(Constants.ORDER_TYPE_REDPACKET == taskType){
				//微信支付发红包
				String record_sn = redPacketService.addRedPacketOrderRecord(userId,money,payType);
				SortedMap<Object, Object> map =  WxPayUtil.getPreperIdFromWX(record_sn, PayCommonUtil.getIpAddress(request),Constants.APP_NAME+Constants.REDPACKET, price);
				if(null == map){
					return JsonUtils.writeJson(0, 19, "订单生成失败");
				}
				return  JsonUtils.writeJson(1, "请求成功", map, "object");
			}else if(Constants.ORDER_TYPE_TASK == taskType){ 
				//微信支付发任务
				
			}else if(Constants.ORDER_TYPE_TRADE == taskType){
				//微信支付充值
				String record_sn = walletService.addRechargeOrderRecord(userId,money,payType);
			}else {
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
			
		}else if(Constants.PAY_TYPE_BALANCE == payType){
			
			if(Constants.ORDER_TYPE_REDPACKET == taskType){
				//余额支付发红包
				result = redPacketService.addRedPacketOrderRecord(userId,money,payType);
				return  JsonUtils.writeJson(1, "请求成功", result, "object");
			}else if(Constants.ORDER_TYPE_TASK == taskType){ 
				//余额支付发任务
			}else {
				return result;
			}
			
		}
		
		
		/*long userId = Long.parseLong(user_id);
		int payType = Integer.parseInt(pay_type);
		if(Constants.PAY_TYPE_BALANCE == payType){//余额支付
			Wallet wallet = walletService.findWalletByUserId(userId);
			if(null == wallet){
				return JsonUtils.writeJson(0, 4, "用户不存在");
			}
			if(new BigDecimal(money).compareTo(wallet.getMoney()) > 0){
				return JsonUtils.writeJson(0, 21, "余额不足");
			}
			
			if(walletService.payByBalance(user_id,record_sn,money)){
				
			}
			
		}else if(Constants.PAY_TYPE_WECHAT == payType){//选择微信支付，返回preperId
			//查询本地记录
			WalletRecord walletRecord = walletRecordService.findWallerOrderByRecordSN(record_sn);
			if(null != walletRecord){
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
			//更新订单的金额和支付方式
			walletRecord.setMoney(new BigDecimal(money));
			walletRecord.setPay_type(Constants.PAY_TYPE_WECHAT);
			walletRecordService.editWalletOrder(walletRecord);
			if(new BigDecimal(money).compareTo(walletRecord.getMoney().multiply(new BigDecimal(100))) != 0){
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
			SortedMap<Object,Object> result  = WxPayUtil.getPreperIdFromWX(record_sn,PayCommonUtil.getIpAddress(request),walletRecord.getMoney().doubleValue());
			return  JsonUtils.writeJson(1, "请求成功", result, "object");
			
		}*/
		return result;
	}
}
