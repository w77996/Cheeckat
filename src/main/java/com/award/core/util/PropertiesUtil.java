package com.award.core.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * 配置文件操作工具类
 * @author 
 * @time 
 * @email 
 */
public class PropertiesUtil {

	
	/**
	 * 获取Properties配置文件
	 * @param path
	 * @return
	 */
	public static Properties getProp(String path){
		 Properties properties = new Properties();
		 InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream(path);
		 try {  
			 properties.load(inputStream);
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }
		 return properties;
	}
	
	/**
	 * 获取Properties配置文件
	 * @param path
	 * @return
	 */
	public Properties getProperites(String path){
		 Properties properties = new Properties();
		 InputStream stream = this.getClass().getClassLoader().getResourceAsStream("/" + path);
		 try {  
			 properties.load(stream);
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }
		 return properties;
	}
	
	public static void setImValue(String filepath, String expires_in, String access_token, String application){
        ///保存属性到b.properties文件
		Properties properties = new Properties();
        FileOutputStream oFile ;        
        try {
        	String url = PropertiesUtil.class.getClassLoader().getResource(filepath).getPath();
            System.out.println("s"+url);
            oFile = new FileOutputStream(url);
            //true表示追加打开
            properties.setProperty("expires_in", expires_in);
            properties.setProperty("access_token", access_token);
            properties.setProperty("application", application);
            properties.store(oFile, "");
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
	/**
	 * 打开某个文件的流
	 * @param path
	 * @return
	 */
	public InputStream getStrean(String path){
		return this.getClass().getClassLoader().getResourceAsStream("/" + path);
	}
}
