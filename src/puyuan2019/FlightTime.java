package puyuan2019;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author SpencerCJH
 * @date 2019/4/22 1603
 */
public class FlightTime {
    private static Pattern PATTERN = Pattern.compile("0[0-9][0-5][0-9]|1[0-9][0-5][0-9]|2[0-3][0-5][0-9]");

    public String calculate(String startTime, String endTime) throws RuntimeException {
        Calendar start = getTime(startTime);
        Calendar end = getTime(endTime);
        long a = start.getTimeInMillis(), b = end.getTimeInMillis();
        return new Date(a - b).toString();
    }

    private Calendar getTime(String str) throws RuntimeException {
        if (PATTERN.matcher(str).matches()) {
            Calendar calendar = Calendar.getInstance();
            if (str.length() == 3) {
                calendar.set(Calendar.HOUR, Integer.parseInt(String.valueOf(str.charAt(0))));
                calendar.set(Calendar.MINUTE, Integer.parseInt(str.substring(1)));
            } else {
                calendar.set(Calendar.HOUR, Integer.parseInt(str.substring(0, 2)));
                calendar.set(Calendar.MINUTE, Integer.parseInt(str.substring(2)));
            }
            return calendar;
        } else {
            throw new RuntimeException();
        }
    }
}
