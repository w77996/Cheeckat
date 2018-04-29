package com.award.sy.service;

public interface WalletLogService {
    /**
     * 插入余额变动log
     * @param record_sn
     * @param userId
     * @param type
     * @param changemoney
     * @param moeny
     * @return
     */
    int addWalletLog(String record_sn,long userId,int type,Double changemoney,Double moeny);

}
