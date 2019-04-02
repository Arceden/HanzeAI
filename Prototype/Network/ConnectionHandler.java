package Network;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The ConnectionHandler is using the ObserverPattern.
 * The game subscribes for all game related messages upon first login until the player has logged out
 */
public class ConnectionHandler implements ServerSubject {

    private ArrayList<Observer> observers;

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

            return true;

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return false;

    }

    /*
     *  Connection Handling
     */

    public void send(String message){
        toServer.println(message);
        toServer.flush();
    }

    /** Listen for new messages and update all observers with the new message */
    public void listen(){
        new Thread(()->{
            try {
                while (true) {
                    String message = fromServer.readLine();
                    String[] args = message.split(" ");

                    notifyObservers(message);
                }
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
