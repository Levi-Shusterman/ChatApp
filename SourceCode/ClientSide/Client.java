package ClientSide;
import java.util.Vector;

/**
 * The Client implements this interface
 * 
 * Interface by which the Gui calls the Client
 * to send messages
 * 
 * @author Levi Shusterman
 * 	Working with Nigel Flower
 *
 */
public interface Client {
	 
	/**
	 * Send a message to all users currently in the chat room
	 * 
	 * @param message
	 * @return True if sent correctly, false if not
	 */
	boolean SendMessage(String message);
	
	/**TODO
	 * Send a message to specified users in the chat room
	 * 
	 * @param message
	 * @param to_whom
	 * @return True if sent correctly, false if not
	 */
	boolean SendMessage(String message, Vector<String> to_whom);
	
	/**
	 * Send the name of the user to the server upon startup
	 * 
	 * @param name
	 * @return
	 */
	boolean SendName(String name);
	
	
	void ExitChat();
}
