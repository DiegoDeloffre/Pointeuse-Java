package misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class IPAddressHandler
 * Verification and manipulation of IP addresses
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class IPAddressHandler {

	/**
	 * Test if an IP address is valid or not
	 * @param ip IP address to check
	 * @return true is the IP address is valid and false if not
	 */
	public static boolean isValidIPAddress(String ip){
        // Regex for digit from 0 to 255.
        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
 
        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the IP address is empty
        // return false
        if (ip == null) {
            return false;
        }
 
        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        Matcher m = p.matcher(ip);
 
        // Return if the IP address
        // matched the ReGex
        return m.matches();
    }
}
