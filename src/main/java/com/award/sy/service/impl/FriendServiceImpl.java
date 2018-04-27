package com.award.sy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.dao.FriendDao;
import com.award.sy.entity.Friend;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.service.FriendService;
import com.award.sy.web.view.DatatablesView;

@Service("friendService")
public class FriendServiceImpl implements FriendService {

@Resource
	private FriendDao friendDao;
	
	public DatatablesView<Friend> getFriendByCondition(QueryCondition query) {
		DatatablesView<Friend> dataView = new DatatablesView<Friend>();
		
		//构建查询条件
		WherePrams where = friendDao.structureConditon(query);
		
		Long count = friendDao.count(where);
		List<Friend> list = friendDao.list(where);
		
		dataView.setRecordsTotal(count.intValue());
		dataView.setData(list);
		
		return dataView;
	}
	
	public List<Map<String,Object>> getFriendByTwoId(Long userId,Long userId2) {
		return friendDao.listBySql("select * from tb_friend where (user_id_fr1 = " + userId + " and user_id_fr2 = "+userId2+") or (user_id_fr1 = "+userId2+" and user_id_fr2 = "+userId+")" );
	}
	
	public List<Friend> getAllFriend(){
		return friendDao.list();
	}
	
	public Friend getFriendById(long friendId){
		return friendDao.get(friendId);
	}
	
	public int addFriend(Friend friend){
		return friendDao.addLocal(friend);
	}
	
	public int editFriend(Friend friend){
		WherePrams where = new WherePrams();
		where.and("friend_id", C.EQ, friend.getFriend_id());
		return friendDao.updateLocal(friend,where);
	}
	
	public int removeFriend(long friendId){
		return friendDao.del(friendId);
	}

	@Override
	public List<Map<String,Object>> getFriendByLatLng(Map<String,double[]> map,long userId) {
		List<Map<String,Object>> list = friendDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex,b.country,b.invisible from tb_friend a, tb_user b where a.user_id = b.user_id and a.user_id <> "+userId+" and a.lat > "+ map.get("rightBottomPoint")[0]+" and a.lat < "+ map.get("leftTopPoint")[0] + " and a.lng > "+map.get("leftTopPoint")[1]+" and a.lng < "+map.get("rightBottomPoint")[1]);
		return list;
	}

	@Override
	public List<Map<String,Object>> getLimitFriendByLatLng(double lat, double lng,
			int start, int count, long userId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = friendDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex,b.country,b.invisible,(POWER(MOD(ABS(lng - "+lng+"),360),2) + POWER(ABS(lat - "+lat+"),2)) AS distance from tb_friend a, tb_user b where a.user_id = b.user_id and a.user_id <> "+userId+" ORDER BY distance LIMIT "+start+","+count);
		//SELECT lng,lat, (POWER(MOD(ABS(lng - $lng),360),2) + POWER(ABS(lat - $lat),2)) AS distance FROM `user_friend` ORDER BY distance LIMIT 100 
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getUserFriends(long userId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = friendDao.listBySql(
				"select u.user_id,u.head_img,u.user_name,u.sex from (select f.user_id_fr2 from tb_friend f LEFT OUTER JOIN tb_user u on  u.user_id = f.user_id_fr1 where f.status =2 and u.user_id ="+userId+") c,tb_user u where c.user_id_fr2 = u.user_id");
		
		return list;
	}

}
