package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * stub of the remote client
 * @author Ettori
 */
public interface PlayerI extends Remote {
    void sendToServer(Message msg) throws RemoteException;
    void receivedEventRMI(Message msg) throws RemoteException;
    void pingClient() throws RemoteException;
}
