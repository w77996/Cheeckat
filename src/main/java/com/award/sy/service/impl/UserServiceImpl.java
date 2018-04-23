package com.award.sy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.dao.UserDao;
import com.award.sy.entity.User;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.service.UserService;
import com.award.sy.web.view.DatatablesView;

/**
 * @描述：用户信息service
 * @作者：bin
 * @版本：V1.0
 * @创建时间：：2018-04-10 下午3:42:45
 */
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;
	
	public DatatablesView<User> getUserByCondition(QueryCondition query) {
		DatatablesView<User> dataView = new DatatablesView<User>();
		
		//构建查询条件
		WherePrams where = userDao.structureConditon(query);
		
		Long count = userDao.count(where);
		List<User> list = userDao.list(where);
		
		dataView.setRecordsTotal(count.intValue());
		dataView.setData(list);
		
		return dataView;
	}
	
	public List<User> getAllUser(){
		return userDao.list();
	}
	
	public User getUserById(long userId){
		return userDao.get(userId);
	}
	
	public int addUser(User user){
		return userDao.addLocal(user);
	}
	
	public int editUser(User user){
		WherePrams where = new WherePrams();
		where.and("user_id", C.EQ, user.getUser_id());
		return userDao.updateLocal(user,where);
	}
	
	public int removeUser(long userId){
		return userDao.del(userId);
	}

}
