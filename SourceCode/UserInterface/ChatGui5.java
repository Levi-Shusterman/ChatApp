package UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

public class ChatGui5 extends JFrame implements GuiClient
{
    // GUI items
    private JLabel userLabel = new JLabel("Connected Users");
    private JButton sendButton;
    private JTextField message;
    private JTextArea history;
    private JPanel usersPanel;
    private JPanel buttonsPanel;
    private JPanel chatPanel;
    private JPanel bottomPanel;
    private String username;
    private JCheckBox allButton;
    private Vector<JCheckBox> userCheckButtonsList;
    
    // set up GUI
    public ChatGui5(String username) {
        super("Echo Client");
        
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.username = username;
        
        // The users panel will contain a list of users to the left side
        usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        
        userCheckButtonsList = new Vector<JCheckBox>();
        addUser(username);
        addUser("Nigel");
        addUser("Levi");
        addUser("Mike");
        
        allButton = new JCheckBox("all");
        allButton.setSelected(true);
        allButton.addActionListener(ae ->{
            for(JCheckBox box : userCheckButtonsList)
                if(allButton.isSelected()) 
                    box.setSelected(true);
                else 
                    box.setSelected(false);
        });
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        for(JCheckBox user : userCheckButtonsList)
            buttonsPanel.add(user);
        
        buttonsPanel.add(allButton);
        
        usersPanel.add(userLabel, "North");
        usersPanel.add(buttonsPanel, "Center");
        
        // The message field and text area will be enclosed in the chat panel
        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        
        history = new JTextArea();
        history.setEditable(false);
        
        history.setFont(new Font("Arial", Font.PLAIN, 16));
        chatPanel.add(history, "Center");
        
        // Create all of the members of the bottom panel: the message area and send
        // button
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        message = new JTextField(30);
        message.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Make "Enter" the default key to send text to the chat
        message.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER && !message.getText().equals(""))
                    if(atLeastOneButtonSelected())
                        addMessage(message.getText());
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.setEnabled(true);
        sendButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e)
            {
                if((!message.getText().equals("")) && atLeastOneButtonSelected())
                    addMessage(message.getText());
            }
        });
        
        // We will set the "default button" to the send button, so whenever the user presses
        // enter, the message that is stored in the message field will be sent
        getRootPane().setDefaultButton(sendButton);
              
        bottomPanel.add(message);
        bottomPanel.add(sendButton);        
        
        chatPanel.add(bottomPanel, "South");

        // Now add all of the panels into the whole frame
        add(usersPanel, "West");
        add(chatPanel, "Center");
        
        //leaveChatRoom("Levi");
        
        setVisible(true);
    } // end CountDown constructor

    /*
     * Add a user to the chatroom by adding them to the list of checkboxes
     * 
     */
    public void addUser(String username)
    {
        JCheckBox newUser = new JCheckBox(username);
        
        if(this.username.equals(username))
        {
            newUser.setEnabled(false);
            newUser.setEnabled(true);
        }
        else
            newUser.setSelected(true);
        
        userCheckButtonsList.add(newUser);
        
    }
    
    /*
     * Send a message to the history text area by showing the user who sent the message along
     * with data concerning who the message is addressed to 
     */
    public void addMessage(String message)
    {
        history.append("<" + username  + " to: ");
        
        if(allButton.isSelected())
        {
            // If the message is addressed to everyone, then we will broadcast
            // the message to everyone
            history.append(" all ");
        }
        else
        {
            // Otherwise we shall send it to everyone in the list of checkboxes 
            // that are checked
            for(JCheckBox box : userCheckButtonsList)
            {
                if(box.isSelected())
                    history.append(box.getText() + " ");
            }
                
        }
        
        history.append(" > " + message + "\n");
        
        this.message.setText("");
    }
    
    /*
     * Check that at least one button in the vector of check buttons is selected 
     * 
     */
    private boolean atLeastOneButtonSelected()
    {
        for(JCheckBox box: userCheckButtonsList)
        {
            if(box.isSelected())
                return true;
        }
        
        return false;
    }
    
    /*
     * When a user leaves the chat room, we will remove him from the vector containing all of the 
     * connected users as well as the list of check boxes
     */
    public void leaveChatRoom(String username)
    {
        for(JCheckBox box : userCheckButtonsList)
        {
            if(box.getText().equals(username))
            {
                buttonsPanel.remove(box);
            }
        }
        
        userCheckButtonsList.remove(username);        
    }
 
    public static void main(String args[]) {
		String username = JOptionPane.showInputDialog(null, "Please enter your user name: ");
		ChatGui5 application = new ChatGui5(username);
    }
}
