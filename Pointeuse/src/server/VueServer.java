package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Class VueServer
 * Graphical interface of the server part of the Pointeuse
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class VueServer extends JPanel{
	private static final long serialVersionUID = 8612853019699274249L;
	private String[] hourValues = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	private String[] minuteValues = {"00","05","10","15","20","25","30","35","40","45","50","55"};
	
	private List<JComboBox<String[]>> arrivalHoursAdding = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> departureHoursAdding = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> arrivalHoursModification = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> departureHoursModification = new ArrayList<JComboBox<String[]>>();
	
	private List<JComboBox<String[]>> arrivalMinutesAdding = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> departureMinutesAdding = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> arrivalMinutesModification = new ArrayList<JComboBox<String[]>>();
	private List<JComboBox<String[]>> departureMinutesModification = new ArrayList<JComboBox<String[]>>();
	
	private JTabbedPane tabs;
	
	//tab1
	private JLabel labelWorkerName; 
	private JLabel arrivalTime;
	private JLabel departureTime;
	private JLabel spareTime;
	
	// tab2
	private DefaultTableModel pointagesHistoryModel;
	private DefaultTableModel pointagesOfTheDayHistoryModel;
	
	//tab4
	private JTextField portNumberTextField;
	private JTextField ipAddressTextField;
	
	//tab3
	private DefaultTableModel employeesListModel;
	private JTextField oldWorkerLastName;
	private JTextField oldWorkerFirstName;
	private JTextField workerLastNameToDelete;
	private JTextField workerFirstNameToDelete;
	
	//frameAdding
	private JFrame frameAdding;
	private JTextField workerLastNameToAdd;
	private JTextField workerFirstNameToAdd;
	
	//frame Modif
	private JFrame frameUpdate;
	private JTextField workerLastNameToUpdate;
	private JTextField workerFirstNameToUpdate;
	
	private ControleurServer controleur;
	private Entreprise entreprise;
	
	
	/**
	 * Create a new VueServer
	 * @param eCorp entreprise linked with the vue
	 */
	public VueServer(Entreprise eCorp) {
		this.setUpTable();
		this.entreprise = eCorp;
		this.controleur = new ControleurServer(this,this.entreprise);
		
		this.setLayout(new BorderLayout());
		this.tabs = new JTabbedPane(SwingConstants.TOP);

	    JPanel tab1 = new JPanel();
	    tab1.setLayout(new BoxLayout(tab1, BoxLayout.PAGE_AXIS));
	    tab1.setPreferredSize(new Dimension(500, 150));
	    tabs.addTab("Recepteur de pointage", tab1);
	    
	    tab1.setLayout(new BoxLayout(tab1, BoxLayout.PAGE_AXIS));
	    JLabel titleTab1 = new JLabel("Recepteur de pointage");
	    titleTab1.setAlignmentX(CENTER_ALIGNMENT);
	    tab1.add(titleTab1);
	    
	    JPanel subPanel1Tab1 = new JPanel(new FlowLayout());
	    tab1.add(subPanel1Tab1);
	    JLabel subPanel1Label1 = new JLabel("Employe : ");
	    this.labelWorkerName = new JLabel("Nom Prenom");
	    subPanel1Tab1.add(subPanel1Label1);
	    subPanel1Tab1.add(labelWorkerName);
	    
	    JPanel subPanel2Tab1 = new JPanel(new FlowLayout());
	    tab1.add(subPanel2Tab1);
	    JLabel subPanel2Label1 = new JLabel("Heure debut : ");
	    this.arrivalTime = new JLabel("Inconnu");
	    subPanel2Tab1.add(subPanel2Label1);
	    subPanel2Tab1.add(arrivalTime);
	    
	    JPanel subPanel3Tab1 = new JPanel(new FlowLayout());
	    tab1.add(subPanel3Tab1);
	    JLabel subPanel3Label1 = new JLabel("Heure Fin : ");
	    this.departureTime = new JLabel("Inconnu");
	    subPanel3Tab1.add(subPanel3Label1);
	    subPanel3Tab1.add(departureTime);
	    
	    JPanel subPanel4Tab1 = new JPanel(new FlowLayout());
	    tab1.add(subPanel4Tab1);
	    JLabel subPanel4Label1 = new JLabel("Heure Supplementaires ou manquantes : ");
	    this.spareTime = new JLabel("Inconnu");
	    subPanel4Tab1.add(subPanel4Label1);
	    subPanel4Tab1.add(spareTime);

	    
	    
	    JPanel tab2 = new JPanel();
	    tab2.setLayout(new BoxLayout(tab2, BoxLayout.PAGE_AXIS));
	    tabs.addTab("Historique des pointages", tab2);
	    
	    JTabbedPane tab2TabbedPane = new JTabbedPane(SwingConstants.TOP);
	    
	    JPanel subtab1Tab2 = new JPanel();
	    subtab1Tab2.setLayout(new BoxLayout(subtab1Tab2, BoxLayout.PAGE_AXIS));
	    JLabel titleTab2 = new JLabel("Historique global");
	    titleTab2.setAlignmentX(CENTER_ALIGNMENT);
	    subtab1Tab2.add(titleTab2);
	    
	    String[] columnNames = {"ID", "Nom", "Prenom", "Debut journee", "Fin journee", "Debut journee prevu", "Fin journee prevu", "Heures Sup/Manq"};
	    this.pointagesHistoryModel = new DefaultTableModel (columnNames,0);
	    JTable pointagesHistory = new JTable(this.pointagesHistoryModel);
	    
	    JScrollPane pointagesHistoryScrollPane = new JScrollPane(pointagesHistory);
	    pointagesHistoryScrollPane.setPreferredSize(new Dimension(950, 400));
	    subtab1Tab2.add(pointagesHistoryScrollPane);
	    tab2TabbedPane.addTab("Historique global",subtab1Tab2);
	    
	    
	    JPanel subtab2Tab2 = new JPanel();
	    subtab2Tab2.setLayout(new BoxLayout(subtab2Tab2, BoxLayout.PAGE_AXIS));
	    JLabel title2Tab2 = new JLabel("Historique de la journee");
	    title2Tab2.setAlignmentX(CENTER_ALIGNMENT);
	    subtab2Tab2.add(title2Tab2);
	    
	    this.pointagesOfTheDayHistoryModel = new DefaultTableModel (columnNames,0);
	    JTable pointagesOfTheDayHistory = new JTable(this.pointagesOfTheDayHistoryModel);
	    
	    JScrollPane pointagesOfTheDayHistoryScrollPane = new JScrollPane(pointagesOfTheDayHistory);
	    pointagesHistoryScrollPane.setPreferredSize(new Dimension(950, 400));
	    subtab2Tab2.add(pointagesOfTheDayHistoryScrollPane);
	    tab2TabbedPane.addTab("Historique de la journee",subtab2Tab2);
	    
	    tab2.add(tab2TabbedPane);
	    
	    
	    
	    JPanel tab3 = new JPanel();
	    tab3.setLayout(new BoxLayout(tab3, BoxLayout.PAGE_AXIS));
	    tabs.addTab("Gestion des employes", tab3);
	    
	    JLabel titleTab3 = new JLabel("Gestion des employes");
	    titleTab3.setAlignmentX(CENTER_ALIGNMENT);
	    tab3.add(titleTab3);
	    
	    String[] columnNames2 = {"ID", "Nom", "Prenom","Heures Sup/Manq"};
	    this.employeesListModel = new DefaultTableModel (columnNames2,0);
	    JTable employeesList = new JTable(employeesListModel);
	    tab3.add(new JScrollPane(employeesList));
	    
	    JPanel subPanelAdding = new JPanel();
	    tab3.add(subPanelAdding);
	    JButton buttonAddEmployee = new JButton("Ajouter employe");
	    buttonAddEmployee.addActionListener(controleur);
	    subPanelAdding.add(buttonAddEmployee);
	    
	    JPanel subPanelUpdate = new JPanel();
	    tab3.add(subPanelUpdate);
	    this.oldWorkerLastName = new JTextField("Nom");
	    this.oldWorkerLastName.setPreferredSize(new Dimension(150, 25));
	    this.oldWorkerLastName.setAlignmentY(CENTER_ALIGNMENT);
	    subPanelUpdate.add(oldWorkerLastName);
	    this.oldWorkerFirstName = new JTextField("Prenom");
	    this.oldWorkerFirstName.setPreferredSize(new Dimension(150, 25));
	    this.oldWorkerFirstName.setAlignmentY(CENTER_ALIGNMENT);
	    subPanelUpdate.add(oldWorkerFirstName);
	    JButton buttonUpdateEmployee = new JButton("Modifier employe");
	    buttonUpdateEmployee.addActionListener(controleur);
	    subPanelUpdate.add(buttonUpdateEmployee);
	    
	    JPanel subPanelDelete = new JPanel();
	    tab3.add(subPanelDelete);
	    this.workerLastNameToDelete = new JTextField("Nom");
	    this.workerLastNameToDelete.setPreferredSize(new Dimension(150, 25));
	    this.workerLastNameToDelete.setAlignmentY(CENTER_ALIGNMENT);
	    subPanelDelete.add(workerLastNameToDelete);
	    this.workerFirstNameToDelete = new JTextField("Prenom");
	    this.workerFirstNameToDelete.setPreferredSize(new Dimension(150, 25));
	    this.workerFirstNameToDelete.setAlignmentY(CENTER_ALIGNMENT);
	    subPanelDelete.add(workerFirstNameToDelete);
	    JButton buttonDeleteEmployee = new JButton("Supprimer employe");
	    buttonDeleteEmployee.addActionListener(controleur);
	    subPanelDelete.add(buttonDeleteEmployee);
	    
	    
	    
	    JPanel tab4 = new JPanel();
	    tab4.setLayout(new BoxLayout(tab4, BoxLayout.PAGE_AXIS));
	    tabs.addTab("Parametres du serveur", tab4);
	    
	    JLabel titleTab4 = new JLabel("Parametres du serveur");
	    titleTab4.setAlignmentX(CENTER_ALIGNMENT);
	    tab4.add(titleTab4);
	    
	    JPanel subPanel1Tab4 = new JPanel(new FlowLayout());
	    subPanel1Tab4.setAlignmentX(CENTER_ALIGNMENT);
	    tab4.add(subPanel1Tab4);
	    JLabel subPanel1Tab4Label1 = new JLabel("Adresse IP : ");
	    subPanel1Tab4Label1.setAlignmentX(CENTER_ALIGNMENT);
	    subPanel1Tab4.add(subPanel1Tab4Label1);
	    this.ipAddressTextField = new JTextField(this.entreprise.getIpAddress());
	    this.ipAddressTextField.setPreferredSize(new Dimension(200, 30));
	    subPanel1Tab4.add(ipAddressTextField);
	    JButton buttonUpdateIpAddress = new JButton("Modifier Adresse IP");
	    buttonUpdateIpAddress.addActionListener(this.controleur);
	    subPanel1Tab4.add(buttonUpdateIpAddress);
	    
	    
	    
	    JPanel subPanel2Tab4 = new JPanel(new FlowLayout());
	    subPanel2Tab4.setAlignmentX(CENTER_ALIGNMENT);
	    tab4.add(subPanel2Tab4);
	    JLabel subPanel2Tab4Label1 = new JLabel("Port");
	    subPanel2Tab4Label1.setAlignmentX(CENTER_ALIGNMENT);
	    subPanel2Tab4.add(subPanel2Tab4Label1);
	    this.portNumberTextField = new JTextField(Integer.toString(this.entreprise.getPortNumber()));
	    this.portNumberTextField.setPreferredSize(new Dimension(200, 30));
	    subPanel2Tab4.add(portNumberTextField);
	    JButton buttonUpdatePortNumber = new JButton("Modifier port");
	    buttonUpdatePortNumber.addActionListener(this.controleur);
	    subPanel2Tab4.add(buttonUpdatePortNumber);
	    

	    tabs.setOpaque(true);
	    this.add(tabs);
	    this.controleur.setUpEmployees();
		this.controleur.setUpPointageHistory();
		this.controleur.setUpPointageHistoryOfTheDay();
	}
	
	
	/**
	 * Set up a new frame to add a new employee
	 */
	public void setUpFrameAddEmployee() {
		this.frameAdding = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		this.frameAdding.setPreferredSize(new Dimension(600, 300));
		this.frameAdding.add(panel);
		
		JLabel frameTitle = new JLabel("Ajouter un employe");
		frameTitle.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(frameTitle);
		
		JPanel workerName = new JPanel(new FlowLayout());
		panel.add(workerName);
		this.workerLastNameToAdd = new JTextField("Nom");
		this.workerFirstNameToAdd = new JTextField("Prenom");
		this.workerLastNameToAdd.setPreferredSize(new Dimension(150, 25));
		this.workerFirstNameToAdd.setPreferredSize(new Dimension(150, 25));
		workerName.add(this.workerLastNameToAdd);
		workerName.add(this.workerFirstNameToAdd);
		
		
		JPanel Monday = new JPanel();
		Monday.setLayout(new GridLayout(1,9));
		Monday.add(new JLabel("Lundi"));
		Monday.add(new JLabel("Arrivee : "));
		Monday.add(this.arrivalHoursAdding.get(0));
		Monday.add(new JLabel("H"));
		Monday.add(this.arrivalMinutesAdding.get(0));
		Monday.add(new JLabel("Depart : "));
		Monday.add(this.departureHoursAdding.get(0));
		Monday.add(new JLabel("H"));
		Monday.add(this.departureMinutesAdding.get(0));
		panel.add(Monday);
		
		JPanel Tuesday = new JPanel();
		Tuesday.setLayout(new GridLayout(1,9));
		Tuesday.add(new JLabel("Mardi"));
		Tuesday.add(new JLabel("Arrivee : "));
		Tuesday.add(this.arrivalHoursAdding.get(1));
		Tuesday.add(new JLabel("H"));
		Tuesday.add(this.arrivalMinutesAdding.get(1));
		Tuesday.add(new JLabel("Depart : "));
		Tuesday.add(this.departureHoursAdding.get(1));
		Tuesday.add(new JLabel("H"));
		Tuesday.add(this.departureMinutesAdding.get(1));
		panel.add(Tuesday);
		
		JPanel Wednesday = new JPanel();
		Wednesday.setLayout(new GridLayout(1,9));
		Wednesday.add(new JLabel("Mercredi"));
		Wednesday.add(new JLabel("Arrivee : "));
		Wednesday.add(this.arrivalHoursAdding.get(2));
		Wednesday.add(new JLabel("H"));
		Wednesday.add(this.arrivalMinutesAdding.get(2));
		Wednesday.add(new JLabel("Depart : "));
		Wednesday.add(this.departureHoursAdding.get(2));
		Wednesday.add(new JLabel("H"));
		Wednesday.add(this.departureMinutesAdding.get(2));
		panel.add(Wednesday);
		
		JPanel Thursday = new JPanel();
		Thursday.setLayout(new GridLayout(1,9));
		Thursday.add(new JLabel("Jeudi"));
		Thursday.add(new JLabel("Arrivee : "));
		Thursday.add(this.arrivalHoursAdding.get(3));
		Thursday.add(new JLabel("H"));
		Thursday.add(this.arrivalMinutesAdding.get(3));
		Thursday.add(new JLabel("Depart : "));
		Thursday.add(this.departureHoursAdding.get(3));
		Thursday.add(new JLabel("H"));
		Thursday.add(this.departureMinutesAdding.get(3));
		panel.add(Thursday);
		
		JPanel Friday = new JPanel();
		Friday.setLayout(new GridLayout(1,9));
		Friday.add(new JLabel("Vendredi"));
		Friday.add(new JLabel("Arrivee : "));
		Friday.add(this.arrivalHoursAdding.get(4));
		Friday.add(new JLabel("H"));
		Friday.add(this.arrivalMinutesAdding.get(4));
		Friday.add(new JLabel("Depart : "));
		Friday.add(this.departureHoursAdding.get(4));
		Friday.add(new JLabel("H"));
		Friday.add(this.departureMinutesAdding.get(4));
		panel.add(Friday);
		
		JButton buttonConfirmAdding = new JButton("Confirmer l'ajout");
		buttonConfirmAdding.setAlignmentX(CENTER_ALIGNMENT);
		buttonConfirmAdding.addActionListener(this.controleur);
		panel.add(buttonConfirmAdding);
		
		this.frameAdding.pack();
		this.frameAdding.setLocationByPlatform(true);
		this.frameAdding.setVisible(true);
		this.frameAdding.requestFocus();
	}
	
	/**
	 * Set up a new frame to modify an existing employee
	 */
	public void setUpFrameUpdateEmployee() {
		this.frameUpdate = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		this.frameUpdate.setPreferredSize(new Dimension(600, 300));
		this.frameUpdate.add(panel);
		
		JLabel frameTitle = new JLabel("Modifier un employe");
		frameTitle.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(frameTitle);
		
		JPanel workerName = new JPanel(new FlowLayout());
		panel.add(workerName);
		this.workerLastNameToUpdate = new JTextField("Nom");
		this.workerFirstNameToUpdate = new JTextField("Prenom");
		this.workerLastNameToUpdate.setPreferredSize(new Dimension(150, 25));
		this.workerFirstNameToUpdate.setPreferredSize(new Dimension(150, 25));
		workerName.add(this.workerLastNameToUpdate);
		workerName.add(this.workerFirstNameToUpdate);
		
		
		JPanel Monday = new JPanel();
		Monday.setLayout(new GridLayout(1,9));
		Monday.add(new JLabel("Lundi"));
		Monday.add(new JLabel("Arrivee : "));
		Monday.add(this.arrivalHoursModification.get(0));
		Monday.add(new JLabel("H"));
		Monday.add(this.arrivalMinutesModification.get(0));
		Monday.add(new JLabel("Depart : "));
		Monday.add(this.departureHoursModification.get(0));
		Monday.add(new JLabel("H"));
		Monday.add(this.departureMinutesModification.get(0));
		panel.add(Monday);
		
		JPanel Tuesday = new JPanel();
		Tuesday.setLayout(new GridLayout(1,9));
		Tuesday.add(new JLabel("Mardi"));
		Tuesday.add(new JLabel("Arrivee : "));
		Tuesday.add(this.arrivalHoursModification.get(1));
		Tuesday.add(new JLabel("H"));
		Tuesday.add(this.arrivalMinutesModification.get(1));
		Tuesday.add(new JLabel("Depart : "));
		Tuesday.add(this.departureHoursModification.get(1));
		Tuesday.add(new JLabel("H"));
		Tuesday.add(this.departureMinutesModification.get(1));
		panel.add(Tuesday);
		
		JPanel Wednesday = new JPanel();
		Wednesday.setLayout(new GridLayout(1,9));
		Wednesday.add(new JLabel("Mercredi"));
		Wednesday.add(new JLabel("Arrivee : "));
		Wednesday.add(this.arrivalHoursModification.get(2));
		Wednesday.add(new JLabel("H"));
		Wednesday.add(this.arrivalMinutesModification.get(2));
		Wednesday.add(new JLabel("Depart : "));
		Wednesday.add(this.departureHoursModification.get(2));
		Wednesday.add(new JLabel("H"));
		Wednesday.add(this.departureMinutesModification.get(2));
		panel.add(Wednesday);
		
		JPanel Thursday = new JPanel();
		Thursday.setLayout(new GridLayout(1,9));
		Thursday.add(new JLabel("Jeudi"));
		Thursday.add(new JLabel("Arrivee : "));
		Thursday.add(this.arrivalHoursModification.get(3));
		Thursday.add(new JLabel("H"));
		Thursday.add(this.arrivalMinutesModification.get(3));
		Thursday.add(new JLabel("Depart : "));
		Thursday.add(this.departureHoursModification.get(3));
		Thursday.add(new JLabel("H"));
		Thursday.add(this.departureMinutesModification.get(3));
		panel.add(Thursday);
		
		JPanel Friday = new JPanel();
		Friday.setLayout(new GridLayout(1,9));
		Friday.add(new JLabel("Vendredi"));
		Friday.add(new JLabel("Arrivee : "));
		Friday.add(this.arrivalHoursModification.get(4));
		Friday.add(new JLabel("H"));
		Friday.add(this.arrivalMinutesModification.get(4));
		Friday.add(new JLabel("Depart : "));
		Friday.add(this.departureHoursModification.get(4));
		Friday.add(new JLabel("H"));
		Friday.add(this.departureMinutesModification.get(4));
		panel.add(Friday);
		
		JButton buttonConfirmUpdate = new JButton("Confirmer la modification");
		buttonConfirmUpdate.setAlignmentX(CENTER_ALIGNMENT);
		buttonConfirmUpdate.addActionListener(this.controleur);
		panel.add(buttonConfirmUpdate);
		
		this.frameUpdate.pack();
		this.frameUpdate.setLocationByPlatform(true);
		this.frameUpdate.setVisible(true);
		this.frameUpdate.requestFocus();
	}
	
	/**
	 * Fill the tabs of JComboBox with the correct values
	 * Values of hours and minutes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setUpTable() {
		for (int i = 0; i < 5; i++) {
			this.arrivalHoursAdding.add(new JComboBox(this.hourValues));
			this.departureHoursAdding.add(new JComboBox(this.hourValues));
			this.arrivalHoursModification.add(new JComboBox(this.hourValues));
			this.departureHoursModification.add(new JComboBox(this.hourValues));
			
			this.arrivalMinutesAdding.add(new JComboBox(this.minuteValues));
			this.departureMinutesAdding.add(new JComboBox(this.minuteValues));
			this.arrivalMinutesModification.add(new JComboBox(this.minuteValues));
			this.departureMinutesModification.add(new JComboBox(this.minuteValues));
		}
	}

	
	/**
	 * @return all the tabs of the tabbed pane
	 */
	public JTabbedPane getTabs() {
		return tabs;
	}
	
	
	//tab1
	/**
	 * @return the JLabel with the employee first name in the tab of reception of a Pointage
	 */
	public JLabel getLabelWorkerName() {
		return labelWorkerName;
	}
	/**
	 * @return the JLabel with the employee time of arrival in the tab of reception of a Pointage
	 */
	public JLabel getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @return the JLabel with the employee time of departure in the tab of reception of a Pointage
	 */
	public JLabel getDepartureTime() {
		return departureTime;
	}
	/**
	 * @return the JLabel with the employee spare time in the tab of reception of a Pointage
	 */
	public JLabel getSpareTime() {
		return spareTime;
	}
	
	
	//tab2
	/**
	 * @return the JTable with all the Pointages
	 */
	public DefaultTableModel getPointagesHistoryModel() {
		return pointagesHistoryModel;
	}
	
	/**
	 * @return the JTable with all the Pointages of the day
	 */
	public DefaultTableModel getPointagesOfTheDayHistoryModel() {
		return pointagesOfTheDayHistoryModel;
	}
	
	
	//tab3
	/**
	 * @return the JTable with all the employees
	 */
	public DefaultTableModel getEmployeesListModel() {
		return this.employeesListModel;
	}
	/**
	 * @return the JTextField with the last name of the employee to delete in the tab 3
	 */
	public JTextField getWorkerLastNameToDelete() {
		return workerLastNameToDelete;
	}
	/**
	 * @return the JTextField with the first name of the employee to delete in the tab 3
	 */
	public JTextField getWorkerFirstNameToDelete() {
		return workerFirstNameToDelete;
	}
	/**
	 * @return the JTextField with the last name of the employee to modify in the tab 3
	 */
	public JTextField getOldWorkerLastName() {
		return oldWorkerLastName;
	}
	/**
	 * @return the JTextField with the first name of the employee to modify in the tab 3
	 */
	public JTextField getOldWorkerFirstName() {
		return oldWorkerFirstName;
	}
	
	
	/**
	 * @return the frame to add a new employee
	 */
	public JFrame getFrameAdding() {
		return frameAdding;
	}
	/**
	 * @return the JTextField with the last name of the employee to add in the frame for adding employees
	 */
	public JTextField getWorkerLastNameToAdd() {
		return workerLastNameToAdd;
	}
	/**
	 * @return the JTextField with the first name of the employee to add in the frame for adding employees
	 */
	public JTextField getWorkerFirstNameToAdd() {
		return workerFirstNameToAdd;
	}
	/**
	 * @return the JComboBox linked with the add of the arrival hours of a new employee 
	 */
	public List<JComboBox<String[]>> getArrivalHoursAdding() {
		return arrivalHoursAdding;
	}
	/**
	 * @return the JComboBox linked with the add of the departure hours of a new employee 
	 */
	public List<JComboBox<String[]>> getDepartureHoursAdding() {
		return departureHoursAdding;
	}
	/**
	 * @return the JComboBox linked with the add of the arrival minutes of a new employee 
	 */
	public List<JComboBox<String[]>> getArrivalMinutesAdding() {
		return arrivalMinutesAdding;
	}
	/**
	 * @return the JComboBox linked with the add of the departure minutes of a new employee 
	 */
	public List<JComboBox<String[]>> getDepartureMinutesAdding() {
		return departureMinutesAdding;
	}
	
	
	/**
	 * @return the vue's controller
	 */
	public ControleurServer getControleur() {
		return controleur;
	}


	/**
	 * @return the frame to modify an existing employee
	 */
	public JFrame getFrameUpdate() {
		return frameUpdate;
	}
	/**
	 * @return the JTextField with the last name of the employee to add in the frame for modifying employees
	 */
	public JTextField getWorkerLastNameToUpdate() {
		return workerLastNameToUpdate;
	}
	/**
	 * @return the JTextField with the first name of the employee to add in the frame for modifying employees
	 */
	public JTextField getWorkerFirstNameToUpdate() {
		return workerFirstNameToUpdate;
	}
	/**
	 * @return the JComboBox linked with the modification of the arrival hours of an existing employee 
	 */
	public List<JComboBox<String[]>> getArrivalHoursModification() {
		return arrivalHoursModification;
	}
	/**
	 * @return the JComboBox linked with the modification of the departure hours of an existing employee 
	 */
	public List<JComboBox<String[]>> getDepartureHoursModification() {
		return departureHoursModification;
	}
	/**
	 * @return the JComboBox linked with the modification of the arrival minutes of an existing employee 
	 */
	public List<JComboBox<String[]>> getArrivalMinutesModification() {
		return arrivalMinutesModification;
	}
	/**
	 * @return the JComboBox linked with the modification of the departure minutes of an existing employee 
	 */
	public List<JComboBox<String[]>> getDepartureMinutesModification() {
		return departureMinutesModification;
	}
	
	
	//tab4
	/**
	 * @return the JTextField with the number of the server port
	 */
	public JTextField getPortNumberTextField() {
		return portNumberTextField;
	}
	
	/**
	 * @return the JTextField with the IP addres of the server
	 */
	public JTextField getIpAddressTextField() {
		return ipAddressTextField;
	}
}
