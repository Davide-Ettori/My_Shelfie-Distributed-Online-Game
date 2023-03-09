package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

interface GreetingsInterface extends Remote{ // interfaccia, non classe
    public void setClient(Greetings g) throws Exception; // devi sempre aggiungere questa eccezione, in tutte le funzioni
    public GreetingsInterface getClient() throws Exception;
    public void sendToClient(String msg) throws Exception;
    public void sendToServer(String msg) throws Exception;
}

public class Greetings extends UnicastRemoteObject implements GreetingsInterface { // in questo modo, è come se facessi ereditarietà multipla
    private Greetings client; // potresti mettere un array di client nel caso avessi più utenti contemporanemente

    public Greetings() throws Exception{ // anche qui è obbligatoria l'eccezione
        this.client = null;
    }

    public void sendToServer(String msg) throws Exception{
        Server.gotMessage(msg, this.client); // triggero la funzione di ricezione del server, gli dico anche quale client gli ha mandato il messaggio
    }

    public void sendToClient(String msg) throws Exception{
        Client.gotMessage(msg); // triggero la funzione di ricezione del client
    }

    public void setClient(Greetings g){
        this.client = g;
    } // imposto il client che sta comunicando in questo momento

    public Greetings getClient(){
        return this.client;
    } // return il client su cui sto lavorando in questo momento
}