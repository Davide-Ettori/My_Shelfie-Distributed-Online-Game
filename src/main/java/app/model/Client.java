package app.model;

import app.controller.Game;
import app.controller.Initializer;
import app.view.GUI.PlayerGUI;
import app.view.IP;
import app.view.TUI.PlayerTUI;
import app.view.UIMode;

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
    public static int threadWeight = 8;
    /**
     * main method which is used by the user to choose the UI and the Network
     @author Ettori
     */
    public Client() {
        String numPlayers;
        int numP;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nInsert the IP address of the server: ");
        String ip;
        try {
            ip = br.readLine();
        } catch (IOException exc) {
            System.out.println("error");
            throw new RuntimeException(exc);
        }
        if(!ip.equals(""))
            IP.activeIP = ip;

        try {
            Socket mySocket = new Socket(IP.activeIP, Initializer.PORT);
            ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
            out.writeObject(true);
            System.out.println("\nThere is already an active game...");
            //FILEHelper.writeSucc();
        }catch (Exception e){
            while(true) {
                System.out.print("\nYou are the first player to connect, insert the number of players: ");
                try {
                    numPlayers = br.readLine();
                } catch (IOException exc) {
                    System.out.println("error");
                    throw new RuntimeException(exc);
                }
                if(numPlayers.length() == 0)
                    numPlayers = "2";
                numP = Integer.parseInt(numPlayers);
                if(numP < 2 || numP > 4){
                    System.out.println("\nInvalid choice, try again");
                    continue;
                }
                String old;
                System.out.print("\nDo you want to load an old game ? (yes or no for persistence): ");
                try {
                    old = br.readLine();
                } catch (IOException exc) {
                    System.out.println("error");
                    throw new RuntimeException(exc);
                }
                int finalNumP = numP;
                Thread serverTh = new Thread(() -> {
                    try {
                        new Game(finalNumP, old);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                serverTh.setPriority(threadWeight);
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
                    new Thread(() -> {
                        try {
                            new PlayerTUI(SOCKET, TUI, opt);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                } else if (net.equalsIgnoreCase("RMI")) {
                    Client.uiModeCur = TUI;
                    new Thread(() -> {
                        try {
                            new PlayerTUI(RMI, TUI, opt);
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
                    new Thread(() -> {
                        try {
                            new PlayerGUI(SOCKET, GUI, opt);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                } else if (net.equalsIgnoreCase("RMI")) {
                    Client.uiModeCur = GUI;
                    new Thread(() -> {
                        try {
                            new PlayerGUI(RMI, GUI, opt);
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