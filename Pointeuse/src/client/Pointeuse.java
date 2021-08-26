package client;

import java.io.Serializable;

/**
 * Class Pointeuse
 * Create and manipulate a Pointeuse that can communicate through sockets with a Server
 * with the portNumber and the IP address passed in parameters
 * 
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 */
public class Pointeuse implements Serializable{

	private static final long serialVersionUID = 1771069444381896718L;
	private int portNumber;
	private String ipAddress;
	
	/**
	 * Create a Pointeuse with a port number and an IP address
	 * @param portNumber port number of the client
	 * @param ipAddress IP address of the client
	 */
	public Pointeuse(int portNumber, String ipAddress) {
		super();
		this.portNumber = portNumber;
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the portNumber of the Pointeuse
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * Set a new portNumber for the Pointeuse
	 * @param portNumber new port number
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * @return the IPAddress of the Pointeuse
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Set a new IPAdress for the Pointeuse
	 * @param ipAddress new IP address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
