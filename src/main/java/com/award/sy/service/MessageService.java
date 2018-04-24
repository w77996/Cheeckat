package com.award.sy.service;

import java.util.List;
import java.util.Map;

import com.award.sy.entity.Message;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.web.view.DatatablesView;

public interface MessageService {


	
	/**
	 * 功能描述：获取所有任务信息
	 * @return
	 */
	public List<Message> getAllMessage();
	
	public List<Map<String,Object>> getAllMessageLimit(int start,int count);
	
	public List<Map<String,Object>> getMyMessage(Long userId);
	
	/**
	 * 功能描述：根据条件获取任务信息
	 * @return
	 */
	public DatatablesView<Message> getMessageByCondition(QueryCondition query);
	
	/**
	 * 功能描述：根据ID获取任务信息
	 * @param AreaId
	 * @return
	 */
	public Message getMessageById(long messageId);
	
	public Message getLastMessage(int type);

	/**
	 * 功能描述：添加任务信息
	 * @param Message
	 * @return
	 */
	public int addMessage(Message message);
	
	/**
	 * 功能描述：修改任务信息
	 * @param Message
	 * @return
	 */
	public int editMessage(Message message);
	
	public int updateExpired();
	
	/**
	 * @功能描述：删除任务信息
	 * @param messageId
	 * @return int
	 */
	public int removeMessage(long messageId);



}
