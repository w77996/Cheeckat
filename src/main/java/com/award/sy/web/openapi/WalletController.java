package com.award.sy.web.openapi;


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
import com.award.sy.entity.WalletLog;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.WalletLogService;
import com.award.sy.service.WalletRecordService;
import com.award.sy.service.WalletService;
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
	/**
	 * 生成订单
	 * @Title:           genOrder
	 * @Description:     TODO
	 * @param:           @param request
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	@RequestMapping(value="/genOrder",method=RequestMethod.POST)
	@ResponseBody
	public String genOrder(HttpServletRequest request){
		String user_id = request.getParameter("user_id");
		String body = request.getParameter("body");//订单类型
		//String total_fee = request.getParameter("total_fee");//订单金额
		String spbill_create_ip = PayCommonUtil.getIpAddress(request);
		String trade_type = request.getParameter("trade_type");
		
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(user_id)||StringUtils.isBlank(body)||StringUtils.isBlank(spbill_create_ip)||StringUtils.isBlank(trade_type)){
			return returnStr;
		}
		int orderType = Integer.parseInt(body);
		long userId = Long.parseLong(user_id);
		//生成订单号并生成订单
		String record_sn = PayCommonUtil.createOutTradeNo();
		
		WalletRecord walletRecord = new WalletRecord();
		
		walletRecord.setFrom_uid(userId);
		if(Constants.ORDER_TYPE_TRADE == orderType ){
			//交易充值
			walletRecord.setTo_uid(0L);
			
		}else if (Constants.ORDER_TYPE_REDPACKET == orderType){
			//红包
			
		}else if(Constants.ORDER_TYPE_TASK == orderType){
			//任务
			
		}else if(Constants.ORDER_TYPE_WITHDRAWLS == orderType){
			//提现
			
		}else if(Constants.ORDER_TYPE_BACK == orderType){
			//退款
			
		}else{
			 return JsonUtils.writeJson(0, 0, "参数错误");
		}
		 walletRecordService.addWalletRecordOrder(Long.parseLong(user_id), record_sn, orderType);
		logger.info("genOrder生成订单 ");
		
		return JsonUtils.writeJson("订单生成成功",1);
	}

}
