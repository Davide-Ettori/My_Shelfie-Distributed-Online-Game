package app.model;

import app.controller.Game;
import app.controller.Initializer;
import app.view.GUI.PlayerGUI;
import app.view.IP;
import app.view.TUI.PlayerTUI;
import app.view.UIMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static app.model.NetMode.RMI;
import static app.model.NetMode.SOCKET;
import static app.view.Dimensions.textCols;
import static app.view.UIMode.GUI;
import static app.view.UIMode.TUI;

/**
 * class which represent the entry point for the client
 * @author Ettori
 */
public class Client {

    /** variable used to keep track of the UI mode that the player is currently using */
    public static UIMode uiModeCur;
    private JFrame setupFrame = new JFrame("Setup of the game");
    private JPanel generalPanel = new JPanel();
    private boolean flag = false;
    private final transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * method for drawing the GUI to ask ip, port for socket and port for RMI
     */
    private void insertIp(){
        JTextField ipText = new JTextField(" Insert ip: ");
        JTextField socketPortText = new JTextField(" Insert socket port: ");
        JTextField rmiPortText = new JTextField(" Insert rmi port: ");
        JButton sendIP = new JButton(" Enter ");

        ipText.addActionListener(event -> sendIP.doClick());
        socketPortText.addActionListener(event -> sendIP.doClick());
        rmiPortText.addActionListener(event -> sendIP.doClick());
        sendIP.addActionListener((event) ->{
            try{
                if(!socketPortText.getText().equals(" Insert socket port: "))
                    Initializer.PORT = Integer.parseInt(socketPortText.getText());
                if(!rmiPortText.getText().equals(" Insert rmi port: "))
                    Initializer.PORT_RMI = Integer.parseInt(rmiPortText.getText());
                if(!ipText.getText().equals(" Insert ip: "))
                    IP.activeIP = ipText.getText();
                try{
                    Socket mySocket = new Socket(IP.activeIP, Initializer.PORT);
                    ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
                    out.writeObject(true);
                    alert("There is already an active game...");
                    new Thread(this::insertInfo).start();
                }catch (Exception e){
                    alert("You are the first player to connect!");
                    flag = true;
                    new Thread(this::insertPlayers).start();
                }
            }catch (Exception e){
                alert("Invalid Selection");
                ipText.setText("Insert ip:");
                socketPortText.setText("Insert socket port:");
                rmiPortText.setText("Insert rmi port:");
            }
        });
        generalPanel.setLayout(new GridLayout(4, 1));
        generalPanel.add(ipText);
        generalPanel.add(socketPortText);
        generalPanel.add(rmiPortText);
        generalPanel.add(sendIP);
        generalPanel.setPreferredSize(new Dimension((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5)));
        setupFrame.add(generalPanel, BorderLayout.CENTER);
        setupFrame.setSize((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.75));
        setupFrame.setResizable(false);
        setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupFrame.pack();
        setupFrame.setLocationRelativeTo(null);
        setupFrame.setVisible(true);
    }
    /**
     * method for drawing the GUI to ask number of players and persistence
     */
    private void insertPlayers(){
        JTextField title = new JTextField(" Choose the number of players ");
        title.setEditable(false);
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
        JTextField pers = new JTextField(" Do you want to activate persistence ");
        pers.setEditable(false);
        JRadioButton yes = new JRadioButton("yes");
        yes.setBorder(BorderFactory.createEmptyBorder(0,75,0,0));
        JRadioButton no = new JRadioButton("no");
        no.setSelected(true);
        ButtonGroup group_2 = new ButtonGroup();
        group_2.add(yes);
        group_2.add(no);

        JButton enterBtn = new JButton(" Enter ");
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
                    throw new RuntimeException(ex);
                }
            });
            serverTh.setPriority(Thread.MAX_PRIORITY);
            serverTh.start();
            Game.waitForSeconds(1);
            new Thread(this::insertInfo).start();
        });
        generalPanel.removeAll();
        generalPanel.setLayout(new GridLayout(4, 3));
        generalPanel.add(new Panel());
        generalPanel.add(title);
        generalPanel.add(new Panel());
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
        JTextField uiText = new JTextField(" Choose UI mode ");
        uiText.setEditable(false);
        JTextField netText = new JTextField(" Choose NET mode ");
        netText.setEditable(false);
        JTextField resilText = new JTextField(" Do you want to activate persistence ");
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

        enterBtn.addActionListener((event) ->{
            setupFrame.dispose();
            if(tui.isSelected() && socket.isSelected()){
                new Thread(() ->{
                    try {
                        new PlayerTUI(SOCKET, yes.isSelected() ? "yes" : "no", flag);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            if(tui.isSelected() && rmi.isSelected()){
                try {
                    new PlayerTUI(RMI, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if(gui.isSelected() && socket.isSelected()){
                try {
                    new PlayerGUI(SOCKET, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if(gui.isSelected() && rmi.isSelected()){
                try {
                    new PlayerGUI(RMI, yes.isSelected() ? "yes" : "no", flag);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
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