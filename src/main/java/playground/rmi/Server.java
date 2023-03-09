package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class Server {
    public static final int PORT = 1099; // porta di default per RMI, per qualche motivo magico funziona solo su questa porta
    public static boolean stop = false;

    public static void gotMessage(String msg, Greetings client) throws Exception{ // questa funzione viene chiamata quando il server riceve un messaggio da un client
        if(client == null)
            return;
        if(msg.equals("end")){
            Server.stop = true;
            return;
        }
        client.sendToClient("Ciao " + msg); // rimando al client il messaggio completo
    }
    public static void main(String[] args){
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        try{
            Greetings server = new Greetings(); // creo l'oggetto remoto
            Registry registry = LocateRegistry.createRegistry(PORT); // istanzio un registry sulla porta 5000
            registry.rebind("Greet", server); // pubblico l'oggetto remoto con l'identificatore "Greet"

            System.out.println("\nRMI attivo sulla porta " + PORT);

            while(!Server.stop){} // questo ciclo si ferma solo se arriva una eccezione, tipo se cade la connessione lato server
            // oppure quando qualche client gli manda un messaggio di fine connessione
            // si potrebbe mettere un'istruzione che stoppa il ciclo quando tutti i client si disconnettono

        }catch(Exception e){
            System.out.println("\nErrore sul server: " + e.toString());
        }
        System.out.println("fine");
    }
}
// ogni client (e il server) ha il suo oggetto Greetings grazie al comunica con l'esterno. Qui sto mandando stringhe, ma in realtà puoi mandare qualsiasi oggetto, come nelle socket