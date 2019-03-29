import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private int port;
    private String address;

    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;


    ServerConnection(String address, int port){
        this.address=address;
        this.port=port;
    }

    void connect() {

        try {
            //Open the socket
            socket = new Socket(address, port);

            //Init the data streams
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    void send(String message){
        try {
            toServer.writeUTF(message+"\n");
            toServer.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
