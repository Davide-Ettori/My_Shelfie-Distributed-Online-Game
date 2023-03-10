package playground.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client{
    public static final int PORT = 5555;
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");
        String name = in.nextLine(); // prendo il nome dell'utente come input da terminale

        try {
            GreetRemoteClient client = new GreetRemoteClient();

            Registry registry = LocateRegistry.getRegistry(Server.PORT); // scarico il registry con gli oggetti remoti
            GreetInterfaceServer remoteServerObj = (GreetInterfaceServer) registry.lookup("RMI_Greet"); // prendo l'oggetto che ho creato sul server
            String response = remoteServerObj.getResponse(name); // chiamo la funzione "getResponse" sull'oggetto preso dal server

            remoteServerObj.setClient(client); // setto questo oggetto come client e lo imposto sull'oggetto remoto del server

            System.out.println(response); // visualizzo la risposta a terminale
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