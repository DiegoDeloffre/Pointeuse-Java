package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class VuePointeuse
 * Create and manipulate a graphical interface for the Pointeuse
 * 
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 */

public class VuePointeuse extends JPanel{
	private static final long serialVersionUID = -7048257757138426355L;
	private LocalDateTime date;
	private JLabel labelDate;
	
	private JTextField textFieldidWorker;
	private JTextField textFieldportNumber;
	private JTextField textFieldipAddress;
	
	private ControleurPointeuse controllerPointeuse;
	private Pointeuse pointeuse;
	
	/**
	 * Create the graphical interface of the Pointeuse pointeuse
	 * @param pointeuse pointeuse linked with the vue
	 */
	public VuePointeuse(Pointeuse pointeuse) {
		this.pointeuse = pointeuse;
		this.controllerPointeuse = new ControleurPointeuse(this);
		this.setLayout(new BorderLayout());
		
		//top
		JPanel head = new JPanel();
		this.add(head, BorderLayout.NORTH);
		head.setLayout(new FlowLayout());
		head.setSize(300, 50);
		
		JLabel labelPort = new JLabel("Numero de port : ");
		head.add(labelPort);
		this.textFieldportNumber = new JTextField(Integer.toString(pointeuse.getPortNumber()));
		this.textFieldportNumber.setColumns(6);
		head.add(this.textFieldportNumber);
		
		JLabel labelIP = new JLabel("Adresse IP : ");
		head.add(labelIP);
		this.textFieldipAddress = new JTextField(pointeuse.getIpAddress());
		this.textFieldipAddress.setColumns(6);
		head.add(this.textFieldipAddress);
		
		JButton buttonModify = new JButton("Modifier");
		buttonModify.addActionListener(this.controllerPointeuse);
		head.add(buttonModify);
		
		
		//mid
		JPanel body = new JPanel();
		this.add(body, BorderLayout.CENTER);
		body.setLayout(new BorderLayout());
		body.setSize(300, 50);
		
		
		this.date = LocalDateTime.now();
		this.labelDate = new JLabel(date.getDayOfMonth() + "/"+ date.getMonthValue() +"/" +date.getYear() +"  "+date.getHour() +":"+date.getMinute()+":"+date.getSecond());
		this.labelDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		javax.swing.Timer t = new javax.swing.Timer(1000, new ClockListener());
        t.start();
		body.add(this.labelDate);
		
		
		//bottom
		JPanel bottom = new JPanel();
		this.add(bottom, BorderLayout.SOUTH);
		bottom.setSize(300, 50);
		bottom.setLayout(new FlowLayout());
		
		
		JLabel labelEmploye = new JLabel("ID employe :");
		bottom.add(labelEmploye);
		this.textFieldidWorker = new JTextField();
		this.textFieldidWorker.setColumns(10);
		bottom.add(this.textFieldidWorker);
		
		JButton buttonCheckInOut = new JButton("Check In/Out");
		buttonCheckInOut.addActionListener(this.controllerPointeuse);
		bottom.add(buttonCheckInOut);
	}

	/**
	 * @return the Pointeuse linked with the vue
	 */
	public Pointeuse getPointeuse() {
		return pointeuse;
	}

	/**
	 * @return the date of the vue
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @return the textFieldidWorker of the vue
	 */
	public String getIdWorker() {
		return textFieldidWorker.getText();
	}

	/**
	 * @return the port number of the vue
	 */
	public JTextField getPortNumber() {
		return textFieldportNumber;
	}

	/**
	 * @return the IP address of the vue
	 */
	public JTextField getIpAddress() {
		return textFieldipAddress;
	}
	
	/**
	 * Class ClockListener
	 * Enclosed class that allows the LocalDateTime of the vue to show the real time with a Timer
	 * 
	 * @author diego, maxime, joris and pierre
	 */
	private class ClockListener implements ActionListener {
		/**
		 * Method that change the LocalDateTime printed on the vue everytime it is called
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			date = LocalDateTime.now();
			labelDate.setText(date.getDayOfMonth() + "/"+ date.getMonthValue() +"/" +date.getYear() +"  "+date.getHour() +":"+date.getMinute()+":"+date.getSecond());
		}
	}
}
