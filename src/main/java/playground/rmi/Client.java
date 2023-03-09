package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args){
        try{
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", Server.PORT); // connetto il client all'indirizzo del server
            Packet responsePac = (Packet) registry.lookup("pacFromServer"); // prendo l'oggetto che mi serve, occhio a mettere il nome giusto (nome dato dal server)

            String msg = responsePac.getMsg(); // leggo il messaggio contenuto nel pacchetto che ho richiesto dal server
            System.out.println("\nIl messaggio del server è: " + msg);

        }catch(Exception e){
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}