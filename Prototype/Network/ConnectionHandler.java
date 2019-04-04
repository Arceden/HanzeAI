package Network;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The ConnectionHandler is using the ObserverPattern.
 * The game subscribes for all game related messages upon first login until the player has logged out
 * TODO: Return boolean on the send() method. True if server returns OK, False if server returns ERR <message>
 */
public class ConnectionHandler implements ServerSubject {

    private ArrayList<Observer> observers;
    private Queue<String> outgoing = new LinkedList<>();
    private Queue<String> incomming = new LinkedList<>();

    private int port;
    private String address;

    private Socket socket;
    private BufferedReader fromServer;
    private PrintWriter toServer;

    public ConnectionHandler(){
        observers = new ArrayList<Observer>();
    }

    public boolean connect(String address, int port){
        this.address=address;
        this.port=port;

        try {
            //Open socket
            socket = new Socket(address, port);

            //Init the data streams
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream());

            messageEmitter();
            listen();

            return true;

        } catch (ConnectException ex){
            System.err.println(ex.getMessage());
            return false;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;

    }

    /*
     *  Connection Handling
     *  Add the message to the outgoing queue
     */
    public void send(String message){
        outgoing.add(message);
    }

    /**
     *  Emit the message to the server and wait for a response.
     *  Do not send another command until the previous command has been acknowledged
     */
    private void messageEmitter(){
        new Thread(()->{
            try {
                while (true){
                    //If there are any outgoing messages in the queue
                    if(outgoing.size()>0){
                        String message = outgoing.remove();
                        toServer.println(message);
                        toServer.flush();

                        while (incomming.size()==0){
                            Thread.sleep(50);
                        }

                        //Return the message to the send method somehow
                        System.out.println(message+" - "+incomming.remove());
                    } else {
                        //Dont push it too hard. The outgoing queue size function needs its time to think
                        Thread.sleep(50);
                    }
                }
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    /** Listen for new messages and update all observers with the new message */
    private void listen(){
        new Thread(()->{
            try {
                while (true) {
                    String message = fromServer.readLine();
                    String[] args = message.split(" ");

                    switch (args[0]){
                        case "OK":  incomming.add(message); break;
                        case "ERR": incomming.add(message); break;
                        default:
                            notifyObservers(message);
                            break;
                    }

                }
            } catch (NullPointerException ex){
                //Connection lost?
                Thread.currentThread().interrupt();
                System.err.println("Connection lost!");
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }).start();
    }


    /*
     *  Observers
     */

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i>=0)
            observers.remove(i);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer: observers)
            observer.update(message);
    }

    //Perform try catches to avoid disconnecting and to avoid any other issues

}
