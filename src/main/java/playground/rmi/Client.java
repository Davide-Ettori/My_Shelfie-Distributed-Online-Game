package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client{
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");
        String name = in.nextLine(); // prendo il nome dell'utente come input da terminale

        try {
            Registry registry = LocateRegistry.getRegistry(); // scarico il registry con gli oggetti remoti
            GreetInterface remoteServerObj = (GreetInterface) registry.lookup("RMI_Greet"); // prendo l'oggetto che ho creato sul server
            String response = remoteServerObj.getResponse(name); // chiamo la funzione "getResponse" sull'oggetto preso dal server

            System.out.println(response); // visualizzo la risposta a terminale
            remoteServerObj.stopServer(); // blocco il server remoto
        }catch(Exception e) {
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}