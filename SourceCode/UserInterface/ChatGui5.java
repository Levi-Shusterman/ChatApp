import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class ChatGui5 extends JFrame 
{
    // GUI items
    private JLabel userLabel = new JLabel("Connected Users");
    private JButton sendButton;
    private JButton connectButton;
    private JTextField machineInfo;
    private JTextField portInfo;
    private JTextField message;
    private JTextArea history;
    private JTextArea users;
    private JPanel usersPanel;
    private JPanel chatPanel;
    private JPanel bottomPanel;
    private String username;

    // Network Items
    // boolean connected;
    // Socket echoSocket;
    // PrintWriter out;
    // BufferedReader in;

    ConnectManager Connector;

    // set up GUI
    public ChatGui(String username) {
        super("Echo Client");
        
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.username = username;
        
        // The users panel will contain a list of users to the left side
        usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        users = new JTextArea();
        users.setEditable(false);
        users.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        addUser(username);
        
        usersPanel.add(userLabel, "North");
        usersPanel.add(users, "Center");
        
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
                    addMessage(message.getText());
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.setEnabled(true);
        
        // We will set the "default button" to the send button, so whenever the user presses
        // enter, the message that is stored in the message field will be sent
        getRootPane().setDefaultButton(sendButton);
              
        bottomPanel.add(message);
        bottomPanel.add(sendButton);        
        
        chatPanel.add(bottomPanel, "South");
        

        // Now add all of the panels into the whole frame
        add(usersPanel, "West");
        add(chatPanel, "Center");
        
        /*
        Connector = new ConnectManager();
        
        // get content pane and set its layout
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // set up the North panel
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(4, 2));
        container.add(upperPanel, BorderLayout.NORTH);

        // create buttons
        // connected = false;

        upperPanel.add(new JLabel("Message: ", JLabel.CENTER));
        message = new JTextField("");
        // message.addActionListener( this );
        upperPanel.add(message);

        sendButton = new JButton("Send Message");
        // sendButton.addActionListener( this );
        sendButton.setEnabled(true);
        upperPanel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                boolean message_sent = Connector.SendMessage(message.getText());
                message.setText("");
            }
        });

        connectButton = new JButton("Connect to Server");
        // connectButton.addActionListener( this );
        upperPanel.add(connectButton);

        // upperPanel.add ( new JLabel ("Server Address: ", JLabel.RIGHT) );
        // machineInfo = new JTextField ("127.0.0.1");
        // upperPanel.add( machineInfo );
        //
        // upperPanel.add ( new JLabel ("Server Port: ", JLabel.RIGHT) );
        // portInfo = new JTextField ("");
        // upperPanel.add( portInfo );

        history = new JTextArea(10, 40);
        history.setEditable(false);
        container.add(new JScrollPane(history), BorderLayout.CENTER);
        */
        
        setVisible(true);
    } // end CountDown constructor

    public void addUser(String username)
    {
        users.append(username + "\n");
    }
    
    public void addMessage(String message)
    {
        history.append("<" + username  + "> " + message + "\n");
        this.message.setText("");
    }
 
    public static void main(String args[]) {
        ChatGui application = new ChatGui("Nigel");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
