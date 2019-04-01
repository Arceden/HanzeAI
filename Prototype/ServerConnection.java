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
//            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    void listen(){
        new Thread(()->{

            while(socket.isConnected()) {
                try {
                    System.out.println(fromServer.readUTF());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Did i just disconnect?");

        }).start();
    }

    void login(String username){
        send("login "+username);
    }

    void send(String message){
        try {
            toServer.writeUTF(message+"\n");
            toServer.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    String read(){
        try {
            return fromServer.readUTF();
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    boolean isConnected(){
        if(!socket.isConnected()) return false;
        // try to send and receive a message
        return true;
    }

}
