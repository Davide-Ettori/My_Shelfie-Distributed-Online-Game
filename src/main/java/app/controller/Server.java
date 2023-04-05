package app.controller;

import java.awt.*;
import java.util.Scanner;

/**
 * class which represent the entry point for the server
 */
public class Server {
    /**
     * main function, which let the user choose the number of player for the next game
     * @param args terminal arguments passed during execution
     */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String num;
        int numP;
        while (true){
            System.out.print("\nChoose the number of players (2, 3, or 4): ");
            num = in.nextLine();
            if(num.length() == 0)
                num  = "2";
            numP = Integer.parseInt(num);

            if(numP < 2 || numP > 4){
                System.out.println("\nInvalid choice, try again");
                continue;
            }
            new Game(numP);
            break;
        }
    }
}
