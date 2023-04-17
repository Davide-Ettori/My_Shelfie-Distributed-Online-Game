package app.model.chat;

import app.view.TUI.PlayerTUI;

import java.io.BufferedReader;

/**
 * class which represent the thread for keep reading input messages from the terminal (by the NON active users)
 */
public class SendChat extends Thread{
    private final PlayerTUI player;
    private final BufferedReader br;

    /**
     * the normal constructor for this thread
     * @param p the player that is writing messages on the terminal
     */
    public SendChat(PlayerTUI p, BufferedReader brPlayer){player = p; br = brPlayer;}
    /**
     * the main function of the thread, it reads messages from the terminal and send them to the right clients
     */
    @Override
    public void run(){
        try {
            String s;
            while (true) {
                while (System.in.available() == 0) {
                    Thread.sleep(100);
                }
                s = br.readLine();
                player.sendChatMsg(s);
            }
        }
        catch (InterruptedException e){}
        catch(Exception e){player.connectionLost(e);}
    }
}