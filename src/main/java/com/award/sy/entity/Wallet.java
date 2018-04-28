package com.award.sy.entity;

import java.math.BigDecimal;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

/**
 * 钱包
 * @ClassName:       Wallet
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月24日        上午9:19:27
 */
@TableName(name="tb_wallet")
public class Wallet extends Po{

	@ID
	private Long   wallet_id;//ID
	
	@TempField
	private String walletIdStr;				
	
	private Long user_id;//用户ID
	
	private Double money;//余额
	
	private String salt;//盐
	
	private String pay_password;//密码
	
	private Integer withdrawals_type;//提现类型
	
	private String withdrawals_pay;//提现账号

	public Long getWallet_id() {
		return wallet_id;
	}

	public void setWallet_id(Long wallet_id) {
		this.wallet_id = wallet_id;
	}

	public String getWalletIdStr() {
		return walletIdStr;
	}

	public void setWalletIdStr(String walletIdStr) {
		this.walletIdStr = walletIdStr;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPay_password() {
		return pay_password;
	}

	public void setPay_password(String pay_password) {
		this.pay_password = pay_password;
	}

	public Integer getWithdrawals_type() {
		return withdrawals_type;
	}

	public void setWithdrawals_type(Integer withdrawals_type) {
		this.withdrawals_type = withdrawals_type;
	}

	public String getWithdrawals_pay() {
		return withdrawals_pay;
	}

	public void setWithdrawals_pay(String withdrawals_pay) {
		this.withdrawals_pay = withdrawals_pay;
	}

	@Override
	public String toString() {
		return "Wallet [wallet_id=" + wallet_id + ", walletIdStr="
				+ walletIdStr + ", user_id=" + user_id + ", money=" + money
				+ ", salt=" + salt + ", pay_password=" + pay_password
				+ ", withdrawals_type=" + withdrawals_type
				+ ", withdrawals_pay=" + withdrawals_pay + "]";
	}
	
	
	
	
}
