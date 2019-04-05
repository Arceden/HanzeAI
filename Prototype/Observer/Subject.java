package Observer;

import Observer.Observer;

import java.util.ArrayList;

public interface Subject {
    ArrayList<Observer> observers = new ArrayList<>();

    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}
