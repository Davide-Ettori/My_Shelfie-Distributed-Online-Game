package playground.rmi;

import java.rmi.Remote;

public interface GreetInterface extends Remote { // rendo questa interfaccia remota
    public String getResponse(String search) throws Exception; // specifico tutte le funzioni che voglio chiamare da remoto
    public void stopServer() throws Exception;
}