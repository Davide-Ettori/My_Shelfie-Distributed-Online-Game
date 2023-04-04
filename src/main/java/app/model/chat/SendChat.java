package app.model.chat;

import app.controller.FILEHelper;
import app.model.Player;

import java.util.Scanner;

/**
 * class which represent the thread for keep reading input messages from the terminal (by the NON active users)
 */
public class SendChat extends Thread{
    private final Player player;

    /**
     * the normal constructor for this thread
     * @param p the player that is writing messages on the terminal
     */
    public SendChat(Player p){player = p;}
    /**
     * the main function of the thread, it reads messages from the terminal and send them to the right clients
     */
    @Override
    public void run(){
        try {
            Scanner in = new Scanner(System.in);
            String s;
            while (true) {
                while (System.in.available() == 0) {
                    Thread.sleep(100);
                }
                s = in.nextLine();
                player.sendChatMsg(s);
            }
        }
        catch (InterruptedException e){}
        catch(Exception e){System.out.println(e);}
    }
}