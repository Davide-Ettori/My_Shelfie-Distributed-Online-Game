package playground.rmi;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

interface GreetInterface{} // non serve a niente, però così posso mettere tutte le interfacce in un solo file

interface GreetInterfaceServer extends Remote { // rendo questa interfaccia remota
    public String getResponse(String search) throws Exception; // specifico tutte le funzioni che voglio chiamare da remoto
    public void stopServer() throws Exception;
}

interface GreetInterfaceClient extends Remote { // rendo questa interfaccia remota
    public void printExitMessage(String msg) throws Exception; // sempre obbligatorio lanciare questa eccezione, la dovrai gestire sul client in futuro
    public void stopClient() throws Exception;
}