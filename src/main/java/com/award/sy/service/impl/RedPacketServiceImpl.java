package com.award.sy.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.Utils;
import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.common.Constants;
import com.award.sy.common.DateUtil;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.dao.RedPacketDao;
import com.award.sy.dao.WalletDao;
import com.award.sy.dao.WalletLogDao;
import com.award.sy.dao.WalletRecordDao;
import com.award.sy.entity.RedPacket;
import com.award.sy.entity.User;
import com.award.sy.entity.Wallet;
import com.award.sy.entity.WalletLog;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.RedPacketService;
import com.award.sy.service.UserService;
import com.award.sy.service.WalletRecordService;
import com.award.sy.service.WalletService;
/**
 * 红包service实现类
 * @ClassName:       RedPacketServiceImpl
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月25日        上午9:58:47
 */
@Service("redPacketService")
public class RedPacketServiceImpl implements RedPacketService {

	@Autowired
	private RedPacketDao redPacketDao;
	
	@Autowired
	private WalletRecordDao walletRecordDao;
	
	@Autowired
	private WalletDao walletDao;
	
	@Autowired
	private WalletLogDao walletLogDao;
	/**
	 * 通过Id获取红包
	 * Title: getRedPacketById
	 * Description: 
	 * @param redpacketId
	 * @return
	 * @see com.award.sy.service.RedPacketService#getRedPacketById(long)
	 */
	@Override
	public RedPacket getRedPacketById(long redpacketId) {
		// TODO Auto-generated method stub
		return redPacketDao.get(redpacketId);
	}
	/**
	 * 添加微信红包订单
	 * Title: addRedPacketOrderRecord
	 * Description: 
	 * @param userId
	 * @param money
	 * @param pay_type
	 * @return
	 * @see com.award.sy.service.RedPacketService#addRedPacketOrderRecord(long, java.lang.String, int)
	 */
	@Transactional
	@Override
	public String addRedPacketOrderRecord(long userId,String money,int pay_type) {
		// TODO Auto-generated method stub
		//生成订单编号
		String record_sn = PayCommonUtil.CreateNoncestr();
		//生成订单记录
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setFrom_uid(userId);
		walletRecord.setRecord_sn(record_sn);
		walletRecord.setType(Constants.ORDER_TYPE_REDPACKET);
		walletRecord.setPay_type(pay_type);
		walletRecord.setMoney(new BigDecimal(money));
		walletRecordDao.addLocal(walletRecord);
		
		//walletRecordDao.excuse("insert tb_wallet_record (from_uid,record_sn,type,pay_type) values ("+userId+","+record_sn+","+Constants.ORDER_TYPE_REDPACKET+","+pay_type+")");
		//生成红包记录
		RedPacket redPacket = new RedPacket();
		redPacket.setPublish_id(userId);
		redPacket.setRecord_sn(record_sn);
		redPacket.setMoney(new BigDecimal(money));
		redPacket.setCreate_time(DateUtil.getNowTime());
		redPacketDao.addLocal(redPacket);
		
		return record_sn;
		
	}
	/**
	 * 微信红包支付完成，修改记录
	 * Title: editRedPacketSendMessage
	 * Description: 
	 * @param record_sn
	 * @param pay_status
	 * @return
	 * @see com.award.sy.service.RedPacketService#editRedPacketSendMessage(java.lang.String, int)
	 */
	@Transactional
	@Override
	public boolean editRedPacketSendMessage(String record_sn,int pay_status) {
		
		if(Constants.PAY_STATUS_SUCCESS == pay_status){
			
			WalletRecord walletRecord = new WalletRecord();
			walletRecord.setPay_status(pay_status);
			walletRecord.setPay_time(DateUtil.getNowTime());
			walletRecord.setPay_type(Constants.PAY_TYPE_WECHAT);
			WherePrams where = new WherePrams();
			where.and("record_sn", C.EQ, record_sn);
			walletRecordDao.updateLocal(walletRecord,where);
			
			
			return true;
		}
		return false;
	}
	/**
	 * 抢红包
	 * Title: getRedPacket
	 * Description: 
	 * @param redPacket
	 * @return
	 * @see com.award.sy.service.RedPacketService#getRedPacket(com.award.sy.entity.RedPacket)
	 */
	@Transactional
	@Override
	public List<Map<String,Object>> getRedPacket(RedPacket redPacket) {
		// TODO Auto-generated method stub
		
		//WalletRecord walletRecord = walletRecordService.findWallerOrderByRecordSN(redPacket.getRecord_sn());
		//查记录
		WherePrams recordWhere = new WherePrams();
		recordWhere.and("record_sn", C.EQ, redPacket.getRecord_sn());
		WalletRecord walletRecord = walletRecordDao.get(recordWhere);
		//查账
		if(redPacket.getMoney().compareTo(walletRecord.getMoney())!=0){
			return null;
		}
		//更新红包状态
		String date = DateUtil.getNowTime();
		redPacket.setAccept_time(date);
		WherePrams where = new WherePrams();
		where.and("redpacket_id", C.EQ, redPacket.getRedpacket_id());
		int i = redPacketDao.updateLocal(redPacket);
		if(i < 0){
			return null;
		}
		//修改被抢用户的金额
		WherePrams walletWhere = new WherePrams();
		walletWhere.and("user_id", C.EQ, redPacket.getAccept_id());
		Wallet wallet = walletDao.get(walletWhere);
		//获取钱包的余额
		BigDecimal walletMoney = wallet.getMoney();
		BigDecimal totalMoney = new BigDecimal(walletMoney.doubleValue());
		//获取红包的数额
		totalMoney.add(new BigDecimal(redPacket.getMoney().doubleValue()));
		//修改钱包余额
		
		walletDao.excuse("update tb_wallet set money ="+totalMoney.doubleValue()+" where user_id = "+redPacket.getAccept_id());
		/*if(!walletService.editUserWalletBalance(redPacket.getAccept_id(),totalMoney)){
			return null;
		}*/
		//修改记录
		WherePrams recordPrams2 = new WherePrams();
		walletRecord.setMoney(totalMoney);
		walletRecord.setFetch_status(Constants.FETCH_SUCCESS);
		walletRecord.setFetch_time(date);
		walletRecord.setTo_uid(redPacket.getAccept_id());
		
		walletRecordDao.updateLocal(walletRecord,recordPrams2);
		
		List<Map<String,Object>> list = redPacketDao.listBySql("select r.*,u.head_img,u.user_name,u.sex from tb_user u,tb_red_packet r where r.publish_id = u.user_id and u.user_id = "+redPacket.getPublish_id()+" and r.accept_id ="+redPacket.getAccept_id()+" and status = 1 order by accept_time desc");
		return list;
	}
	/**
	 * 使用余额发红包
	 * Title: addRedPacketPyByBalanceRecord
	 * Description: 
	 * @param userId
	 * @param money
	 * @param payType
	 * @return
	 * @see com.award.sy.service.RedPacketService#addRedPacketPyByBalanceRecord(long, java.lang.String, int)
	 */
	@Transactional
	@Override
	public boolean addRedPacketPayByBalanceRecord(long userId, String money,
			int payType,Wallet wallet) {
		//生成订单编号
		String record_sn = PayCommonUtil.CreateNoncestr();
		//生成订单记录
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setFrom_uid(userId);
		walletRecord.setRecord_sn(record_sn);
		walletRecord.setType(Constants.ORDER_TYPE_REDPACKET);
		walletRecord.setPay_type(payType);
		walletRecord.setMoney(new BigDecimal(money));
		walletRecordDao.addLocal(walletRecord);
		
		//walletRecordDao.excuse("insert tb_wallet_record (from_uid,record_sn,type,pay_type) values ("+userId+","+record_sn+","+Constants.ORDER_TYPE_REDPACKET+","+pay_type+")");
		//生成红包记录
		RedPacket redPacket = new RedPacket();
		redPacket.setPublish_id(userId);
		redPacket.setRecord_sn(record_sn);
		redPacket.setMoney(new BigDecimal(money));
		redPacket.setCreate_time(DateUtil.getNowTime());
		redPacketDao.addLocal(redPacket);
		
		//扣除用户余额中的钱
		BigDecimal result = null;
		BigDecimal total_balance = wallet.getMoney();
		result = total_balance.subtract(new BigDecimal(money));
		wallet.setMoney(result);
		int i = walletDao.updateLocal(wallet);
		return 1 < i;
	}
	
	

}
