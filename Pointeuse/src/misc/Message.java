package misc;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class Message
 * Create and manipulate a message
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -7160773203314951622L;
	private int messageId;
	private LocalDateTime date;
	
	/**
	 * Create a new message with an worker messageId and a time of point date
	 * @param messageId id of the worker
	 * @param date  date of the point
	 */
	public Message(int messageId, LocalDateTime date) {
		super();
		this.messageId = messageId;
		this.date = date;
	}

	/**
	 * @return the messageId in the message
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * Set a new messageId for the message
	 * @param messageId new Id
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the date of the message
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Set a new date for the message
	 * @param date new date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
