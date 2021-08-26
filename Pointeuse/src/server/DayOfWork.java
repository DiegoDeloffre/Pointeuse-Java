package server;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Class DayOfWork
 * Create and manipulate a DayOfWork
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class DayOfWork implements Serializable{

	private static final long serialVersionUID = -8799475325145708791L;
	private int dayId;
	private LocalTime arrivalTime;
	private LocalTime departureTime;
	
	/**
	 * Create a new Day of Work with an id, an arrival time and a departure time
	 * @param dayId id of the day
	 * @param arrivalTime arrival time of the day
	 * @param departureTime departure time of the day
	 * @throws IllegalArgumentException if the id is incorrect or if the departure time is before the arrival time of the Day of Work
	 */
	public DayOfWork(int dayId, LocalTime arrivalTime, LocalTime departureTime) throws IllegalArgumentException {
		super();
		if (dayId <= 5 && dayId >= 1) {
			if (Duration.between(arrivalTime, departureTime).toMinutes() > 0 ) {
				this.dayId = dayId;
				this.arrivalTime = arrivalTime;
				this.departureTime = departureTime;
				return;
			}
			throw new IllegalArgumentException("arrivalTime is after departureTime.");
		}
		throw new IllegalArgumentException("Day Id out of range.");

	}
	
	/**
	 * @return the id of the day
	 */
	public int getDayId() {
		return dayId;
	}
	
	/**
	 * Set a new id for a Day of Work
	 * @param dayId new Id
	 * @throws IllegalArgumentException if the id is incorrect
	 */
	public void setDayId(int dayId) throws IllegalArgumentException {
		if (dayId <= 5 && dayId >= 1) {
			this.dayId = dayId;
			return;
		}
		throw new IllegalArgumentException("Day Id out of range.");
	}
	
	/**
	 * @return the arrival time of the Day of Work
	 */
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Set a new arrival time for a Day of Work
	 * @param arrivalTime new arrival time
	 * @throws IllegalArgumentException if the arrivalTime is after the departureTime of the Day of Work
	 */
	public void setArrivalTime(LocalTime arrivalTime) throws IllegalArgumentException {
		if (Duration.between(arrivalTime, this.departureTime).toMinutes() > 0 ) {
			this.arrivalTime = arrivalTime;
			return;
		}
		throw new IllegalArgumentException("arrivalTime is after departureTime.");
		
	}
	
	/**
	 * @return the departure time of the Day of Work
	 */
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	
	/**
	 * Set a new departure time for a Day of Work
	 * @param departureTime new departure time
	 * @throws IllegalArgumentException if the arrivalTime is after the departureTime of the Day of Work
	 */
	public void setDepartureTime(LocalTime departureTime) throws IllegalArgumentException {
		if (Duration.between(this.arrivalTime, departureTime).toMinutes() > 0 ) {
			this.departureTime = departureTime;
			return;
		}
		throw new IllegalArgumentException("arrivalTime is after departureTime.");
	}

	
}
