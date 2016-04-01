import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * Thread that deals with a client connection
 * Receives messages from its client
 * Relays that message to its sibling clients through a shared vector
 * */
class ClientThread implements Runnable{
  Socket Sock;
  static ServerGui Gui;
//  ObjectInputStream Reader;
    ObjectInputStream Reader;
  boolean ClientConnected;
  
  
  public ClientThread( Socket sock, ServerGui gui ){
    Sock = sock;
    Gui = gui;
    
       try {
             Reader = new ObjectInputStream(Sock.getInputStream());

           } catch(Exception ex) {ex.printStackTrace();}
           
         ClientConnected = true;
           
  }
 
  @SuppressWarnings("unchecked")
public void run(){
	  Vector<String> readin = new Vector<String>();
     Gui.history.insert("New Communication Thread Started",0);

       try {
           while ((readin = (Vector<String>) Reader.readObject()) != null) {
//             Gui.history.insert(message + "\n" ,0);
        	   
        	   
        	   if( readin.size()>=1){
        		   Gui.history.insert( readin.get(0), 0 );
        	   }else if( readin.size() == 0){
        		   Gui.history.insert("Received an empty object\n", 0);
        	   }
        	   
           } // close while
        } catch(Exception ex) {ex.printStackTrace();}
  }

}