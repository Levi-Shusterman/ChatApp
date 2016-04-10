package ClientSide;
import java.net.*;
import java.util.Vector;
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import UserInterface.ChatGui;
import UserInterface.ChatGui;

/**
 * The Gui instantiates this class to be able to connect to the server
 * 
 * Connect to the Server, send 
 * and receive messages from it.
 * 
 * 	CS 342 Software Design 
 *  With Professor Troy at UIC
 * 
 * @author Levi Shusterman
 * 	working with Nigel Flower
 * 
 * 
 * */

public class ConnectManager implements Client{
  
  // connection info
   private Integer Port = 9090;
   private String IP_Address = "192.168.1.69" ;
   
     // Network Items
   boolean Connected;
   ObjectOutputStream Out;
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
             Out = new ObjectOutputStream(Sock.getOutputStream());
            In = new BufferedReader(new InputStreamReader( Sock.getInputStream()));
            Connected = true;
            Gui.DisplayMessage("Successfuly connected to Chat Room", " ");
      } catch (NumberFormatException e) {
            Gui.DebugMessage( "Server Port must be an integer " + Port + " \n");
        } catch (UnknownHostException e) {
            Gui.DebugMessage("Don't know about host: " + IP_Address  );
        } catch (IOException e) {
            Gui.DebugMessage("Couldn't get I/O for "
                               + "the connection to: " + IP_Address);
        }
        
        
        // Listen on messages from the server
        Thread listen = new Thread( new Receiver(Gui, Sock) );
        listen.start();
  }
  
  
  /**
   * 
   * Send a messsage to all users
   * */
  @SuppressWarnings("null")
  @Override
  public boolean SendMessage(String message){
	  if( message.length() == 0)
		  return false;
	  
      Vector<String> out_message = new Vector<String>();
      out_message.add("ALL");
      out_message.add(message);
	  
	  try
      {
        Out.writeObject(out_message);
        Gui.DisplayMessage(message, "Me");
      }
      catch (Exception e) 
      {
        Gui.DebugMessage("Client: Error in processing message :  " + message);
        return false;
      }
      
      return true;
  }
  
  /**
   * Send a message to the specified user
   * */
  @Override
  public boolean SendMessage( String message, String to_whom){
	  if( message.length() == 0)
		  return false;
	  
      Vector<String> out_message = new Vector<String>();
      out_message.add("ONE");
      out_message.add(to_whom);
      out_message.add(message);
	  
	  try
      {
        Out.writeObject(out_message);
        Gui.DisplayMessage(message, "Me to : " + to_whom);
      }
      catch (Exception e) 
      {
        Gui.DebugMessage("Client: Error in processing message :  " + message);
        return false;
      }
      
      return true;
  }


@Override
public boolean SendName(String name) {
	  
	
	Vector<String> out_message = new Vector<String>();
      out_message.add("NAME");
	  out_message.add(name);
	  
	  try
      {
        Out.writeObject(out_message);
      }
      catch (Exception e) 
      {
        Gui.DebugMessage("Client: Error in sending name to server");
        return false;
      }
      
      return true;
}


@Override
public void ExitChat() {
	// TODO Auto-generated method stub
    Vector<String> out_message = new Vector<String>();
    out_message.add("EXIT");
	  
	  try
    {
      Out.writeObject(out_message);
    }
    catch (Exception e) 
    {
      Gui.DebugMessage("Client: Error in closing ");
    }
}
  
 

}