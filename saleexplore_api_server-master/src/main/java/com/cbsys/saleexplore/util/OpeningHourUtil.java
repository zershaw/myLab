package com.cbsys.saleexplore.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class OpeningHourUtil {

    /**
     * Input date format by hand, w.r.t military time format.
     * The minimum granularity of time is 30 mins, Sunday is the 1st day of week.
     *
     * E.g., 6 pm -> 1800
     *       6 am -> 0600
     *
     * @param hours opening hours for seven days of a week, e.g.,:
     *                         {"0600", "1800", "0600", "1800"},    ->   Sunday, open from 6 pm, closed at 6 pm
     *                         {"0600", "1800", "0600", "1800"},
     *                         {"0600", "1800", "0600", "1800"},
     *                         {"0600", "1800", "0600", "1800"},
     *                         {"0600", "1800", "0600", "1800"},
     *                         {"0600", "1800", "0600", "1800"},
     *                         {"0600", "1800", "0600", "1800"},
     *
     * @return Serialized date format in SQL table
     */
    public static String openingHoursSerializer(List<String[]> hours) throws Exception {
        assert hours.size() == 7;

        StringBuilder serializedDate = new StringBuilder();

        for (String[] hour: hours) {
            if (!checkInputFormat(hour)) {
                throw  new Exception("Input time format is incorrect");
            }

            char[] serializedOpeningHourPerDay = new char[48];
            Arrays.fill(serializedOpeningHourPerDay, '0');
            int start = (int) Math.ceil(Float.parseFloat(hour[0])/50) ;			// 50 = 100 * 30 / 60
            int end = (int) Math.ceil(Float.parseFloat(hour[1])/50);
            for (int i = start; i < end; i ++) {
                serializedOpeningHourPerDay[i] = '1';
            }

            serializedDate.append(serializedOpeningHourPerDay);
        }
        return serializedDate.toString();
    }


    /**
     * Convert opening hours from SQL table to readable military time format.
     * E.g.,
     * 			000000000000000000001111111111111111111111110000  ->  "1000", "2200"
     *
     * @param openingHours
     * @return
     */
    public static List<String[]> openingHoursDeserializer(String openingHours) {
        assert openingHours.length() == 336;

        char[] hoursArr = openingHours.toCharArray();
        List<String[]> outputDate = new ArrayList<>();

        for (int i = 0; i < hoursArr.length; i += 48) {
            int start = i;
            while (start < 336 && hoursArr[start] != '1') { start ++; }

            int end = start;
            while (end < 336 && hoursArr[end] == '1') { end ++ ;}

            int startTime = (start - i) % 2 == 0 ? (start - i)  * 50 : (start - i - 1) * 50 + 30;
            int endTime = (end - i) % 2 == 0 ? (end - i)  * 50 : (end - i - 1) * 50 + 30;
            String startStr = String.valueOf(startTime).length() < 4 ? "0" + startTime : String.valueOf(startTime);
            String endStr = String.valueOf(endTime).length() < 4 ? "0" + endTime : String.valueOf(endTime);

            outputDate.add(new String[]{startStr, endStr});
        }

        return outputDate;
    }

    /**
     * get the current date of today and test whether it is open based on the opening hour
     * @param deserializedOpeningHours in the deserialized format
     */
    public static boolean isOpen(List<String[]> deserializedOpeningHours) {
        Calendar calendar = Calendar.getInstance();
        int dateFormat = Integer.parseInt(new SimpleDateFormat("HHmm").format(calendar.getTime()));

        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int start = Integer.parseInt(deserializedOpeningHours.get(currentDayOfWeek)[0]);
        int end = Integer.parseInt(deserializedOpeningHours.get(currentDayOfWeek)[1]);

        return dateFormat >= start && dateFormat <= end;
    }


    private static boolean checkInputFormat(String[] hour) {
        int start = Integer.parseInt(hour[0]);
        int end = Integer.parseInt(hour[1]);
        return start >= 0 &&
                end >= 0 && end <= 2400 &&
                start <= end;
    }


}
