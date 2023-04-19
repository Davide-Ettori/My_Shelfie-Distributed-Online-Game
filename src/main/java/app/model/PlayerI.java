package app.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerI extends Remote {
    void stampaTerminale(String s) throws RemoteException;
}
