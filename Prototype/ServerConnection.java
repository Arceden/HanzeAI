import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private int port;
    private String address;

    private Socket socket;
    private BufferedReader fromServer;
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
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    void listen(){
        new Thread(()->{

            while(socket.isConnected()) {
                try {
                    String message = fromServer.readLine();

                    String[] args = message.split(" ");
                    switch (args[0]){
                        case "OK": serverLogger("ok", message); break;
                        case "ERR": serverLogger("ERROR", message); break;
                        case "SVR": serverLogger("Server", message); break;
                        default: serverLogger("?", message);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Did i just disconnect?");

        }).start();
    }

    private void serverLogger(String prefix, String message){
        System.out.println("[Server "+prefix+"]\t\t"+message);
    }

    void login(String username){
        send("login "+username);
    }

    void getGameList() {
        send("get gamelist");
    }

    void getPlayerList(){
        send("get playerlist");
    }

    void send(String message){
        try {
            System.out.println("[SERVER to]\t"+message);
            toServer.writeUTF(message+"\n\r");
            toServer.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    boolean isConnected(){
        if(!socket.isConnected()) return false;
        // try to send and receive a message
        return true;
    }

}
