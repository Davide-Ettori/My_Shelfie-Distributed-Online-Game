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
            new File("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt"));
            bw.write("SUCC");
            bw.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * write 'FAIL' on status.txt indicating tha the server closed in a NOT normal way
     * @author Ettori
     */
    public static void writeFail(){
        try {
            new File("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt"));
            bw.write("FAIL");
            bw.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * check if there is a cache server from a previous game
     * @author Ettori
     * @return true iff there is a cached server
     */
    public static boolean havaCachedServer(){
        try {
            new File("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/app/controller/cache/status" + Initializer.PORT + ".txt"));
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
            new File("src/main/java/app/controller/cache/server" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        try {
            FileOutputStream fos = new FileOutputStream("src/main/java/app/controller/cache/server" + Initializer.PORT + ".txt");
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
            new File("src/main/java/app/controller/cache/server" + Initializer.PORT + ".txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File System Corrupted");
        }
        Game server = null;
        try {
            FileInputStream fin = new FileInputStream("src/main/java/app/controller/cache/server" + Initializer.PORT + ".txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            server = (Game) ois.readObject();
            ois.close();
        }catch(Exception e){throw new RuntimeException(e);}
        return server;
    }
    /**
     * write helper for testing
     * @author Ettori
     */
    public static void writeString(String s){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/test.txt"));
            bw.write(s);
            bw.close();
        }catch(Exception e){throw new RuntimeException(e);}
    }
}