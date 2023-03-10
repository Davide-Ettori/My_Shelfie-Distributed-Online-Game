package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

public interface GreetInterface extends Remote { // rendo questa interfaccia remota
    public String getResponse(String search) throws Exception; // specifico tutte le funzioni che voglio chiamare da remoto
    public void stopServer() throws Exception;
}

class GreetRemote extends UnicastRemoteObject implements GreetInterface{
    public boolean stop;
    GreetRemote() throws Exception{ // costruttore classico con eventuale logica aggiuntiva e la chiamata al costruttore della superclasse
        super();
        this.stop = false;
        // tutta la logica del server va inserita in questa classe qui
    }
    public String getResponse(String name) throws Exception {
        return "Ciao " + name + "!"; // risultato della computazione che verrà utilizzato dal server
    }
    public void stopServer(){ // stoppo l'ascolto del server
        this.stop = true;
    }
}