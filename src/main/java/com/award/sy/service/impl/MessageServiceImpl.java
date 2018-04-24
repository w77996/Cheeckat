package com.award.sy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.dao.MessageDao;
import com.award.sy.entity.Message;
import com.award.sy.entity.bean.QueryCondition;
import com.award.sy.service.MessageService;
import com.award.sy.web.view.DatatablesView;


@Service("messageService")
public class MessageServiceImpl implements MessageService{
	
	@Resource
	private MessageDao messageDao;
	
	public DatatablesView<Message> getMessageByCondition(QueryCondition query) {
		DatatablesView<Message> dataView = new DatatablesView<Message>();
		
		//构建查询条件
		WherePrams where = messageDao.structureConditon(query);
		
		Long count = messageDao.count(where);
		List<Message> list = messageDao.list(where);
		
		dataView.setRecordsTotal(count.intValue());
		dataView.setData(list);
		
		return dataView;
	}
	
	public List<Message> getAllMessage(){
		return messageDao.list();
	}
	
	public Message getMessageById(long messageId){
		return messageDao.get(messageId);
	}
	
	public Message getLastMessage(int type) {
		WherePrams where = new WherePrams();
		where.and("type", C.EQ, type);
		where.orderBy("create_time DESC");
		return messageDao.get(where);
	}
	
	public int addMessage(Message message){
		return messageDao.addLocal(message);
	}
	
	public int editMessage(Message message){
		WherePrams where = new WherePrams();
		where.and("message_id", C.EQ, message.getMessage_id());
		return messageDao.updateLocal(message,where);
	}
	
	public int updateExpired() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return messageDao.excuse("update tb_message set status = 4 where start_time < "+sdf.format(date)+"and status <> 4");
	}
	
	public int removeMessage(long messageId){
		return messageDao.del(messageId);
	}

	@Override
	public List<Map<String,Object>> getAllMessageLimit(int start,int count) {
		List<Map<String,Object>> list = messageDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex as user_sex,b.country,b.invisible from tb_message a,tb_user b where a.publish_id = b.user_id and a.status=0 and a.to_user = 0 order by money desc limit "+start+","+count);
//		WherePrams where = new WherePrams();
//		where.and("status", C.EQ, 0);
//		where.and("to_user", C.EQ, 0);
//		where.limit(start, count);
//		where.orderBy("money DESC");
//		return messageDao.list(where);
		return list;
	}

	@Override
	public List<Map<String,Object>> getMyMessage(Long userId) {
		List<Map<String,Object>> list = messageDao.listBySql("select a.*,b.head_img,b.birth,b.user_name,b.sex as user_sex,b.country,b.invisible from tb_message a,tb_user b where a.publish_id = b.user_id and (a.publish_id = "+userId+" or a.accept_id = "+userId+" or a.to_user = "+userId+") order by create_time desc");
//		WherePrams where = new WherePrams();
//		where.orStart();
//		where.or("publish_id", C.EQ, userId);
//		where.or("accept_id", C.EQ, userId);
//		where.or("to_user", C.EQ, userId);
//		where.orEnd();
//		where.orderBy("create_time DESC");
//		return messageDao.list(where);
		return list;
	}


}
