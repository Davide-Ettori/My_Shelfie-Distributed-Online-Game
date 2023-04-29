package app.model.chat;

import app.controller.Game;
import app.controller.Message;
import app.model.NetMode;
import app.model.Player;
import app.view.TUI.PlayerTUI;

import java.io.IOException;

import static app.controller.MessageType.*;
import static app.model.NetMode.RMI;

/**
 * class which represent the thread which listen for received chat message (used by the active player)
 */
public class ReceiveChat extends Thread{
    private final PlayerTUI player;

    /**
     * normal constructor for this thread
     * @param p the active player that will see those messages
     */
    public ReceiveChat(PlayerTUI p){player = p;}

    /**
     * the main function of the thread, it reads messages from the inputStream and updates the game chat
     */
    @Override
    public void run(){
        if(player.netMode == RMI)
            return;
        try {
            while (true) {
                Message msg = (Message) player.getInStream().readObject();
                if(msg == null || msg.getType() == STOP)
                    return;
                if(msg.getType() == FINAL_SCORE){
                    System.out.println("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
                    Game.waitForSeconds(5);
                    System.exit(0);
                }
                System.out.println("\n" + msg.getContent());
                player.addToFullChat((String)msg.getContent());
            }
        }
        catch (IOException ignored){}
        catch(Exception e){
            player.connectionLost(e);
        }
    }
}