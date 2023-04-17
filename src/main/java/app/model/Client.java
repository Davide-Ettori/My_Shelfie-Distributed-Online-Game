package app.model;

import app.controller.Game;
import app.view.GUI.PlayerGUI;
import app.view.IP;
import app.view.TUI.PlayerTUI;
import playground.socket.Server;

import java.io.*;
import java.net.Socket;

import static app.model.NetMode.RMI;
import static app.model.NetMode.SOCKET;
import static app.view.UIMode.GUI;
import static app.view.UIMode.TUI;

/**
 * class which represent the entry point for the client
 */
public class Client {
    /**
     * main method which is used by the user to choose the UI and the Network
     */
    public Client() {
        String numPlayers;
        int numP;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket mySocket = new Socket(IP.LOCAL_HOST, Server.PORT);
            ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
            out.writeObject(true);
            System.out.println("\nThere is already an active game...");
        }catch (Exception e){
            while(true) {
                System.out.print("\nYou are the first player to connect, insert the number of players: ");
                try {
                    numPlayers = br.readLine();
                } catch (IOException exc) {
                    System.out.println("errore");
                    throw new RuntimeException(exc);
                }
                if(numPlayers.length() == 0)
                    numPlayers = "2";
                numP = Integer.parseInt(numPlayers);
                if(numP < 2 || numP > 4){
                    System.out.println("\nInvalid choice, try again");
                    continue;
                }
                int finalNumP = numP;
                new Thread(() -> new Game(finalNumP)).start();
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
                System.out.println("errore");
                throw new RuntimeException(e);
            }
            if(ui.length() == 0)
                ui = "TUI";
            System.out.print("\nChoose game mode (Socket or RMI): ");
            String net;
            try {
                net = br.readLine();
            } catch (IOException e) {
                System.out.println("errore");
                throw new RuntimeException(e);
            }
            if(net.length() == 0)
                net = "Socket";
            if (ui.equals("TUI")) {
                if (net.equals("Socket")) {
                    new PlayerTUI(SOCKET, TUI);
                    break;
                } else if (net.equals("RMI")) {
                    new PlayerTUI(RMI, TUI);
                    break;
                }
            }
            if (ui.equals("GUI")) {
                if (net.equals("Socket")) {
                    new PlayerGUI(SOCKET, GUI);
                    break;
                } else if (net.equals("RMI")) {
                    new PlayerGUI(RMI, GUI);
                    break;
                }
            }
            System.out.println("\nInvalid choice, try again");
        }
    }
}