package app.model;

import app.controller.Game;
import app.view.GUI.PlayerGUI;
import app.view.IP;
import app.view.TUI.PlayerTUI;
import playground.socket.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static app.model.NetMode.RMI;
import static app.model.NetMode.SOCKET;
import static app.view.UIMode.GUI;
import static app.view.UIMode.TUI;

/**
 * class which represent the entry point for teh client
 */
public class Client {
    /**
     * main method which is used by the user to choose the UI and the Network
     */
    public Client() {
        String numPlayers;
        int numP;
        Scanner in = new Scanner(System.in);
        try {
            Socket mySocket = new Socket(IP.LOCAL_HOST, Server.PORT);
            ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
            out.writeObject(true);
        }catch (Exception e){
            while(true) {
                System.out.println("\nYou are the first player to connect, insert the number of players: ");
                numPlayers = in.nextLine();
                if(numPlayers.length() == 0)
                    numPlayers = "2";
                numP = Integer.parseInt(numPlayers);
                if(numP < 2 || numP > 4){
                    System.out.println("\nInvalid choice, try again");
                    continue;
                }
                new Game(numP);
                break;
            }
        }

        while(true) {
            System.out.print("\nChoose game mode (TUI or GUI): ");
            String ui = in.nextLine();
            if(ui.length() == 0)
                ui = "TUI";
            System.out.print("\nChoose game mode (Socket or rmi): ");
            String net = in.nextLine();
            if(net.length() == 0)
                net = "Socket";
            if (ui.equals("TUI")) {
                if (net.equals("Socket")) {
                    new PlayerTUI(SOCKET, TUI);
                    break;
                } else if (net.equals("rmi")) {
                    new PlayerTUI(RMI, TUI);
                    break;
                }
            }
            if (ui.equals("GUI")) {
                if (net.equals("Socket")) {
                    new PlayerGUI(SOCKET, GUI);
                    break;
                } else if (net.equals("rmi")) {
                    new PlayerGUI(RMI, GUI);
                    break;
                }
            }
            System.out.println("\nInvalid choice, try again");
        }
    }
}