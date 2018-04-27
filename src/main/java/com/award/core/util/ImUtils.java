package com.award.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ImUtils {
	private static String client_id = "YXA6kQpwAEg_EeiS__vkAC3bvw";
	private static String client_secret = "YXA6skgv7DwiIMDr-8uBzMEFrjSe_Ok";
	private static String org_name = "1165180409228696";
	private static String app_name = "apptest";
	private static String url = "https://a1.easemob.com/";
	
	private static String getToken() {	
		    String token = "";
		    HttpPost post = null;
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Properties properties =  new PropertiesUtil().getProperites("im.properties");
		    String expiresDate = properties.getProperty("expires_in", "");
		    String access_token = properties.getProperty("access_token", "");
		    try {
		    if(!StringUtils.isBlank(expiresDate) && sdf.parse(expiresDate).getTime() > new Date().getTime()) {
		    	token = access_token;
		    }else {
		   
		        HttpClient httpClient = new DefaultHttpClient();

		        // 设置超时时间
		        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
		        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
		            
		        post = new HttpPost(url+org_name+"/"+app_name+"/token");
		        // 构造消息头
		        post.setHeader("Content-type", "application/json; charset=utf-8");
		        JSONObject jsonObj = new JSONObject(); 
		        jsonObj.put("grant_type", "client_credentials");
		        jsonObj.put("client_id",client_id);
		        jsonObj.put("client_secret",client_secret);
		        // 构建消息实体
		        StringEntity entity = new StringEntity(jsonObj.toString());
		        entity.setContentEncoding("UTF-8");
		        // 发送Json格式的数据请求
		        entity.setContentType("application/json");
		        post.setEntity(entity);
		            
		        HttpResponse response = httpClient.execute(post);
		            
		        // 检验返回码
		        int statusCode = response.getStatusLine().getStatusCode();
		        if(statusCode == HttpStatus.SC_OK){
		        	String strResult = EntityUtils.toString(response.getEntity());
		        	JSONObject jo = JSON.parseObject(strResult);
		        	token = jo.getString("access_token");
		        	String expires_in = jo.getString("expires_in");
		        	String application = jo.getString("application");
		        	Calendar calendar = Calendar.getInstance();
		        	calendar.add(Calendar.SECOND, Integer.parseInt(expires_in));   //2小时之后的时间
		        	String expires_date = sdf.format(calendar.getTime());
		        	PropertiesUtil.setImValue("im.properties", expires_date, token, application);
		        }else{		        	

		        }
		  
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
		    return token;
	}
	
	public static void authRegister(String name,String password,String nickName) {
	    HttpPost post = null;
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost(url+org_name+"/"+app_name+"/users");
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("Authorization","Bearer "+ getToken());
	        JSONObject jsonObj = new JSONObject(); 
	        jsonObj.put("username", name);
	        jsonObj.put("password",password);
	        System.out.println("发送的数据"+jsonObj.toJSONString());
	        // 构建消息实体
	        StringEntity entity = new StringEntity(jsonObj.toString());
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	String strResult = EntityUtils.toString(response.getEntity());
	        	JSONObject jo = JSON.parseObject(strResult);
	        	System.out.println(jo.toJSONString());
	        }else{		        	
	        	System.out.println("失败");
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
	}
	
	public static void sendTextMessage(String targetType,String[] target,String msg) {

	    HttpPost post = null;
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost(url+org_name+"/"+app_name+"/messages");
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("Authorization","Bearer "+ getToken());
	        JSONObject jsonObj = new JSONObject(); 
	        jsonObj.put("target_type", targetType);
	        jsonObj.put("target",target);
	        JSONObject msgJson = new JSONObject();
	        msgJson.put("type", "txt");
	        msgJson.put("msg", msg);//msg格式为WtwdMissionTxt:好友X发布了一个任务，点击查看:任务id
	        jsonObj.put("msg", msgJson);
	        // 构建消息实体
	        StringEntity entity = new StringEntity(jsonObj.toString());
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	String strResult = EntityUtils.toString(response.getEntity());
	        	JSONObject jo = JSON.parseObject(strResult);
	        }else{		        	

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
	
	}
	
	

}
