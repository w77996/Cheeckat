package com.award.sy.service;

import java.math.BigDecimal;

import com.award.sy.entity.Wallet;
/**
 * 用户钱包借口
 * @ClassName:       WalletService
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月25日        下午1:22:32
 */
public interface WalletService {
	/**
	 * 修改用户钱包月
	 * @Title:           editUserWalletBalance
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param money
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	boolean editUserWalletBalance(long user_id,BigDecimal money);
	/**
	 * 通过ID查找用户钱包数据
	 * @Title:           findWalletByUserId
	 * @Description:     TODO
	 * @param:           @param u_id
	 * @param:           @return   
	 * @return:          Wallet   
	 * @throws
	 */
	Wallet findWalletByUserId(long u_id);
	/**
	 * 通过余额来支付红包，提现，任务
	 * @Title:           payByBalance
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param record_sn
	 * @param:           @param money
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	//boolean payByBalance(String user_id, String record_sn, String money);
	/**
	 * 微信支付充值
	 * @Title:           addRechargeOrderRecord
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @param money
	 * @param:           @param payType
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	String addRechargeOrderRecord(long userId, String money, int payType);
	/**
	 * 提现申请
	 * @Title:           withdrawMoney
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @param price
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	boolean withdrawMoney(long userId, BigDecimal price,Wallet wallet);
	
}
