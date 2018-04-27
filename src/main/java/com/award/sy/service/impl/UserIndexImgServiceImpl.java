package com.award.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.common.DateUtil;
import com.award.sy.dao.UserIndexImgDao;
import com.award.sy.entity.UserIndexImg;
import com.award.sy.service.UserIndexImgService;
@Service("userIndexImg")
public class UserIndexImgServiceImpl implements UserIndexImgService{

	@Autowired
	private UserIndexImgDao userIndexImgDao;
	
	@Override
	public int addUserIndexImg(long user_id, String img_path) {
		// TODO Auto-generated method stub
		UserIndexImg userIndexImg = new UserIndexImg();
		userIndexImg.setCreate_time(DateUtil.getNowTime());
		userIndexImg.setUser_id(user_id);
		userIndexImg.setImg(img_path);
		return userIndexImgDao.addLocal(userIndexImg);
	}

	@Override
	public int delUserIndexImg(long user_id, String img_path) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 获取主页list
	 * Title: getUserIndexImgList
	 * Description: 
	 * @param parseLong
	 * @return
	 * @see com.award.sy.service.UserIndexImgService#getUserIndexImgList(long)
	 */
	@Override
	public List<UserIndexImg> getUserIndexImgList(long userId) {
		WherePrams where = new WherePrams();
		where.and("user_id", C.EQ,userId);
		List<UserIndexImg> list = userIndexImgDao.list(where);
		return list;
	}
	
	

}
