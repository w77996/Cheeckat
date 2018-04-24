package com.award.sy.service;

import java.math.BigDecimal;

import com.award.sy.entity.Wallet;

public interface WalletService {

	int editUserWalletBalance();
	
	Wallet getWalletByUserId(long u_id);
}
