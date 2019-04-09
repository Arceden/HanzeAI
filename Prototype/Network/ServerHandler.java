package Network;

import Observer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler extends ObservationSubject {

    //Server IO
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    int expectingOKCount = 0;
    int OKCount = 0;

    //Threads
    Thread listenerThread;

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
        try {
            listenerThread.interrupt();

            socket.close();
            toServer.close();
            fromServer.close();
        } catch (NullPointerException ex){
            //ignore
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /** Receive all incomming messages */
    void listen(){
        listenerThread = new Thread(()->{
            try {
                while (true) {

                    //Wait until a new message has arrived
                    String message = fromServer.readLine();

                    //In some cases, a message may be null. If so, ignore.
                    if (message==null)
                        continue;

                    String[] args = message.split(" ");

                    switch (args[0]){
                        case "OK":
                            //Message was received and proccessed succesfully
                            OKCount++;
//                            System.out.println("Exected amount ok OK's:"+expectingOKCount+"\tReceived amount of OK's:"+OKCount);
                            break;
                        case "ERR":
                            //Looks like we made a mistake
                            System.out.println(message);
                            break;
                        case "SVR":
                            //Server has sent important data which has to be processed
                            notifyObservers(message);
                            break;
                        default:
                            System.out.println(message);
//                            break;
                    }

                }
            } catch (SocketException ex){

            } catch (IOException ex){
                System.out.println("Stopped listening to the server.");
            }
        });

        listenerThread.start();
    }

    /** Send a message to the server */
    public String send(String message, boolean expectsOK){
        if (expectsOK)
            expectingOKCount++;
        toServer.println(message);
        toServer.flush();
        return "OK";
    }

    public String send(String message){
        return send(message, false);
    }


    /*
     * HELPER COMMANDS
     */

    /** Let the user send a move to the server */
    public void getPlayers(){
        send("get playerlist", true);
    }

    public void getGamelist(){
        send("get gamelist", true);
    }

    public boolean move(int cell) {
        send("move "+cell);

        //Check if move was valid
        return true;
    }

    public void subscribe(String game){
        send("subscribe "+game, true);
    }

    public boolean login(String username){
        send("login "+username, true);
        return true;
    }

    public void logout(){
        send("logout");

        try {
            Thread.sleep(50);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }

        disconnect();
    }

    public Map<String, String> parseData(String prefix, String message){
        message = message.replace(prefix, "");
        message = message.substring(1, message.length()-1);
        String[] valuePairs = message.split(",");

        Map<String, String> data = new HashMap<>();

        for (String pair: valuePairs){
            String[] item = pair.split(": ");
            data.put(item[0].trim(), item[1].trim().substring(1, item[1].length()-1));
        }

        return data;
    }

}
