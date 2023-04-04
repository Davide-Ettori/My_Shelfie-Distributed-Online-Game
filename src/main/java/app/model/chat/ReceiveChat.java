package app.model.chat;

import app.model.Player;

public class ReceiveChat extends Thread{
    private Player player;
    public ReceiveChat(Player p){player = p;}
    @Override
    public void run(){}
}
