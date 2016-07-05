package br.com.surycaty.utils;

import java.text.DecimalFormat;

public class Format {

	public static float moeda(float valor){
		DecimalFormat df = new DecimalFormat("#0.00");
		
		return Float.parseFloat(df.format(valor).replace(",", "."));
	}
}
