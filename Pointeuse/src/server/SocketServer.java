package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import misc.Logger;

/**
 * Class SocketServer
 * Create and manipulate a SocketServer
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class SocketServer extends Thread {
	private ServerSocket listener;
	private int clientCounter;
	private Socket socketOfServer;
	private Entreprise entreprise;
	private VueServer vue;
	
	/**
	 * Create a new Socket Server with a entreprise, a vue and a port 
	 * @param entreprise entreprise linked with the server
	 * @param vue graphical interface
	 * @param port port number of exchanges to send messages
	 */
	public SocketServer(Entreprise entreprise,VueServer vue, int port) {
		super();
		this.clientCounter = 0;
		this.entreprise = entreprise;
		this.vue = vue;
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e);
		    System.exit(1);
		}
	}
	
	/**
	 * Close the SocketServer
	 * @throws IOException if listener can't be closed
	 */
	public void close() throws IOException {
		this.listener.close();
	}
	
	/**
	 * Run the socket server
	 */
	@Override
	public void run() {
		try {
			Logger.log("Server listening ...");
			while (true) {
				this.socketOfServer = listener.accept();
				new CommunicationHandler(this.entreprise, this.socketOfServer, this.clientCounter++, this.vue).start();
				Logger.log("Client connected");
			}
		} catch (IOException e) {
			System.out.println(e);
            e.printStackTrace();
		}
		finally {
			try {
				listener.close();
			} catch (IOException e) {
				System.out.println(e);
	            e.printStackTrace();
			}
		}
	}
}
