package fr.opensagres.nosql.ide.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitaire pour les dates.
 * 
 * @author Angelo ZERR
 * 
 */
public class DateUtils {

	private static final String DEFAULT_DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";

	/**
	 * Formatte les milli second en "dd/MM/yyyy HH:mm:ss".
	 * 
	 * @param miliSecond
	 * @return
	 */
	public static String formatDatetime(long miliSecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(miliSecond);
		return formatDatetime(calendar.getTime(), DEFAULT_DATE_PATTERN);
	}

	/**
	 * Formatte la date avec le patten par defaut "dd/MM/yyyy HH:mm:ss".
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return formatDatetime(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * Formattage d'un date/heure
	 * 
	 * @param date
	 *            Date : la date à formatter
	 * @param outputPattern
	 *            String : format de sortie
	 * @return String : la date formattée, null si la date n'est pas
	 *         rensiegnée
	 */
	public static String formatDatetime(Date date, String outputPattern) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(outputPattern);
		return sdf.format(date);
	}
}
