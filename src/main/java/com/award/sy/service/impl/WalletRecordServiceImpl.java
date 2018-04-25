package com.award.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
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
	@Override
	public boolean addWalletRecordOrder(long from_uid, String record_sn, int type) {
		// TODO Auto-generated method stub
		int i = walletRecordDao.excuse("insert tb_wallet_record (from_uid,record_sn,type) values ("+from_uid+","+record_sn+","+type+")");
		return 1 == i;
	}
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
		walletRecordDao.updateLocal(walletRecord,where);
		return true ;
	}
	/**
	 * 修改
	 * Title: addLocalWalletRecord
	 * Description: 
	 * @param walletRecord
	 * @return
	 * @see com.award.sy.service.WalletRecordService#addLocalWalletRecord(com.award.sy.entity.WalletRecord)
	 */
	@Override
	public boolean addLocalWalletRecord(WalletRecord walletRecord) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("record_sn", C.EQ, walletRecord.getRecord_sn());
		int i = walletRecordDao.addLocal(walletRecord);
		return 1 == i;
	}
	
	

}
