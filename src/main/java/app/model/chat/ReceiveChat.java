package app.model.chat;

import app.controller.Message;
import app.view.TUI.Player;

import static app.controller.MessageType.*;

/**
 * class which represent the thread which listen for received chat message (used by the active player)
 */
public class ReceiveChat extends Thread{
    private final Player player;

    /**
     * normal constructor for this thread
     * @param p the active player that will see those messages
     */
    public ReceiveChat(Player p){player = p;}

    /**
     * the main function of the thread, it reads messages from the inputStream and updates the game chat
     */
    @Override
    public void run(){
        try {
            while (true) {
                Message msg = (Message) player.getInStream().readObject();
                if(msg.getType() == STOP)
                    return;
                System.out.println("\n" + msg.getContent());
                player.addToFullChat((String)msg.getContent());
            }
        }
        catch(Exception e){System.out.println(e);}
    }
}