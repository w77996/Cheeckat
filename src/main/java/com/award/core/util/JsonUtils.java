package com.award.core.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils{

	/**
	 * 写入对象
	 * @param status
	 * @param msg
	 * @param object
	 * @param objectName
	 */
	public static String writeJson(int status, String msg, Object object, String objectName) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(objectName, object);
		jsonMap.put("status", status);
		jsonMap.put("msg", msg);
		return JSONObject.toJSONString(jsonMap);
	}


	/**
	 * 返回JSON
	 */
	public static String writeJson(String msg,int status) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", status);
		jsonMap.put("msg", msg);
		return JSONObject.toJSONString(jsonMap);
	}
	
	/**
	 * 返回JSON
	 * 
	 * @param errCode
	 *            : 0 参数为空 1 系统繁忙 2 该用户已经存在 3 密码错误 4该用户不存在 5 两次密码输入不一样 6 验证码失效
	 *            7验证码输入错误  8 短信发送失败  9邮件发送失败 10数据有误  11获取token失败  12无该城市信息  13没有查询到天气信息
	 * @param status
	 *            0请求失败 1 请求成功
	 * @param msg
	 *            请求说明
	 */
	public static String writeJson(int status, int errCode, String msg) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("status", status);
			jsonMap.put("errCode", errCode);
			jsonMap.put("msg", msg);
			return JSONObject.toJSONString(jsonMap);
	}
	
	
	public static String writeJson(int status, String msg, Map<String, Object> jsonMap)
	{
		Map<String, Object> map = jsonMap;
		if(jsonMap == null)
		{
			map = new HashMap<String, Object>();
		}
		map.put("status", status);
		map.put("msg", msg);
		return JSONObject.toJSONString(jsonMap);
	}

}
