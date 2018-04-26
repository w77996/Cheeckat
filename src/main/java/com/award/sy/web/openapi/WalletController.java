package com.award.sy.web.openapi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.award.core.util.JsonUtils;
import com.award.sy.common.Constants;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.common.WxPayUtil;
import com.award.sy.entity.User;
import com.award.sy.entity.Wallet;
import com.award.sy.entity.WalletLog;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.UserService;
import com.award.sy.service.WalletLogService;
import com.award.sy.service.WalletRecordService;
import com.award.sy.service.WalletService;
import com.google.zxing.Result;
import com.sun.tools.javac.code.Attribute.Constant;

@Controller
public class WalletController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WalletRecordService walletRecordService;

	@Autowired
	private WalletLogService walletLogService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private UserService userService;

	/**
	 * 生成订单
	 * 
	 * @Title: genOrder
	 * @Description: TODO
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	/*
	 * @RequestMapping(value="/genOrder",method=RequestMethod.POST)
	 * 
	 * @ResponseBody public String genOrder(HttpServletRequest request){ String
	 * user_id = request.getParameter("user_id"); String body =
	 * request.getParameter("body");//订单类型 //String total_fee =
	 * request.getParameter("total_fee");//订单金额 String spbill_create_ip =
	 * PayCommonUtil.getIpAddress(request); String trade_type =
	 * request.getParameter("trade_type");
	 * 
	 * String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
	 * if(StringUtils.isBlank
	 * (user_id)||StringUtils.isBlank(body)||StringUtils.isBlank
	 * (spbill_create_ip)||StringUtils.isBlank(trade_type)){ return returnStr; }
	 * int orderType = Integer.parseInt(body); long userId =
	 * Long.parseLong(user_id); //生成订单号并生成订单 String record_sn =
	 * PayCommonUtil.createOutTradeNo();
	 * 
	 * WalletRecord walletRecord = new WalletRecord();
	 * 
	 * walletRecord.setFrom_uid(userId); if(Constants.ORDER_TYPE_TRADE ==
	 * orderType ){ //交易充值 walletRecord.setTo_uid(0L);
	 * 
	 * }else if (Constants.ORDER_TYPE_REDPACKET == orderType){ //红包
	 * 
	 * }else if(Constants.ORDER_TYPE_TASK == orderType){ //任务
	 * 
	 * }else if(Constants.ORDER_TYPE_WITHDRAWLS == orderType){ //提现
	 * 
	 * }else if(Constants.ORDER_TYPE_BACK == orderType){ //退款
	 * 
	 * }else{ return JsonUtils.writeJson(0, 0, "参数错误"); }
	 * walletRecordService.addWalletRecordOrder(Long.parseLong(user_id),
	 * record_sn, orderType); logger.info("genOrder生成订单 ");
	 * 
	 * return JsonUtils.writeJson("订单生成成功",1); }
	 */
	/**
	 * 申请提现
	 * 
	 * @Title: withdrawMoney
	 * @Description: TODO
	 * @param: @param user_id
	 * @param: @param money
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping(value = "/open/withdraw",method=RequestMethod.POST)
	@ResponseBody
	public String withdrawMoney(@RequestParam String user_id,
			@RequestParam String money,@RequestParam String type) {
		String result = JsonUtils.writeJson(0, 0, "参数为空");
		if (StringUtils.isBlank(user_id) || StringUtils.isBlank(money)||StringUtils.isBlank(type)) {
			return result;
		}
		if(Constants.ORDER_TYPE_WITHDRAWLS != Integer.parseInt(type)){
			return result;
		}
		long userId = Long.parseLong(user_id);
		User user = userService.getUserById(userId);
		BigDecimal price = new BigDecimal(money);
		if (null == user) {
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		if (price.doubleValue() < 100) {
			return JsonUtils.writeJson(0, 23, "提现额度错误");
		}
		Wallet wallet = walletService.findWalletByUserId(userId);
		if (wallet.getMoney().compareTo(price) < 0) {
			return JsonUtils.writeJson(0, 21, "余额不足");
		}
		walletService.withdrawMoney(userId, price, wallet);
		return null;
	}

	/**
	 * 充值
	 * 
	 * @Title: rechargMoney
	 * @Description: TODO
	 * @param: @param user_id
	 * @param: @param money
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping(value = "/open/recharge",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String rechargeMoney(@RequestParam String userId,
			@RequestParam String payType, @RequestParam String money,
			@RequestParam String type, HttpServletRequest request) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(payType)
				|| StringUtils.isBlank(money) || StringUtils.isBlank(type)) {
			return JsonUtils.writeJson(0, 0, "参数错误");
		}
		String result = JsonUtils.writeJson(0, 0, "参数错误");
		int pay_type = Integer.parseInt(payType);
		int taskType = Integer.parseInt(type);
		long user_id = Long.parseLong(userId);
		double price = Double.parseDouble(money);
		User user = userService.getUserById(user_id);
		if (null == user) {
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}

		if (Constants.PAY_TYPE_WECHAT == pay_type) {
			 if (Constants.ORDER_TYPE_TRADE == taskType) {
				// 微信支付充值
				String record_sn = walletService.addRechargeOrderRecord(user_id,
						money, pay_type);
				if (null == record_sn) {
					return JsonUtils.writeJson(0, 19, "订单生成失败");
				}
				SortedMap<Object, Object> map = WxPayUtil.getPreperIdFromWX(
						record_sn, PayCommonUtil.getIpAddress(request),
						Constants.APP_NAME + Constants.RECHARGE, price);
				if (null == map) {
					return JsonUtils.writeJson(0, 19, "订单生成失败");
				}
				return JsonUtils.writeJson(1, "请求成功", map, "object");
			} else {
				return JsonUtils.writeJson(0, 0, "参数错误");
			}
		}
		return JsonUtils.writeJson(0, 0, "参数错误");
	}
	/**
	 * 获取余额
	 * @Title:           getBalance
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	@RequestMapping("/open/getBalance")
	@ResponseBody
	public String getBalance(@RequestParam String userId){
		if (StringUtils.isBlank(userId)) {
			return JsonUtils.writeJson(0, 0, "参数错误");
		}
		
		long user_id = Long.parseLong(userId);
		User user = userService.getUserById(user_id);
		if(null == user){
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		Wallet wallet = walletService.findWalletByUserId(user_id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("balance",wallet.getMoney().doubleValue());
		return JsonUtils.writeJson(1, "请求成功", result, "object");
	}
}
