package server;

import java.io.IOException;

/**
 * Class Server
 * Create and manipulate a Server
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Server {
	public static int port;
	public static Entreprise entreprise;
	private static VueServer vue;
	private static SocketServer server;
	
	/**
	 * Create a new Server with an entreprise, a vue and a port number
	 * Start the socket server
	 * @param entreprise entreprise linked with the server
	 * @param vue graphical interface linked with the server
	 * @param port port number of the Server
	 */
	public Server(Entreprise entreprise,VueServer vue, int port) {
		Server.port = port;
		Server.entreprise = entreprise;
		Server.vue = vue;
		Server.server = new SocketServer(Server.entreprise,Server.vue, Server.port);
		Server.server.start();
	}
	
	/**
	 * Change the port number of the server
	 * @param port port number
	 * @throws IOException if we can't 
	 */
	public static void changeServerPort(int port) throws IOException {
		Server.server.close();
		Server.port = port;
		Server.server = new SocketServer(Server.entreprise,Server.vue, Server.port);
		Server.server.start();
	}
}
