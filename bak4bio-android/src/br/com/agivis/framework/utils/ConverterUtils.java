/*
 * Copyright (c) 2012, Agivis Sistemas LTDA. Todos os direitos reservados.
 *
 * Os Programas desta Aplicação (que incluem tanto o software quanto a sua
 * documentação) contém informações proprietárias da Agivis Sistemas LTDA; eles são
 * licenciados de acordo com um contrato de licença contendo restrições de uso e
 * confidencialidade, e são também protegidos pela Lei 9609/98 e 9/610/98,
 * respectivamente Lei do Software e Lei dos Direitos Autorais. Engenharia
 * reversa, descompilação e desmontagem dos programas são proibidos. Nenhuma
 * parte destes programas pode ser reproduzida ou transmitida de nenhuma forma e
 * por nenhum meio, eletrônico ou mecânico, por motivo algum, sem a permissão
 * escrita da Agivis Sistemas LTDA.
 */
package br.com.agivis.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Métodos úteis para conversão, parse e formatação de dados.
 * @author Time Agivis
 */
public class ConverterUtils {
	
	private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
	private static final Locale DEFAULT_LOCALE = LOCALE_PT_BR;
	
	private static Locale selectedLocale = DEFAULT_LOCALE;
	
	/**
	 * Define um novo locale a ser usado.
	 * O padrão é pt-BR
	 * @param newLocale
	 */
	public static void changeLocale(Locale newLocale) {
		selectedLocale = newLocale;
	}

	/**
	 * Converte string no formato dd/MM/yyyy para Calendar
	 * @param stringDate
	 * @return
	 */
	public static Calendar toCalendar(String stringDate) {
		return toCalendar(stringDate, "dd/MM/yyyy");
	}
	
	/**
	 * Converte string no formato desejado para Calendar
	 * @param stringDate
	 * @param dateFormat
	 * @return
	 */
	public static Calendar toCalendar(String stringDate, String dateFormat) {
		if (stringDate == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(stringDate, dateFormat));
			
		return calendar;
	}
	
	/**
	 * Converte string no formato dd/MM/yyyy para Date
	 * @param stringDate
	 * @return
	 */
	public static Date toDate(String stringDate) {
		return toDate(stringDate, "dd/MM/yyyy");
	}
	
	/**
	 * Converte string no formato desejado para Date
	 * @param stringDate
	 * @param dateFormat
	 * @return
	 */
	public static Date toDate(String stringDate, String dateFormat) {
		if (stringDate == null) {
			return null;
		}
		
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		
		try {
			return df.parse(stringDate);
		} catch (ParseException e) {
			//TODO tratar
			throw new RuntimeException("Invalid format", e);
		}
	}
	
	/**
	 * Converte string para integer
	 * @param string
	 * @return
	 */
	public static Integer toInteger(String string) {
		if ((string == null) || (string.length() == 0)) {
			return null;
		}

		return Integer.parseInt(string);
	}
	
	/**
	 * Converte string para BigDecimal.
	 * Deve estar no padrão do BigDecimal, por exemplo, 150.00
	 * @param string
	 * @return
	 */
	public static BigDecimal toBigDecimal(String string) {
		if ((string == null) || (string.length() == 0)) {
			return null;
		}

		return new BigDecimal(string);
	}
	
	/**
	 * Realiza parse de string para BigDecimal.
	 * Deve estar no formado do locale. Por exemplo, para pt-BR seria 150,00
	 * @param stringNumber
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal parseBigDecimal(String stringNumber) throws ParseException {
		if (stringNumber == null) {
			return null;
		}
		
		DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(selectedLocale);
		df.setParseBigDecimal(true);
		
		return (BigDecimal) df.parse(stringNumber);
	}
	
	/**
	 * Formata BigDecimal para string no locale selecionado
	 * @param number
	 * @return
	 */
	public static String formatBigDecimal(BigDecimal number) {
		return formatBigDecimal(number, selectedLocale);
	}
	
	/**
	 * Formata BigDecimal para string no locale passado para parâmetro
	 * @param number
	 * @param locale
	 * @return
	 */
	public static String formatBigDecimal(BigDecimal number, Locale locale) {
		if (number == null) {
			number = BigDecimal.ZERO;
		}
		
		DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		//df.setRoundingMode(RoundingMode.DOWN);
		df.setParseBigDecimal(true);
		
		return df.format(number);
	}
	
	
	/**
	 * Formata Date para string no formato padrão para datas (dd/MM/yyyy)
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDateTime(date, "dd/MM/yyyy");
	}
	
	/**
	 * Formata Date para string no formato padrão para horas (HH:mm)
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatTime(Date time) {
		return formatDateTime(time, "HH:mm");
	}
	
	/**
	 * Formata Date para string no formato definido
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
}
