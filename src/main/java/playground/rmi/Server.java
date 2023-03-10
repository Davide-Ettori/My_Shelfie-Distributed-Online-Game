package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 3000; // la porta di default RMI è 1099
    public static void main(String args[]) {
        try {
            GreetRemoteServer server = new GreetRemoteServer(); // inizializzo l'oggetto remore che userò dal client

            Registry registry = LocateRegistry.createRegistry(Server.PORT); // setto il RMI sulla porta 3000
            registry.rebind("RMI_Greet", server); // chiamo l'oggetto con un nome riconoscibile
            System.out.println("\nRMI pronto sulla porta " + Server.PORT);
        } catch(Exception e) {
            System.out.println("\nErrore sul server: " + e.toString());
        }
    }
}
class GreetRemoteServer extends UnicastRemoteObject implements GreetInterfaceServer{ // oggetto remoto lato server
    private GreetInterfaceClient client; // nel caso di più client basterebbe mettere una lista o una mappa al posto di una variabile
    GreetRemoteServer() throws Exception{ // costruttore classico con eventuale logica aggiuntiva e la chiamata al costruttore della superclasse
        super();
        this.client = null;
        // tutta la logica del server va inserita in questa classe qui, queste funzioni saranno quindi chiamate dai vari client
        // simulando una sorta d'interazione peer-to-peer --> tipico uso di RMI, molto diverso dalle socket
    }
    public String getResponse(String name) throws Exception {
        new Thread(() -> { // lancio il thread che tra 2.5 secondi chiuderà la connessione
            try{
                this.closeConnection();
            } catch (Exception e) {System.out.println("\nErrore nel lanciare il thread di chiusura: " + e.toString());}
        }).start();
        System.out.println("\nMessaggio dal client ricevuto correttamente");
        return "Ciao " + name + "!"; // risultato della computazione che verrà utilizzato dal client
    }
    public void closeConnection() throws Exception {
        try {
            if(this.client != null) { // controllo di avere un client connesso
                this.getClient().printExitMessage("La connessione si chiuderà a breve...");
            }
        }catch(Exception e){} // Altrimenti, mi dà errore quando faccio exit sul client. Comunque funziona in ogni caso

        Thread.sleep(2500); // Dopo 2.5 secondi chiudo la connessione remota
        System.out.println("\nChiudo il server...");
        System.exit(0); // chiudo il server
    }
    public void setClient(GreetInterfaceClient client){
        this.client = client;
    }
    public GreetInterfaceClient getClient(){
        return this.client;
    }
}