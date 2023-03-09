package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

interface GreetingsInterface extends Remote{ // interfaccia, non classe
    public String getMsg() throws Exception; // devi sempre aggiungere questa eccezione
    public void setMsg(String msg) throws Exception;
    public void setClient(GreetingsInterface g) throws Exception;
    public GreetingsInterface getClient() throws Exception;
}

public class Greetings extends UnicastRemoteObject implements GreetingsInterface {
    private String msg;
    private GreetingsInterface client;

    public Greetings(String msg) throws Exception{ // anche qui è obbligatoria l'eccezione
        this.msg = msg;
    }

    public String getMsg() throws Exception{
        //System.out.println(this.msg);
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setClient(GreetingsInterface g){
        this.client = g;
    }

    public GreetingsInterface getClient(){
        return this.client;
    }
}