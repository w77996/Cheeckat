package com.award.core.util;

import org.springframework.core.convert.converter.Converter;

public class StringToDouble implements Converter<String, Double>
{
	@Override
	public Double convert(String text)
	{
		if (text.isEmpty())
			return 0.0;
		else
			return Double.parseDouble(text);
	}

}