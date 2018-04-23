package com.award.sy.service;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.Friend;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.web.view.DatatablesView;
/**
 * 好友service接口
* <p>Title: FriendService</p>  
* <p>Description: </p>  
* @author w77996  
* @date 2018年4月20日
 */
public interface FriendService {
	/**
	 * 获取好友列表
	 * <p>Title: getUserFriends</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	/**
	 * 功能描述：获取所有位置信息
	 * @return
	 */
	public List<Friend> getAllFriend();
	
	public List<Map<String,Object>> getFriendByTwoId(Long userId,Long userId2);
	
	/**
	 * 功能描述：根据条件获取位置信息
	 * @return
	 */
	public DatatablesView<Friend> getFriendByCondition(QueryCondition query);
	
	/**
	 * 功能描述：根据ID获取位置信息
	 * @param AreaId
	 * @return
	 */
	public Friend getFriendById(long friendId);
	
	public List<Map<String,Object>> getFriendByLatLng(Map<String,double[]> map,long userId);
	
	public List<Map<String,Object>> getLimitFriendByLatLng(double lat,double lng,int start,int count,long userId);
	
	/**
	 * 功能描述：添加位置信息
	 * @param Friend
	 * @return
	 */
	public int addFriend(Friend friend);
	
	/**
	 * 功能描述：修改位置信息
	 * @param Friend
	 * @return
	 */
	public int editFriend(Friend friend);
	
	/**
	 * @功能描述：删除位置信息
	 * @param friendId
	 * @return int
	 */
	public int removeFriend(long friendId);
	List<Map<String,Object>> getUserFriends(long userId);

}
