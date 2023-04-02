package app.model;
import java.util.Scanner;

import static app.model.NetMode.*;
import static app.view.UiMode.*;

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
                    new Player(SOCKET, TUI);
                } else if (net.equals("rmi")) {
                    new Player(RMI, TUI);
                }
                break;
            }
            if (ui.equals("GUI")) {
                if (net.equals("Socket")) {
                    new Player(SOCKET, GUI);
                } else if (net.equals("rmi")) {
                    new Player(RMI, GUI);
                }
                break;
            }
            System.out.println("\nInvalid choice, try again");
        }
    }
}
