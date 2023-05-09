package app.model;

import app.controller.Game;
import app.controller.Initializer;
import app.view.GUI.PlayerGUI;
import app.view.IP;
import app.view.TUI.PlayerTUI;
import app.view.UIMode;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static app.model.NetMode.RMI;
import static app.model.NetMode.SOCKET;
import static app.view.UIMode.GUI;
import static app.view.UIMode.TUI;

/**
 * class which represent the entry point for the client
 * @author Ettori
 */
public class Client {

    /** variable used to keep track of the UI mode that the player is currently using */
    public static UIMode uiModeCur;
    private JFrame setupFrame = new JFrame();
    private JPanel generalPanel = new JPanel();
    private boolean flag = false; //true only for the first player that connect to the server
    private static boolean close = true;
    private final transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * method for drawing the GUI to ask ip, port for socket and port for RMI
     */
    private void insertIp(){
        setupFrame.setTitle("Insert the IP Address");
        JTextField ipText = new JTextField(" Insert ip: (default = 127.0.0.1) ");
        JTextField socketPortText = new JTextField(" Insert socket port: (default = 3333) ");
        JTextField rmiPortText = new JTextField(" Insert rmi port: (default = 5555) ");
        JButton sendIP = new JButton(" Enter ");
        sendIP.setFont(new Font("Calibri", Font.PLAIN, 25));

        ipText.addActionListener(event -> sendIP.doClick());
        socketPortText.addActionListener(event -> sendIP.doClick());
        rmiPortText.addActionListener(event -> sendIP.doClick());
        sendIP.addActionListener((event) ->{
            try{
                if(!socketPortText.getText().equals(" Insert socket port: (default = 3333) "))
                    Initializer.PORT = Integer.parseInt(socketPortText.getText());
                if(!rmiPortText.getText().equals(" Insert rmi port: (default = 5555) "))
                    Initializer.PORT_RMI = Integer.parseInt(rmiPortText.getText());
                if(!ipText.getText().equals(" Insert ip: (default = 127.0.0.1) "))
                    IP.activeIP = ipText.getText();
                try{
                    new Thread(() ->{
                        Game.waitForSeconds(3);
                        if(!Client.close)
                            return;
                        System.out.println("\nThe ip address was incorrect...");
                        System.exit(0);
                    }).start();
                    Socket mySocket = null;
                    try{
                        mySocket = new Socket(IP.activeIP, Initializer.PORT);
                    } catch (Exception e){
                        System.out.println("\nThe ip address was incorrect...");
                        System.exit(0);
                    }
                    Client.close = false;
                    ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
                    out.writeObject(true);
                    alert("There is already an active game...");
                    new Thread(this::insertInfo).start();
                }catch (Exception e){
                    Client.close = false;
                    alert("You are the first player to connect!");
                    flag = true;
                    new Thread(this::insertPlayers).start();
                }
            }catch (Exception e){
                alert("Invalid Selection");
                ipText.setText(" Insert ip: ");
                socketPortText.setText(" Insert socket port: ");
                rmiPortText.setText(" Insert rmi port: ");
                generalPanel.requestFocusInWindow();
            }
        });
        int w = 800;
        int h = 450;
        generalPanel.setLayout(new GridLayout(4, 1));
        generalPanel.add(ipText);
        generalPanel.add(socketPortText);
        generalPanel.add(rmiPortText);
        generalPanel.add(sendIP);
        generalPanel.setPreferredSize(new Dimension(w, h));
        generalPanel.requestFocusInWindow();
        setupFrame.add(generalPanel, BorderLayout.CENTER);
        setupFrame.setSize(w, h);
        setupFrame.setResizable(false);
        setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupFrame.pack();
        setupFrame.setLocationRelativeTo(null);
        setupFrame.setVisible(true);
        generalPanel.requestFocus();
    }
    /**
     * method for drawing the GUI to ask number of players and persistence
     */
    private void insertPlayers(){
        setupFrame.setTitle("Insert the Number of Players");
        JRadioButton p_2 = new JRadioButton("2 players");
        JRadioButton p_3 = new JRadioButton("3 players");
        JRadioButton p_4 = new JRadioButton("4 players");
        p_2.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        p_3.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        p_4.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        p_2.setSelected(true);
        ButtonGroup group_1 = new ButtonGroup();
        group_1.add(p_2);
        group_1.add(p_3);
        group_1.add(p_4);
        JTextField pers = new JTextField(" Do you want to activate persistence ?");
        pers.setEditable(false);
        JRadioButton yes = new JRadioButton("yes");
        yes.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        JRadioButton no = new JRadioButton("no");
        no.setSelected(true);
        ButtonGroup group_2 = new ButtonGroup();
        group_2.add(yes);
        group_2.add(no);

        JButton enterBtn = new JButton(" Enter ");
        enterBtn.setFont(new Font("Calibri", Font.PLAIN, 25));
        enterBtn.addActionListener((event) ->{
            int numP = 0;
            String persOpt = "";
            if(p_2.isSelected())
                numP = 2;
            if(p_3.isSelected())
                numP = 3;
            if(p_4.isSelected())
                numP = 4;
            if(yes.isSelected())
                persOpt = "yes";
            else
                persOpt = "no";

            int finalNumP = numP;
            String finalPersOpt = persOpt;
            Thread serverTh = new Thread(() -> {
                try {
                    new Game(finalNumP, finalPersOpt);
                } catch (RemoteException ex) {
                    alert("Server unable to start...");
                    System.exit(0);
                }
            });
            serverTh.setPriority(Thread.MAX_PRIORITY);
            serverTh.start();
            Game.waitForSeconds(1);
            new Thread(this::insertInfo).start();
        });
        generalPanel.removeAll();
        generalPanel.setLayout(new GridLayout(3, 3));
        generalPanel.add(p_2);
        generalPanel.add(p_3);
        generalPanel.add(p_4);
        generalPanel.add(pers);
        generalPanel.add(yes);
        generalPanel.add(no);
        generalPanel.add(new Panel());
        generalPanel.add(enterBtn);
        generalPanel.add(new Panel());
        generalPanel.revalidate();
        generalPanel.repaint();
        setupFrame.setVisible(true);
    }
    /**
     * method for drawing the GUI to ask UI mode, net Mode and resilience
     */
    private void insertInfo(){
        setupFrame.setTitle("Insert the Game Mode");
        JTextField uiText = new JTextField(" Choose UI mode ");
        uiText.setEditable(false);
        JTextField netText = new JTextField(" Choose NET mode ");
        netText.setEditable(false);
        JTextField resilText = new JTextField(" Do you want to activate resilience ?");
        resilText.setEditable(false);
        JRadioButton tui = new JRadioButton("TUI");
        tui.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        JRadioButton gui = new JRadioButton("GUI");
        gui.setSelected(true);
        JRadioButton socket = new JRadioButton("Socket");
        socket.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        JRadioButton rmi = new JRadioButton("RMI");
        socket.setSelected(true);
        JRadioButton yes = new JRadioButton("Yes");
        yes.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        JRadioButton no = new JRadioButton("No");
        no.setSelected(true);
        ButtonGroup group_1 = new ButtonGroup();
        ButtonGroup group_2 = new ButtonGroup();
        ButtonGroup group_3 = new ButtonGroup();
        group_1.add(tui);
        group_1.add(gui);
        group_2.add(socket);
        group_2.add(rmi);
        group_3.add(yes);
        group_3.add(no);
        JButton enterBtn = new JButton(" Enter ");
        enterBtn.setFont(new Font("Calibri", Font.PLAIN, 25));

        enterBtn.addActionListener((event) ->{
            setupFrame.dispose();
            if(tui.isSelected() && socket.isSelected()){
                alert("The game will continue in the terminal...");
                Client.uiModeCur = TUI;
                new Thread(() ->{
                    try {
                        new PlayerTUI(SOCKET, yes.isSelected() ? "yes" : "no", flag);
                    } catch (RemoteException e) {
                        alert("Client process unable to start...");
                        System.exit(0);
                    }
                }).start();
            }
            if(tui.isSelected() && rmi.isSelected()){
                Client.uiModeCur = TUI;
                alert("The game will continue in the terminal...");
                try {
                    new PlayerTUI(RMI, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
            }
            if(gui.isSelected() && socket.isSelected()){
                Client.uiModeCur = GUI;
                try {
                    new PlayerGUI(SOCKET, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
            }
            if(gui.isSelected() && rmi.isSelected()){
                Client.uiModeCur = GUI;
                try {
                    new PlayerGUI(RMI, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
            }
        });
        generalPanel.removeAll();
        generalPanel.setLayout(new GridLayout(4, 3));
        generalPanel.add(uiText);
        generalPanel.add(tui);
        generalPanel.add(gui);
        generalPanel.add(netText);
        generalPanel.add(socket);
        generalPanel.add(rmi);
        generalPanel.add(resilText);
        generalPanel.add(yes);
        generalPanel.add(no);
        generalPanel.add(new Panel());
        generalPanel.add(enterBtn);
        generalPanel.add(new Panel());
        generalPanel.revalidate();
        generalPanel.repaint();
        setupFrame.setVisible(true);
    }
    /**
     * helper function for alerting a message to the user (pop-up)
     * @param s the string og the message to show
     */
    private void alert(String s){javax.swing.JOptionPane.showMessageDialog(null, s);}

    /**
     * main method which is used by the user to choose the UI and the Network
     @author Ettori
     */
    public Client() {
        new Thread(this::insertIp).start();
    }
}