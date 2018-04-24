package com.award.sy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.award.core.beans.WherePrams;
import com.award.core.sql.where.C;
import com.award.sy.dao.WalletDao;
import com.award.sy.entity.Wallet;
import com.award.sy.service.WalletService;
@Service("walletServiceImpl")
public class WalletServiceImpl implements WalletService{

	@Autowired
	private WalletDao walletDao;
	
	@Override
	public int editUserWalletBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Wallet getWalletByUserId(long u_id) {
		// TODO Auto-generated method stub
		WherePrams where = new WherePrams();
		where.and("user_id", C.EQ, u_id);
		return walletDao.get(where);
	}

}
