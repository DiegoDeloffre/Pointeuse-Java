package server;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Class Worker
 * Create and manipulate a worker
 * 
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 */
public class Worker implements Serializable{
	private static final long serialVersionUID = 3898322322183722260L;
	private int workerId;
	private String name;
	private String lastName;
	private ArrayList<DayOfWork> days;
	private ArrayList<Pointage> pointages;
	private long spareTime;
	
	/**
	 * Create a new Worker with an Id, a first name and a lastName
	 * @param workerId id of the worker
	 * @param name first name of the worker
	 * @param lastName last name of the worker
	 */
	public Worker(int workerId, String name, String lastName) {
		super();
		this.workerId = workerId;
		this.name = name;
		this.lastName = lastName;
		this.days = new ArrayList<DayOfWork>();
		this.pointages = new ArrayList<Pointage>();
		this.spareTime = 0;
	}

	/**
	 * Add a day of work to a Worker
	 * @param day dayOfWork of a Worker
	 * @throws IllegalArgumentException if the worker already has a day set 
	 */
	public void addDayOfWork(DayOfWork day) throws IllegalArgumentException {
		for (DayOfWork currentDay : this.days) {
			if (currentDay.getDayId() == day.getDayId()) {
				throw new IllegalArgumentException("A DayOfWork object already exists for this day.");
			}
		}
		this.days.add(day);
	}
	
	/**
	 * Add a day of work with a dayID, an arrival time and a departure time to a Worker
	 * @param dayId id of the worker
	 * @param arrivalTime arrival time of the day of work
	 * @param departureTime departure time of the day of work
	 * @throws IllegalArgumentException if the worker already has a day set
	 */
	public void addDayOfWork(int dayId, LocalTime arrivalTime, LocalTime departureTime) throws IllegalArgumentException {
		for (DayOfWork day : this.days) {
			if (day.getDayId() == dayId) {
				throw new IllegalArgumentException("A DayOfWork object already exists for this day.");
			}
		}
		
		DayOfWork newDay = new DayOfWork(dayId, arrivalTime, departureTime);
		this.days.add(newDay);
		
	}
	
	/**
	 * Add a new pointage to a Worker
	 * @param pointage pointage to add
	 * @throws IllegalArgumentException if the employee already pointed in an out this day
	 * @return true if the pointage is added and false if not
	 */
	public boolean addPointage(Pointage pointage) throws IllegalArgumentException{
		int pointagesCounter = 0;
		for (Pointage currentPointage : pointages) {
			if (currentPointage.isSameDay(LocalDate.of(pointage.getTimeOfPoint().getYear(), pointage.getTimeOfPoint().getMonthValue(), pointage.getTimeOfPoint().getDayOfMonth()))) {
				pointagesCounter++;
			}
		}
		if (pointagesCounter < 2) {
			this.pointages.add(pointage);
			DayOfWork today = searchDayOfWork(pointage.getTimeOfPoint().getDayOfWeek().getValue());
			LocalTime pointageTime = LocalTime.of(pointage.getTimeOfPoint().getHour(), pointage.getTimeOfPoint().getMinute());
			if (pointagesCounter == 0) {
				this.spareTime += Duration.between(pointageTime, today.getArrivalTime()).toMinutes();
			}
			if (pointagesCounter == 1) {
				this.spareTime += Duration.between(today.getDepartureTime(), pointageTime).toMinutes();
			}
			return true;
		}
		JOptionPane.showMessageDialog(null,"L'employe a deja pointe deux fois aujourd'hui.");
		return false;
	}
	
	/**
	 * Search a specific day of work with a dayID
	 * @param dayId id of the day to look for
	 * @return the day if found and null if not found
	 */
	public DayOfWork searchDayOfWork(int dayId) {
		for (DayOfWork day : this.days) {
			if (day.getDayId() == dayId) {
				return day;
			}
		}
		return null;
	}

	/**
	 * @return the worker id
	 */
	public int getWorkerId() {
		return workerId;
	}
	
	/**
	 * Set a new worker id for the Worker
	 * @param workerId new worker id
	 */
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	/**
	 * @return a worker first name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a new first name for the worker
	 * @param name new first name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a worker last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set a new last name for the worker
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the days of work of an employee
	 */
	public ArrayList<DayOfWork> getDays() {
		return days;
	}

	/**
	 * @return the spare time of an employee
	 */
	public long getSpareTime() {
		return spareTime;
	}

	/**
	 * Set the spare time of an employee
	 * @param spareTime new spare time
	 */
	public void setSpareTime(int spareTime) {
		this.spareTime = spareTime;
	}
}
