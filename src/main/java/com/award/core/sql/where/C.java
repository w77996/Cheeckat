package com.award.core.sql.where;

public enum C {

	EQ,NE,LIKE,DA,IXAO,IN,DADENG,XIAODENG;
	
	public static String getSqlWhere(C c){
		switch (c) {
		case EQ:
			return "=";
		case NE:
			return "<>";
		case LIKE:
			return "like";	
		case DA:
			return ">";	
		case IXAO:
			return "<";	
		case IN:
			return "in";	
		case DADENG:
			return ">=";
		case XIAODENG:
			return "<=";
		default:
			return "=";	
		}
	}
}
