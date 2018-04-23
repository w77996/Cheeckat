package com.award.core.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ImUtils {
	private static String client_id = "";
	private static String client_secret = "";
	private static String org_name = "";
	private static String app_name = "";
	private static String url = "https://a1.easemob.com/";
	
	private static String getToken() {	
		    String token = "";
		    HttpPost post = null;
		    try {
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
