package com.award.sy.service;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.Group;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.web.view.DatatablesView;
/**
 * 群组service接口
* <p>Title: GroupService</p>  
* <p>Description: </p>  
* @author w77996  
* @date 2018年4月20日
 */
public interface GroupService {
	/**
	 * 获取群组下的user头像及username
	 */
	/**
	 * 功能描述：获取所有位置信息
	 * @return
	 */
	public List<Group> getAllGroup();
	
	/**
	 * 功能描述：根据条件获取位置信息
	 * @return
	 */
	public DatatablesView<Group> getGroupByCondition(QueryCondition query);
	
	/**
	 * 功能描述：根据ID获取位置信息
	 * @param AreaId
	 * @return
	 */
	public Group getGroupById(long groupId);
	
	public Group getGroupByName(String groupName);
	
	public List<Map<String,Object>> getGroupByLatLng(Map<String,double[]> map,long userId);
	
	public List<Map<String,Object>> getLimitGroupByLatLng(double lat,double lng,int start,int count,long userId);
	
	/**
	 * 功能描述：添加位置信息
	 * @param Group
	 * @return
	 */
	public int addGroup(Group group);
	
	/**
	 * 功能描述：修改位置信息
	 * @param Group
	 * @return
	 */
	public int editGroup(Group group);
	
	/**
	 * @功能描述：删除位置信息
	 * @param groupId
	 * @return int
	 */
	public int removeGroup(long groupId);
	List<Map<String,Object>> getUserGroup(long groupId);
	
	int deleteGroup(long groupId);
}
