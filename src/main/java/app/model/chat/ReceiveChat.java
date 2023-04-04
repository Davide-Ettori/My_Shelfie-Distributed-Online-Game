package app.model.chat;

import app.controller.Message;
import app.model.Player;

import static app.controller.MessageType.STOP;

public class ReceiveChat extends Thread{
    private final Player player;
    public ReceiveChat(Player p){player = p;}
    @Override
    public void run(){
        try {
            while (true) {
                Message msg = (Message) player.inStream.readObject();
                if(msg.getType() == STOP)
                    return;
                System.out.println(msg.getContent());
                player.fullChat += msg.getContent();
            }
        }
        catch(Exception e){System.out.println(e);}
    }
}
