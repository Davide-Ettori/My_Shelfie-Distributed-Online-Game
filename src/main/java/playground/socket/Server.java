package playground.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 3000;
    public static void main(String[] args) throws Exception{
        new Server(); // inizializzo il server
    }
    public Server() throws Exception{ // eccezione lanciata dalla "new ServerSocket(PORT)" se la porta è occupata
        ServerSocket serverSocket = new ServerSocket(PORT); // scelgo la porta su localhost
        System.out.println("\nServer attivo sulla porta " + PORT);
        Socket socket = serverSocket.accept(); // accetto una nuova connessione dal client - BLOCCANTE
        System.out.println("\nClient connesso con successo");

        // inizializzo le stream di input e output
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        Packet pac = (Packet)inStream.readObject(); // leggo ciò che mi manda il client - BLOCCANTE
        System.out.println("\nIl client ti ha mandato il suo nome: " + pac.getMsg());

        Packet responsePac = new Packet("Ciao " + pac.getMsg() + "!"); // creo il pacchetto di risposta
        outStream.writeObject(responsePac); // mando la risposta

        serverSocket.close(); // chiudo il server, nel caso reale lo dovremo tenere aperto in un ciclo while finché dura la partita
    }
}