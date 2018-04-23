package com.award.core.util;

public class StringUtils {
	public static boolean isBlank(String args) {
		boolean isBlank = true;
		if(args != null && !"".equals(args)) {
			isBlank = false;
		}
		return isBlank;
	}

}
