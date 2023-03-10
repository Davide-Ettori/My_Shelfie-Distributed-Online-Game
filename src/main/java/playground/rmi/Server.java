package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 3000; // la porta di default RMI è 1099
    //public static stopRunning(){}
    public static void main(String args[]) {
        try {
            GreetRemoteServer server = new GreetRemoteServer(); // inizializzo l'oggetto remore che userò dal client

            Registry registry = LocateRegistry.createRegistry(Server.PORT); // setto il RMI sulla porta 1099
            registry.rebind("RMI_Greet", server); // chiamo l'oggetto con un nome riconoscibile
            System.out.println("\nRMI pronto sulla porta " + Server.PORT);
            while(!server.stop){
                Thread.sleep(100); // ricontrolla il valore di server.stop ogni 100 ms
            } // il server resta in ascolto, fino a che il client non gli comanda la fine
            // oppure finché non cade la connessione ed esce dal blocco try, ovviamente

            // il seguente codice serve a mandare notifiche, dal server verso il client
            // di solito con RMI si fa solo il contrario, ma nel caso servisse si fa così
            Registry reg = LocateRegistry.getRegistry(Client.PORT); // scarico il registry con gli oggetti remoti
            GreetInterfaceClient remoteClientObj = (GreetInterfaceClient) reg.lookup("Client_1"); // prendo l'oggetto che ho creato sul client
            try {
                remoteClientObj.printExitMessage("Il server si chiuderà a breve...");
            }catch(Exception e){} // Altrimenti, mi dà errore quando faccio exit sul client. Comunque funziona in ogni caso

            Thread.sleep(2500); // Dopo 2.5 secondi chiudo il server. Il client l'ho chiuso già prima
            System.out.println("\nChiudo il server...");
            System.exit(0); // chiudo il server
        } catch(Exception e) {
            System.out.println("\nErrore sul server: " + e.toString());
        }
    }
}

class GreetRemoteServer extends UnicastRemoteObject implements GreetInterfaceServer{ // oggetto remoto lato server
    public boolean stop;
    GreetRemoteServer() throws Exception{ // costruttore classico con eventuale logica aggiuntiva e la chiamata al costruttore della superclasse
        super();
        this.stop = false;
        // tutta la logica del server va inserita in questa classe qui
    }
    public String getResponse(String name) throws Exception {
        return "Ciao " + name + "!"; // risultato della computazione che verrà utilizzato dal server
    }
    public void stopServer() throws Exception{ // stoppo l'ascolto del server
        this.stop = true;
    }
}