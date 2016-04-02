package ServerSide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * @author Levi Shusterman
 * 	working with Nigel Flower
 * 
 * 	CS 342 Software Design 
 *  With Professor Troy at UIC
 * 
 * Thread that deals with a client connection
 * Receives messages from its client
 * Relays that message to its sibling clients through a shared vector
 * */

/**
 * Messaging Protocol
 * 
 * Vector<String> passed between Server
 * and Client. 
 * 
 * Receiving from Client:
 * 
 * First element is the message itself. 
 * 
 * If the size is one, it is meant for
 * everyone in the chat.
 * 
 * If the size is not one, the remaining 
 * elements specify to whom it should be sent.
 *
 */
class ClientThread implements Runnable{
  Socket Sock;
  static ServerGui Gui;
 
  // Sending and receiving messages
  ObjectInputStream Reader;
  ObjectOutputStream Writer;
  
  boolean ClientConnected;
  
  // Refers to threads
  Vector<ThreadIdentifier> outStreams;
  
  // My index in outStreams vector
  int MyIndex;
  
  String MyName;
  
  public ClientThread( Socket sock, ServerGui gui,
	  Vector<ThreadIdentifier> outstreams,
	  int index){

	  outStreams = outstreams;
	  MyIndex = index;
  
    Sock = sock;
    Gui = gui;
    
       try {
             Reader = new ObjectInputStream(Sock.getInputStream());
             Writer = new ObjectOutputStream(Sock.getOutputStream());
             
             // Add yourself to the threads vector 
             outStreams.add(MyIndex, new ThreadIdentifier(Writer) );
             
           } catch(Exception ex) {ex.printStackTrace();}
           
         ClientConnected = true;
           
  }
 
  @SuppressWarnings("unchecked")
public void run(){
	  Vector<String> readin = new Vector<String>();
     Gui.history.insert("New Communication Thread Started\n",0);

       try {
           while ((readin = (Vector<String>) Reader.readObject()) != null) {
        	   
        	   processMessage(readin);
    	   
           } // close while
        } catch(Exception ex) {ex.printStackTrace();}
  }


  	/**
  	 * Process a message from the client and decide how to respond to it
  	 * 
  	 * @param readin : The message from the client
  	 */
	private void processMessage(Vector<String> readin ){
 	   String key = readin.elementAt(0);
 	   
	 	   try{
		 	   /**
		 	    * Received the name of this thread from client
		 	    */
		 	   if( key.equals("NAME")){
		 		   String name = readin.elementAt(1);
		 		   outStreams.elementAt(MyIndex).addName(name);
		 		   
		 		   MyName = name;
		 		   
		 		  Gui.history.insert("Receiving name from client:"
		 				  + name + " " + MyIndex +"\n",0);
		 		   
		 	   }
	 	   }catch(ArrayIndexOutOfBoundsException e){
	 		   Gui.history.insert("ERROR : Array index out of bounds in"+
	 				   "Receiving name from client\n",0);
	 		   
	 	   }catch(Exception e){
	 		   e.printStackTrace();
	 	   }
				
		 	   
	    		   
		   /**
		    * Send the message to everyone
		    */
		   if( key.equals("ALL")){
			   Gui.history.insert("Sending message to all\n",0);
			   
			   for( int i = 0; i < outStreams.size(); i++){
				   
				   // Index is initialized
				   if(outStreams.elementAt(i) != null){
					   
					   // Don't resend messages to yourself 
					   String name_of_thread = outStreams.elementAt(i).Name; 
					   if( !MyName.equals( name_of_thread )){
						   
	//	 				   // To the socket object output stream of this thread, 
	//	 				   			// write the message   
						   Vector<String> to_send = new Vector<String>();
						   String message = readin.elementAt(1);
						   to_send.add(message);
						   outStreams.elementAt(i).sendMessage(to_send);
					   }
				   }
			   }
	 	   }		
	}
}