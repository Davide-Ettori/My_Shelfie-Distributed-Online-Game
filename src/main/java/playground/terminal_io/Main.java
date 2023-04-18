package playground.terminal_io;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main { // questo è il miglior modo di prendere un generico input da terminale il Java
    public static void testBuffer() throws IOException {
        Scanner in = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("vai: ");
        String s;
        s = in.nextLine();
        /*
        try {
            s = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        System.out.println(s);
    }
    public static void cycle(){
        InputStreamReader y = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(y);
        System.out.print("inserisci input: ");
        String s = null;
        new Thread(() ->{
            try {
                String str;
                while (true) {
                    while (System.in.available() == 0) {
                        Thread.sleep(100);
                    }
                    str = in.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        try {
            s = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
    }
    public static void main(String[] args) throws IOException {
        //testBuffer();
        cycle();
        /*
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
        System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        //cycle();
        */
    }
    private static void flushInputBuffer(){
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
