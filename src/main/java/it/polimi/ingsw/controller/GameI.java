package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * skeleton of the remote server
 * @author Ettori
 */
public interface GameI extends Remote {
    void addClient(String name, PlayerI p) throws RemoteException;
    void redirectToClientRMI(Message msg) throws RemoteException;
    void ping() throws RemoteException;
}
