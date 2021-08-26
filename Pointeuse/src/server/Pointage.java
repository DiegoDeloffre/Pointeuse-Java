package server;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class Pointage
 * Create and manipulate a Pointage
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Pointage implements Serializable {
	
	private static final long serialVersionUID = -8775010452757991939L;
	private Worker worker;
	private LocalDateTime timeOfPoint;
	
	/**
	 * Create a Pointage with an worker and a time of point
	 * @param worker worker who pointed
	 * @param timeOfPoint time of the worker's point
	 */
	public Pointage(Worker worker, LocalDateTime timeOfPoint) {
		super();
		this.worker = worker;
		this.timeOfPoint = timeOfPoint;
	}
	
	/**
	 * Tell if a pointage is today or not
	 * @return true if the pointage is today and false if not
	 */
	public boolean isToday() {
		LocalDate dateOfPoint = LocalDate.of(timeOfPoint.getYear(), timeOfPoint.getMonthValue(), timeOfPoint.getDayOfMonth());
		if (dateOfPoint.isEqual(LocalDate.now())) {
			return true;
		}
		return false;
	}
	
	public boolean isSameDay(LocalDate toTest) {
		LocalDate dateOfPoint = LocalDate.of(timeOfPoint.getYear(), timeOfPoint.getMonthValue(), timeOfPoint.getDayOfMonth());
		if (dateOfPoint.isEqual(toTest)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the Worker linked to the Pointage
	 */
	public Worker getWorker() {
		return worker;
	}

	/**
	 * Set a new worker for a Pointage
	 * @param worker new Worker
	 */
	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	/**
	 * @return the time of point of a Pointage
	 */
	public LocalDateTime getTimeOfPoint() {
		return timeOfPoint;
	}

	/**
	 * Set a new time of point for a Pointage
	 * @param timeOfPoint new time of point
	 */
	public void setTimeOfPoint(LocalDateTime timeOfPoint) {
		this.timeOfPoint = timeOfPoint;
	}
}
