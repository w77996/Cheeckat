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
	 * 修改用户钱包余额
	 * @Title:           editUserWalletBalance
	 * @Description:     TODO
	 * @param:           @param user_id
	 * @param:           @param money
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	boolean editUserWalletBalance(long user_id,Double money);
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
	 * 提现申请
	 * @Title:           withdrawMoney
	 * @Description:     TODO
	 * @param:           @param userId
	 * @param:           @param price
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	boolean withdrawMoney(long userId, Double price,Wallet wallet);
	
}
