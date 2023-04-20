package app.controller;

import app.model.PlayerI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameI extends Remote {
    void stampa(String s) throws RemoteException;
    void addClient(String name, PlayerI p) throws RemoteException;
    void redirectToClientRMI(Message msg) throws RemoteException;
    void ping() throws RemoteException;
}
