package Network;

import Observer.Observer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Logger implements Observer {
   private TextArea taLog;

   public Logger(TextArea taLog){
       this.taLog=taLog;
   }

    @Override
    public void update(String message) {
        Platform.runLater(()->{
            taLog.appendText(message+"\r\n");
        });
    }
}
