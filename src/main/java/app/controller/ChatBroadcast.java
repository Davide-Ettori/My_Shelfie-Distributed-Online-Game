package app.controller;

import static app.controller.MessageType.*;

public class ChatBroadcast extends Thread{
    private final Game game;
    private final int index;
    public ChatBroadcast(Game g, int i){game = g; index = i;}
    @Override
    public void run(){
        try {
            while (true) {
                Message msg = (Message) game.inStreams.get(index).readObject();
                if(msg.getType() == STOP)
                    return;
                System.out.println("trovato da game");
                game.sendChatToClients(game.names.get(index), msg.getAuthor(), (String) msg.getContent());
            }
        }
        catch(Exception e){System.out.println(e);}
    }
}
