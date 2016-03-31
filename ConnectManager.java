import java.net.*; 
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ConnectManager{
  
  // connection info
   private Integer Port = 9090;
   private String IP_Address = "127.0.0.1";
   
     // Network Items
   boolean Connected;
   PrintWriter Out;
   BufferedReader In;
   private Socket Sock;
   private ChatGui Gui;
  
  
  /**
   * Construct a connection the server at IP_Address + Port
   * */
  public ConnectManager(ChatGui gui){
	  Gui = gui;
    try{
            Sock = new Socket(IP_Address, Port );       
             Out = new PrintWriter(Sock.getOutputStream(), true);
            In = new BufferedReader(new InputStreamReader( Sock.getInputStream()));
            Connected = true;
      } catch (NumberFormatException e) {
            Gui.history.insert( "Server Port must be an integer " + Port + " \n",0);
        } catch (UnknownHostException e) {
            Gui.history.insert("Don't know about host: " + IP_Address,0 );
        } catch (IOException e) {
            Gui.history.insert("Couldn't get I/O for "
                               + "the connection to: " + IP_Address,0);
        }
        
        Gui.history.insert("Successfuly connected: " + IP_Address + " " + Port,0);
            
  }
  
  
  /**
   * 
   * Send a messsage to all users
   * */
  public boolean SendMessage(String message){
       try
      {
        Out.println(message);
      }
      catch (Exception e) 
      {
        Gui.history.insert("Client: Error in processing message :  " + message,0);
        return false;
      }
      
      return true;
  }
  
  /**
   * Send a message to the users specified in the users[] array
   * */
  public boolean SendMessage( String message, String[] users){
    return false;
  }
  
  /**
   * Terminate the connection
   * */
  public boolean CloseConnection(){
    return false;
  }
  
}