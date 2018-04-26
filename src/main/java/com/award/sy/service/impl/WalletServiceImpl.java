package com.award.sy.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.common.Constants;
import com.award.sy.common.DateUtil;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.dao.WalletDao;
import com.award.sy.dao.WalletRecordDao;
import com.award.sy.entity.RedPacket;
import com.award.sy.entity.Wallet;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.WalletRecordService;
import com.award.sy.service.WalletService;
/**
 * 钱包service借口实现类
 * @ClassName:       WalletServiceImpl
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月25日        下午1:24:52
 */
@Service("walletServiceImpl")
public class WalletServiceImpl implements WalletService{

	@Autowired
	private WalletDao walletDao;
	
	@Autowired
	private WalletRecordDao walletRecordDao;
	
	/*@Autowired
	private WalletRecordService walletRecordService;*/
	/**
	 * 修改用户钱包余额
	 * Title: editUserWalletBalance
	 * Description: 
	 * @param user_id
	 * @param money
	 * @return
	 * @see com.award.sy.service.WalletService#editUserWalletBalance(long, java.math.BigDecimal)
	 */
	@Override
	public boolean editUserWalletBalance(long user_id,BigDecimal money) {
		// TODO Auto-generated method stub
		int i = walletDao.excuse("update tb_wallet set money ="+money.doubleValue()+" where user_id = "+user_id);
		return   i >0;
	}
	/**
	 * 查询用户钱包
	 * Title: findWalletByUserId
	 * Description: 
	 * @param u_id
	 * @return
	 * @see com.award.sy.service.WalletService#findWalletByUserId(long)
	 */
	@Override
	public Wallet findWalletByUserId(long u_id) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("user_id", C.EQ, u_id);
		return walletDao.get(where);
	}
	
	/**
	 * 微信支付充值
	 * Title: addRechargeOrderRecord
	 * Description: 
	 * @param userId
	 * @param money
	 * @param payType
	 * @return
	 * @see com.award.sy.service.WalletService#addRechargeOrderRecord(long, java.lang.String, int)
	 */
	@Override
	public String addRechargeOrderRecord(long userId, String money, int payType) {
		// TODO Auto-generated method stub
		//生成订单编号
		String record_sn = PayCommonUtil.CreateNoncestr();
		//生成订单记录
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setFrom_uid(userId);
		walletRecord.setRecord_sn(record_sn);
		walletRecord.setType(Constants.ORDER_TYPE_REDPACKET);
		walletRecord.setPay_type(payType);
		walletRecord.setMoney(new BigDecimal(money));
		int i = walletRecordDao.addLocal(walletRecord);
		
		
		if(i < 0){
			return null;
		}
		//walletRecordDao.excuse("insert tb_wallet_record (from_uid,record_sn,type,pay_type) values ("+userId+","+record_sn+","+Constants.ORDER_TYPE_REDPACKET+","+pay_type+")");
		//生成红包记录
		/*RedPacket redPacket = new RedPacket();
		redPacket.setPublish_id(userId);
		redPacket.setRecord_sn(record_sn);
		redPacket.setMoney(new BigDecimal(money));
		redPacket.setCreate_time(DateUtil.getNowTime());
		redPacketDao.addLocal(redPacket);*/
		
		return record_sn;
	}
	/**
	 * 申请提现
	 * Title: withdrawMoney
	 * Description: 
	 * @param userId
	 * @param price
	 * @return
	 * @see com.award.sy.service.WalletRecordService#withdrawMoney(long, java.math.BigDecimal)
	 */
	@Transactional
	@Override
	public boolean withdrawMoney(long userId, BigDecimal price,Wallet wallet) {
		// TODO Auto-generated method stub
		//生成订单编号
		String record_sn = PayCommonUtil.CreateNoncestr();
		//生成订单记录
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setFrom_uid(0L);
		walletRecord.setTo_uid(userId);
		walletRecord.setRecord_sn(record_sn);
		walletRecord.setType(Constants.ORDER_TYPE_WITHDRAWLS);
		walletRecord.setMoney(price);
		int i = walletRecordDao.addLocal(walletRecord);
		
		BigDecimal result = null;
		result = wallet.getMoney().subtract(price);
		WherePrams where = new WherePrams();
		where.and("user_id", C.EQ, userId);
		int j = walletDao.update(wallet, where);
		return 0 < j;
	}

}
