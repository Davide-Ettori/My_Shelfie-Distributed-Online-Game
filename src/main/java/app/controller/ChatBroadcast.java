package app.controller;

public class ChatBroadcast extends Thread{
    private Game game;
    public ChatBroadcast(Game g){game = g;}
    @Override
    public void run(){}
}
