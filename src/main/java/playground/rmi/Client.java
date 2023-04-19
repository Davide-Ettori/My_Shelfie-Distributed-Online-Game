package playground.rmi;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client{
    public static int PORT; // la porta di default RMI è 1099
    private final int id;
    public static void main(String args[]) throws Exception {
        new Client(5555).run();
    }
    public Client(int port){
        this.id = 123456789;
        Client.PORT = port;
    }
    public void run(){ // tutta la logica del client va inserita in questa classe qui
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");

        String name = in.nextLine(); // prendo il nome dell'utente come input da terminale

        try {
            GreetRemoteClient client = new GreetRemoteClient(); // potresti passare this come parametro per
            // modificare il Client direttamente dalla classe GreetRemoteClient (passi la reference)
            Registry registry = LocateRegistry.getRegistry(Client.PORT); // scarico il registry con gli oggetti remoti
            GreetInterfaceServer remoteServerObj = (GreetInterfaceServer) registry.lookup("RMI_Greet"); // prendo l'oggetto che ho creato sul server
            remoteServerObj.setClient(client); // setto questo oggetto come client e lo imposto sull'oggetto remoto del server

            String response = remoteServerObj.getResponse(name); // chiamo la funzione "getResponse" sull'oggetto preso dal server

            System.out.println(response); // visualizzo la risposta a terminale
        }catch(Exception e) {
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}
class GreetRemoteClient extends UnicastRemoteObject implements GreetInterfaceClient { // oggetto utilizzatore lato client
    GreetRemoteClient() throws Exception{
        super();
    }
    public void printExitMessage(String msg) throws Exception{
        System.out.println("\n" + msg);
        this.stopClient();
    }
    public void stopClient() throws Exception {
        System.exit(0); // questa funzione è chiamata dal client quando deve stoppare il server
    }
}