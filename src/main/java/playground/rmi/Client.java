package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args){
        try{
            Greetings client = new Greetings("Hello World"); // creo l'oggetto remoto

            Registry registry = LocateRegistry.getRegistry(); // scarico i registri
            Greetings server = (Greetings) registry.lookup("Greet"); // prendo il server dalla rete

            server.setClient(client); // imposto l'oggetto "client" come client e lo userò per comunicare con il server

        }catch(Exception e){
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}