package com.award.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ImUtils {
	private static String client_id = "YXA6kQpwAEg_EeiS__vkAC3bvw";
	private static String client_secret = "YXA6skgv7DwiIMDr-8uBzMEFrjSe_Ok";
	private static String org_name = "1165180409228696";
	private static String app_name = "apptest";
	private static String url = "https://a1.easemob.com/";
	
	private static String getToken() {	
		    String token = "";
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Properties properties =  new PropertiesUtil().getProperites("im.properties");
		    String expiresDate = properties.getProperty("expires_in", "");
		    String access_token = properties.getProperty("access_token", "");
		    try {
		    if(!StringUtils.isBlank(expiresDate) && sdf.parse(expiresDate).getTime() > new Date().getTime()) {
		    	token = access_token;
		    }else {
		        JSONObject jsonObj = new JSONObject(); 
		        jsonObj.put("grant_type", "client_credentials");
		        jsonObj.put("client_id",client_id);
		        jsonObj.put("client_secret",client_secret);

				String result = post("token",jsonObj,true,true);
				if(!StringUtils.isBlank(result)) {
				    	JSONObject jo = JSON.parseObject(result);
				    	token = jo.getString("access_token");
			        	String expires_in = jo.getString("expires_in");
			        	String application = jo.getString("application");
			        	Calendar calendar = Calendar.getInstance();
			        	calendar.add(Calendar.SECOND, Integer.parseInt(expires_in));   //2小时之后的时间
			        	String expires_date = sdf.format(calendar.getTime());
			        	PropertiesUtil.setImValue("im.properties", expires_date, token, application);
				 }
		  
	}
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return token;
	}
	
	public static boolean authRegister(String name,String password,String nickName) {
		
		boolean isSuccess = true;
		JSONObject jsonObj = new JSONObject(); 
        jsonObj.put("username", name);
        jsonObj.put("password",password);
        System.out.println("发送的数据"+jsonObj.toJSONString());
		String result = post("users",jsonObj,false,true);
		if(!StringUtils.isBlank(result)) {
		    	JSONObject jo = JSON.parseObject(result);
		    	JSONArray groupJson = jo.getJSONArray("entities");
		        for(int i = 0; i < groupJson.size(); i++) {
		        	JSONObject eJson = (JSONObject) groupJson.get(i);
		        	boolean active = eJson.getBoolean("activated");
		        	if(!active) {
		        		return active;
		        	}
		        }
		 }	
		return isSuccess;
	}
	
	public static boolean sendTextMessage(String targetType,String[] target,String msg) {
		boolean isSuccess = true;
		JSONObject jsonObj = new JSONObject(); 
	    jsonObj.put("target_type", targetType);
	    jsonObj.put("target",target);
	    JSONObject msgJson = new JSONObject();
	    msgJson.put("type", "txt");
	    msgJson.put("msg", msg);//msg格式为WtwdMissionTxt:好友X发布了一个任务，点击查看:任务id    WtwdRedPacketTxt:好友发送了一个红包：红包id
	    jsonObj.put("msg", msgJson);
		String result = post("messages",jsonObj,false,true);
		if(!StringUtils.isBlank(result)) {
		    	JSONObject jo = JSON.parseObject(result);
		    	String groupJson = jo.getString("data");
		    	JSONObject groupJo = JSON.parseObject(groupJson);
		    	for(int i = 0 ; i < target.length; i++) {
		    		String s = groupJo.getString(target[i]);
		    		if(!s.equals("success")) {
		    			isSuccess = false;
		    			return isSuccess;
		    		}
		    	}
		 }	
		return isSuccess;
	}
	
//	添加群主发送消息{
//	    "groupname":"testrestgrp12", //群组名称，此属性为必须的
//	    "desc":"server create group", //群组描述，此属性为必须的
//	    "public":true, //是否是公开群，此属性为必须的
//	    "maxusers":300, //群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
//	    "members_only":true // 加入群是否需要群主或者群管理员审批，默认是false
//	    "allowinvites": true  //是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
//	    "owner":"jma1", //群组的管理员，此属性为必须的
//	    "members":["jma2","jma3"] //群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
//	}
//	返回消息{
//		  "action" : "post",
//		  "application" : "4d7e4ba0-dc4a-11e3-90d5-e1ffbaacdaf5",
//		  "params" : { },
//		  "uri" : "https://a1.easemob.com/easemob-demo/chatdemoui",
//		  "entities" : [ ],
//		  "data" : {
//		    "groupid" : "1411527886490154"
//		  },
//		  "timestamp" : 1411527886457,
//		  "duration" : 125,
//		  "organization" : "easemob-demo",
//		  "applicationName" : "chatdemoui"
//		}
	
	public static String addGroup(String groupName,String[] members,String admin) {
		
		String groupId = "";		
		JSONObject jsonObj = new JSONObject(); 
	    jsonObj.put("groupname", groupName);//群组名称，此属性为必须的
	    jsonObj.put("desc",groupName);//群组描述，此属性为必须的
	    jsonObj.put("public", true);//是否是公开群，此属性为必须的
	    jsonObj.put("maxusers", 300);//群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
	    jsonObj.put("members_only", false);// 加入群是否需要群主或者群管理员审批，默认是false
	    jsonObj.put("allowinvites", true);//是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
	    jsonObj.put("owner", admin);
	    jsonObj.put("members", members);
	    String result = post("chatgroups",jsonObj,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	groupId = groupJo.getString("groupid");
	    }
	    return groupId;
	}
	
	public static boolean updateGroup(String groupName,String groupId) {
		boolean isSuccess = false;
		JSONObject jsonObj = new JSONObject(); 
	    jsonObj.put("groupname", groupName);//群组名称，此属性为必须的
	    jsonObj.put("desc",groupName);//群组描述，此属性为必须的
	    jsonObj.put("maxusers", 300);//群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的	    
	    String result = httpPut("chatgroups/"+groupId,jsonObj,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	isSuccess = groupJo.getBoolean("groupname");
	    }
	    return isSuccess;		
	}
	
	public static boolean deleteGroup(String groupId) {
		boolean isSuccess = false;   
	    String result = httpDelete("chatgroups/"+groupId,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	isSuccess = groupJo.getBoolean("success");
	    }
	    return isSuccess;		
	}
	
	public static boolean addSingleMember(String groupId,String userName) {
		boolean isSuccess = false;   
	    String result = post("chatgroups/"+groupId+"/users/"+userName,null,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	isSuccess = groupJo.getBoolean("result");
	    }
	    return isSuccess;	
	}
	
	public static boolean addMembers(String groupId,String[] userNames) {
		boolean isSuccess = false;   
		JSONObject jsonObj = new JSONObject(); 
	    jsonObj.put("usernames", userNames);
	    String result = post("chatgroups/"+groupId+"/users",jsonObj,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	String members = groupJo.getString("newmembers");
	    	if(!StringUtils.isBlank(members)) {
	    		isSuccess = true;
	    	}
	    }
	    return isSuccess;	
	}
	
	public static boolean delSingleMember(String groupId,String userName) {
		boolean isSuccess = false;   
	    String result = httpDelete("chatgroups/"+groupId+"/users/"+userName,false,false);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	isSuccess = groupJo.getBoolean("result");
	    }
	    return isSuccess;	
	}
	
	public static boolean transferAdmin(String groupId,String userName) {
		boolean isSuccess = false;   
		JSONObject jsonObj = new JSONObject(); 
	    jsonObj.put("newowner", "${"+userName+"}");//群组名称，此属性为必须的
	    String result = httpPut("chatgroups/"+groupId,jsonObj,false,true);
	    if(!StringUtils.isBlank(result)) {
	    	JSONObject jo = JSON.parseObject(result);
	    	String groupJson = jo.getString("data");
	    	JSONObject groupJo = JSON.parseObject(groupJson);
	    	isSuccess = groupJo.getBoolean("newowner");
	    }
	    return isSuccess;	
	}
	
	private static String post(String apiName,JSONObject jsonObj,boolean isGetToken,boolean isJson) {
	    HttpPost post = null;
	    String strResult = "";
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost(url+org_name+"/"+app_name+"/"+apiName);
	        // 构造消息头
	        if(isJson) {
	        	post.setHeader("Content-type", "application/json; charset=utf-8");
	        }
	        if(!isGetToken) {
	        	post.setHeader("Authorization","Bearer "+ getToken());
	        }
	        // 构建消息实体
	        if(jsonObj != null) {
	        	StringEntity entity = new StringEntity(jsonObj.toString());
	            entity.setContentEncoding("UTF-8");
	            // 发送Json格式的数据请求
	            entity.setContentType("application/json");
	            post.setEntity(entity);
	        }
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	strResult = EntityUtils.toString(response.getEntity());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return strResult;
	}
	
	
	public static String httpPut(String apiName,JSONObject jsonObj,boolean isGetToken,boolean isJson){    

	    HttpPut put = null;
	    String strResult = "";
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        put = new HttpPut(url+org_name+"/"+app_name+"/"+apiName);
	        // 构造消息头
	        if(isJson) {
	        	put.setHeader("Content-type", "application/json; charset=utf-8");
	        }
	        if(!isGetToken) {
	        	put.setHeader("Authorization","Bearer "+ getToken());
	        }
	        // 构建消息实体
	        if(jsonObj != null) {
	        	StringEntity entity = new StringEntity(jsonObj.toString());
	            entity.setContentEncoding("UTF-8");
	            // 发送Json格式的数据请求
	            entity.setContentType("application/json");
	            put.setEntity(entity);
	        }
	        HttpResponse response = httpClient.execute(put);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	strResult = EntityUtils.toString(response.getEntity());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(put != null){
	            try {
	            	put.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return strResult;
	
    } 
	
	public static String httpDelete(String apiName,boolean isGetToken,boolean isJson){    

	    HttpDelete delete = null;
	    String strResult = "";
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        delete = new HttpDelete(url+org_name+"/"+app_name+"/"+apiName);
	        // 构造消息头
	        if(isJson) {
	        	delete.setHeader("Content-type", "application/json; charset=utf-8");
	        }
	        if(!isGetToken) {
	        	delete.setHeader("Authorization","Bearer "+ getToken());
	        }	            
	        HttpResponse response = httpClient.execute(delete);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	strResult = EntityUtils.toString(response.getEntity());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(delete != null){
	            try {
	            	delete.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return strResult;
	
    }
	

}
