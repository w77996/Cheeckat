package com.award.sy.service;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.Mission;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.web.view.DatatablesView;

public interface MissionService {

	
	/**
	 * 功能描述：获取所有任务信息
	 * @return
	 */
	public List<Mission> getAllMission();
	
	public List<Map<String,Object>> getAllMissionLimit(int start,int count);
	
	public List<Map<String,Object>> getMyMission(Long userId);
	
	/**
	 * 功能描述：根据条件获取任务信息
	 * @return
	 */
	public DatatablesView<Mission> getMissionByCondition(QueryCondition query);
	
	/**
	 * 功能描述：根据ID获取任务信息
	 * @param AreaId
	 * @return
	 */
	public Mission getMissionById(long missionId);

	/**
	 * 功能描述：添加任务信息
	 * @param Mission
	 * @return
	 */
	public int addMission(Mission mission);
	
	/**
	 * 功能描述：修改任务信息
	 * @param Mission
	 * @return
	 */
	public int editMission(Mission mission);
	
	/**
	 * @功能描述：删除任务信息
	 * @param missionId
	 * @return int
	 */
	public int removeMission(long missionId);

}

