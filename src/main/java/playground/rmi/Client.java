package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static Greetings getServer() throws Exception{
        Registry registry = LocateRegistry.getRegistry(); // scarico i registri
        Greetings server = (Greetings) registry.lookup("Greet"); // prendo il server dalla rete
        return server;
    }
    public static void gotMessage(String msg) throws Exception{ // questa funzione viene chiamata quando il client riceve un messaggio dal server
        Client.getServer().sendToServer(msg + "!");
    }
    public static void main(String[] args){
        try{
            Greetings client = new Greetings(); // creo l'oggetto remoto
            System.out.println("ciaooqq");
            Greetings server = Client.getServer();
            //System.out.println("eccoo");

            server.setClient(client); // imposto l'oggetto "client" come client e lo userò per comunicare con il server

            Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
            System.out.print("\nInserisci il tuo nome: ");
            String s = in.nextLine(); // prendo il nome dell'utente come input da terminale

            server.sendToServer(s); // mando il mio nome al server
            // ATTENZIONE - l'ultimo client che ha fatto server.setClient(client) sarà quello che il server vede come mittente del messaggio
            // in questo caso c'è un solo client quindi il problema non si pone

        }catch(Exception e){
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}