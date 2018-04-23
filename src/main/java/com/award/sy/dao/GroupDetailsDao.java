package com.award.sy.dao;

import org.springframework.stereotype.Repository;

import com.award.core.beans.WherePrams;
import com.award.core.dao.impl.DaoImpl;
import com.award.core.sql.where.C;
import com.award.sy.entity.GroupDetails;
import com.award.sy.entity.bean.QueryCondition;

@Repository("groupDetailsDao")
public class GroupDetailsDao extends DaoImpl<GroupDetails, Long>{
	
	/**
	 * 构建查询条件
	 * @param query
	 * @return
	 */
	public WherePrams structureConditon(QueryCondition query){
		WherePrams where = new WherePrams();
		
		//关键字
		if(query.getKeyword() != null && !"".equals(query.getKeyword())){
			where.orStart();
			where.or("user_id", C.LIKE, query.getKeyword());
			where.orEnd();
		}
		
		where.orderBy("last_time DESC");
		where.limit(query.getStart(), query.getLength());
		
		return where;
	}
}

