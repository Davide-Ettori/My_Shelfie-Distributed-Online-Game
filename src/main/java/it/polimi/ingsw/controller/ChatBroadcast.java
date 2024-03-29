package it.polimi.ingsw.controller;

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
        while (true) {
            Message msg = null;
            try {
                msg = (Message) game.getInStreams().get(index).readObject();
            } catch (Exception e) {
                game.playerDisconnected(index, e);
                return;
            }
            if(msg == null)
                return;
            if(msg.getType() == MessageType.PING)
                continue;
            if(msg.getType() == MessageType.STOP)
                return;
            game.sendChatToClients(game.getNames().get(index), msg.getAuthor(), (String) msg.getContent());
        }
    }
}