package playground.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in); // inizializzo uno scanner sul terminale
        System.out.print("\nInserisci il tuo nome: ");
        String s = in.nextLine(); // prendo il nome dell'utente come input da terminale

        Packet sendPac = new Packet(s);

        new Client(sendPac);
    }

    public Client(Packet sendPac) throws Exception{
        Socket socket = new Socket("127.0.0.1", Server.PORT); // mi connetto al server, sulla stessa porta

        // inizializzo le stream di input e output
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        outStream.writeObject(sendPac); // mando il mio pacchetto (ovvero il mio nome) al server
        Packet responsePackageSever = (Packet) inStream.readObject(); // ricevo la risposta - BLOCCANTE

        System.out.println("\nIl server ha risposto: " + responsePackageSever.getMsg()); // visualizzo la risposta del server a terminale

        socket.close(); // chiude il client
        outStream.flush(); // ripulisco il buffer di comunicazione (in rete) con il sever
        outStream.close(); // chiude la stream di comunicazione con il server
    }
}
