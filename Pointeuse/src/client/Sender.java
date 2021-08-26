package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import misc.Logger;
import misc.Message;

/**
 * Class Sender
 * Create and manipulate a Sender to communicate through sockets
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Sender extends Thread  {

	private VuePointeuse vuePointeuse;
	private int idWorker;
	private LocalDateTime timeOfPoint;

	public Sender(VuePointeuse vuePointeuse, int idWorker, LocalDateTime timeOfPoint) {
		super();
		this.vuePointeuse = vuePointeuse;
		this.idWorker = idWorker;
		this.timeOfPoint = timeOfPoint;
	}
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		boolean sent = false;
		while (!sent) {
			try {
				Socket socket = null;
				socket = new Socket(this.vuePointeuse.getPointeuse().getIpAddress(), this.vuePointeuse.getPointeuse().getPortNumber());
				
				Logger.log("Connected!");

		        // get the output stream from the socket.
		        OutputStream outputStream = null;
				try {
					outputStream = socket.getOutputStream();
				} catch (IOException e2) {
					e2.printStackTrace();
					return;
				}
				ObjectOutputStream objectOutputStream = null;
		        // create an object output stream from the output stream so we can send an object through it
		        try {
		        	objectOutputStream = new ObjectOutputStream(outputStream);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
		        
				
				Message m = new Message(Integer.valueOf(idWorker), this.timeOfPoint);
				Logger.log("Sending messages to the ServerSocket");
				
				try {
					objectOutputStream.writeObject(m);
				} catch (IOException e2) {
					e2.printStackTrace();
					return;
				}
				
				Logger.log("Closing socket and terminating program.");
		        try {
					socket.close();
					sent = true;
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			} catch (UnknownHostException e) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
