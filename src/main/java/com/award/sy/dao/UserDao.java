package com.award.sy.dao;

import org.springframework.stereotype.Repository;

import com.award.core.beans.WherePrams;
import com.award.core.dao.impl.DaoImpl;
import com.award.core.sql.where.C;
import com.award.sy.entity.User;
import com.award.sy.entity.bean.QueryCondition;

/**
 * 
 * @描述：用户信息Dao
 * @作者：bin
 * @版本：V1.0
 * @创建时间：：2018-04-10 下午10:57:45
 *
 */
@Repository("userDao")
public class UserDao extends DaoImpl<User, Long>{
	
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
			where.or("user_name", C.LIKE, query.getKeyword());
			where.orEnd();
		}
		
		where.orderBy("create_time DESC");
		where.limit(query.getStart(), query.getLength());
		
		return where;
	}
}
