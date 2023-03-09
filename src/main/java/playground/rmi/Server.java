package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 1099; // porta di default per RMI
    public static void main(String[] args){
        System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // fisso hostname su localhost
        try{
            System.out.println("inizio");
            PacketImpl pac = new PacketImpl("Hello World"); // creo il pacchetto
            Packet stubPac = (Packet) UnicastRemoteObject.exportObject(pac, 0); // esporto l'oggetto che ho creato poco fa

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", PORT); // apro la connessione sulla porta 5000
            registry.rebind("pacFromServer", stubPac); // ora il client vedrà l'oggetto stubPac con il nome pacFromServer

        }catch(Exception e){
            System.out.println("\nErrore sul server: " + e.toString());
        }
        System.out.println("fine");
    }
}