package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

interface GreetingsInterface extends Remote{ // interfaccia, non classe
    //public String getMsg() throws Exception; // devi sempre aggiungere questa eccezione
    public void getMsg() throws Exception;
}

public class Greetings extends UnicastRemoteObject implements GreetingsInterface {
    private String msg;

    public Greetings(String msg) throws Exception{ // anche qui è obbligatoria l'eccezione
        this.msg = msg;
    }

    public void getMsg() throws Exception{
        System.out.println(this.msg);
        //return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}