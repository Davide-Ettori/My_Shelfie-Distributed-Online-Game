package it.polimi.ingsw.chat;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.controller.Message;
import it.polimi.ingsw.view.TUI.PlayerTUI;
import it.polimi.ingsw.controller.MessageType;

import java.io.IOException;

import static it.polimi.ingsw.model.NetMode.RMI;

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
                //in the following case the message is use to catch event in the game
                Message msg = (Message) player.getInStream().readObject();
                if(msg == null || msg.getType() == MessageType.STOP)
                    return;
                if(msg.getType() == MessageType.FINAL_SCORE){
                    System.out.println("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
                    Game.waitForSeconds(Game.showTimer);
                    System.exit(0);
                }
                if(msg.getType() == MessageType.LOST_CLIENT){
                    player.handleLostClientEvent(msg);
                    continue;
                }
                if(msg.getType() == MessageType.SHOW_EVENT){
                    if(msg.getAuthor() != null && msg.getAuthor().equals("win")){
                        System.out.println("\n" + msg.getContent());
                        Game.waitForSeconds(Game.waitTimer);
                        System.exit(0);
                    }
                    System.out.println("\n" + msg.getContent());
                    continue;
                }
                //IN this case the message is a message in the chat
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