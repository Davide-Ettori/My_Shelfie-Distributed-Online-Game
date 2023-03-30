package app.model.player;

import app.model.player.PlayerGUI;

import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        String ui, net;
        Scanner in = new Scanner(System.in);
        System.out.print("\nChoose game mode (CLI or GUI): ");
        ui = in.nextLine();
        System.out.print("\nChoose game mode (Socket or rmi): ");
        net = in.nextLine();
        if(ui.equals("CLI")){
            if(net.equals("Socket"))
                new PlayerCLI("s");
            else if(net.equals("rmi"))
                new PlayerCLI("r");
        } else if(ui.equals("GUI")){
            if(net.equals("Socket"))
                new PlayerGUI("s");
            else if(net.equals("rmi"))
                new PlayerGUI("r");
        }
        else
            System.out.println("\nInvalid choice, try again");
    }
}
