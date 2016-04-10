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
  
  // IP + Port
  String machineAddress;
  int Port = 9090;

  // set up GUI
   /**
 * 
 */
/**
 * 
 */
/**
 * 
 */
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
      
      try
      {  
        InetAddress IP = InetAddress.getLocalHost();
        machineAddress = IP.getHostAddress().toString();
        System.out.println( machineAddress +"\n" );
      }
      
      catch (UnknownHostException e)
      {
        history.insert("Issue getting the IP address of this machine",0);
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
		application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	      application.addWindowListener(new WindowAdapter() {
	          @Override
	          public void windowClosing(WindowEvent event) {
	        	  System.exit(0);
	          }
	      });   }
     
     /**
      * Starts a connection on a socket
      * Listens for incoming Clients
      * Spawns threads to receive and send messages between them
      * */
     private void startListen() {
    	   Vector<ThreadIdentifier> outStreams
    	   	= new Vector<ThreadIdentifier>();
    	       	   
	       try{
	         serverSocket = new ServerSocket(Port, 0, InetAddress.getLocalHost());      
	         serverSocket.setReuseAddress(true);
	         portInfo.setText(Integer.toString(Port));
		        history.insert("Connection Socket Created\n",0);
	       }catch( IOException e){
	         history.insert( "Error connecting\n", 0);
	         e.printStackTrace();
	       }
	       try{
		        portInfo.setText("Listening on Port: " + serverSocket.getLocalPort());
		        machineInfo.setText( machineAddress);
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
   