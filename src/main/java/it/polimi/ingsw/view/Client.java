package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.controller.Initializer;
import it.polimi.ingsw.view.GUI.PlayerGUI;
import it.polimi.ingsw.view.TUI.PlayerTUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static it.polimi.ingsw.model.NetMode.RMI;
import static it.polimi.ingsw.model.NetMode.SOCKET;
import static it.polimi.ingsw.view.UIMode.GUI;
import static it.polimi.ingsw.view.UIMode.TUI;

/**
 * class which represent the entry point for the client (everything is made with GUI)
 * @author Ettori
 */
public class Client {

    /** variable used to keep track of the UI mode that the player is currently using */
    public static UIMode uiModeCur;
    private final JFrame setupFrame = new JFrame();
    private final JPanel generalPanel = new JPanel();
    private boolean close = true;

    /**
     * method for drawing the GUI to ask ip, port for Socket and port for RMI
     * @author Ettori Giammusso
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
            try {
                if (!socketPortText.getText().equals(" Insert socket port: (default = 3333) "))
                    Initializer.PORT = Integer.parseInt(socketPortText.getText());
                if (!rmiPortText.getText().equals(" Insert rmi port: (default = 5555) "))
                    Initializer.PORT_RMI = Integer.parseInt(rmiPortText.getText());
                if (!ipText.getText().equals(" Insert ip: (default = 127.0.0.1) "))
                    IP.activeIP = ipText.getText();
                if(IP.activeIP.equals(""))
                    IP.activeIP = "-1";
                Socket mySocket = null;
                try {
                    new Thread(() ->{
                        Game.waitForSeconds(3);
                        if(close){
                            System.out.println("\nThe IP address does not exists on this network\n");
                            System.exit(0);
                        }
                    }).start();
                    mySocket = new Socket(IP.activeIP, Initializer.PORT);
                    close = false;
                    ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
                    out.writeObject(true);
                    alert("There is already an active game...");
                    new Thread(this::insertInfo).start();
                } catch (Exception e) {
                    close = false;
                    alert("You are the first player to connect!");
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
        int w = 1000;
        int h = 600;
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
     * @author Ettori
     */
    private void insertPlayers(){
        setupFrame.setTitle("Insert the Number of Players");
        JRadioButton p_2 = new JRadioButton("2 players");
        JRadioButton p_3 = new JRadioButton("3 players");
        JRadioButton p_4 = new JRadioButton("4 players");
        p_2.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        p_3.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        p_4.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        p_2.setSelected(true);
        ButtonGroup group_1 = new ButtonGroup();
        group_1.add(p_2);
        group_1.add(p_3);
        group_1.add(p_4);
        JTextField pers = new JTextField(" Do you want to load the old game ?");
        pers.setEditable(false);
        JRadioButton yes = new JRadioButton("yes");
        yes.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
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
            setupFrame.dispose();
            try {
                new Game(finalNumP, finalPersOpt);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
     * @author Ettori
     */
    private void insertInfo(){
        setupFrame.setTitle("Insert the Game Mode");
        JTextField uiText = new JTextField(" Choose UI mode ");
        uiText.setEditable(false);
        JTextField netText = new JTextField(" Choose NET mode ");
        netText.setEditable(false);
        JTextField resilText = new JTextField(" Do you want to reconnect to the running game ?");
        resilText.setEditable(false);
        JRadioButton tui = new JRadioButton("TUI");
        tui.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        JRadioButton gui = new JRadioButton("GUI");
        gui.setSelected(true);
        JRadioButton socket = new JRadioButton("Socket");
        socket.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        JRadioButton rmi = new JRadioButton("RMI");
        socket.setSelected(true);
        JRadioButton yes = new JRadioButton("Yes");
        yes.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
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
                try {
                    new PlayerTUI(SOCKET, yes.isSelected() ? "yes" : "no");
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
                return;
            }
            if(tui.isSelected() && rmi.isSelected()){
                Client.uiModeCur = TUI;
                alert("The game will continue in the terminal...");
                try {
                    new PlayerTUI(RMI, yes.isSelected() ? "yes" : "no");
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
                return;
            }
            if(gui.isSelected() && socket.isSelected()){
                Client.uiModeCur = GUI;
                try {
                    new PlayerGUI(SOCKET, yes.isSelected() ? "yes" : "no");
                } catch (RemoteException e) {
                    alert("Client process unable to start...");
                    System.exit(0);
                }
                return;
            }
            if(gui.isSelected() && rmi.isSelected()){
                Client.uiModeCur = GUI;
                try {
                    new PlayerGUI(RMI, yes.isSelected() ? "yes" : "no");
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
     * @author Ettori
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