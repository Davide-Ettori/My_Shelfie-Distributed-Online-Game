package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 1099; // porta di default RMI, sul mio PC è l'unica che funziona
    //public static stopRunning(){}
    public static void main(String args[]) {
        try {
            GreetRemote server = new GreetRemote(); // inizializzo l'oggetto remore che userò dal client

            Registry registry = LocateRegistry.createRegistry(PORT); // setto il RMI sulla porta 1099
            registry.rebind("RMI_Greet", server); // chiamo l'oggetto con un nome riconoscibile
            System.out.println("\nRMI pronto sulla porta " + PORT);
            while(!server.stop){
                Thread.sleep(100); // ricontrolla il valore di server.stop ogni 100 ms
            } // il server resta in ascolto, fino a che il client non gli comanda la fine
            // oppure finché non cade la connessione ed esce dal blocco try, ovviamente
            System.out.println("\nChiudo il server...");
            System.exit(0); // chiudo il server
        } catch(Exception e) {
            System.out.println("\nErrore sul server: " + e.toString());
        }
    }
}