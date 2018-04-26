package com.award.sy.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.award.sy.entity.WalletRecord;

/**
 * 钱包记录接口
 * @ClassName:       WalletRecordService
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月24日        下午2:16:26
 */
public interface WalletRecordService {
	/**
	 * 进入支付页面生成订单
	 * @Title:           addWalletOrder
	 * @Description:     TODO
	 * @param:           @param from_uid
	 * @param:           @param record_sn
	 * @param:           @param type
	 * @param:           @return   
	 * @return:          int   
	 * @throws
	 */
	boolean addWalletRecordOrder(long from_uid,String record_sn,int type);
	
	//int editWalletOrder(long )
	/**
	 * 通过SN查找记录
	 * @Title:           findWallerOrderByRecordSN
	 * @Description:     TODO
	 * @param:           @param record_sn
	 * @param:           @return   
	 * @return:          WalletRecord   
	 * @throws
	 */
	WalletRecord findWallerOrderByRecordSN(String record_sn);
	
	/**
	 * 修改记录表的状态
	 * @Title:           editWalletOrder
	 * @Description:     TODO
	 * @param:           @param walletRecord
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	boolean editWalletOrder(WalletRecord walletRecord);
	
	boolean addLocalWalletRecord(WalletRecord walletRecord);


	
	
	

}
