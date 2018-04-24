package com.award.sy.dao;

import org.springframework.stereotype.Repository;

import com.award.core.dao.impl.DaoImpl;
import com.award.sy.entity.Wallet;
/**
 * 钱包DAO
 * @ClassName:       WalletDao
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月24日        上午9:41:07
 */
@Repository("walletDao")
public class WalletDao extends DaoImpl<Wallet, Long>{

}
