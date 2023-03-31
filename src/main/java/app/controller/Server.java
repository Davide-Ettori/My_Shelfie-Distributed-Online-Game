package app.controller;

import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String ui, num;
        int numP;
        while (true){
            System.out.print("\nChoose game mode (CLI or GUI): ");
            ui = in.nextLine();
            if(ui.length() == 0)
                ui = "CLI";
            System.out.print("\nChoose the number of players (2, 3, or 4): ");
            num = in.nextLine();
            if(num.length() == 0)
                num  = "2";
            numP = Integer.parseInt(num);

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
