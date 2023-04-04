package app.model.chat;

import app.controller.Message;
import app.model.Player;

public class ReceiveChat extends Thread{
    private final Player player;
    public ReceiveChat(Player p){player = p;}
    @Override
    public void run(){
        try {
            while (!isInterrupted()) {
                new Thread(() ->{
                    //player.inStream.set
                }).start();
                Thread.sleep(100);
            }
        }
        catch(Exception e){System.out.println(e);}
    }
}
