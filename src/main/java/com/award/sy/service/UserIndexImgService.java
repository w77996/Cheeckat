package com.award.sy.service;

import java.util.List;

import com.award.sy.entity.UserIndexImg;

public interface UserIndexImgService {

	int addUserIndexImg(long user_id,String img_path);
	
	int delUserIndexImg(long user_id,String img_path);
	/**
	 * 获取用户主页图片
	 * @Title:           getUserIndexImgList
	 * @Description:     TODO
	 * @param:           @param parseLong
	 * @param:           @return   
	 * @return:          List<UserIndexImg>   
	 * @throws
	 */
	List<UserIndexImg> getUserIndexImgList(long parseLong);
}
