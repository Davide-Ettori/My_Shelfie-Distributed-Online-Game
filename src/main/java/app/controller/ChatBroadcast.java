package app.controller;

public class ChatBroadcast extends Thread{
    private final Game game;
    private final int index;
    public ChatBroadcast(Game g, int i){game = g; index = i;}
    @Override
    public void run(){
        try {
            while (true) {
                while (game.inStreams.get(index).available() == 0)
                    Thread.sleep(100);
                Message msg = (Message) game.inStreams.get(index).readObject();
                game.sendChatToClients(game.names.get(index), msg.getAuthor(), (String)msg.getContent());
            }
        }
        catch (InterruptedException e){System.out.println("exit with success");}
        catch(Exception e){System.out.println(e);}
    }
}
