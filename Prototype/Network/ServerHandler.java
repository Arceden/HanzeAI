package Network;

import Observer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends ObservationSubject {

    //Server IO
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    public ServerHandler(){

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

            return true;

        } catch (IOException ex){
            //Send the error message to the observers
            notifyObservers("STATUS "+ex.getMessage());
        }

        return false;
    }

    /** Close the connection by closing the sockets and the data streams */
    public void disconnect(){

    }

    /** Receive all incomming messages */
    void listen(){

    }

    /** Send a message to the server */
    public String send(String message){
        return null;
    }




    /*
     * HELPER COMMANDS
     */

    public String getPlayers(){
        return null;
    }

    public String getGamelist(){
        return null;
    }

    public boolean login(){
        return false;
    }

    public void logout(){

    }

}
