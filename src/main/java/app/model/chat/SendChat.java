package app.model.chat;

import app.model.Player;

public class SendChat extends Thread{
    private Player player;
    public SendChat(Player p){player = p;}
    @Override
    public void run(){}
}
