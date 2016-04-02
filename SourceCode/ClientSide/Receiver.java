package ClientSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import UserInterface.ChatGui;

/**
   * 
   * @author Levi Shusterman
   *  	working with Nigel Flower
   *  
   * Receives and handles messages from the server
   */

class Receiver implements Runnable {
	
	ChatGui Gui;
	ObjectInputStream Reader;
	
	public Receiver(ChatGui gui, Socket sock) {
		Gui = gui;
		
		try {
			Reader = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Receiver : Constructor\n");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Vector<String> readin;

		try {
			while ( (readin = (Vector<String>) Reader.readObject()) != null) {
	        	   
	        	   processMessage(readin);
	    	   
	           } // close while
	        } catch(Exception ex) {ex.printStackTrace();}
	}

	private void processMessage(Vector<String> readin) {
		// TODO Auto-generated method stub
		
		// Message from the server
		if ( readin.size() ==1 ){
			Gui.history.insert(readin.elementAt(0), 0);
		}
		
	}

}
