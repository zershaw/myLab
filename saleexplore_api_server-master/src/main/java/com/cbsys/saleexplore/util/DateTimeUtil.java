package com.cbsys.saleexplore.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTimeUtil {

	public static final long HOUR_MILLISECOND = 60 * 60 * 1000;
	public static final long DAY_MILLISECON = 24 * 60 * 60 * 1000;
	public static final long MINUTE_MILLISECON = 60 * 1000;

	/**
	 * Given a mon in MMM format, return how many days in that month
	 */
	public static int daysInAMonth(String month) {
		switch (month) {
		case "JAN":
		case "MAR":
		case "MAY":
		case "JUL":
		case "AUG":
		case "OCT":
		case "DEC":
			return 31;
		case "FEB":
			return 27;
		default:
			return 30;
		}
	}

	/**
	 * this function will be used to handle HTTP header time stamp
	 */
	public static String fromDateToString_GMT(Date dateTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss zzz");
			format.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME,
					"UTC"));
			return format.format(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this function will be used to handle HTTP header time stamp
	 */
	public static Date fromStringToDate_GMT(String dateTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss zzz");
			format.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME,
					"UTC"));
			return format.parse(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this function is the de-factor function of in changing data
	 * infor with the server
	 */
	public static Date fromStringToDate(String dateTime) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			format.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME,
					"UTC"));
			dateTime += ".550";
			format.parse(dateTime);
			return format.parse(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this function is the de-factor function  in changing data
	 * infor with the server
	 */
	public static String fromDateToString(Date date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			format.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME,
					"UTC"));
			return format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * get the current UTC/GMT time
	 */
	public static Date getUTCTime() {
		try {
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			// Local time zone
			SimpleDateFormat dateFormatLocal = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// Time in GMT
			return dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get the current UTC/GMT time in string format
	 */
	public static String getUTCTime_String() {
		try {
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			return dateFormatGmt.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * given the dob in Date format. we calculate the age of this person as a Integer
	 */
	public static int computeAge(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);
		Calendar now = new GregorianCalendar();
		int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
				|| (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) && cal
						.get(Calendar.DAY_OF_MONTH) > now
						.get(Calendar.DAY_OF_MONTH))) {
			res--;
		}

		return res;
	}

	/**
	 * compute the interval dates between two dates, date2 is later and larger
	 */
	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}



}
