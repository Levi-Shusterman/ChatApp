package UserInterface;

import java.util.Vector;

/**
 * Gui implements this interface
 * 
 * Interface by which the client updates the gui
 * when it receives messages from the server
 * 
 * @author Levi Shusterman
 *	Working with Nigel Flower
 *
 */
public interface GuiClient {
	
	 /**
	  * Gives the Gui a vector of who is in the chat room
	  * at the moment. The Gui is then to display these names.
	  * 
	  * 
	  * @param friends A vector of the people who are currently 
	  * in the chat room
	  */
	 default void EnteredChatRoom(Vector<String> friends){
		
	}
	 
	 /**
	  * Client calls this method when a message has been received.
	  * The gui is then to display it along with who sent it.
	  * 
	  * @param message
	  * @param name 	Name of person who sent message
	  */
	 default void DisplayMessage( String message, String name){
		 
		 
	 }
	 
	 /**
	  * The chat has ended. Terminate all the threads and exit.
	  * 
	  */
	 default void ChatRoomTerminated(){
		 
	 }
	 
	 /**
	  * Display a debugging message on the gui
	  * For development purposes
	  * 
	  * @param message
	  */
	 default void DebugMessage(String message){
		 
	 }
}
