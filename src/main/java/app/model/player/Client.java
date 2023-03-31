package app.model.player;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

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
                    new PlayerTUI(NetMode.SOCKET);
                } else if (net.equals("rmi")) {
                    new PlayerTUI(NetMode.RMI);
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
