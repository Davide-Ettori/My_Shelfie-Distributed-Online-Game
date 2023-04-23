package app.controller;

import java.io.IOException;

import static app.controller.MessageType.*;

/**
 * class which represents the threads for listening to the eventual chat messages for ALL the NON active players
 */
public class ChatBroadcast extends Thread{
    private final Game game;
    private final int index;

    /**
     * normal constructor for this type of objects
     * @param g the server object that is handling the game
     * @param i the index of the client that this thread is listening to
     */
    public ChatBroadcast(Game g, int i){game = g; index = i;}

    /**
     * main function of the thread, it listens for chat messages and send them back to the interested clients
     */
    @Override
    public void run(){
        //System.out.println("SERVER ASCOLTA CHAT");
        while (true) {
            Message msg = null;
            try {
                msg = (Message) game.getInStreams().get(index).readObject();
            } catch (IOException | ClassNotFoundException e) {
                game.playerDisconnected(index);
                return;
            }
            if(msg == null)
                return;
            if(msg.getType() == PING)
                continue;
            if(msg.getType() == STOP)
                return;
            //System.out.println("\nRICEVUTO MEX - " + msg.getContent());
            game.sendChatToClients(game.getNames().get(index), msg.getAuthor(), (String) msg.getContent());
        }
    }
}