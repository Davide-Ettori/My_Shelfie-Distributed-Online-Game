package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 3000; // porta di default per RMI
    public static void greet(Greetings server, String msg) throws Exception {
        server.getClient().sendMsg("Ciao " + msg, true);
    }
    public static void main(String[] args){
        try{
            Greetings server = new Greetings("Hello World"); // creo l'oggetto remoto
            Registry registry = LocateRegistry.createRegistry(PORT); // istanzio un registry sulla porta 5000
            registry.rebind("Greet", server); // pubblico l'oggetto remoto con l'identificatore "Greet"

            System.out.println("\nRMI attivo sulla porta " + PORT);


        }catch(Exception e){
            System.out.println("\nErrore sul server: " + e.toString());
        }
        System.out.println("fine");
    }
}