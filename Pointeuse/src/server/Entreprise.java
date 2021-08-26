package server;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class Pointage
 * Create and manipulate a Pointage
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class Entreprise implements Serializable{
	private static final long serialVersionUID = 2726397565700100163L;
	private int portNumber;
	private String ipAddress;
	private ArrayList<Worker> employees;
	private ArrayList<Pointage> pointages;
	
	/**
	 * Create a new empty Entreprise with a default port number and IP address
	 */
	public Entreprise() {
		super();
		this.employees = new ArrayList<Worker>();
		this.pointages = new ArrayList<Pointage>();
		this.portNumber = 6666;
		this.setIpAddress("127.0.0.1");
	}
	/**
	 * Add an employee to an entreprise
	 * @param worker worker to add
	 * @throws IllegalArgumentException if the worker is already in this entreprise
	 */
	public void addEmployee(Worker worker) throws IllegalArgumentException {
		for (Worker currentWorker : employees) {
			if (currentWorker.getWorkerId() == worker.getWorkerId()) {
				throw new IllegalArgumentException("Employee with that id already exists.");
			}
		}
		employees.add(worker);
	}
	
	/**
	 * Add an employee to an entreprise with a worker id, a first name and a last name
	 * @param workerId id of the worker to add
	 * @param name first name of the worker to add
	 * @param surname last name of the worker to add
	 * @return the worker added
	 * @throws IllegalArgumentException if the worker is already in this entreprise
	 */
	public Worker addEmployee(int workerId, String name, String surname) throws IllegalArgumentException {
		for (Worker currentWorker : employees) {
			if (currentWorker.getWorkerId() == workerId) {
				throw new IllegalArgumentException("Employee with that id already exists.");
			}
		}
		Worker newWorker = new Worker(workerId, name, surname);
		employees.add(newWorker);
		return newWorker;
	}
	
	/**
	 * Search an employee with his id
	 * @param id id of the employee to search
	 * @return the employee if found and null if not found
	 */
	public Worker searchEmployeeById(int id) {
		for (Worker currentWorker : employees) {
			if (currentWorker.getWorkerId() == id) {
				return currentWorker;
			}
		}
		return null;
	}
	
	/**
	 * Search an employee with his first name
	 * @param name first name of the employee to search
	 * @return the employee if found and null if not found
	 */
	public Worker searchEmployeeByName(String name) {
		for (Worker currentWorker : employees) {
			if (currentWorker.getName() == name) {
				return currentWorker;
			}
		}
		return null;
	}
	
	/**
	 * Search an employee with his last name
	 * @param surname last name of the employee to search
	 * @return the employee if found and null if not found
	 */
	public Worker searchEmployeeBySurname(String surname) {
		for (Worker currentWorker : employees) {
			if (currentWorker.getLastName() == surname) {
				return currentWorker;
			}
		}
		return null;
	}
	
	
	/**
	 * Add a pointage to an entreprise with a worker id and a time of point
	 * @param workerId id of the worker who pointed
	 * @param timeOfPoint time of the Worker's Pointage
	 * @throws IllegalArgumentException if the worker is not found in the entreprise
	 * @return true if the pointage is added and false if not
	 */
	public boolean addPointage(int workerId, LocalDateTime timeOfPoint) throws IllegalArgumentException{
		Worker currentWorker = searchEmployeeById(workerId);
		if (currentWorker == null) {
			throw new IllegalArgumentException("Worker not found");
		}
		Pointage newPointage = new Pointage(currentWorker, timeOfPoint);
		if (currentWorker.addPointage(newPointage)) {
			this.pointages.add(newPointage);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove an employee from an entreprise
	 * @param workerId id of the worker to remove
	 * @throws IllegalArgumentException if the worker is not found in the entreprise
	 */
	public void deleteEmployee(int workerId) throws IllegalArgumentException{
		Worker currentWorker = searchEmployeeById(workerId);
		if (currentWorker != null) {
			for (int indexInEmployees = 0; indexInEmployees < this.employees.size(); indexInEmployees++) {
				if (currentWorker.getWorkerId() == this.employees.get(indexInEmployees).getWorkerId()) {
					this.employees.remove(indexInEmployees);
					return;
				}
			}
		}
		throw new IllegalArgumentException("Worker not found");
	}

	/**
	 * @return the list of employees of the entreprise
	 */
	public ArrayList<Worker> getEmployees() {
		return employees;
	}

	/**
	 * @return the list of Pointages of the entreprise
	 */
	public ArrayList<Pointage> getPointages() {
		return pointages;
	}
	
	/**
	 * @return the port number linked with this entreprise
	 */
	public int getPortNumber() {
		return portNumber;
	}
	
	/**
	 * Set a new number to the portNumber of this entreprise
	 * @param portNumber new port number
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	/**
	 * @return the IP Address linked with this entreprise
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * Set a IP Address to the ipAddress of this entreprise
	 * @param ipAddress new IP address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
