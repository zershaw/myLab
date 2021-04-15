package com.cbsys.saleexplore.util;

import com.google.common.net.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * 
 * @author Hector
 * this Util class wraps the http cache functions, such as check the last time modified in the request header
 * and set the response header if something changed in the server 
 */
public class HttpCacheUtil {

	/**
	 * this function will be used in the GET APIs
	 * @return false if nothing changed in the server
	 * @return true if the last time modified in the request is the same as the
	 *         server if the request is out of date, we updateOpeningHours the response
	 *         status
	 */
	public static boolean checkLastTimeModified(HttpServletRequest request,
			HttpServletResponse response, long serverLastUpdated) {

		try {
			String modifiedSince = request
					.getHeader(HttpHeaders.IF_MODIFIED_SINCE);
			if (modifiedSince != null) {

				long lastUpdatedTime = DateTimeUtil.fromStringToDate_GMT(
						modifiedSince).getTime();
				if (Math.abs(lastUpdatedTime - serverLastUpdated) < 2000) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				}
			}
			/*
			 * some changes happened in the server
			 */
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			Date date_modified = new Date();
			date_modified.setTime(serverLastUpdated);
			response.setHeader(HttpHeaders.LAST_MODIFIED,
					DateTimeUtil.fromDateToString_GMT(date_modified));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * this functions will be used in the delete/updateOpeningHours APIS
	 * after any change to the server, set the time stamp to the response
	 * @return last time updated stamp which need to be set in the server
	 */
	public static long setLastUpdatedLog(HttpServletResponse response, long guideId) {
		/*
		 * set updateOpeningHours log
		 */
		Date time_modified = DateTimeUtil.getUTCTime();
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setHeader(HttpHeaders.LAST_MODIFIED,
				DateTimeUtil.fromDateToString_GMT(time_modified));
		return time_modified.getTime();
	}

}
