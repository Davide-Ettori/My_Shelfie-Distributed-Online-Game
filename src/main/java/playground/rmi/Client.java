package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client{
    public static final int PORT = 5555;
    public static void main(String args[]) throws Exception {
        GreetRemoteClient client = new GreetRemoteClient(); // inizializzo l'oggetto remoto che userò dal server
        Registry reg = LocateRegistry.createRegistry(Client.PORT); // setto il RMI sulla porta 1098
        reg.rebind("Client_1", client); // chiamo l'oggetto con un nome riconoscibile
        // le istruzioni sopra rendono il client simile a un server, così posso mandare delle notifiche da remoto, non è necessario
        // spesso in RMI si implementa solo la comunicazione da client verso server e non il viceversa

        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");
        String name = in.nextLine(); // prendo il nome dell'utente come input da terminale

        try {
            Registry registry = LocateRegistry.getRegistry(Server.PORT); // scarico il registry con gli oggetti remoti
            GreetInterfaceServer remoteServerObj = (GreetInterfaceServer) registry.lookup("RMI_Greet"); // prendo l'oggetto che ho creato sul server
            String response = remoteServerObj.getResponse(name); // chiamo la funzione "getResponse" sull'oggetto preso dal server

            System.out.println(response); // visualizzo la risposta a terminale
            remoteServerObj.stopServer(); // blocco il server remoto
        }catch(Exception e) {
            System.out.println("\nErrore sul client: " + e.toString());
        }
    }
}

class GreetRemoteClient extends UnicastRemoteObject implements GreetInterfaceClient{ // oggetto remoto lato client
    GreetRemoteClient() throws Exception{
        super();
        // tutta la logica del client va inserita in questa classe qui, se vuoi che riceva le notifiche dal server
    }
    public void printExitMessage(String msg) throws Exception{
        System.out.println("\n" + msg);
        this.stopClient();
    }

    public void stopClient() throws Exception {
        System.exit(0); // questa funzione è chiamata dal client quando deve stoppare il server
    }
}