package com.award.sy.web.openapi;

import java.io.File;
import java.util.List;

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
import com.award.core.util.ImUtils;
import com.award.core.util.JsonUtils;
import com.award.sy.common.Constants;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.entity.User;
import com.award.sy.entity.UserIndexImg;
import com.award.sy.service.FriendService;
import com.award.sy.service.UserIndexImgService;
import com.award.sy.service.UserService;

@Controller
public class UserOpenController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserIndexImgService userIndexImgService;
	
	@Autowired
	private FriendService friendService;
	
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
		User user = userService.getUserById(user_id);
		if(null == user){
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
		//User user = new User();
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
	 * 设置user信息
	 * <p>Title: registUser</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param headImg
	 * @param userName
	 * @param birth
	 * @param height
	 * @param sex
	 * @return
	 */
	@RequestMapping(value="/open/registUser")
	@ResponseBody
	public String registUser(@RequestParam String headImg,@RequestParam String userName,@RequestParam String birth,@RequestParam String height,@RequestParam String sex,@RequestParam String type,@RequestParam(required=false) String phone,@RequestParam(required=false) String open_id,HttpServletRequest request){
		String returnStr = JsonUtils.writeJson(0, 0, "参数为空");
		if(StringUtils.isBlank(headImg)||StringUtils.isBlank(userName)||StringUtils.isBlank(birth)||StringUtils.isBlank(height)||StringUtils.isBlank(sex+"")){
			return returnStr;
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
		int login_type = Integer.parseInt(type);
		boolean addImSuccess = false;
		if(Constants.LOGIN_TYPE_PHONE == login_type){
			addImSuccess = ImUtils.authRegister(phone, "123456", phone);
		}else if(Constants.LOGIN_TYPE_WECHAT == login_type){
			addImSuccess = ImUtils.authRegister(open_id, "123456", open_id);
		}else {
			return JsonUtils.writeJson(0, 0, "参数为空");
		}
		
		User user = new User();
		user.setBirth(birth);
		user.setHead_img(uploadPath+fileName);
		user.setHeight(Integer.parseInt(height));
		user.setSex(Integer.parseInt(sex));
		//user.setUser_id(user_id);
		
		
		if(Constants.LOGIN_TYPE_PHONE == login_type){
			user.setUser_name(phone);
			userService.addNewUser(user);
		}else if(Constants.LOGIN_TYPE_WECHAT == login_type){
			user.setOpen_id(open_id);
			user.setUser_name(open_id);
			userService.addNewUser(user);
		}else{
			return JsonUtils.writeJson(0, 0, "参数为空");
		}
		
		User userResult = userService.getUserByUserName(open_id);
		return  JsonUtils.writeJson(1, "请求成功", userResult, "object");
		
	}
	/**
	 * 用户上传主页头像
	 * <p>Title: uploadUserHeadImg</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/open/uploadImg",produces = "text/html;charset=UTF-8")
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
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		log.info("uploadPath路径："+uploadPath);
		File file = new File(uploadPath+Constants.HEAD_IMG_PATH);
		if(!file.exists()){
			 file.mkdirs();
		}
		 
        String fileName = System.currentTimeMillis() + String.valueOf((int)((Math.random()*9+1)*100000)) + ".jpg";
        log.info("fileName路径："+fileName);
		boolean isSuccess = FileUtil.CreateImgBase64(headImg, uploadPath+Constants.HEAD_IMG_PATH+fileName);
		if(!isSuccess){
			return JsonUtils.writeJson(0, 24, "图片上传失败");
		}
		int i = userIndexImgService.addUserIndexImg(user_id, path+fileName);
		//user.setHeight(Integer.parseInt(height));
		//user.setSex(sex);
		if (1 < i){
			return JsonUtils.writeJson("修改成功",1);
		}else {
			return JsonUtils.writeJson(0, 0, "修改失败");
		}
	}
	
	/**
	 * 获取用户主页信息
	 * @Title:           getUserIndex
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	@RequestMapping(value="/getUserIndex",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserIndex(@RequestParam String userId){
		if(StringUtils.isBlank(userId)){
			return JsonUtils.writeJson(0, 0, "参数为空");
		}
		long user_id = Long.parseLong(userId);
		User user = userService.getUserById(user_id);
		if(null == user){
			return JsonUtils.writeJson(0, 4, "用户不存在");
		}
		
		//查询好友关系
		//friendService.
		
		//查询用户信息
		
		return null;
		
	}
	
	/**
	 * 获取用户主页信息图片
	 * @Title:           getUserIndex
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	@RequestMapping(value="/getUserIndexImg",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserIndexImg(@RequestParam String userId){
		if(StringUtils.isBlank(userId)){
			return JsonUtils.writeJson(0, 0, "参数为空");
		}
		List<UserIndexImg> list = userIndexImgService.getUserIndexImgList(Long.parseLong(userId));
		
		return JsonUtils.writeJson(1, "请求成功", list, "object");
		
	}

}
