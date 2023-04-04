package app.model.chat;

import app.controller.FILEHelper;
import app.model.Player;

import java.util.Scanner;

public class SendChat extends Thread{
    private final Player player;
    public SendChat(Player p){player = p;}
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
                System.out.println(s);
                player.sendChatMsg(s);
            }
        }
        catch (InterruptedException e){System.out.println("exit with success");
            FILEHelper.writeString(player.getName());
        }
        catch(Exception e){System.out.println(e);}
    }
}
