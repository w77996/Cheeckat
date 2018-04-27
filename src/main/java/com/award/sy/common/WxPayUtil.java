package com.award.sy.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;


public class WxPayUtil {

	
	public static SortedMap<Object, Object> getPreperIdFromWX(String record_sn,String body,String ip,double price){
		// String price = goodsTrade.getPrice();
		//	String price = request.getParameter("price");
			int price100 = new BigDecimal(price).multiply(new BigDecimal(100))
					.intValue();
			if (price100 <= 0) {
				//resultMap.put("msg", "付款金额错误");
				//resultMap.put("code", "500");
				return  null;
			}
			// 设置回调地址-获取当前的地址拼接回调地址
			//String url = request.getRequestURL().toString();
			String domain = ip.substring(0, ip.length() - 13);
			// 生产环境
			String notify_url = domain + "wxNotify";
			// 测试环境
			// String notify_url =
			// "http://1f504p5895.51mypc.cn/cia/app/wxNotify.html";

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", Constants.APPID);
			parameters.put("mch_id", Constants.MCH_ID);
			parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
			parameters.put("body", body);
			parameters.put("out_trade_no", record_sn); // 订单id
			parameters.put("fee_type", "CNY");
			parameters.put("total_fee", String.valueOf(price100));
			parameters.put("spbill_create_ip", ip);
			parameters.put("notify_url", notify_url);
			parameters.put("trade_type", "APP");
			// 设置签名
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);
			// 封装请求参数结束
			String requestXML = PayCommonUtil.getRequestXml(parameters);
			// 调用统一下单接口
			String result = PayCommonUtil.httpsRequest(Constants.UNIFIED_ORDER_URL,
					"POST", requestXML);
			System.out.println("\n" + result);
			try {
				/**
				 * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，
				 * partnerId
				 * ，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay
				 **/
				Map<String, String> map = XMLUtil.doXMLParse(result);
				SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
				parameterMap2.put("appid", Constants.APPID);
				parameterMap2.put("partnerid", Constants.MCH_ID);
				parameterMap2.put("prepayid", map.get("prepay_id"));
				parameterMap2.put("package", "Sign=WXPay");
				parameterMap2.put("noncestr", PayCommonUtil.CreateNoncestr());
				parameterMap2.put(
						"timestamp",
						Long.parseLong(String.valueOf(System.currentTimeMillis())
								.toString()));
				String sign2 = PayCommonUtil.createSign("UTF-8", parameterMap2);
				parameterMap2.put("sign", sign2);
				/*resultMap.put("code", "200");
				resultMap.put("msg", parameterMap2);*/
				return parameterMap2;
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
}
