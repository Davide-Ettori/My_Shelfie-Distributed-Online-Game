package app.controller;

import java.io.*;

public class FILEHelper {
    public static void writeSucc(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status.txt"));
            bw.write("SUCC");
            bw.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static void writeFail(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/app/controller/cache/status.txt"));
            bw.write("FAIL");
            bw.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static boolean havaCachedServer(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/app/controller/cache/status.txt"));
            return br.readLine().equals("FAIl");
        }catch(Exception e){System.out.println();}
        return false;
    }
    public static void writeServer(GameCLI server){
        try {
            FileOutputStream fos = new FileOutputStream("src/main/java/app/controller/cache/server.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(server);
            oos.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static GameCLI loadServerCLI(){
        GameCLI server = null;
        try {
            FileInputStream fin = new FileInputStream("src/main/java/app/controller/cache/server.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            server = (GameCLI) ois.readObject();
            ois.close();
        }catch(Exception e){System.out.println(e);}
        return server;
    }
}