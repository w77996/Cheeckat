package com.award.core.util;

import com.alibaba.fastjson.JSONObject;
import com.award.sy.common.DateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
				
				/*ImUtils.authRegister("wangyi", "123456", "wangyi");
				ImUtils.authRegister("lier","123456","lier");*/

				Double b = new Double("0.01");
				System.out.println(b.compareTo(new Double("123")));
				System.out.println(b.doubleValue());
				System.out.println(new Double("0.01")*100);
		int price100 = new BigDecimal(b).multiply(new BigDecimal(100))
				.intValue();
				Double a = b - new Double("-0.01");
		System.out.println(new Double("1.00").compareTo(new Double(b*100)));
		System.out.println(a);
		if(b> 0 & b< 200){
			System.out.println(true);
		}
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf.format(date);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(d);
			System.out.println(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}



	}
	
}
