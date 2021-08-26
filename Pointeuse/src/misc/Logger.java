package misc;

/**
 * Class Logger
 * Print in the console with a prefix LOG
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Logger {
	/**
	 * Print a message with a prefix LOG
	 * @param message message to print
	 */
	public static void log(String message) {
		System.out.println("[LOG] " + message);
	}
}

