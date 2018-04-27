package com.award.sy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.dao.GroupDao;
import com.award.sy.dao.GroupDetailsDao;
import com.award.sy.entity.Group;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.service.GroupService;
import com.award.sy.web.view.DatatablesView;

@Service("groupService")
public class GroupServiceImp implements GroupService{

	@Resource
	private GroupDao groupDao;
	
	@Autowired
	private GroupDetailsDao groupDetailsDao;
	
	
	/**
	 * 获取群组名称及群组ID,是否是管理员
	 */
	public DatatablesView<Group> getGroupByCondition(QueryCondition query) {
		DatatablesView<Group> dataView = new DatatablesView<Group>();
		
		//构建查询条件
		WherePrams where = groupDao.structureConditon(query);
		
		Long count = groupDao.count(where);
		List<Group> list = groupDao.list(where);
		
		dataView.setRecordsTotal(count.intValue());
		dataView.setData(list);
		
		return dataView;
	}
	
	public List<Group> getAllGroup(){
		return groupDao.list();
	}
	
	public Group getGroupById(long groupId){
		return groupDao.get(groupId);
	}
	
	public int addGroup(Group group){
		return groupDao.addLocal(group);
	}
	
	public int editGroup(Group group){
		WherePrams where = new WherePrams();
		where.and("group_id", C.EQ, group.getGroup_id());
		return groupDao.updateLocal(group,where);
	}
	
	public int removeGroup(long groupId){
		return groupDao.del(groupId);
	}

	@Override
	public List<Map<String,Object>> getGroupByLatLng(Map<String,double[]> map,long userId) {
		List<Map<String,Object>> list = groupDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex,b.country,b.invisible from tb_group a, tb_user b where a.user_id = b.user_id and a.user_id <> "+userId+" and a.lat > "+ map.get("rightBottomPoint")[0]+" and a.lat < "+ map.get("leftTopPoint")[0] + " and a.lng > "+map.get("leftTopPoint")[1]+" and a.lng < "+map.get("rightBottomPoint")[1]);
		return list;
	}

	@Override
	public List<Map<String,Object>> getLimitGroupByLatLng(double lat, double lng,
			int start, int count, long userId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = groupDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex,b.country,b.invisible,(POWER(MOD(ABS(lng - "+lng+"),360),2) + POWER(ABS(lat - "+lat+"),2)) AS distance from tb_group a, tb_user b where a.user_id = b.user_id and a.user_id <> "+userId+" ORDER BY distance LIMIT "+start+","+count);
		//SELECT lng,lat, (POWER(MOD(ABS(lng - $lng),360),2) + POWER(ABS(lat - $lat),2)) AS distance FROM `user_group` ORDER BY distance LIMIT 100 
		return list;
	}

	@Override
	public Group getGroupByName(String groupName) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("group_name", C.EQ, groupName);
		return groupDao.get(where);
	}
	
	@Override
	public Group getGroupByNameAndAdminId(String groupName,Long adminId) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("group_name", C.EQ, groupName);
		where.and("admin_id", C.EQ, adminId);
		return groupDao.get(where);
	}
	
	
	@Override
	public List<Map<String, Object>> getUserGroup(long userId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = groupDao.listBySql("select g.group_name,c.group_id,c.is_admin,g.create_time from tb_group g,(select DISTINCT gd.group_id,gd.is_admin from tb_group_details gd where gd.member_id ="+userId+") c where g.group_id = c.group_id");
		return list;
	}

	@Transactional
	@Override
	public int deleteGroup(long groupId) {
		// TODO Auto-generated method stub
		int i = groupDetailsDao.excuse("delete from tb_group_details where group_id = "+groupId);
		int j = groupDao.excuse("delete from tb_group where group_id = "+groupId);
		if(i > 0 && j > 0){
			return 1;
		}else{
			return 0;
		}
		
	}

}
