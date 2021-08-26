package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import misc.DateAndTimeHandler;
import misc.IPAddressHandler;

/**
 * Class ControleurServer
 * Controleur of the server part of the Pointeuse
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class ControleurServer implements ActionListener{

	private VueServer vue;
	private Entreprise entreprise;
	
	/**
	 * Create a controleur with a vue and an entreprise
	 * @param vue graphical interface
	 * @param entreprise entreprise linked with the controleur
	 */
	public ControleurServer(VueServer vue, Entreprise entreprise) {
		super();
		this.vue = vue;
		this.entreprise = entreprise;
	}

	/**
	 * Reaction to all the button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if(b.getText().equals("Ajouter employe")) {
			this.vue.setUpFrameAddEmployee();
		}
		if(b.getText().equals("Confirmer l'ajout")) {
			addEmployee();
		}
		
		if(b.getText().equals("Modifier employe")) {
			int workerId = getWorkerId(this.vue.getOldWorkerLastName().getText(), this.vue.getOldWorkerFirstName().getText());
			if(workerId == -1) {
				JOptionPane.showMessageDialog(this.vue, "L'employe que vous voulez modifier n'existe pas.");
			}else {
				this.vue.setUpFrameUpdateEmployee();
				updateFrameModification(workerId);
			}
			
		}
		if(b.getText().equals("Confirmer la modification")) {
			int line = getLineinTable(this.vue.getOldWorkerLastName().getText(), this.vue.getOldWorkerFirstName().getText());
			int workerId = getWorkerId(this.vue.getOldWorkerLastName().getText(), this.vue.getOldWorkerFirstName().getText());
			if(line == -1 || workerId == -1) {
				JOptionPane.showMessageDialog(this.vue, "L'employe que vous voulez modifier n'existe pas.");
			}else {
				updateEmployee(workerId,line);
			}
			
		}
		
		if(b.getText().equals("Supprimer employe")) {
			int line = getLineinTable(this.vue.getWorkerLastNameToDelete().getText(), this.vue.getWorkerFirstNameToDelete().getText());
			int workerId = getWorkerId(this.vue.getWorkerLastNameToDelete().getText(), this.vue.getWorkerFirstNameToDelete().getText());
			if(line == -1 || workerId == -1) {
				JOptionPane.showMessageDialog(this.vue, "L'employe que vous voulez supprimer n'existe pas.");
			}else {
				deleteEmployee(workerId,line);
			}
			
			
		}
		if(b.getText().equals("Modifier Adresse IP")) {
			String newIpAddress = this.vue.getIpAddressTextField().getText();
			if(newIpAddress.equals("localhost") || IPAddressHandler.isValidIPAddress(newIpAddress)) {
				this.entreprise.setIpAddress(newIpAddress);
				this.vue.repaint();
			}else {
				JOptionPane.showMessageDialog(this.vue, "L'adresse IP semble etre incorrecte. Veuillez saisir une adresse IP valide s'il vous plait.");
			}
			
		}
		if(b.getText().equals("Modifier port")) {
			String newPortNumberString = this.vue.getPortNumberTextField().getText();
			if(newPortNumberString.equals("")) {
				JOptionPane.showMessageDialog(this.vue, "Le numero de port est null. Veuillez saisir un numero de port valide s'il vous plait.");
			}else {
				int newPortNumber = Integer.valueOf(newPortNumberString);
				if (newPortNumber < 0 || newPortNumber > 0xFFFF) {
					JOptionPane.showMessageDialog(this.vue, "Le numero de port semble etre incorrect. Veuillez saisir un numero de port valide s'il vous plait.");
				}else {
					this.entreprise.setPortNumber(newPortNumber);
					try {
						Server.changeServerPort(newPortNumber);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					this.vue.repaint();
				}
			}
			
			
		}
	}
	
	

	/**
	 * Set the JTable with all the Pointages at each launch
	 */
	public void setUpPointageHistory() {
		int i = 0;
		boolean found = false;
		for(Pointage p : this.entreprise.getPointages()) {
			for (int j = 0; j < this.vue.getPointagesHistoryModel().getRowCount(); j++) {
				if(p.getWorker().getName() == (String)this.vue.getPointagesHistoryModel().getValueAt(j, 1) && p.getWorker().getLastName() == (String)this.vue.getPointagesHistoryModel().getValueAt(j, 2) && DateAndTimeHandler.formatDate(p.getTimeOfPoint()).substring(0, 11).equals(((String) this.vue.getPointagesHistoryModel().getValueAt(j, 3)).substring(0, 11))) {
					
					this.vue.getPointagesHistoryModel().setValueAt(DateAndTimeHandler.formatDate(p.getTimeOfPoint()), j, 4);
					
					String timeOfArrivalString = (this.vue.getPointagesHistoryModel().getValueAt(j, 5).toString());
					LocalTime timeOfArrival = LocalTime.of(Integer.valueOf(timeOfArrivalString.substring(0, 2)), Integer.valueOf(timeOfArrivalString.substring(3,5)));
					String timeOfDepartureString = (this.vue.getPointagesHistoryModel().getValueAt(j, 6).toString());
					LocalTime timeOfDeparture = LocalTime.of(Integer.valueOf(timeOfDepartureString.substring(0, 2)), Integer.valueOf(timeOfDepartureString.substring(3,5)));
					
					String pointTimeOfArrivalString = (this.vue.getPointagesHistoryModel().getValueAt(j, 3).toString());
					LocalTime pointTimeOfArrival = LocalTime.of(Integer.valueOf(pointTimeOfArrivalString.substring(11, 13)), Integer.valueOf(pointTimeOfArrivalString.substring(14,16)));
					String pointTimeOfDepartureString = (this.vue.getPointagesHistoryModel().getValueAt(j, 4).toString());
					LocalTime pointTimeOfDeparture = LocalTime.of(Integer.valueOf(pointTimeOfDepartureString.substring(11, 13)), Integer.valueOf(pointTimeOfDepartureString.substring(14,16)));
					long timeInBetween = Duration.between(pointTimeOfArrival, pointTimeOfDeparture).toMinutes() - Duration.between(timeOfArrival, timeOfDeparture).toMinutes();
					this.vue.getPointagesHistoryModel().setValueAt(timeInBetween, j, 7);
					int line = getLineinTable(p.getWorker().getName(), p.getWorker().getLastName());
					if(line != -1) {
						this.vue.getEmployeesListModel().setValueAt(p.getWorker().getSpareTime(), line, 3);
					}
					found = true;
				}
			}
			if(found == false) {
				LocalTime timeOfArrival = p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()).getArrivalTime();
				long timeInBetween = Duration.between(LocalTime.of(p.getTimeOfPoint().getHour(), p.getTimeOfPoint().getMinute()), timeOfArrival).toMinutes();
				
				Object [] tab = {i+1,p.getWorker().getName(),p.getWorker().getLastName(),DateAndTimeHandler.formatDate(p.getTimeOfPoint()),null,p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()-1).getArrivalTime(),p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()-1).getDepartureTime(),timeInBetween};
				this.vue.getPointagesHistoryModel().addRow(tab);
				int line = getLineinTable(p.getWorker().getName(), p.getWorker().getLastName());
				if(line != -1) {
					this.vue.getEmployeesListModel().setValueAt(p.getWorker().getSpareTime(), line, 3);
				}
				i++;
			}
			found = false;
		}
		this.vue.repaint();
	}
	
	/**
	 * Set the JTable with all the Pointages of the day at each launch
	 */
	public void setUpPointageHistoryOfTheDay() {
		int i = 0;
		boolean found = false;
		for(Pointage p : this.entreprise.getPointages()) {
			if(p.isToday()) {
				for (int j = 0; j < this.vue.getPointagesOfTheDayHistoryModel().getRowCount(); j++) {
					
					if(p.getWorker().getWorkerId() == (int)this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 0) && DateAndTimeHandler.formatDate(p.getTimeOfPoint()).substring(0, 11).equals(((String) this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 3)).substring(0, 11))) {
						this.vue.getPointagesOfTheDayHistoryModel().setValueAt(DateAndTimeHandler.formatDate(p.getTimeOfPoint()), j, 4);
						String timeOfArrivalString = (this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 5).toString());
						LocalTime timeOfArrival = LocalTime.of(Integer.valueOf(timeOfArrivalString.substring(0, 2)), Integer.valueOf(timeOfArrivalString.substring(3,5)));
						String timeOfDepartureString = (this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 6).toString());
						LocalTime timeOfDeparture = LocalTime.of(Integer.valueOf(timeOfDepartureString.substring(0, 2)), Integer.valueOf(timeOfDepartureString.substring(3,5)));
						
						String pointTimeOfArrivalString = (this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 3).toString());
						LocalTime pointTimeOfArrival = LocalTime.of(Integer.valueOf(pointTimeOfArrivalString.substring(11, 13)), Integer.valueOf(pointTimeOfArrivalString.substring(14,16)));
						String pointTimeOfDepartureString = (this.vue.getPointagesOfTheDayHistoryModel().getValueAt(j, 4).toString());
						LocalTime pointTimeOfDeparture = LocalTime.of(Integer.valueOf(pointTimeOfDepartureString.substring(11, 13)), Integer.valueOf(pointTimeOfDepartureString.substring(14,16)));
						long timeInBetween = Duration.between(pointTimeOfArrival, pointTimeOfDeparture).toMinutes() - Duration.between(timeOfArrival, timeOfDeparture).toMinutes();
						this.vue.getPointagesOfTheDayHistoryModel().setValueAt(timeInBetween, j, 7);
						found = true;
					}
				}
				if(found == false) {	
					LocalTime timeOfArrival = p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()-3).getArrivalTime();
					long timeInBetween = Duration.between(LocalTime.of(p.getTimeOfPoint().getHour(), p.getTimeOfPoint().getMinute()), timeOfArrival).toMinutes();
					Object [] tab = {i+1,p.getWorker().getName(),p.getWorker().getLastName(),DateAndTimeHandler.formatDate(p.getTimeOfPoint()),null,p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()-3).getArrivalTime(),p.getWorker().getDays().get(p.getTimeOfPoint().getDayOfWeek().getValue()-3).getDepartureTime(),timeInBetween};
					this.vue.getPointagesOfTheDayHistoryModel().addRow(tab);
					i++;
				}
				found = false;
			}
		}
		this.vue.repaint();
	}
	
	
	/**
	 * Set the JTable with all the Workers at each launch
	 */
	public void setUpEmployees() {
		int i =0;
		for(Worker w : this.entreprise.getEmployees()) {
			Object [] tab = {i+1,w.getName(),w.getLastName()};
			this.vue.getEmployeesListModel().addRow(tab);
			i++;
		}
		this.vue.repaint();
	}
	
	/**
	 * Add a new Employee to the vue and the entreprise
	 */
	private void addEmployee() {
		this.entreprise.getEmployees().size();
		int workerId = this.entreprise.getEmployees().get(this.entreprise.getEmployees().size()-1).getWorkerId()+1;
		
		String workerLastName = this.vue.getWorkerLastNameToAdd().getText();
		String workerFirstName = this.vue.getWorkerFirstNameToAdd().getText();
		Object [] tab = {workerId, workerLastName, workerFirstName};
		this.entreprise.addEmployee(workerId, workerFirstName, workerLastName);
		this.addDaysOfWork(workerId);
		
		this.vue.getEmployeesListModel().addRow(tab);
		this.vue.getFrameAdding().dispose();
		this.vue.repaint();
	}
	
	/**
	 * Add a new DayOfWork to the vue and the entreprise
	 * @param idWorker id of the worker the day is added to
	 */
	private void addDaysOfWork(int idWorker) {
		Worker w = this.entreprise.searchEmployeeById(idWorker);
		for (int i = 0; i < 5; i++) {
			if(Duration.between(LocalTime.of(Integer.valueOf((String)this.vue.getArrivalHoursAdding().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getArrivalMinutesAdding().get(i).getSelectedItem())),LocalTime.of(Integer.valueOf((String)this.vue.getDepartureHoursAdding().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getDepartureMinutesAdding().get(i).getSelectedItem()))).toMinutes() <= 0 ) {
				JOptionPane.showMessageDialog(this.vue, "L'heure d'arrivee du jour "+ (i+1) + " de la semaine est apres l'heure de depart. Un horaire par defaut est defini de 08:00 a 18:00.");
				w.addDayOfWork(i+1,LocalTime.of(8,0),LocalTime.of(18, 00));
			}else {
				w.addDayOfWork(i+1,LocalTime.of(Integer.valueOf((String)this.vue.getArrivalHoursAdding().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getArrivalMinutesAdding().get(i).getSelectedItem())),LocalTime.of(Integer.valueOf((String)this.vue.getDepartureHoursAdding().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getDepartureMinutesAdding().get(i).getSelectedItem())));
			}
		}
	}
	
	/**
	 * Get the line number of a specific worker in a JTable
	 * @param lastName last name of the worker
	 * @param firstName first name of the worker
	 * @return the line number of the Worker
	 */
	public int getLineinTable(String lastName, String firstName) {
		for(int i = 0; i < this.vue.getEmployeesListModel().getRowCount(); i++) {
			if(this.vue.getEmployeesListModel().getValueAt(i, 1).equals(lastName) && this.vue.getEmployeesListModel().getValueAt(i,2).equals(firstName)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get the Id of a specific Employee
	 * @param lastName last name of the worker
	 * @param firstName first name of the worker
	 * @return the Worker Id
	 */
	private int getWorkerId(String lastName, String firstName) {
		for(int i = 0; i < this.vue.getEmployeesListModel().getRowCount(); i++) {
			if(this.vue.getEmployeesListModel().getValueAt(i, 1).equals(lastName) && this.vue.getEmployeesListModel().getValueAt(i,2).equals(firstName)) {
				return (int) this.vue.getEmployeesListModel().getValueAt(i, 0);
			}
		}
		return -1;
	}
	
	/**
	 * Delete a Worker of the vue and the entreprise
	 * @param id id of the worker to delete
	 * @param line line where the worker is in the JTable
	 */
	private void deleteEmployee(int id,int line) {
		this.entreprise.deleteEmployee(id);
		this.vue.getEmployeesListModel().removeRow(line);
		this.vue.repaint();
	}
	
	/**
	 * Modify a Worker (first name, last name and working hours) in the entreprise
	 * @param id id of the worker to modify
	 * @param line line where the worker is in the JTable
	 */
	private void updateEmployee(int id,int line) {
		this.vue.getEmployeesListModel().setValueAt(this.vue.getWorkerLastNameToUpdate().getText(), line, 1);
		this.vue.getEmployeesListModel().setValueAt(this.vue.getWorkerFirstNameToUpdate().getText(), line, 2);
		Worker w = this.entreprise.searchEmployeeById(id);
		w.setName(this.vue.getWorkerLastNameToUpdate().getText());
		w.setLastName(this.vue.getWorkerFirstNameToUpdate().getText());
		w.getDays().clear();
		for (int i = 0; i < 5; i++) {
			if(Duration.between(LocalTime.of(Integer.valueOf((String)this.vue.getArrivalHoursModification().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getArrivalMinutesModification().get(i).getSelectedItem())),LocalTime.of(Integer.valueOf((String)this.vue.getDepartureHoursModification().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getDepartureMinutesModification().get(i).getSelectedItem()))).toMinutes() <= 0 ) {
				JOptionPane.showMessageDialog(this.vue, "L'heure d'arrivee du jour "+ (i+1) + " de la semaine est apres l'heure de depart. Un horaire par defaut est defini de 08:00 a 18:00.");
				w.addDayOfWork(i+1,LocalTime.of(8,0),LocalTime.of(18, 00));
			}else {
				w.addDayOfWork(i+1,LocalTime.of(Integer.valueOf((String)this.vue.getArrivalHoursModification().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getArrivalMinutesModification().get(i).getSelectedItem())),LocalTime.of(Integer.valueOf((String)this.vue.getDepartureHoursModification().get(i).getSelectedItem()), Integer.valueOf((String)this.vue.getDepartureMinutesModification().get(i).getSelectedItem())));
			}
		}
		this.vue.getFrameUpdate().dispose();
		this.vue.repaint();
	}
	
	/**
	 * Modify the vue after a Worker modification
	 * @param id id of the worker to modify
	 */
	private void updateFrameModification(int id) {
		Worker w = this.entreprise.searchEmployeeById(id);
		this.vue.getWorkerLastNameToUpdate().setText(w.getName());
		this.vue.getWorkerFirstNameToUpdate().setText(w.getLastName());
		String arrivalHoursModification;
		String arrivalMinutesModification;
		String departureHoursModification;
		String departureMinutesModification;
		for (int i = 0; i < 5; i++) {
			if(Integer.toString(w.getDays().get(i).getArrivalTime().getHour()).length() == 1) {
				arrivalHoursModification = "0"+Integer.toString(w.getDays().get(i).getArrivalTime().getHour());
			}else {
				arrivalHoursModification = Integer.toString(w.getDays().get(i).getArrivalTime().getHour());
			}
			this.vue.getArrivalHoursModification().get(i).setSelectedItem(arrivalHoursModification);
			
			
			if(Integer.toString(w.getDays().get(i).getArrivalTime().getMinute()).length() == 1) {
				arrivalMinutesModification = "0"+Integer.toString(w.getDays().get(i).getArrivalTime().getMinute());
			}else {
				arrivalMinutesModification = Integer.toString(w.getDays().get(i).getArrivalTime().getMinute());
			}
			this.vue.getArrivalMinutesModification().get(i).setSelectedItem(arrivalMinutesModification);
			
			
			if(Integer.toString(w.getDays().get(i).getDepartureTime().getHour()).length() == 1) {
				departureHoursModification = "0"+Integer.toString(w.getDays().get(i).getDepartureTime().getHour());
			}else {
				departureHoursModification = Integer.toString(w.getDays().get(i).getDepartureTime().getHour());
			}
			this.vue.getDepartureHoursModification().get(i).setSelectedItem(departureHoursModification);
			
			
			if(Integer.toString(w.getDays().get(i).getDepartureTime().getMinute()).length() == 1) {
				departureMinutesModification = "0"+Integer.toString(w.getDays().get(i).getDepartureTime().getMinute());
			}else {
				departureMinutesModification = Integer.toString(w.getDays().get(i).getDepartureTime().getMinute());
			}
			this.vue.getDepartureMinutesModification().get(i).setSelectedItem(departureMinutesModification);
		}
		this.vue.getFrameUpdate().repaint();
		this.vue.repaint();
		
	}
	
}
