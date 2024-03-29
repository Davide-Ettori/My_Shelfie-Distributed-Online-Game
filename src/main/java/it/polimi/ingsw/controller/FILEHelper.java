package it.polimi.ingsw.controller;

import java.io.*;

/**
 * helper class for reading and writing txt file, useful for persistence
 * @author Ettori
 */
public class FILEHelper {
    /**
     * write 'SUCC' on status.txt indicating that the server was closed in a normal way
     * @author Ettori
     */
    public static void writeSucc(){
        try {
            new File("status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("status" + Initializer.PORT + ".txt"));
            bw.write("SUCC");
            bw.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * write 'FAIL' on status.txt indicating that the server was closed in a NOT normal way
     * @author Ettori
     */
    public static void writeFail(){
        try {
            new File("status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("status" + Initializer.PORT + ".txt"));
            bw.write("FAIL");
            bw.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * check if there is a cache server from a previous game
     * @author Ettori
     * @return true if there is a cached server
     */
    public static boolean havaCachedServer(){
        try {
            new File("status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("status" + Initializer.PORT + ".txt"));
            return br.readLine().equals("FAIL");
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
            new File("server" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            FileOutputStream fos = new FileOutputStream("server" + Initializer.PORT + ".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(server);
            oos.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * load the current state of the server from the file server.txt
     * @author Ettori
     * @return the state of the old server that was saved (Game Object)
     */
    public static Game loadServer(){
        try {
            new File("server" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        Game server = null;
        try {
            FileInputStream fin = new FileInputStream("server" + Initializer.PORT + ".txt");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fin);
            }catch(Exception e){return null;}
            server = (Game) ois.readObject();
            ois.close();
        }catch(Exception e){throw new RuntimeException(e);}
        return server;
    }
}