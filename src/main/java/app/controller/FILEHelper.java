package app.controller;

import java.io.*;

/**
 * helper class for reading and writing txt file, useful for persistence
 * @author Ettori
 */
public class FILEHelper {
    /**
     * write 'SUCC' on status.txt indicating tha the server closed in a normal way
     * @author Ettori
     */
    public static void writeSucc(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status.txt"));
            bw.write("SUCC");
            bw.close();
        }catch(Exception e){System.out.println(e);}
    }
    /**
     * write 'FAIL' on status.txt indicating tha the server closed in a NOT normal way
     * @author Ettori
     */
    public static void writeFail(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status.txt"));
            bw.write("FAIL");
            bw.close();
        }catch(Exception e){System.out.println(e);}
    }
    /**
     * check if there is a cache server from a previous game
     * @author Ettori
     * @return true iff there is a cached server
     */
    public static boolean havaCachedServer(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/app/controller/cache/status.txt"));
            return br.readLine().equals("FAIl");
        }catch(Exception e){System.out.println();}
        return false;
    }
    /**
     * save the current state of the server in the file server.txt
     * @author Ettori
     * @param server the current server that will be saved
     */
    public static void writeServer(Game server){
        try {
            FileOutputStream fos = new FileOutputStream("src/main/java/app/controller/cache/server.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(server);
            oos.close();
        }catch(Exception e){System.out.println(e);}
    }
    /**
     * load the current state of the server from the file server.txt
     * @author Ettori
     * @return the state of the old server that was saved (Game Object)
     */
    public static Game loadServerCLI(){
        Game server = null;
        try {
            FileInputStream fin = new FileInputStream("src/main/java/app/controller/cache/server.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            server = (Game) ois.readObject();
            ois.close();
        }catch(Exception e){System.out.println(e);}
        return server;
    }
}