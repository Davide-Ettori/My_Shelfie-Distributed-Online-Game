package playground.thread;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Thread t = new ProvaThread("davide");
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }
}

class ProvaThread extends Thread{
    String name;
    public ProvaThread(String n){
        name = n;
    }
    @Override
    public void run(){
        try {
            Scanner in = new Scanner(System.in);
            String s;
            while (true) {
                System.out.println("Inserisci input (" + name + "): ");
                while (System.in.available() == 0) // stessa cosa con inStream.available() == 0
                    Thread.sleep(100);
                s = in.nextLine();
                System.out.println(s);
            }
        }
        catch (InterruptedException e){System.out.println("exit with success");}
        catch(Exception e){System.out.println(e);}
    }
}