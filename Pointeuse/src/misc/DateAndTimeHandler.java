package misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Class DateAndTimeHandler
 * Manipulation of Date and Time
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class DateAndTimeHandler {

	/**
	 * Format the date with a specific pattern
	 * @param date date to modify
	 * @return the date with the modified pattern
	 */
	public static String formatDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = date.format(formatter);
        return formatDateTime;
	}
	
	/**
     * This method calculates the nearest hour quarter of a given date
     * @param datetime date to round
     * @return The nearest hour quarter of the given datetime
     */
	public static LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        LocalDateTime newDatetime;
        if (mod < 8) {
            newDatetime = datetime.minusMinutes(mod);
        } else {
            newDatetime = datetime.plusMinutes(15 - mod);
        }

        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);
        
        return newDatetime;
    }
}
