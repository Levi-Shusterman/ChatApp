package ClientSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import UserInterface.ChatGui;
import UserInterface.ChatGui5;

/**
   * 
   * @author Levi Shusterman
   *  	working with Nigel Flower
   *  
   * Receives and handles messages from the server
   */

class Receiver implements Runnable {
	
	ChatGui5 Gui;
	ObjectInputStream Reader;
	
	public Receiver(ChatGui5 gui2, Socket sock) {
		Gui = gui2;
		
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
		
		// User entered the chat room
		if( readin.elementAt(0).equals("ADD USER")){
			Gui.addUser(readin.elementAt(1));
			Gui.DisplayMessage( "Entered Chat",readin.elementAt(1) );

		}
		
		// Process removing a user when he exits the chat room
		else if(readin.elementAt(0).equals("REMOVE USER")){
			Gui.removeUser(readin.elementAt(1));
			Gui.DisplayMessage( "Exited Chat",readin.elementAt(1) );

		}
		// Message from the server
		else if ( readin.size() == 1 ){
			Gui.DisplayMessage( readin.elementAt(0),"Server" );
		}
		
		// Message from individual user
		else{
			String name = readin.elementAt(0);
			Gui.DisplayMessage(readin.elementAt(1), name);
		}
		
		
	}

}
