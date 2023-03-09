package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

interface GreetingsInterface extends Remote{ // interfaccia, non classe
    public void sendMsg(String msg, boolean fromServer) throws Exception; // devi sempre aggiungere questa eccezione
    public void setClient(GreetingsInterface g) throws Exception;
    public GreetingsInterface getClient() throws Exception;
}

public class Greetings extends UnicastRemoteObject implements GreetingsInterface {
    private String msg;
    private GreetingsInterface client;

    public Greetings(String msg) throws Exception{ // anche qui è obbligatoria l'eccezione
        this.msg = msg;
    }

    public void sendMsg(String msg, boolean fromServer){
        if(fromServer){

        }else{

        }
        //this.msg = msg;
    }

    public void setClient(GreetingsInterface g){
        this.client = g;
    }

    public GreetingsInterface getClient(){
        return this.client;
    }
}