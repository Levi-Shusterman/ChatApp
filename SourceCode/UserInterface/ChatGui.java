package UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import ClientSide.ConnectManager;

public class ChatGui extends JFrame implements GuiClient
{
    // GUI items
    private JLabel userLabel = new JLabel("Connected Users");
    private JButton sendButton;
    private JTextField message;
    private JTextArea history;
    
    private JPanel usersPanel;
    private JPanel chatPanel;
    private JPanel bottomPanel;
    
    // Connecting to the server
    private String userName;
	private static ConnectManager Connector;
	
	private JComboBox<String> ToSend;
    
	// Represents list of users in the chat room
    private JList list;
    private DefaultListModel userList;
    JScrollPane listScrollPane;
    
    // set up GUI
    public ChatGui(String username) {
        super("Echo Client");
        
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.userName = username;
        
        // The users panel will contain a list of users to the left side
        usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        
        // Gui object will observe this list
        userList = new DefaultListModel();
        list = new JList(userList);
        
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //list.setSelectedIndex(0);
        
        listScrollPane = new JScrollPane(list);
        listScrollPane.setLayout(new ScrollPaneLayout());
        listScrollPane.add(Box.createRigidArea(new Dimension(0, 20)));
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        

        usersPanel.add(userLabel, "North");
        usersPanel.add(listScrollPane, "Center");
        
        // The message field and text area will be enclosed in the chat panel
        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        
        history = new JTextArea();
        history.setEditable(false);
        
        JScrollPane scrollable = new JScrollPane(history);
        history.setFont(new Font("Arial", Font.PLAIN, 16));
        chatPanel.add(scrollable, "Center");
        
        // Create all of the members of the bottom panel: the message area and send
        // button
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        message = new JTextField(25);
        message.setFont(new Font("Arial", Font.PLAIN, 16));
        
       
        sendButton = new JButton("Send");
        sendButton.setEnabled(true);
        
        // send message
        sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
    	        if( message.getText()!=null && message.getText()!=""
    	        		&&message.getText()!= ""){
    	        	
    	        	if( String.valueOf(ToSend.getSelectedItem()).equals("All")){
    		          boolean message_sent = Connector.SendMessage(message.getText() );
    		          message.setText("");
	  		          //addMessage(message.getText(), userName);
    	        	}else{
    	        	  boolean message_sent = Connector.SendMessage(message.getText(),
    	        			  String.valueOf(ToSend.getSelectedItem()) );
      		          message.setText("");
	  		          //addMessage(message.getText(), userName + " to " +   ToSend.getSelectedItem());
    	        	}
    	        }
    	       }
            });

        // send message by pressing enter
        sendButton.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                	if( message.getText()!=null && message.getText()!=""
	        		&& message.getText()!= ""){
	                    
                		if( String.valueOf(ToSend.getSelectedItem()).equals("All")){
		  		          boolean message_sent = Connector.SendMessage(message.getText() );
		  		          message.setText("");
	//	  		          addMessage(message.getText(), userName);
		  	        	}else{
		  	        		 boolean message_sent = Connector.SendMessage(message.getText(),
		  	        			  String.valueOf(ToSend.getSelectedItem()) );
		    		          message.setText("");
	//		  		          addMessage(message.getText(), userName + " to " +   ToSend.getSelectedItem());
	
		  	        	}
                		
                	}
            }
        });
        
        // We will set the "default button" to the send button, so whenever the user presses
        // enter, the message that is stored in the message field will be sent
        getRootPane().setDefaultButton(sendButton);
              
        bottomPanel.add(new JLabel(userName));
        bottomPanel.add(message);
        bottomPanel.add(sendButton);
        
        ToSend = new JComboBox<String>();
        bottomPanel.add(ToSend);
        ToSend.addItem("All");
        
        
        chatPanel.add(bottomPanel, "South");

        // Now add all of the panels into the whole frame
        add(usersPanel, "West");
        add(chatPanel, "Center");
        
        setVisible(true);
        Connector = new ConnectManager(this);
        Connector.SendName(userName);
                
    } // end CountDown constructor
    
    /**
     * Add a new user in the chat room to the panel
     * @param username
     */
    @SuppressWarnings("unchecked")
	public void addUser(String username)
    {
    	userList.addElement(username);
    	ToSend.addItem(username);
    }
    
    @SuppressWarnings("unchecked")
	public void removeUser( String username){
    	userList.removeElement(username);
    	ToSend.removeItem(username);
    }
    
    
    
    public void addMessage(String message, String username)
    {
        history.append("<" + username  + " ");
        

       
        history.append(" > " + message + "\n");
        
        this.message.setText("");
    }
 
    public static void main(String args[]) {
		String username = JOptionPane.showInputDialog(null, "Please enter your user name: ");
		ChatGui application = new ChatGui(username);
	    
		application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	      application.addWindowListener(new WindowAdapter() {
	          @Override
	          public void windowClosing(WindowEvent event) {
	        	  Connector.ExitChat();
	        	  System.exit(0);
	          }
	      });
    }
    
    @Override
    public void DebugMessage(String message){
    	addMessage(message, "debug");
    }

	@Override
	public void DisplayMessage(String message, String name) {
		// TODO Auto-generated method stub
    	addMessage(message, name);

	}

	@Override
	public void ChatRoomTerminated() {
		// TODO Auto-generated method stub
		Connector.ExitChat();
  	  	System.exit(0);
		
	}
}
