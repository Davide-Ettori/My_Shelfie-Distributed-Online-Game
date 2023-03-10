package playground.terminal_io;

import java.util.Scanner;

public class Main { // questo è il miglior modo di prendere un generico input da terminale il Java
    public static void main(String[] args){
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");
        String name = in.nextLine(); // prendo il nome dell'utente come input da terminale

        System.out.println("\nCiao " + name + "!"); // saluto l'utente con il suo nome
    }
}
