package app.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameI extends Remote {
    void stampa(String s) throws RemoteException;
}
