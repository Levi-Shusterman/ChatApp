import java.net.*; 
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ClientSide.ConnectManager;

public class ChatGui extends JFrame 
{  
  // GUI items
  JButton sendButton;
  JButton connectButton;
  JTextField machineInfo;
  JTextField portInfo;
  JTextField message;
  JTextArea history;

  // Network Items
//  boolean connected;
//  Socket echoSocket;
//  PrintWriter out;
//  BufferedReader in;
  
  ConnectManager Connector;

   // set up GUI
   public ChatGui()
   {
      super( "Echo Client" );
      
      // get content pane and set its layout
      Container container = getContentPane();
      container.setLayout (new BorderLayout ());
      
      // set up the North panel
      JPanel upperPanel = new JPanel ();
      upperPanel.setLayout (new GridLayout (4,2));
      container.add (upperPanel, BorderLayout.NORTH);
      
      // create buttons
      
//      connected = false;

      upperPanel.add ( new JLabel ("Message: ", JLabel.CENTER) );
      message = new JTextField ("");
//      message.addActionListener( this );
      upperPanel.add( message );
      
      sendButton = new JButton( "Send Message" );
//      sendButton.addActionListener( this );
      sendButton.setEnabled (true);
      upperPanel.add( sendButton );
      
      sendButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ev){
	        if( message.getText()!=null && message.getText()!=""){
		          boolean message_sent = Connector.SendMessage(message.getText() );
		          message.setText("");
	        }
	       }
        });

      connectButton = new JButton( "Connect to Server" );
//      connectButton.addActionListener( this );
      upperPanel.add( connectButton );
                      
//      upperPanel.add ( new JLabel ("Server Address: ", JLabel.RIGHT) );
//      machineInfo = new JTextField ("127.0.0.1");
//      upperPanel.add( machineInfo );
//                      
//      upperPanel.add ( new JLabel ("Server Port: ", JLabel.RIGHT) );
//      portInfo = new JTextField ("");
//      upperPanel.add( portInfo );
                      
      history = new JTextArea ( 10, 40 );
      history.setEditable(false);
      container.add( new JScrollPane(history) ,  BorderLayout.CENTER);

      setSize( 500, 250 );
      setVisible( true );
      
      Connector = new ConnectManager(this);
   } // end CountDown constructor

   public static void main( String args[] )
   { 
      ChatGui application = new ChatGui();
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   }
}