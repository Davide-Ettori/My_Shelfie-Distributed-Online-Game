package playground.socket;

import java.io.Serializable;

public class Packet implements Serializable { // così java capisce che questo oggetto va serializzato
    private String msg;
    public Packet(String msg){
        this.msg = msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    } // setto il messaggio da mandare
    public String getMsg(){
        return this.msg;
    } // leggo il messaggio che ho spedito
}
