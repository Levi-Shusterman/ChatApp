package ServerSide;
import java.net.*;
import java.util.Vector;
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerGui extends JFrame{
  
  
  // GUI items
  JButton ssButton;
  JLabel machineInfo;
  JLabel portInfo;
  JTextArea history;

  // Network Items
  boolean serverContinue;
  ServerSocket serverSocket;

  // set up GUI
   public ServerGui()
   {
      super( "Echo Server" );

      // get content pane and set its layout
      Container container = getContentPane();
      container.setLayout( new FlowLayout() );

//       create buttons
      ssButton = new JButton( "Terminate" );
//      ssButton.addActionListener( this );
      ssButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ev){
        	  System.exit(0);
          }
          });
      container.add( ssButton );
      

      String machineAddress = null;
      try
      {  
        InetAddress addr = InetAddress.getLocalHost();
       machineAddress = "127.0.0.1" ;
//        machineAddress = addr.getHostAddress();
      }
      
      catch (UnknownHostException e)
      {
        machineAddress = "127.0.0.1";
      }
      
      machineInfo = new JLabel (machineAddress);
      container.add( machineInfo );
      portInfo = new JLabel (" Not Listening ");
      container.add( portInfo );

      history = new JTextArea ( 10, 40 );
      history.setEditable(false);
      container.add( new JScrollPane(history) );

      setSize( 500, 250 );
      setVisible( true );
      
      startListen();
   } // end CountDown constructor
   
     public static void main( String args[] )
   { 
      ServerGui application = new ServerGui();
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   }
     
     /**
      * Starts a connection on a socket
      * Listens for incoming Clients
      * Spawns threads to receive and send messages between them
      * */
     private void startListen() {
    	   Vector<ThreadIdentifier> outStreams
    	   	= new Vector<ThreadIdentifier>();
    	       	   
	       try{
	         serverSocket = new ServerSocket(9090, 0, InetAddress.getByName("localhost"));      
	         serverSocket.setReuseAddress(true);
	         portInfo.setText("9090");
		        history.insert("Connection Socket Created",0);
	       }catch( IOException e){
	         history.insert( "Error connecting\n", 0);
	         e.printStackTrace();
	       }
	       try{
	        portInfo.setText("Listening on Port: " + serverSocket.getLocalPort());
	        machineInfo.setText( InetAddress.getByName("localhost").toString());
	       }catch(Exception e){
	         e.printStackTrace();
	       }

	   // index of each thread in
	      // outStreams vector
	   int MyIndex = 0;
	   
       serverContinue = true;
	   while(serverContinue){
	         try{
		          Thread t = new Thread(new ClientThread(serverSocket.accept(), this,
		        		 outStreams, MyIndex));
		          
		          MyIndex++;
		          t.start();
		          
		          history.insert("Accepted a new connection\n", 0);
		          
	     }catch( IOException e){
	    	 history.insert("Failed to accept a new connection\n", 0);
	        }
	   }
     }
}
   