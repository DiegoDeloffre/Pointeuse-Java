package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;

import misc.Logger;

/**
 * Class Application Pointeuse of the project
 * Launch the client part of the pointeuse
 * 
 * @author Diego Deloffre, Maxime Senger, Joris Loit and Pierre Fourre
 */
public class ApplicationPointeuse {

	/**
	 * Main method of the project
	 * @param args eventual argument of main method
	 * @throws FileNotFoundException if the file is not found
	 * @throws IOException if there is an error while opening the file
	 * @throws ClassNotFoundException if there is an error while reading the file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Pointeuse pointeuse;
		File saveFile = new File("saveClientFile.ser");
		Logger.log("Importing entreprise ...");
		if (saveFile.exists()) {
			Logger.log("Save found, importing last state ...");
			ObjectInputStream clientObj = new ObjectInputStream(new FileInputStream(saveFile));
			pointeuse = (Pointeuse)clientObj.readObject();
			clientObj.close();
		}
		else {
			Logger.log("Save not found, new Entreprise created from stub method");
			pointeuse = stub();
		}
		Logger.log("Entreprise imported !");
		JFrame clientFrame = new JFrame();
		clientFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	ObjectOutputStream clientObj;
				try {
					clientObj = new ObjectOutputStream(new FileOutputStream(saveFile));
					clientObj.writeObject(pointeuse);
					clientObj.close();
					Logger.log("System closing, saving changes.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
		    }
		});
		clientFrame.setLayout(new GridLayout(1,1));
		clientFrame.add(new VuePointeuse(pointeuse));
		clientFrame.pack();
		clientFrame.setVisible(true);
		clientFrame.setTitle("Partie client de la Pointeuse");
		clientFrame.setMinimumSize(new Dimension(400, 200));
		
	}

	/**
	 * Stub method to create a new Pointeuse with a portNumber and an IPAddress
	 * @return a new Pointeuse
	 */
	private static Pointeuse stub() {
		Pointeuse p = new Pointeuse(6666,"127.0.0.1");
		return p;
	}
}
