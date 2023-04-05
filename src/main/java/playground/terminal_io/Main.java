package playground.terminal_io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main { // questo è il miglior modo di prendere un generico input da terminale il Java
    public static void cycle(){
        while (true){
            Scanner in = new Scanner(System.in);
            flushInputBuffer();
            System.out.println("inserisci input: ");
            String s = in.nextLine();
            System.out.println(s);
        }
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nInserisci il tuo nome: ");
        //String name = in.nextLine(); // prendo il nome dell'utente come input da terminale
        String name = null;
        try {
            name = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nCiao " + name + "!"); // saluto l'utente con il suo nome
        //cycle();
    }
    private static void flushInputBuffer(){
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
