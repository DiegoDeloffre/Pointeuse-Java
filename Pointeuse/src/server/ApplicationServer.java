package server;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JFrame;

import misc.Logger;

/**
 * Class Main of the project
 * Launch the server part of the pointeuse
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 *
 */
public class ApplicationServer {
	
	/**
	 * Stub method to create a new Entreprise with employees and Pointages
	 * @return a new Entreprise
	 */
	public static Entreprise stub() {
		
		Entreprise eCorp = new Entreprise();
		Logger.log("Entreprise created.");
		
		Worker elliot = eCorp.addEmployee(1, "Alderson", "Elliot");
		Worker tyrell = eCorp.addEmployee(2, "Wellick", "Tyrell");
		Worker angela = eCorp.addEmployee(3, "Moss", "Angela");
		Worker gideon = eCorp.addEmployee(4, "Goddard", "Gideon");
		Logger.log("Employees created.");
		
		LocalTime morning1 = LocalTime.of(7, 30);
		LocalTime morning2 = LocalTime.of(9, 0);
		
		LocalTime evening1 = LocalTime.of(17, 0);
		LocalTime evening2 = LocalTime.of(18, 30);
		
		elliot.addDayOfWork(1, morning1, evening1);
		elliot.addDayOfWork(2, morning1, evening1);
		elliot.addDayOfWork(3, morning2, evening2);
		elliot.addDayOfWork(4, morning1, evening1);
		elliot.addDayOfWork(5, morning1, evening1);
		
		tyrell.addDayOfWork(1, morning2, evening1);
		tyrell.addDayOfWork(2, morning1, evening2);
		tyrell.addDayOfWork(3, morning2, evening1);
		tyrell.addDayOfWork(4, morning2, evening1);
		tyrell.addDayOfWork(5, morning2, evening1);
		
		angela.addDayOfWork(1, morning1, evening1);
		angela.addDayOfWork(2, morning1, evening1);
		angela.addDayOfWork(3, morning2, evening2);
		angela.addDayOfWork(4, morning1, evening1);
		angela.addDayOfWork(5, morning1, evening1);
		
		gideon.addDayOfWork(1, morning2, evening1);
		gideon.addDayOfWork(2, morning1, evening2);
		gideon.addDayOfWork(3, morning2, evening1);
		gideon.addDayOfWork(4, morning1, evening1);
		gideon.addDayOfWork(5, morning2, evening1);
		
		eCorp.addPointage(1, LocalDateTime.of(2021, 06, 14, 7, 45));
		eCorp.addPointage(1, LocalDateTime.of(2021, 06, 14, 17, 45));
		eCorp.addPointage(2, LocalDateTime.of(2021, 06, 14, 9, 45));
		eCorp.addPointage(2, LocalDateTime.of(2021, 06, 14, 19, 45));
		eCorp.addPointage(3, LocalDateTime.of(2021, 06, 14, 7, 30));
		eCorp.addPointage(3, LocalDateTime.of(2021, 06, 14, 17, 0));
		eCorp.addPointage(4, LocalDateTime.of(2021, 06, 14, 9, 45));
		eCorp.addPointage(4, LocalDateTime.of(2021, 06, 14, 19, 45));
		eCorp.addPointage(1, LocalDateTime.of(2021, 06, 15, 7, 45));
		eCorp.addPointage(1, LocalDateTime.of(2021, 06, 15, 17, 45));
		
		Logger.log("Schedul created.");
		
		return eCorp;
	}

	/**
	 * Main method of the project
	 * @param args eventual argument of the main method
	 * @throws FileNotFoundException if the file is not found
	 * @throws IOException if there is an error while opening the file
	 * @throws ClassNotFoundException if there is an error while reading the file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Entreprise eCorp;
		File saveFile = new File("saveFile.ser");
		Logger.log("Importing entreprise ...");
		if (saveFile.exists()) {
			Logger.log("Save found, importing last state ...");
			ObjectInputStream entrepriseObj =  new ObjectInputStream(new FileInputStream(saveFile)) ;
			eCorp = (Entreprise)entrepriseObj.readObject();
			entrepriseObj.close();
		}
		else {
			Logger.log("Save not found, new Entreprise created from stub method");
			eCorp = stub();
		}
		Logger.log("Entreprise imported !");
		VueServer vue = new VueServer(eCorp);
		
		JFrame serverFrame = new JFrame();
		serverFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	ObjectOutputStream entrepriseObj;
				try {
					entrepriseObj = new ObjectOutputStream(new FileOutputStream(saveFile));
					entrepriseObj.writeObject(eCorp);
					entrepriseObj.close();
					Logger.log("System closing, saving changes.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
		    }
		});
		serverFrame.setLayout(new GridLayout(1,1));
		serverFrame.add(vue);
		serverFrame.pack();
		serverFrame.setVisible(true);
		serverFrame.setTitle("Station de pointage");
		serverFrame.setSize(1000, 550);
		
		new Server(eCorp,vue,eCorp.getPortNumber());
	}

}
