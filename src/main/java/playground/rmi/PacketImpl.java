package playground.rmi;

import java.rmi.Remote;

interface Packet extends Remote{ // interfaccia, non classe
    public String getMsg() throws Exception; // devi sempre aggiungere questa eccezione
}

public class PacketImpl implements Packet{
    private String msg;

    public PacketImpl(String msg) throws Exception{ // anche qui è obbligatoria l'eccezione
        this.msg = msg;
    }

    public String getMsg() throws Exception{
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}