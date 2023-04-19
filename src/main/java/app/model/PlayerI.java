package app.model;

import app.controller.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerI extends Remote {
    void stampaTerminale(String s) throws RemoteException;
    void sendToServer(Message msg) throws RemoteException;
    void receivedEventRMI(Message msg) throws RemoteException;
}
