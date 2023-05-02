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
    /** variable used to give a weight to the threads*/
    public static int threadWeight = 8;
    private final transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private void tryToChooseIP(){

    }

    /**
     * main method which is used by the user to choose the UI and the Network
     @author Ettori
     */
    public Client() {

        //Panel and Frame
        JFrame setupFrame = new JFrame("Setup of the game");
        JPanel setupPanel = new JPanel(new GridBagLayout());
        //Label for the Wallpaper
        JLabel generalLabelChooseName = new JLabel(new ImageIcon(new ImageIcon("assets/Publisher material/Display_5.jpg").getImage().getScaledInstance(screenSize.width * 5 / 6, screenSize.height * 9 / 10, Image.SCALE_SMOOTH)));
        generalLabelChooseName.setPreferredSize(new Dimension(screenSize.width * 5 / 6, screenSize.height * 8 / 10 + 65));
        generalLabelChooseName.setLayout(new GridBagLayout());
        //Informative text
        JTextField informativeText = new JTextField(120);
        informativeText.setText(" Here you can see the last relevant information about the setup ");
        informativeText.setMinimumSize(new Dimension(textCols * 45, textCols + 8));
        informativeText.setEditable(false);
        informativeText.setBorder(null);
        //IP Button and text field
        JButton sendIP = new JButton("Choose IP Address");
        JTextField textIP = new JTextField(20);
        textIP.setBounds(100, 20, 165, 25);
        textIP.addActionListener(event -> sendIP.doClick());

        String numPlayers;
        int numP;
        boolean flag = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nInsert the IP address of the server: ");
        String ip;
        String tempIP;

        sendIP.addActionListener((event) ->{
            while(true){
                try {
                    ip = br.readLine();
                } catch (IOException exc) {
                    System.out.println("error");
                    throw new RuntimeException(exc);
                }
                if(!ip.equals(""))
                    IP.activeIP = ip;
            }
        });

        try {
            Socket mySocket = new Socket(IP.activeIP, Initializer.PORT);
            ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
            out.writeObject(true);
            System.out.println("\nThere is already an active game...");
            //FILEHelper.writeSucc();
        }catch (Exception e){
            flag = true;
            while(true) {
                System.out.print("\nYou are the first player to connect, insert the number of players: ");
                try {
                    numPlayers = br.readLine();
                } catch (IOException exc) {
                    System.out.println("error");
                    throw new RuntimeException(exc);
                }
                if(numPlayers.length() == 0){
                    numP = 2;
                    break;
                }
                numP = Integer.parseInt(numPlayers);
                if(numP < 2 || numP > 4){
                    System.out.println("\nInvalid choice, try again");
                }else
                    break;
            }
            final String old;
            String oldTemporary;
            while(true){
                System.out.print("\nDo you want to load an old game ? (yes or no for persistence): ");
                try {
                    oldTemporary = br.readLine();
                } catch (IOException exc) {
                    System.out.println("error");
                    throw new RuntimeException(exc);
                }
                if(!(oldTemporary.equalsIgnoreCase("YES")||oldTemporary.equalsIgnoreCase("NO")||oldTemporary.equalsIgnoreCase(""))){
                    System.out.println("\nInvalid choice, try again");
                    continue;
                }
                int finalNumP = numP;
                old = oldTemporary;
                Thread serverTh = new Thread(() -> {
                    try {
                        new Game(finalNumP, old);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                //serverTh.setPriority(threadWeight);
                serverTh.setPriority(Thread.MAX_PRIORITY);
                serverTh.start();
                Game.waitForSeconds(1);
                break;
            }
        }

        while(true) {
            System.out.print("\nChoose game mode (TUI or GUI): ");
            String ui;
            try {
                ui = br.readLine();
            } catch (IOException e) {
                System.out.println("error");
                throw new RuntimeException(e);
            }
            if(ui.length() == 0)
                ui = "TUI";
            System.out.print("\nChoose connection mode (Socket or RMI): ");
            String net;
            try {
                net = br.readLine();
            } catch (IOException e) {
                System.out.println("error");
                throw new RuntimeException(e);
            }
            if(net.length() == 0)
                net = "Socket";
            System.out.print("\nDo you want to connect to a running game with your old name ? (yes or no for resilience): ");
            String opt;
            try {
                opt = br.readLine();
            } catch (IOException exc) {
                System.out.println("error");
                throw new RuntimeException(exc);
            }
            if (ui.equalsIgnoreCase("TUI")) {
                if (net.equalsIgnoreCase("Socket")) {
                    Client.uiModeCur = TUI;
                    boolean finalFlag = flag;
                    new Thread(() -> {
                        try {
                            new PlayerTUI(SOCKET, TUI, opt, finalFlag);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                } else if (net.equalsIgnoreCase("RMI")) {
                    Client.uiModeCur = TUI;
                    boolean finalFlag1 = flag;
                    new Thread(() -> {
                        try {
                            new PlayerTUI(RMI, TUI, opt, finalFlag1);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                }
            }
            if (ui.equalsIgnoreCase("GUI")) {
                if (net.equalsIgnoreCase("Socket")) {
                    Client.uiModeCur = GUI;
                    boolean finalFlag2 = flag;
                    new Thread(() -> {
                        try {
                            new PlayerGUI(SOCKET, GUI, opt, finalFlag2);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                } else if (net.equalsIgnoreCase("RMI")) {
                    Client.uiModeCur = GUI;
                    boolean finalFlag3 = flag;
                    new Thread(() -> {
                        try {
                            new PlayerGUI(RMI, GUI, opt, finalFlag3);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                }
            }
            System.out.println("\nInvalid choice, try again");
        }
    }
}