package app.model.player;

import app.model.player.PlayerGUI;

import java.net.Socket;
import java.util.Scanner;

import static app.model.player.NetMode.*;

public class Client {
    public static void main(String[] args){
        String ui, net;
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("\nChoose game mode (CLI or GUI): ");
            ui = in.nextLine();
            System.out.print("\nChoose game mode (Socket or rmi): ");
            net = in.nextLine();
            if(ui.equals("CLI")){
                if(net.equals("Socket"))
                    new PlayerCLI(SOCKET);
                else if(net.equals("rmi"))
                    new PlayerCLI(RMI);
                break;
            } else if(ui.equals("GUI")){
                if(net.equals("Socket"))
                    new PlayerGUI(SOCKET);
                else if(net.equals("rmi"))
                    new PlayerGUI(RMI);
                break;
            }
            else
                System.out.println("\nInvalid choice, try again");
        }
    }
}
