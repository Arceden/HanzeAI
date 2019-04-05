package Network;

import Observer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerHandler extends ObservationSubject {

    //Server IO
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    //Running booleans
    private boolean connected;

    Thread listenerThread;

    public ServerHandler(){
        connected=false;
    }

    /** Start the actual connection */
    public boolean connect(String address, int port){

        try {
            //Open socket
            socket = new Socket(address, port);

            //Init the data streams
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream());

            //Start the server listener
            listen();

            //Notify the state of the connection
            notifyObservers("STATUS Connected");

            connected = true;
            return true;

        } catch (IOException ex){
            //Send the error message to the observers
            notifyObservers("STATUS "+ex.getMessage());
        }

        return false;
    }

    /** Close the connection by closing the sockets and the data streams */
    public void disconnect(){
        try {
            connected=false;
            socket.close();
            toServer.close();
            fromServer.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /** Receive all incomming messages */
    void listen(){
        listenerThread = new Thread(()->{
            try {
                while (true) {

                    //If the connected variable is false, stop this thread.
                    if (!connected)
                        Thread.currentThread().interrupt();

                    String message = fromServer.readLine();
                    System.out.println(message);

                }
            } catch (SocketException ex){
                System.out.println("Stopped listening to the server.");
            } catch (IOException ex){
                ex.printStackTrace();
            }
        });

        listenerThread.start();
    }

    /** Send a message to the server */
    public String send(String message, boolean expectsOK, boolean expectsSecondaryMessage){
        toServer.println(message);
        toServer.flush();
        return "OK";
    }

    public String send(String message, boolean expectsOK){
        return send(message, expectsOK, false);
    }

    public String send(String message){
        return send(message, false);
    }




    /*
     * HELPER COMMANDS
     */

    public String getPlayers(){
        return send("get playerlist", true, true);
    }

    public String getGamelist(){
        return send("get gamelist", true, true);
    }

    public boolean login(String username){
        send("login "+username, true);
        return false;
    }

    public void logout(){
        send("logout");
        disconnect();
    }

}
