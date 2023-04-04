package app.model.chat;

import app.controller.Message;
import app.model.Player;

public class ReceiveChat extends Thread{
    private final Player player;
    public ReceiveChat(Player p){player = p;}
    @Override
    public void run(){
        try {
            while (true) {
                Message msg = (Message) player.inStream.readObject();
                System.out.println(msg.getContent());
                player.fullChat += msg.getContent() + "\n";
            }
        }
        catch(Exception e){System.out.println(e);}
    }
}
