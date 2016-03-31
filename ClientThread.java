import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Thread that deals with a client connection
 * Receives messages from its client
 * Relays that message to its sibling clients through a shared vector
 * */
class ClientThread implements Runnable{
  Socket Sock;
  static ServerGui Gui;
//  ObjectInputStream Reader;
    BufferedReader Reader;
  boolean ClientConnected;
  
  
  public ClientThread( Socket sock, ServerGui gui ){
    Sock = sock;
    Gui = gui;
    
       try {
         InputStreamReader isReader = new InputStreamReader(Sock.getInputStream());
             Reader = new BufferedReader(isReader);
//             Reader = new ObjectInputStream(Sock.getInputStream());

           } catch(Exception ex) {ex.printStackTrace();}
           
         ClientConnected = true;
           
  }
 
  public void run(){
    System.out.println ("New Communication Thread Started");

           String message;
           try {
               while ((message = Reader.readLine()) != null) {
                 Gui.history.insert(message + "\n" ,0); 
               } // close while
            } catch(Exception ex) {ex.printStackTrace();}
  }

}