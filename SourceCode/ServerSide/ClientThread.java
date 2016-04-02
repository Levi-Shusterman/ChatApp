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
	  
	  ClientConnected = true;
  
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
    /**
     * Stop the thread because the client
     * exited the chat room
     * 
     * Nullify its index in the outStreams vector
     */
    void ExitChat(){
	  // Remove yourself 
	  	outStreams.set(MyIndex, null);
	  
	  	// build the message
	   String name = MyName;
	   Vector<String> to_send = new Vector<String>();
	   to_send.add("REMOVE USER");
	   to_send.add(name);
	   
	   sendMessageToAll(to_send);
	   ClientConnected = false;
	   Gui.history.insert(MyName + " exited chat just now.\n",0);	   
  }
 
  @SuppressWarnings("unchecked")
public void run(){
	  Vector<String> readin = new Vector<String>();
     Gui.history.insert("New Communication Thread Started\n",0);
     
     //updateClientWithUsers();
     
       try {
           while ((readin = (Vector<String>) Reader.readObject()) != null
        		   && ClientConnected ) {
        	   
        	   processMessage(readin);
    	   
           } // close while
        } catch(Exception ex) {ex.printStackTrace();}
  }

    
    /**
     * When a new client logs in, update him
     * with who is in the chat room
     * 
     * This method will be called when the server 
     * receives the name of this client thread
     */
  	private void updateClientWithUsers() {
	// TODO Auto-generated method stub
  		Vector<String> user = new Vector<String>();
  		user.add("ADD USER");
  		
		   for( int i = 0; i < outStreams.size(); i++){
			   
			   // Index is initialized
			   if(outStreams.elementAt(i) != null){
				   
				   
				   // Don't resend messages to yourself 
				   String name_of_thread = outStreams.elementAt(i).Name; 
				   if( !MyName.equals( name_of_thread )){
					   
	 				   user.add(1, name_of_thread); 
					   outStreams.elementAt(MyIndex).sendMessage(user);
				   }
			   }
		   }
  	}


	/**
  	 * Process a message from the client and decide how to respond to it
  	 * 
  	 * @param readin : The message from the client
  	 */
	private synchronized void processMessage(Vector<String> readin ){
 	   String key = readin.elementAt(0);
 	   	   
 	   	   if( key.equals("EXIT")){
 	   		   ExitChat();
 	   	   }
	 	   try{
		 	   /**
		 	    * Received the name of this thread from client
		 	    * Resend it to all users
		 	    */
		 	   if( key.equals("NAME")){
		 		   // Assign the name to the thread
		 		   String name = readin.elementAt(1);
		 		   outStreams.elementAt(MyIndex).addName(name);
		 		   MyName = name;
		 		   
		 		   Gui.history.insert("Receiving name from client:"
		 				   + name + " " + MyIndex +"\n",0);
		 		   Gui.history.insert("Sending name to all\n", 0);
		 		   
		 		  Vector<String> to_send = new Vector<String>();
				  
		 		  to_send.add("ADD USER");
		 		  to_send.add(name);
				   
				   sendMessageToAll(to_send);
				   updateClientWithUsers();
		 		   
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
			   
			   Vector<String> to_send = new Vector<String>();
			   String message = readin.elementAt(1);
			   to_send.add(MyName);
			   to_send.add(message);
			   
			   sendMessageToAll(to_send);
	 	   }		
	}
	
	private void sendMessageToAll( Vector<String> message){
		   for( int i = 0; i < outStreams.size(); i++){
			   
			   // Index is initialized
			   if(outStreams.elementAt(i) != null){
				   
				   // Don't resend messages to yourself 
				   String name_of_thread = outStreams.elementAt(i).Name; 
				   if( !MyName.equals( name_of_thread )){
					   
	 				   // To the socket object output stream of this thread, 
	 				   			// write the message   
					   outStreams.elementAt(i).sendMessage(message);
				   }
			   }
		   }
	}
}