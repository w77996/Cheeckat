package com.award.sy.service;

import java.util.List;

import com.award.sy.entity.User;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.web.view.DatatablesView;

/**
 * @描述：用户信息service接口
 * @作者：bin
 * @版本：V1.0
 * @创建时间：：2018-04-10
 */
public interface UserService{
	
	/**
	 * 功能描述：获取所有用户信息
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 功能描述：根据条件获取用户信息
	 * @return
	 */
	public DatatablesView<User> getUserByCondition(QueryCondition query);
	
	/**
	 * 功能描述：根据ID获取用户信息
	 * @param AreaId
	 * @return
	 */
	public User getUserById(long userId);
	
	/**
	 * 功能描述：添加用户信息
	 * @param User
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 功能描述：修改用户信息
	 * @param User
	 * @return
	 */
	public int editUser(User user);
	
	/**
	 * @功能描述：删除用户信息
	 * @param userId
	 * @return int
	 */
	public int removeUser(long userId);
	
}
