package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args){
        try{
            GreetingsInterface client = new Greetings("Hello World"); // creo l'oggetto remoto

            Registry registry = LocateRegistry.getRegistry(); // scarico i registri
            GreetingsInterface server = (GreetingsInterface) registry.lookup("Greet"); // prendo il server dalla rete

        }catch(Exception e){
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}