package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import misc.DateAndTimeHandler;
import misc.Logger;
import misc.Message;

/**
 * Class CommunicationHandler
 * Create and manipulate a Communication Handler
 * Handle all the communications between the Client and the Server
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class CommunicationHandler extends Thread {
	private Entreprise entreprise;
	private Socket socketOfServer;
	private int clientId;
	private VueServer vue;
	
	/**
	 * Create a new Communication Handler with an entreprise, a SocketServer, a client id and a vue
	 * @param entreprise entreprise linked with the server
	 * @param socketOfServer the socket of the server used to communicate
	 * @param clientId id of the client connected
	 * @param vue graphical interface
	 */
	public CommunicationHandler(Entreprise entreprise, Socket socketOfServer, int clientId,VueServer vue) {
		super();
		this.vue = vue;
		this.entreprise = entreprise;
		this.socketOfServer = socketOfServer;
		this.clientId = clientId;
		Logger.log("New connection from " + this.clientId + " at " + this.socketOfServer);
	}
	
	/**
	 * Run the communication Handler
	 */
	@Override
	public void run() {
		InputStream inputStream = null;
		ObjectInputStream objectInputStream = null;
		Message message = null;
		try {
			while (true) {
				inputStream = socketOfServer.getInputStream();
				objectInputStream = new ObjectInputStream(inputStream);
				message = (Message)objectInputStream.readObject();
				if(this.entreprise.searchEmployeeById(message.getMessageId()) == null) {
					JOptionPane.showMessageDialog(this.vue, "L'employe qui vient de pointer n'est pas inscrit dans l'entreprise.");
				}else {
					if (this.entreprise.addPointage(this.entreprise.searchEmployeeById(message.getMessageId()).getWorkerId(), message.getDate())) {
						int line = this.vue.getControleur().getLineinTable(this.entreprise.searchEmployeeById(message.getMessageId()).getName(), this.entreprise.searchEmployeeById(message.getMessageId()).getLastName());
						System.out.println(line);
						if(line != -1) {
							this.vue.getEmployeesListModel().setValueAt(this.entreprise.searchEmployeeById(message.getMessageId()).getSpareTime(), line, 3);
						}
						this.receivePointage(this.entreprise.searchEmployeeById(message.getMessageId()).getWorkerId(), message.getDate());
						Logger.log("New pointage from " + entreprise.searchEmployeeById(message.getMessageId()).getLastName() + " " + entreprise.searchEmployeeById(message.getMessageId()).getName() + " at " + message.getDate());
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException e) {
            
        }
	}
	
	/**
	 * Reception of a Pointage communication 
	 * @param id id of the worker who pointed
	 * @param date time of the worker's point
	 */
	public void receivePointage(int id, LocalDateTime date) {
		this.resetTab1();
		for(int i = 0; i < this.vue.getPointagesHistoryModel().getRowCount(); i++) {
			if(this.vue.getPointagesHistoryModel().getValueAt(i, 0).equals(id) && this.vue.getPointagesHistoryModel().getValueAt(i, 4)==(null)) {
				this.vue.getLabelWorkerName().setText(this.vue.getPointagesHistoryModel().getValueAt(i, 1) +" "+this.vue.getPointagesHistoryModel().getValueAt(i, 2));
				this.vue.getArrivalTime().setText(this.vue.getPointagesHistoryModel().getValueAt(i, 3).toString());
				
				this.vue.getDepartureTime().setText(DateAndTimeHandler.formatDate(date));
				Worker w = this.entreprise.searchEmployeeById(id);
				this.vue.getSpareTime().setText(Long.toString(w.getSpareTime()));
				this.addPointage(id, date);
				this.vue.getTabs().setSelectedIndex(0);
				this.vue.repaint();
				return;
			}
		}
		Worker w = this.entreprise.searchEmployeeById(id);
		this.vue.getLabelWorkerName().setText(w.getLastName() +" "+w.getName());
		this.vue.getArrivalTime().setText(DateAndTimeHandler.formatDate(date));
		this.vue.getSpareTime().setText(Long.toString(w.getSpareTime()));
		this.addPointage(id, date);
		this.vue.getTabs().setSelectedIndex(0);
		this.vue.repaint();
	}
	
	/**
	 * Add the pointage to the entreprise and the vue
	 * @param id id of the worker who pointed
	 * @param date time of the worker's point
	 */
	private void addPointage(int id, LocalDateTime date) {
		for(int i = 0; i < this.vue.getPointagesHistoryModel().getRowCount(); i++) {
			if(this.vue.getPointagesHistoryModel().getValueAt(i, 0).equals(id) && this.vue.getPointagesHistoryModel().getValueAt(i, 4)==(null)) {
				this.vue.getPointagesHistoryModel().setValueAt(DateAndTimeHandler.formatDate(date), i, 4);
				String timeOfArrivalString = (this.vue.getPointagesHistoryModel().getValueAt(i, 5).toString());
				LocalTime timeOfArrival = LocalTime.of(Integer.valueOf(timeOfArrivalString.substring(0, 2)), Integer.valueOf(timeOfArrivalString.substring(3,5)));
				String timeOfDepartureString = (this.vue.getPointagesHistoryModel().getValueAt(i, 6).toString());
				LocalTime timeOfDeparture = LocalTime.of(Integer.valueOf(timeOfDepartureString.substring(0, 2)), Integer.valueOf(timeOfDepartureString.substring(3,5)));
				
				String pointTimeOfArrivalString = (this.vue.getPointagesHistoryModel().getValueAt(i, 3).toString());
				LocalTime pointTimeOfArrival = LocalTime.of(Integer.valueOf(pointTimeOfArrivalString.substring(11, 13)), Integer.valueOf(pointTimeOfArrivalString.substring(14,16)));
				String pointTimeOfDepartureString = (this.vue.getPointagesHistoryModel().getValueAt(i, 4).toString());
				LocalTime pointTimeOfDeparture = LocalTime.of(Integer.valueOf(pointTimeOfDepartureString.substring(11, 13)), Integer.valueOf(pointTimeOfDepartureString.substring(14,16)));
				long timeInBetween = Duration.between(pointTimeOfArrival, pointTimeOfDeparture).toMinutes() - Duration.between(timeOfArrival, timeOfDeparture).toMinutes();
				this.vue.getPointagesHistoryModel().setValueAt(timeInBetween, i, 7);
				if(date.toLocalDate().isEqual(LocalDate.now()) && this.vue.getPointagesOfTheDayHistoryModel().getValueAt(i, 0).equals(id) && this.vue.getPointagesOfTheDayHistoryModel().getValueAt(i, 4)==(null)) {
					this.vue.getPointagesOfTheDayHistoryModel().setValueAt(DateAndTimeHandler.formatDate(date), i, 4);
					this.vue.getPointagesOfTheDayHistoryModel().setValueAt(timeInBetween, i, 7);
				}
				this.vue.repaint();
				return;
			}
		}
		
		Worker w = this.entreprise.searchEmployeeById(id);
		LocalTime timeOfArrival = w.getDays().get(date.getDayOfWeek().getValue()-3).getArrivalTime();
		long timeInBetween = Duration.between(LocalTime.of(date.getHour(), date.getMinute()), timeOfArrival).toMinutes();
		Object [] tab = {id,w.getName(),w.getLastName(),DateAndTimeHandler.formatDate(date),null,w.getDays().get(date.getDayOfWeek().getValue()-3).getArrivalTime(),w.getDays().get(date.getDayOfWeek().getValue()-3).getDepartureTime(),timeInBetween};
		this.vue.getPointagesHistoryModel().addRow(tab);
		if(date.toLocalDate().isEqual(LocalDate.now())) {
			this.vue.getPointagesOfTheDayHistoryModel().addRow(tab);
		}
		this.vue.repaint();
		
	}
	
	/**
	 * Reset the first tab of the vue with default values
	 */
	private void resetTab1() {
		this.vue.getLabelWorkerName().setText("Nom Prenom");
		this.vue.getArrivalTime().setText("Inconnu");
		this.vue.getDepartureTime().setText("Inconnu");
		this.vue.getSpareTime().setText("Inconnu");		
	}
	
	
	
}
