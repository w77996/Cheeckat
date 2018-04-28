package com.award.sy.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.common.Constants;
import com.award.sy.common.PayCommonUtil;
import com.award.sy.dao.WalletLogDao;
import com.award.sy.dao.WalletRecordDao;
import com.award.sy.entity.WalletLog;
import com.award.sy.entity.WalletRecord;
import com.award.sy.service.WalletRecordService;
@Service("walletRecordService")
public class WalletRecordServiceImpl implements WalletRecordService{

	@Autowired
	private WalletRecordDao walletRecordDao;
	
	@Autowired
	private WalletLogDao walletLogDao;
	/**
	 * 生成订单
	 * Title: addWalletOrder
	 * Description: 
	 * @param from_uid
	 * @param record_sn
	 * @param type
	 * @return
	 * @see com.award.sy.service.WalletRecordService#addWalletRecordOrder(long, java.lang.String, int)
	 */
	/*@Override
	public boolean addWalletRecordOrder(long from_uid, String record_sn, int type) {
		// TODO Auto-generated method stub
		int i = walletRecordDao.excuse("insert tb_wallet_record (from_uid,record_sn,type) values ("+from_uid+","+record_sn+","+type+")");
		return 1 < i;
	}*/
	/**
	 * 通过record_sn查找订单
	 * Title: findWallerOrderByRecordSN
	 * Description: 
	 * @param record_sn
	 * @return
	 * @see com.award.sy.service.WalletRecordService#findWallerOrderByRecordSN(java.lang.String)
	 */
	@Override
	public WalletRecord findWallerOrderByRecordSN(String record_sn) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("record_sn", C.EQ, record_sn);
		WalletRecord  walletRecord= walletRecordDao.get(where);
		return walletRecord;
	}
	/**
	 * 修改记录信息
	 * Title: editWalletOrder
	 * Description: 
	 * @param walletRecord
	 * @return
	 * @see com.award.sy.service.WalletRecordService#editWalletOrder(com.award.sy.entity.WalletRecord)
	 */
	@Override
	public boolean editWalletOrder(WalletRecord walletRecord) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("record_sn", C.EQ, walletRecord.getRecord_sn());
		int i = walletRecordDao.update(walletRecord,where);
		return 0 < i ;
	}

	/**
	 * 生成订单
	 * @param user_id
	 * @param money
	 * @param pay_type
	 * @param type
	 * @return
	 */
	@Override
	public String addWalletRecordOrder(long user_id, String money, int pay_type, int type) {
		String record_sn = PayCommonUtil.createOutTradeNo();
		//生成订单记录
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setFrom_uid(user_id);
		walletRecord.setRecord_sn(record_sn);
		walletRecord.setType(Constants.ORDER_TYPE_REDPACKET);
		walletRecord.setPay_type(pay_type);
		walletRecord.setMoney(Double.parseDouble(money));
		int i = walletRecordDao.addLocal(walletRecord);
		if(0 < i){
			return record_sn;
		}
		return null;
	}


}
