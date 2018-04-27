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

import com.award.core.util.FileUtil;
import com.award.core.util.JsonUtils;
import com.award.sy.common.Constants;
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
	public String editUser(@RequestParam String userId,@RequestParam String headImg,@RequestParam String userName,@RequestParam String birth,@RequestParam String height,@RequestParam int sex,HttpServletRequest request){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(headImg)||StringUtils.isBlank(userName)||StringUtils.isBlank(birth)||StringUtils.isBlank(height)||StringUtils.isBlank(sex+"")){
			return returnStr;
		}
		long user_id = Long.parseLong(userId);
		User userExist = userService.getUserById(user_id);
		if(null == userExist){
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		String path = Constants.HEAD_IMG_PATH;
		//获取绝对路径
		String uploadPath = request.getSession().getServletContext().getRealPath(path);
		File file = new File(uploadPath);
		if(!file.exists()){
			 file.mkdirs();
		}
		 
        String fileName = System.currentTimeMillis() + String.valueOf((int)((Math.random()*9+1)*100000)) + ".jpg";
		boolean isSuccess = FileUtil.CreateImgBase64(headImg, path+fileName);
		if(!isSuccess){
			return JsonUtils.writeJson(0, 24, "图片上传失败");
		}
		User user = new User();
		user.setBirth(birth);
		user.setHead_img(uploadPath+fileName);
		user.setHeight(Integer.parseInt(height));
		user.setSex(sex);
		user.setUser_id(user_id);
		int i = userService.editUser(user);
		if (1 < i){
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
	public String uploadImg(@RequestParam String userId,@RequestParam String headImg,HttpServletRequest request){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(headImg)){
			return returnStr;
		}
		long user_id = Long.parseLong(userId);
		User userExist = userService.getUserById(user_id);
		if(null == userExist){
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		String path = Constants.HEAD_IMG_PATH;
		//获取绝对路径
		String uploadPath = request.getSession().getServletContext().getRealPath(path);
		File file = new File(uploadPath);
		if(!file.exists()){
			 file.mkdirs();
		}
		 
        String fileName = System.currentTimeMillis() + String.valueOf((int)((Math.random()*9+1)*100000)) + ".jpg";
		boolean isSuccess = FileUtil.CreateImgBase64(headImg, path+fileName);
		if(!isSuccess){
			return JsonUtils.writeJson(0, 24, "图片上传失败");
		}
		User user = new User();
		
		user.setHead_img(uploadPath+fileName);
		//user.setHeight(Integer.parseInt(height));
		//user.setSex(sex);
		user.setUser_id(user_id);
		int i = userService.editUser(user);
		if (1 < i){
			return JsonUtils.writeJson("修改成功",1);
		}else {
			return JsonUtils.writeJson(0, 0, "修改失败");
		}
	}

}
