package app.controller;

import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("\nChoose game mode (CLI or GUI): ");
        String ui = in.nextLine();

        if(ui.equals("CLI"))
            new GameCLI();
        else if(ui.equals("GUI"))
            new GameGUI();
        else
            System.out.println("\nInvalid choice, try again");
    }
}
