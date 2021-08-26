package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import misc.DateAndTimeHandler;
import misc.IPAddressHandler;

/**
 * Class ControleurPointeuse
 * Create and manipulate a controller for the Pointeuse
 * 
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 */
public class ControleurPointeuse implements ActionListener{

	private VuePointeuse vuePointeuse;	
	
	/**
	 * Create a new controller linked with the view
	 * @param vuePointeuse graphical interface of the pointeuse
	 */
	public ControleurPointeuse(VuePointeuse vuePointeuse) {
		super();
		this.vuePointeuse = vuePointeuse;
	}


	/**
	 * Reaction to all the button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String idWorker;
		
		JButton b = (JButton)e.getSource();
		
		if(b.getText().equals("Modifier")) {
			if(this.vuePointeuse.getIpAddress().getText().equals("") || this.vuePointeuse.getPortNumber().getText().equals("") || Integer.valueOf(this.vuePointeuse.getPortNumber().getText()) < 1 || Integer.valueOf(this.vuePointeuse.getPortNumber().getText()) > 9999) {
				JOptionPane.showMessageDialog(this.vuePointeuse, "Erreur de saisie sur le numero de port ou l'adresse IP. Veuillez saisir des valeurs valides.");
			}else {
				int input = JOptionPane.showConfirmDialog(this.vuePointeuse, "Etes-vous sur de vouloir modifier l'adresse IP et le numero de port ?");
		        // 0=yes, 1=no, 2=cancel
				if(input == 0) {
					String newIpAddress = this.vuePointeuse.getIpAddress().getText();
					if(newIpAddress.equals("localhost") || IPAddressHandler.isValidIPAddress(newIpAddress)) {
						this.vuePointeuse.getPointeuse().setIpAddress(newIpAddress);
					}else {
						JOptionPane.showMessageDialog(this.vuePointeuse, "L'adresse IP semble etre incorrecte. Veuillez saisir une adresse IP valide s'il vous plait.");
					}
					String newPortNumberString = this.vuePointeuse.getPortNumber().getText();
					if(newPortNumberString.equals("")) {
						JOptionPane.showMessageDialog(this.vuePointeuse, "Le numero de port est null. Veuillez saisir un numero de port valide s'il vous plait.");
					}else {
						int newPortNumber = Integer.valueOf(newPortNumberString);
						if (newPortNumber < 0 || newPortNumber > 0xFFFF) {
							JOptionPane.showMessageDialog(this.vuePointeuse, "Le numero de port semble etre incorrect. Veuillez saisir un numero de port valide s'il vous plait.");
						}else {
							this.vuePointeuse.getPointeuse().setPortNumber(newPortNumber);
						}
					}
				}else {
					this.vuePointeuse.getIpAddress().setText(this.vuePointeuse.getPointeuse().getIpAddress());
					this.vuePointeuse.getPortNumber().setText(Integer.toString(this.vuePointeuse.getPointeuse().getPortNumber()));
					this.vuePointeuse.repaint();
				}
			}			
		}
		
		if(b.getText().equals("Check In/Out")) {
			if(this.vuePointeuse.getIdWorker().equals("")) {
				idWorker = JOptionPane.showInputDialog("Rentrez un ID employe svp");
			}else {
				idWorker = this.vuePointeuse.getIdWorker();
			}
			Sender sender = new Sender(this.vuePointeuse, Integer.valueOf(idWorker), DateAndTimeHandler.getNearestHourQuarter(this.vuePointeuse.getDate()));
			sender.start();
		}
	}
}
