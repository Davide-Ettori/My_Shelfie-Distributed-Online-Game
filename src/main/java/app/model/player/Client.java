package app.model.player;

import app.model.player.PlayerGUI;

import java.net.Socket;
import java.util.Scanner;

import static app.model.player.NetMode.*;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print("\nChoose game mode (CLI or GUI): ");
            String ui = in.nextLine();
            if(ui.length() == 0)
                ui = "CLI";
            System.out.print("\nChoose game mode (Socket or rmi): ");
            String net = in.nextLine();
            if(net.length() == 0)
                net = "Socket";
            if (ui.equals("CLI")) {
                if (net.equals("Socket")) {
                    new PlayerCLI(NetMode.SOCKET);
                } else if (net.equals("rmi")) {
                    new PlayerCLI(NetMode.RMI);
                }
                break;
            }

            if (ui.equals("GUI")) {
                if (net.equals("Socket")) {
                    new PlayerGUI(NetMode.SOCKET);
                } else if (net.equals("rmi")) {
                    new PlayerGUI(NetMode.RMI);
                }
                break;
            }

            System.out.println("\nInvalid choice, try again");
        }

    }
}
