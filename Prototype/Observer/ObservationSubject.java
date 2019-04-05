package Observer;

import java.util.ArrayList;

public class ObservationSubject implements Subject {

    private ArrayList<Observer> observers = new ArrayList<>();

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

}
