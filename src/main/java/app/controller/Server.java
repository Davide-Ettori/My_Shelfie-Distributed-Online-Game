package app.controller;

import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String ui;
        int numP;
        while (true){
            System.out.print("\nChoose game mode (CLI or GUI): ");
            ui = in.nextLine();
            System.out.print("\nChoose game mode (CLI or GUI): ");
            numP = Integer.parseInt(in.nextLine());

            if(numP < 2 || numP > 4){
                System.out.println("\nInvalid choice, try again");
                continue;
            }

            if(ui.equals("CLI")) {
                new GameCLI(numP);
                break;
            }
            else if(ui.equals("GUI")) {
                new GameGUI(numP);
                break;
            }
            else
                System.out.println("\nInvalid choice, try again");
        }
    }
}
