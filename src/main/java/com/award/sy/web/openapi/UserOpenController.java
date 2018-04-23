package com.award.sy.web.openapi;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.award.core.util.JsonUtils;
import com.award.sy.entity.User;
import com.award.sy.service.UserService;

@Controller
public class UserOpenController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	/**
	 * 设置user信息
	 * <p>Title: editUser</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param headImg
	 * @param userName
	 * @param birth
	 * @param height
	 * @param sex
	 * @return
	 */
	@RequestMapping("/open/editUser")
	@ResponseBody
	public String editUser(@RequestParam long userId,@RequestParam String headImg,@RequestParam String userName,@RequestParam String birth,@RequestParam String height,@RequestParam int sex){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")||StringUtils.isBlank(headImg)||StringUtils.isBlank(userName)||StringUtils.isBlank(birth)||StringUtils.isBlank(height)||StringUtils.isBlank(sex+"")){
			return returnStr;
		}
		User user = new User();
		user.setBirth(birth);
		user.setHead_img(headImg);
		user.setHeight(Integer.parseInt(height));
		user.setSex(sex);
		user.setUser_id(userId);
		int i = userService.editUser(user);
		if (1 == i){
			return JsonUtils.writeJson("修改成功",1);
		}else {
			return JsonUtils.writeJson(0, 0, "修改失败");
		}
	}
	/**
	 * 用户上传头像，返回头像保存地址
	 * <p>Title: uploadUserHeadImg</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("/open/uploadImg")
	@ResponseBody
	public String uploadImg(@RequestParam long userId,@RequestParam(value="file",required = true) MultipartFile file,HttpServletRequest request,@RequestParam int type){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId+"")||StringUtils.isBlank(type+"")){
			return returnStr;
		}
		String path = "";
		//上传头像
		if(1 == type){
			//上传文件目录定义
	        path = "/upload/head/image/";
		}else if(2 == type){
			//上传文件目录定义
	         path = "/upload/user/image/";
		}else{
			return returnStr;
		}
		
        if (file.getSize() > 0) {
        	//获取绝对路径
            String uploadPath = request.getSession().getServletContext().getRealPath(path);
            //修改文件名称
            String fileName = file.getOriginalFilename();
            if(fileName.indexOf(".")>=0){  
                int indexdot = fileName.indexOf(".");  
                String suffix = fileName.substring(indexdot);  
                fileName = System.currentTimeMillis() + String.valueOf((int)((Math.random()*9+1)*100000)) + suffix;
            }  
            try {
            	//创建目标文件
            	File targetFile = new File(uploadPath, fileName);
            	if (!targetFile.exists()) {
        			targetFile.mkdirs();
        		}
            	
            	file.transferTo(targetFile);
                //FileUtils.copyInputStreamToFile(file.getInputStream(), f);
            	
            	if(1 == type){
            		//更新用户头像
            		
            	}else if(2 == type){
            		//插入用户主页图片
            		
            	}
            	
            	return JsonUtils.writeJson(1, "获取成功", path+fileName, "object");
               
            } catch (Exception e) {
            	return  JsonUtils.writeJson(0, 0, "未知错误");
            }
        }else{
        	return returnStr;
        }
	}

}
