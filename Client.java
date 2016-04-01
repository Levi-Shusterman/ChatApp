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
	 * @return
	 */
	default boolean SendMessage(String message){
		return false;
	}
	
	/**
	 * Send a message to specified users in the chat room
	 * 
	 * @param message
	 * @param to_whom
	 * @return
	 */
	default boolean SendMessage(String message, Vector<String> to_whom){
		return false;
	}
	
}
