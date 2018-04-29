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
		int i = userIndexImgDao.excuse("delete from tb_user_index_img where user_id ="+user_id+" and img="+img_path);
		return  i;
	}

	/**
	 * 获取主页图片
	 * @param userId
	 * @param start
	 * @param count
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getUserIndexImgList(long userId,String start,String count) {
		/*WherePrams where = new WherePrams();
		where.and("user_id", C.EQ,userId);
		List<UserIndexImg> list = userIndexImgDao.list(where);*/
		List<Map<String,Object>> list = userIndexImgDao.listBySql("select * from tb_user_index_img where user_id ="+userId+" order by create_time desc limit "+start+","+count);
		return list;
	}
	
	

}
